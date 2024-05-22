package heyblack.flexiblepcb.command;

import carpet.CarpetSettings;
import carpet.settings.SettingsManager;
import carpet.utils.Messenger;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;


public class ItemShadowCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){
        dispatcher.register(CommandManager.literal("item_shadow")
                .requires((player) -> SettingsManager.canUseCommand(player, true))
                .then(CommandManager.literal("show").
                        executes(context -> show(context.getSource()))));
    }

    public static int show(ServerCommandSource source) throws CommandSyntaxException {
        ServerPlayerEntity player = source.getPlayer();
        player.sendSystemMessage(new LiteralText(Integer.toHexString(((Object)(player.getMainHandStack())).hashCode())), player.getUuid());
        return 1;
    }
}
