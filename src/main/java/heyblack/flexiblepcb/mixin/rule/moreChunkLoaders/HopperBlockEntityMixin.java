package heyblack.flexiblepcb.mixin.rule.moreChunkLoaders;

import heyblack.flexiblepcb.FlexiblePCBSettings;
import heyblack.flexiblepcb.util.rule.moreChunkLoaders.ExtraTickets;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.block.HopperBlock.FACING;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin extends BlockEntity {
    public HopperBlockEntityMixin(BlockEntityType<?> type) {
        super(type);
    }

    @Inject(at = @At("HEAD"), method = "getOutputInventory")
    private void addTicket(CallbackInfoReturnable<Inventory> cir) {
        if (FlexiblePCBSettings.moreChunkLoaders) {
            BlockState state = this.world.getBlockState(pos);
            if (world instanceof ServerWorld && state.isOf(Blocks.HOPPER)) {
                Direction dir = state.get(FACING);
                int facingX = pos.getX() + dir.getOffsetX();
                int facingY = pos.getY() + dir.getOffsetY();
                int facingZ = pos.getZ() + dir.getOffsetZ();

                BlockPos facingBlockPos = new BlockPos(facingX, facingY, facingZ);
                BlockState facingBlock = this.world.getBlockState(facingBlockPos);

                ChunkPos selfPos = new ChunkPos(pos);
                ChunkPos facingPos = new ChunkPos(facingBlockPos);

                if (!selfPos.equals(facingPos) && facingBlock.isAir())
                    ((ServerWorld) world).getChunkManager().addTicket(
                            ExtraTickets.HOPPER,
                            facingPos, 2,
                            facingPos
                    );
            }
        }
    }
}