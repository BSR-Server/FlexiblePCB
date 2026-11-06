package heyblack.flexiblepcb.mixin.rule.noHalloweenForBats;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import heyblack.flexiblepcb.FlexiblePCBSettings;
import net.minecraft.entity.passive.BatEntity;

@Mixin(BatEntity.class)
public class BatEntityMixin {

    @Inject(
        method = "isTodayAroundHalloween", 
        at = @At("HEAD"),
        cancellable = true
    )
    private static void noHalloweenForYouSir(CallbackInfoReturnable<Boolean> cir) {
        if (FlexiblePCBSettings.noHalloweenForBats)
            cir.setReturnValue(false);

    }
}
