package heyblack.flexiblepcb.mixin.command.commandChunkSaveState;

import heyblack.flexiblepcb.FlexiblePCBSettings;
import heyblack.flexiblepcb.command.ChunkSaveStateCommand;
import heyblack.flexiblepcb.util.command.ChunkSaveStateException;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.ChunkSerializer;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkSerializer.class)
public abstract class ChunkSerializerMixin {
    @Inject(method = "serialize", at = @At("HEAD"))
    private static void throwExceptionWhenSerialize(ServerWorld world, Chunk chunk, CallbackInfoReturnable<NbtCompound> cir) throws ChunkSaveStateException
    {
        if (FlexiblePCBSettings.commandChunkSaveState) {
            if (ChunkSaveStateCommand.getMarkedChunks(world) != null && ChunkSaveStateCommand.getMarkedChunks(world).contains(chunk.getPos())) {
                throw new ChunkSaveStateException();
            }
        }
    }
}
