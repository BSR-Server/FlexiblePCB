package heyblack.flexiblepcb;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.settings.SettingsManager;
import com.mojang.brigadier.CommandDispatcher;
import heyblack.flexiblepcb.command.ItemShadowCommand;
import heyblack.flexiblepcb.command.UpdateBlockCommand;
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

        CarpetServer.settingsManager.addRuleObserver( (serverCommandSource, currentRuleState, originalUserTest) -> {
            // called after setting ANY carpet rule
//            if (currentRuleState.categories.contains("flexiblepcb")) {
//
//            }
        });
    }

    @Override
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        UpdateBlockCommand.register(dispatcher);
        ItemShadowCommand.register(dispatcher);
    }
}
