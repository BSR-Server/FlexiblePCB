package heyblack.flexiblepcb.hook;

import heyblack.flexiblepcb.FlexiblePCBSettings;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;

public class Hooks {

    @SuppressWarnings("unused")
    public static void checkVelocity(Block block, BlockView world, Entity entity, Vec3d vec3d) {
        block.onEntityLand(world, entity);
        if (FlexiblePCBSettings.fixUnstableOnGroundTag) {
            // 条件大概是adjustMovementForCollisions后的y轴动量小于0且绝对值小于0.08
            System.out.println("block = " + block);
            System.out.println("world = " + world);
            System.out.println("entity = " + entity);
            System.out.println("vec3d = " + vec3d);
            if (vec3d.y < -0.08f) {

            }
        }
    }
}
