package heyblack.flexiblepcb.mixin.rule.moreChunkLoaders;

import heyblack.flexiblepcb.FlexiblePCBSettings;
import heyblack.flexiblepcb.util.rule.moreChunkLoaders.ExtraTickets;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonBlock.class)
public class PistonBlockMixin {
    @Inject(at = @At("HEAD"), method = "onSyncedBlockEvent")
    private void addTicket(BlockState state, World world, BlockPos pos, int type, int data, CallbackInfoReturnable<Boolean> cir) {
        if (FlexiblePCBSettings.moreChunkLoaders) {

            if (world instanceof ServerWorld) {
                Direction direction = state.get(FacingBlock.FACING);
                int facingX = pos.getX() + direction.getOffsetX();
                int facingZ = pos.getZ() + direction.getOffsetZ();

                ChunkPos basePos = new ChunkPos(pos.getX() >> 4, pos.getZ() >> 4);
                ChunkPos headPos = new ChunkPos(facingX >> 4, facingZ >> 4);

                if (!basePos.equals(headPos)) {
                    ((ServerWorld) world).getChunkManager().addTicket(
                            ExtraTickets.PISTON,
                            headPos, 1,
                            headPos);

                    // wait why did i do this?
//                    ((ServerWorld) world).getChunkManager().addTicket(
//                            ExtraTickets.PISTON,
//                            basePos, 1,
//                            basePos);
                }
            }
        }
    }
}