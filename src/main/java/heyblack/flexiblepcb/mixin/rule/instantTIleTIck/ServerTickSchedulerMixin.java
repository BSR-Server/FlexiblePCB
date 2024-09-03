package heyblack.flexiblepcb.mixin.rule.instantTIleTIck;

import heyblack.flexiblepcb.FlexiblePCBSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerTickScheduler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ScheduledTick;
import net.minecraft.world.TickPriority;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerTickScheduler.class)
public class ServerTickSchedulerMixin<T> {
    @Inject(
            method = "schedule",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerTickScheduler;addScheduledTick(Lnet/minecraft/world/ScheduledTick;)V"
            ),
            cancellable = true
    )
    public void iTT(BlockPos pos, T object, int delay, TickPriority priority, CallbackInfo ci) {
        if (FlexiblePCBSettings.instantTileTick) {
            ServerWorld world = ((ServerTickSchedulerAccessor) (ServerTickScheduler) (Object) this).getWorld();
            if (!world.isClient()) {
                ScheduledTick<Block> tick = new ScheduledTick<Block>(pos, (Block) object, (long)delay + world.getTime(), priority);
                BlockState state = world.getBlockState(pos);
                if (state.isOf(tick.getObject())) {
                    state.scheduledTick(world, tick.pos, world.random);
                }

                ci.cancel();
            }
        }
    }
}
