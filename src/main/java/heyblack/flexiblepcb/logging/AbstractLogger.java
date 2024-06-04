package heyblack.flexiblepcb.logging;

import carpet.logging.Logger;

public abstract class AbstractLogger
{
    private final String name;
    public String[] options = null;

    public AbstractLogger(String name) {
        this.name = name;
    }

    public Logger createLogger() {
        return FlexiblePCBLoggerRegistry.stardardLogger(
                this.getName(),
                this.getDefaultOption(),
                this.getOptions()
        );
    }

    public void log() {

    }

    public String getName() {
        return this.name;
    }

    public String getDefaultOption() {
        return this.options == null ? null : options[0];
    }

    public String[] getOptions() {
        return this.options;
    }
}
