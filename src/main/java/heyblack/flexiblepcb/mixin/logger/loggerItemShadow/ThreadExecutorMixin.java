package heyblack.flexiblepcb.mixin.logger.loggerItemShadow;

import net.minecraft.util.thread.ThreadExecutor;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ThreadExecutor.class)
public class ThreadExecutorMixin<R extends Runnable>
{
//    @Inject(method = "executeTask", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/thread/ThreadExecutor;getName()Ljava/lang/String;"))
//    public void onException(R task, CallbackInfo ci) {
//        ItemStack stack = ScreenHandlerMixin.getTrackingStack();
//
//        if (stack != null && ScreenHandlerMixin.getDirty()) {
//            ScreenHandlerMixin.afterSuppression();
//        }
//    }
}
