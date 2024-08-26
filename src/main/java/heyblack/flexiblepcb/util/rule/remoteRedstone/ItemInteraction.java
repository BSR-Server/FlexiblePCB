package heyblack.flexiblepcb.util.rule.remoteRedstone;

import heyblack.flexiblepcb.FlexiblePCBSettings;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemInteraction {
    public static boolean changeSenderState(World world, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (
            !world.isClient()
            && FlexiblePCBSettings.remoteRedStone
            && !player.getMainHandStack().isEmpty()
            && player.getMainHandStack().getItem() instanceof BlockItem
            && ((BlockItem) player.getMainHandStack().getItem()).getBlock() == Blocks.BARRIER
        ) {
            BlockPos pos = hit.getBlockPos();
            if (RemoteRedstoneManager.hasRemoteRedstone(pos)) {
                RemoteRedstone remoteRedstone = RemoteRedstoneManager.getRemoteRedstone(pos);
                remoteRedstone.flipSenderState();

                return true;
            }
        }

        return false;
    }

    public static boolean changeGroup(World world, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (
            !world.isClient()
            && FlexiblePCBSettings.remoteRedStone
            && !player.getMainHandStack().isEmpty()
            && player.getMainHandStack().getItem() instanceof DyeItem
        ) {
            BlockPos pos = hit.getBlockPos();
            if (RemoteRedstoneManager.hasRemoteRedstone(pos)) {
                RemoteRedstone remoteRedstone = RemoteRedstoneManager.getRemoteRedstone(pos);
                remoteRedstone.changeGroup(((DyeItem) player.getMainHandStack().getItem()).getColor());

                return true;
            }
        }

        return false;
    }
}
