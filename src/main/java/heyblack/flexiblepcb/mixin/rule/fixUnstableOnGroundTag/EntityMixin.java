package heyblack.flexiblepcb.mixin.rule.fixUnstableOnGroundTag;

import heyblack.flexiblepcb.FlexiblePCBSettings;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public class EntityMixin
{
//    @Redirect(
//            method = "move",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/block/Block;onEntityLand(Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;)V"
//            )
//    )
//    public void checkVelocity(Block instance, BlockView world, Entity entity) {
//        if (FlexiblePCBSettings.fixUnstableOnGroundTag) {
//            // 条件大概是adjustMovementForCollisions后的y轴动量小于0且绝对值小于0.08
//            if () {
//
//            }
//        }
//    }
}
