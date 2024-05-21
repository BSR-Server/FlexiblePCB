package heyblack.flexiblepcb;

import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import carpet.settings.Validator;
import net.minecraft.server.command.ServerCommandSource;

import static carpet.settings.RuleCategory.CREATIVE;
import static carpet.settings.RuleCategory.SURVIVAL;

public class FlexiblePCBSettings
{
//    private static class CheckValue extends Validator<Integer>
//    {
//        @Override
//        public Integer validate(ServerCommandSource source, ParsedRule<Integer> currentRule, Integer newValue, String typedString)
//        {
//            return newValue < 20000000 ? newValue : null;
//        }
//    }

//    @Rule(
//            desc = "exampleRule",
//            category = {CREATIVE, "flexiblepcb"},
//            validate = {
//                    Validator.NONNEGATIVE_NUMBER.class,
//                    CheckValue.class
//            },
//            options = {"1", "10", "20"},
//            extra = {
//                    "each element here",
//                    "is a line of desc",
//                    "in gray text"
//            }
//    )
//    public static int testIntRule = 1;

    @Rule(
            desc = "Adds more vanilla like chunk loaders.",
            category = {SURVIVAL, "flexiblepcb"}
    )
    public static boolean moreChunkLoaders = false;
}
