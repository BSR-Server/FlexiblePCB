package heyblack.flexiblepcb.command;

import carpet.settings.SettingsManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import heyblack.flexiblepcb.FlexiblePCBSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.server.ServerTask;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.math.BlockPos;

import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;

public class RemoveBlockCommand {

    private static final LiteralArgumentBuilder<ServerCommandSource> COMMAND =
            literal("removeblock").
                    requires(s -> SettingsManager.canUseCommand(s, FlexiblePCBSettings.commandRemoveBlock)).
                    then(argument("pos", BlockPosArgumentType.blockPos()).
                            executes(it -> {
                                handleRemoveBlock(
                                        it.getSource(),
                                        BlockPosArgumentType.getLoadedBlockPos(it, "pos"),
                                        null
                                );
                                return 0;
                            }).
                            then(literal("replacewith").
                                    then(argument("block", BlockStateArgumentType.blockState()).
                                            executes(it -> {
                                                handleRemoveBlock(
                                                        it.getSource(),
                                                        BlockPosArgumentType.getLoadedBlockPos(it, "pos"),
                                                        BlockStateArgumentType.getBlockState(it, "block").getBlockState()
                                                );
                                                return 0;
                                            })
                                    )
                            )
                    );

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(COMMAND);
    }

    private static void handleRemoveBlock(ServerCommandSource src, BlockPos pos, BlockState newState) {
        src.getMinecraftServer().send(new ServerTask(src.getMinecraftServer().getTicks(), () -> {
            try {
                ServerPlayerEntity player = src.getPlayer();
                ServerWorld level = player.getServerWorld();
                BlockState bs = level.getBlockState(pos);
                BlockEntity e = level.getBlockEntity(pos);
                level.setBlockState(
                        pos,
                        newState != null ? newState : Blocks.AIR.getDefaultState(),
                        130,
                        0
                );
                level.removeBlockEntity(pos);
                if (e != null) {
                    e.cancelRemoval();
                    level.setBlockEntity(pos, e);
                }
                if (newState == null) {
                    src.sendFeedback(
                            Text.of("Removed ").copy().append(bs.getBlock().getName()),
                            true
                    );
                } else {
                    src.sendFeedback(
                            Text.of("Replaced ")
                                    .copy()
                                    .append(bs.getBlock().getName())
                                    .append(" with ")
                                    .append(level.getBlockState(pos).getBlock().getName()),
                            true
                    );
                }
            } catch (CommandSyntaxException e) {
                src.sendError(Texts.toText(e.getRawMessage()));
            }
        }));
    }
}
