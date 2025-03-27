package heyblack.flexiblepcb.util.rule.remoteRedstone;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemInteraction {
    public static void changeSenderState(World world, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            BlockPos pos = hit.getBlockPos();
            if (RemoteRedstoneManager.hasRemoteRedstone(pos)) {
                RemoteRedstone remoteRedstone = RemoteRedstoneManager.getRemoteRedstone(pos);
                remoteRedstone.flipSenderState(player);
            }
        }
    }

    public static void changeGroup(World world, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            BlockPos pos = hit.getBlockPos();
            if (RemoteRedstoneManager.hasRemoteRedstone(pos)) {
                RemoteRedstone remoteRedstone = RemoteRedstoneManager.getRemoteRedstone(pos);
                remoteRedstone.changeGroup(((DyeItem) player.getMainHandStack().getItem()).getColor(), player);
            }
        }
    }
}
