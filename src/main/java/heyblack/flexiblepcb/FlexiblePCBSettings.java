package heyblack.flexiblepcb;

import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import carpet.settings.Validator;
import net.minecraft.server.command.ServerCommandSource;

import static carpet.settings.RuleCategory.*;

public class FlexiblePCBSettings {
    private static class CheckValue extends Validator<Integer> {
        @Override
        public Integer validate(ServerCommandSource source, ParsedRule<Integer> currentRule, Integer newValue, String typedString) {
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

    // Rule
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

    public static final double VANILLA_ITEM_PICK_UP_RANGE_HORIZONTAL = 1.0;

    @Rule(
            desc = "Changes the horizontal edge length of item pick up range for player",
            category = {CREATIVE, "flexiblepcb"},
            extra = {
                    "Note that this range means the range between the hitboxes of player and item, not the position of them."
            },
            validate = {Validator.NONNEGATIVE_NUMBER.class},
            options = {"1.0", "2.0", "5.0", "10.0", "0.0"}
    )
    public static double itemPickUpRangeHorizontal = VANILLA_ITEM_PICK_UP_RANGE_HORIZONTAL;

    public static final double VANILLA_ITEM_PICK_UP_RANGE_VERTICAL = 0.5;
    @Rule(
            desc = "Changes the vertical edge length of item pick up range for player",
            category = {CREATIVE, "flexiblepcb"},
            extra = {
                    "In vanilla, whether the player is riding a vehicle or not will affect the vertical range of item pick up, this rule will not consider and will override that.",
                    "Note that this range means the range between the hitboxes of player and item, not the position of them."
            },
            validate = {Validator.NONNEGATIVE_NUMBER.class},
            options = {"1.0", "2.0", "5.0", "10.0", "0.0"}
    )
    public static double itemPickUpRangeVertical = VANILLA_ITEM_PICK_UP_RANGE_VERTICAL;

    @Rule(
            desc = "Using debug stick will send block update",
            category = {CREATIVE, "flexiblepcb"}
    )
    public static boolean debugStickSendBlockUpdate = false;

    // Command
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
            desc = "Enables /removeBlock command to remove block",
            extra = {
                    "The removal and replacement will not send block/light update, the original block entity will be kept and no new block entity will be added",
                    "Note that light will be updated client-side"
            },
            category = {COMMAND, CREATIVE, "flexiblepcb"},
            options = {"ops", "0", "1", "2", "3", "4", "false", "true"},
            validate = {Validator._COMMAND_LEVEL_VALIDATOR.class}
    )
    public static String commandRemoveBlock = "ops";

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

    // Logger

    // Bugfix
    @Rule(
            desc = "Fixes MC-254307, and the same issue with beds",
            category = {BUGFIX, "flexiblepcb"}
    )
    public static boolean unstableOnGroundTagFix = false;
}
