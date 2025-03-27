package heyblack.flexiblepcb.mixin.carpet.logger.enhancedTntLogger;

import carpet.logging.LoggerRegistry;
import carpet.logging.logHelpers.TNTLogHelper;
import carpet.utils.Messenger;
import heyblack.flexiblepcb.FlexiblePCBSettings;
import net.minecraft.text.BaseText;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TNTLogHelper.class)
public class TNTLogHelperMixin {
    @Inject(
            method = "onPrimed",
            at = @At(
                    value = "TAIL"
            )
    )
    private void logRandomMotion(double x, double y, double z, Vec3d motion, CallbackInfo ci) {
        if (!FlexiblePCBSettings.enhancedTntLogger) return;

        LoggerRegistry.getLogger("tnt").log(
                option -> {
                    if (option.equals("full")) {
                        return new BaseText[] {
                                Messenger.c(
                                        "l P ", Messenger.dblt("l", x, y, z),
                                        "r  M ", Messenger.dblt("r", motion.x, motion.y, motion.z)
                                )
                        };
                    }

                    return null;
                }
        );
    }
}
