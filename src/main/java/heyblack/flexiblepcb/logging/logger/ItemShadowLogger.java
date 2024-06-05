package heyblack.flexiblepcb.logging.logger;

import heyblack.flexiblepcb.logging.AbstractLogger;
import heyblack.flexiblepcb.logging.FlexiblePCBLoggerRegistry;

public class ItemShadowLogger extends AbstractLogger
{
    private static final String NAME = "itemshadow";
    private static final ItemShadowLogger INSTANCE = new ItemShadowLogger();

    public void onItemShadowCreate() {
        if (!FlexiblePCBLoggerRegistry.__itemshadow) {
            return;
        }
    }

    public static ItemShadowLogger getInstance() {
        return INSTANCE;
    }

    private ItemShadowLogger() {
        super(NAME);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDefaultOption() {
        return null;
    }

    @Override
    public String[] getOptions() {
        return null;
    }
}
