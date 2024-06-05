package heyblack.flexiblepcb.hook;

import heyblack.flexiblepcb.FlexiblePCBSettings;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlimeBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;

public class Hooks {

    @SuppressWarnings("unused")
    public static void checkVelocity(Block block, BlockView world, Entity entity, Vec3d vec3d) {
        if (FlexiblePCBSettings.UnstableOnGroundTagFix) {
            if ((entity.getVelocity().y <= 0.0 && entity.getVelocity().y >= -0.08)
                    && (block instanceof SlimeBlock || block instanceof BedBlock)
            ){
                    entity.setVelocity(entity.getVelocity().multiply(1.0, 0.0, 1.0));
                    return;
            }
        }
        block.onEntityLand(world, entity);
    }
}
