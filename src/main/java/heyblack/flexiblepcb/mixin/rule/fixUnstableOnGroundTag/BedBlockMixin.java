package heyblack.flexiblepcb.mixin.rule.fixUnstableOnGroundTag;

import heyblack.flexiblepcb.FlexiblePCBSettings;
import net.minecraft.block.BedBlock;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BedBlock.class)
public class BedBlockMixin
{
    @Inject(method = "bounceEntity", at = @At("HEAD"), cancellable = true)
    public void checkVelocity(Entity entity, CallbackInfo ci) {
        if (FlexiblePCBSettings.UnstableOnGroundTagFix) {
            if (entity.getVelocity().y <= 0.0 && entity.getVelocity().y >= -0.08) {
                entity.setVelocity(entity.getVelocity().multiply(1.0, 0.0, 1.0));
                ci.cancel();
            }
        }
    }
}
