package heyblack.flexiblepcb.logging;

import carpet.logging.Logger;
import carpet.logging.LoggerRegistry;
import heyblack.flexiblepcb.logging.logger.ItemShadowLogger;

public class FlexiblePCBLoggerRegistry
{
    public static boolean __itemshadow;

    public static void registerLoggers() {
        register(ItemShadowLogger.getInstance());
    }

    public static void register(AbstractLogger logger) {
        LoggerRegistry.registerLogger(logger.getName(), stardardLogger(logger.getName(), logger.getDefaultOption(), logger.getOptions()));
    }

    public static Logger stardardLogger(String logName, String def, String [] options) {
        try {
            return new Logger(FlexiblePCBLoggerRegistry.class.getField("__"+logName), logName, def, options);
        }
        catch (NoSuchFieldException e) {
            throw new RuntimeException("Failed to create logger " + logName);
        }
    }
}
