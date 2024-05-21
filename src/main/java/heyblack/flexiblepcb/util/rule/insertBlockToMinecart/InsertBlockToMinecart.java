package heyblack.flexiblepcb.util.rule.insertBlockToMinecart;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class InsertBlockToMinecart
{
    // this is a "survival" category rule, so yeah
    public static boolean canInsert(ItemStack itemStack) {
        return itemStack.getItem() == Items.CHEST || itemStack.getItem() == Items.HOPPER || itemStack.getItem() == Items.FURNACE || itemStack.getItem() == Items.TNT;
    }
}
