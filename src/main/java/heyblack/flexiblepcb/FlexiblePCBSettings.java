package heyblack.flexiblepcb;

import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import carpet.settings.Validator;
import net.minecraft.server.command.ServerCommandSource;

import static carpet.settings.RuleCategory.*;

public class FlexiblePCBSettings
{
    private static class CheckValue extends Validator<Integer>
    {
        @Override
        public Integer validate(ServerCommandSource source, ParsedRule<Integer> currentRule, Integer newValue, String typedString)
        {
            return newValue < 32768 ? newValue : null;
        }
    }

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

    @Rule(
            desc = "Insert item in mainhand to minecart",
            category = {SURVIVAL, "flexiblepcb"}
    )
    public static boolean insertBlockToMinecart = false;

    @Rule(
            desc = "Enables /updateBlock command to update blocks",
            category = {COMMAND, "flexiblepcb"}
    )
    public static boolean commandUpdateBlock = false;

    @Rule(
            desc = "Sets the maximum amount of blocks can be updated by command /updateBlock",
            category = {COMMAND, "flexiblepcb"},
            validate = {Validator.NONNEGATIVE_NUMBER.class, CheckValue.class},
            options = {"10000", "32768"}
    )
    public static int updateBlockCommandLimit = 32768;
}
