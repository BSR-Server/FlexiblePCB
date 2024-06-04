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
            category = {FEATURE, "flexiblepcb"}
    )
    public static boolean insertBlockToMinecart = false;

    @Rule(
            desc = "Enables /updateBlock command to update blocks",
            category = {COMMAND, CREATIVE, "flexiblepcb"}
    )
    public static boolean commandUpdateBlock = false;

    @Rule(
            desc = "Sets the maximum amount of blocks can be updated by command \"/updateBlock\"",
            category = {COMMAND, CREATIVE, "flexiblepcb"},
            validate = {Validator.NONNEGATIVE_NUMBER.class, CheckValue.class}
    )
    public static int updateBlockCommandLimit = 32768;

    @Rule(
            desc = "Shows the status of shadowed items by command \"/itemshadow show\"",
            extra = {"The status of the item in the main hand will be shown"},
            category = {COMMAND, "flexiblepcb"}
    )
    public static boolean commandItemShadowShow = false;

    @Rule(
            desc = "Creates an item shadow by command \"/itemshadow create\"",
            extra = {"An item shadow of the item in the main-hand will be created in the off-hand"},
            category = {COMMAND, CREATIVE, "flexiblepcb"}
    )
    public static boolean commandItemShadowCreate = false;

    @Rule(
            desc = "Manages chunks for save-stating by command \"/chunksavestate\"",
            category = {COMMAND, CREATIVE, "flexiblepcb"}
    )
    public static boolean commandChunkSaveState = false;

    @Rule(
            desc = "Fixes MC-254307",
            category = {FEATURE, BUGFIX, "flexiblepcb"}
    )
    public static boolean fixUnstableOnGroundTag = false;
}
