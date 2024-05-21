package heyblack.flexiblepcb;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.settings.SettingsManager;

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
}