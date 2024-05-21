package heyblack.flexiblepcb;

import carpet.CarpetServer;
import net.fabricmc.api.ModInitializer;

public class FlexiblePCBInitializer implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        CarpetServer.manageExtension(new FlexiblePCBMod());
    }
}
