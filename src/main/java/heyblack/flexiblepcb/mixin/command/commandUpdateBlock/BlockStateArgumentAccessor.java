package heyblack.flexiblepcb.mixin.command.commandUpdateBlock;

import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.state.property.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(BlockStateArgument.class)
public interface BlockStateArgumentAccessor
{
    @Accessor
    Set<Property<?>> getProperties();
}
