package heyblack.flexiblepcb;

import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import carpet.settings.Validator;
import net.minecraft.server.command.ServerCommandSource;

import static carpet.settings.RuleCategory.CREATIVE;

public class FlexiblePCBSettings
{
    private static class CheckValue extends Validator<Integer>
    {
        @Override
        public Integer validate(ServerCommandSource source, ParsedRule<Integer> currentRule, Integer newValue, String typedString)
        {
            return newValue < 20000000 ? newValue : null;
        }
    }

    @Rule(
            desc = "test",
            category = {CREATIVE, "flexiblepcb"},
            validate = {Validator.NONNEGATIVE_NUMBER.class},
            options = {"1", "10", "20"}
    )
    public static int testIntRule = 1;
}
