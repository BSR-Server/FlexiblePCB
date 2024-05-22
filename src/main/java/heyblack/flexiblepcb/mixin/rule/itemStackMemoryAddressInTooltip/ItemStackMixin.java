package heyblack.flexiblepcb.mixin.rule.itemStackMemoryAddressInTooltip;

import heyblack.flexiblepcb.FlexiblePCBSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "getTooltip", at = @At("RETURN"), cancellable = true)
    private void addMemoryAddress(@Nullable PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir){
        if (FlexiblePCBSettings.itemStackMemoryAddressInTooltip) {
            List<Text> list = cir.getReturnValue();
            if (context.isAdvanced()) {
                list.add(new LiteralText(((Object)this).toString()).formatted(Formatting.DARK_GRAY));
            }
            cir.setReturnValue(list);
        }
    }
}
