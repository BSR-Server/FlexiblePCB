package heyblack.flexiblepcb.command;

import carpet.settings.SettingsManager;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import heyblack.flexiblepcb.FlexiblePCBSettings;
import heyblack.flexiblepcb.mixin.command.commandUpdateBlock.BlockStateArgumentAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Set;

public class UpdateBlockCommand
{
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("updateBlock")
                .requires((player) -> SettingsManager.canUseCommand(player, FlexiblePCBSettings.commandUpdateBlock))
                .then(CommandManager.literal("neighbor_changed")
                        .then(
                                CommandManager.argument("from", BlockPosArgumentType.blockPos())
                                .then(CommandManager.argument("to", BlockPosArgumentType.blockPos())
                                        // update every block in area when no target is assigned
                                        .executes(context -> UpdateBlockCommand.updateNC(
                                                context.getSource().getPlayer(),
                                                new BlockBox(
                                                        BlockPosArgumentType.getLoadedBlockPos(context, "from"),
                                                        BlockPosArgumentType.getLoadedBlockPos(context, "to")
                                                ),
                                                // nobody needs to update a structure void right?
                                                null,
                                                false
                                        ))

                                        // update the assigned type of block only
                                        .then(CommandManager.argument("target", BlockStateArgumentType.blockState())
                                                .executes(context -> UpdateBlockCommand.updateNC(
                                                        context.getSource().getPlayer(),
                                                        new BlockBox(
                                                                BlockPosArgumentType.getLoadedBlockPos(context, "from"),
                                                                BlockPosArgumentType.getLoadedBlockPos(context, "to")
                                                        ),
                                                        BlockStateArgumentType.getBlockState(context,"target"),
                                                        true
                                                ))
                                        )
                                )
                        )
                )

                .then(CommandManager.literal("post_placement")
                        .then(CommandManager.argument("from", BlockPosArgumentType.blockPos())
                                .then(CommandManager.argument("to", BlockPosArgumentType.blockPos())
                                        // update every block in area when no target is assigned
                                        .executes(context -> UpdateBlockCommand.updatePP(
                                                context.getSource().getPlayer(),
                                                new BlockBox(
                                                        BlockPosArgumentType.getLoadedBlockPos(context, "from"),
                                                        BlockPosArgumentType.getLoadedBlockPos(context, "to")
                                                ),
                                                null
                                        ))

                                        // update the assigned type of block only
                                        .then(CommandManager.argument("target", BlockStateArgumentType.blockState())
                                                .executes(context -> UpdateBlockCommand.updatePP(
                                                        context.getSource().getPlayer(),
                                                        new BlockBox(
                                                                BlockPosArgumentType.getLoadedBlockPos(context, "from"),
                                                                BlockPosArgumentType.getLoadedBlockPos(context, "to")
                                                        ),
                                                        BlockStateArgumentType.getBlockState(context,"target").getBlockState()
                                                ))
                                        )
                                )
                        )
                )

                .then(CommandManager.literal("both")
                )
        );
    }

    public static int updateNC(PlayerEntity player, BlockBox box, BlockStateArgument targetStateArgument, boolean hasTarget) {
        if ((box.getBlockCountX() * box.getBlockCountY() * box.getBlockCountZ()) < FlexiblePCBSettings.updateBlockCommandLimit) {
            World world = player.getEntityWorld();
            ArrayList<BlockPos> list = Lists.newArrayList();

            Set<Property<?>> targetProperties = null;
            BlockState targetState = null;

            if (targetStateArgument != null) {
                targetProperties = ((BlockStateArgumentAccessor) targetStateArgument).getProperties();
                targetState = targetStateArgument.getBlockState();
            }

            for (BlockPos pos : BlockPos.iterate(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ)) {
                BlockState state = world.getBlockState(pos);

                if (targetState == null) {
                    list.add(pos.toImmutable());
                }
                else if (state.getBlock() == targetState.getBlock()) {
                    if (checkProperties(state, targetProperties, targetStateArgument))
                        list.add(pos.toImmutable());
                }
            }

            for (BlockPos pos : list) {
                world.updateNeighbor(pos, Blocks.AIR, pos);
            }

            return 1;
        }

        return 0;
    }

    public static int updatePP(PlayerEntity player, BlockBox box, BlockState block) {
        return 0;
    }

    private static boolean checkProperties(BlockState state, Set<Property<?>> targetProperties, BlockStateArgument targetStateArgument) {
        for (Property<?> property : targetProperties) {
            if ((state.get(property) != targetStateArgument.getBlockState().get(property))) {
                return false;
            }
        }

        return true;
    }
}
