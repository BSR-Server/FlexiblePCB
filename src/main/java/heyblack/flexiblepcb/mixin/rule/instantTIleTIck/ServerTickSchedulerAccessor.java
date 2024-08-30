package heyblack.flexiblepcb.mixin.rule.instantTIleTIck;

import net.minecraft.server.world.ServerTickScheduler;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerTickScheduler.class)
public interface ServerTickSchedulerAccessor {
    @Accessor
    ServerWorld getWorld();
}
