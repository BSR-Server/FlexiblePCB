package heyblack.flexiblepcb.mixin.rule.remoteRedstone;

import heyblack.flexiblepcb.FlexiblePCBSettings;
import heyblack.flexiblepcb.util.rule.remoteRedstone.ItemInteraction;
import heyblack.flexiblepcb.util.rule.remoteRedstone.RemoteRedstone;
import heyblack.flexiblepcb.util.rule.remoteRedstone.RemoteRedstoneManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static net.minecraft.block.RedstoneWireBlock.POWER;

@Mixin(RedstoneWireBlock.class)
public class RedstoneWireBlockMixin {
    @Inject(
            method = "onBlockAdded",
            at = @At("TAIL")
    )
    void addTrackingPos(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (!world.isClient() && FlexiblePCBSettings.remoteRedstone && world.getBlockState(pos.offset(Direction.DOWN)).isOf(Blocks.BARRIER)) {
            RemoteRedstoneManager.addRemoteRedstone(world, pos);
        }
    }

    @Inject(
            method = "onStateReplaced",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;onStateReplaced(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Z)V",
                    shift = At.Shift.BY,
                    by = 1
            )
    )
    void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved, CallbackInfo ci) {
        if (!world.isClient() && FlexiblePCBSettings.remoteRedstone && RemoteRedstoneManager.hasRemoteRedstone(pos)) {
            RemoteRedstone remoteRedstone = RemoteRedstoneManager.getRemoteRedstone(pos);

            if (!state.isOf(newState.getBlock())) {
                RemoteRedstoneManager.remove(remoteRedstone);
            }
        }
    }

    @Inject(
            method = "update",
            at = @At("TAIL"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    void updateRemotePower(World world, BlockPos pos, BlockState state, CallbackInfo ci, int i) {
        if (!world.isClient() && FlexiblePCBSettings.remoteRedstone && RemoteRedstoneManager.hasRemoteRedstone(pos)) {
            RemoteRedstone remoteRedstone = RemoteRedstoneManager.getRemoteRedstone(pos);

            if (state.get(POWER) != i && remoteRedstone.isSender()) {
                remoteRedstone.notifySignalToGroup(i);
            }
        }
    }

    @Inject(
            method = "getReceivedRedstonePower",
            at = @At("HEAD"),
            cancellable = true
    )
    void useRemoteSignal(World world, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (!world.isClient() && FlexiblePCBSettings.remoteRedstone && RemoteRedstoneManager.hasRemoteRedstone(pos)) {
            RemoteRedstone remoteRedstone = RemoteRedstoneManager.getRemoteRedstone(pos);

            if (!remoteRedstone.isSender()) {
                cir.setReturnValue(remoteRedstone.getRemoteSignal());
            }
        }
    }

    @Inject(
            method = "onUse",
            at = @At("HEAD"),
            cancellable = true
    )
    void flipSenderState(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (ItemInteraction.changeSenderState(world, player, hand, hit)) {
            cir.setReturnValue(ActionResult.SUCCESS);
        } else if (ItemInteraction.changeGroup(world, player, hand, hit)) {
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
