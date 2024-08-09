package heyblack.flexiblepcb.mixin.rule.debugStickNoUpdate;

import heyblack.flexiblepcb.FlexiblePCBSettings;
import net.minecraft.block.BlockState;
import net.minecraft.item.DebugStickItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DebugStickItem.class)
public class DebugStickItemMixin {
    @Redirect(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/WorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"
            )
    )
    boolean onSetBlockState(WorldAccess instance, BlockPos blockPos, BlockState blockState, int i) {
        if (!FlexiblePCBSettings.debugStickNoBlockUpdate) {
            return instance.setBlockState(blockPos, blockState, i);
        }
        return instance.setBlockState(blockPos, blockState, 0, 0);
    }
}
