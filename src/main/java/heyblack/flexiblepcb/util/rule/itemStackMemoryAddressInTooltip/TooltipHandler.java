package heyblack.flexiblepcb.util.rule.itemStackMemoryAddressInTooltip;


import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class TooltipHandler{
    public static void addMemoryAddress(){
        ItemTooltipCallback.EVENT.register(new ItemTooltipCallback() {
            @Override
            public void getTooltip(ItemStack stack, TooltipContext context, List<Text> lines) {
                lines.add(new LiteralText(((Object)stack).toString() + "-1235").formatted(Formatting.DARK_GRAY));
            }
        });
    }
}
