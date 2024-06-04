package heyblack.flexiblepcb.mixin.logger.loggerItemShadow;

import heyblack.flexiblepcb.logging.FlexiblePCBLoggerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScreenHandler.class)
public abstract class ScreenHandlerMixin
{
    @Shadow public abstract ScreenHandlerType<?> getType();

    private static ItemStack trackingStack = null;
    private Inventory trackingInventory = null;
    private static boolean dirty = false;

    @Inject(
            method = "method_30010",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/screen/slot/Slot;setStack(Lnet/minecraft/item/ItemStack;)V"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;setStack(Lnet/minecraft/item/ItemStack;)V", ordinal = 8),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;setStack(ILnet/minecraft/item/ItemStack;)V", ordinal = 1)
            )
    )
    public void onSlotSwap(int i, int j, SlotActionType slotActionType, PlayerEntity playerEntity, CallbackInfoReturnable<ItemStack> cir) {
        if (FlexiblePCBLoggerRegistry.__itemshadow) {
            trackingStack = playerEntity.inventory.getStack(i);
//            trackingInventory = this.
            dirty = true;
        }
    }

    public ItemStack getTrackingStack() {
        return trackingStack;
    }

    static void afterSuppression() {
        dirty = false;
    }

    static boolean getDirty() {
        return dirty;
    }
}
