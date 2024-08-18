package heyblack.flexiblepcb.mixin.rule.enderPearlAlwaysTicks;

import heyblack.flexiblepcb.FlexiblePCBSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.server.world.ServerChunkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerChunkManager.class)
public class ServerChunkManagerMixin
{
    @Inject(
            method = "shouldTickEntity",
            at = @At("HEAD"),
            cancellable = true
    )
    private void loadPearl(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (FlexiblePCBSettings.enderPearlAlwaysTicks && entity instanceof EnderPearlEntity) {
            cir.setReturnValue(true);
        }
    }
}
