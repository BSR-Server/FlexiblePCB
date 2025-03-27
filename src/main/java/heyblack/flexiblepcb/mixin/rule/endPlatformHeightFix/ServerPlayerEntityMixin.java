package heyblack.flexiblepcb.mixin.rule.endPlatformHeightFix;

import heyblack.flexiblepcb.FlexiblePCBSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends Entity {
    public ServerPlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "getTeleportTarget",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void getTeleportTargetFix(ServerWorld destination, CallbackInfoReturnable<TeleportTarget> cir) {
        if (FlexiblePCBSettings.endPlatformHeightFix) {
            TeleportTarget teleportTarget = super.getTeleportTarget(destination);
            if (teleportTarget != null && this.world.getRegistryKey() != World.END && destination.getRegistryKey() == World.END) {
                Vec3d vec3d = teleportTarget.position.add(0.0, -1.0, 0.0);
                cir.setReturnValue(new TeleportTarget(vec3d, Vec3d.ZERO, 90.0f, 0.0f));

                return;
            }
            cir.setReturnValue(teleportTarget);
        }
    }
}
