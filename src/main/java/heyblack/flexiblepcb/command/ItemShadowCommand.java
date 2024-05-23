package heyblack.flexiblepcb.command;

import carpet.CarpetSettings;
import carpet.settings.SettingsManager;
import carpet.utils.Messenger;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import heyblack.flexiblepcb.FlexiblePCBSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;


public class ItemShadowCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){
        dispatcher.register(CommandManager.literal("itemshadow")
                .requires((player) -> SettingsManager.canUseCommand(player, FlexiblePCBSettings.commandItemShadowShow || FlexiblePCBSettings.commandItemShadowCreate))
                .then(CommandManager.literal("show")
                        .requires((player) -> SettingsManager.canUseCommand(player, FlexiblePCBSettings.commandItemShadowShow))
                        .executes(context -> show(context.getSource()))
                )
                .then(CommandManager.literal("create")
                        .requires((player) -> SettingsManager.canUseCommand(player, FlexiblePCBSettings.commandItemShadowCreate))
                        .executes(context -> create(context.getSource()))
                )
        );
    }

    public static int show(ServerCommandSource source) throws CommandSyntaxException {
        ServerPlayerEntity player = source.getPlayer();
        player.sendSystemMessage(new LiteralText(Integer.toHexString(player.getMainHandStack().hashCode())), player.getUuid());
        return 1;
    }

    public static int create(ServerCommandSource source) throws CommandSyntaxException
    {
        PlayerEntity player = source.getPlayer();
        if (player.getOffHandStack() == ItemStack.EMPTY && player.getMainHandStack() != ItemStack.EMPTY) {
            player.setStackInHand(Hand.OFF_HAND, player.getMainHandStack());

            return 1;
        }

        player.sendMessage(Text.of("invalid hand state!"), false);

        return 0;
    }
}
