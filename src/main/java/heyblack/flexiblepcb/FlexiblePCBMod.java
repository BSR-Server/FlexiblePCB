package heyblack.flexiblepcb;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.mojang.brigadier.CommandDispatcher;
import heyblack.flexiblepcb.command.ChunkSaveStateCommand;
import heyblack.flexiblepcb.command.ItemShadowCommand;
import heyblack.flexiblepcb.command.RemoveBlockCommand;
import heyblack.flexiblepcb.command.UpdateBlockCommand;
import heyblack.flexiblepcb.util.rule.remoteRedstone.RemoteRedstoneManager;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class FlexiblePCBMod implements CarpetExtension
{
    @Override
    public void onGameStarted() {
        CarpetServer.settingsManager.parseSettingsClass(FlexiblePCBSettings.class);

        CarpetServer.settingsManager.addRuleObserver( (serverCommandSource, currentRuleState, originalUserTest) -> {
            // called after setting ANY carpet rule
            if (currentRuleState.categories.contains("flexiblepcb")) {
                PlayerManager pm = serverCommandSource.getMinecraftServer().getPlayerManager();
                for (ServerPlayerEntity player : pm.getPlayerList()) {
                    pm.sendCommandTree(player);
                }

                if (currentRuleState.name.equals("remoteRedstone") && !currentRuleState.getBoolValue()) {
                    RemoteRedstoneManager.removeAll();
                }
            }
        });
    }

    @Override
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        UpdateBlockCommand.register(dispatcher);
        ItemShadowCommand.register(dispatcher);
        ChunkSaveStateCommand.register(dispatcher);
        RemoveBlockCommand.register(dispatcher);
    }
}
