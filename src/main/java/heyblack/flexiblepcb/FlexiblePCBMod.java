package heyblack.flexiblepcb;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.settings.SettingsManager;
import com.mojang.brigadier.CommandDispatcher;
import heyblack.flexiblepcb.command.ItemShadowCommand;
import net.minecraft.server.command.ServerCommandSource;

public class FlexiblePCBMod implements CarpetExtension
{
    private static SettingsManager settingsManager;
    static {
        settingsManager = new SettingsManager("0.0.1", "flexiblepcb", "FlexiblePCB");
    }

    @Override
    public void onGameStarted() {
        CarpetServer.settingsManager.parseSettingsClass(FlexiblePCBSettings.class);

        // called after setting carpet rule
        CarpetServer.settingsManager.addRuleObserver( (serverCommandSource, currentRuleState, originalUserTest) -> {
            if (currentRuleState.categories.contains("flexiblepcb")) {

            }
        });
    }

    @Override
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        ItemShadowCommand.register(dispatcher);
    }
}
