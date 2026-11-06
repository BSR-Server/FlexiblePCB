package heyblack.flexiblepcb.mixin.rule.playerRidingPoseFix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import heyblack.flexiblepcb.FlexiblePCBSettings;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "updatePose", at = @At("HEAD"), cancellable = true)
    private void fixPlayerRidingPose(CallbackInfo ci) {
        if (!FlexiblePCBSettings.playerRidingPoseFix) return;

        PlayerEntity self = (PlayerEntity) (Object) this;

        if (!self.isSpectator() && self.hasVehicle() && self.isFallFlying()) {
            self.setPose(EntityPose.STANDING);
            ci.cancel();
        }
    }
}
