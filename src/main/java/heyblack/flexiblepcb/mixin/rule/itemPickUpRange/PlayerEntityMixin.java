package heyblack.flexiblepcb.mixin.rule.itemPickUpRange;

import heyblack.flexiblepcb.FlexiblePCBSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity
{
    public PlayerEntityMixin(EntityType<?> type, World world)
    {
        super(type, world);
    }

    @ModifyArg(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getOtherEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;)Ljava/util/List;"), index = 1)
    public Box setRange(Box box) {
        double horizontal = FlexiblePCBSettings.itemPickUpRangeHorizontal;
        double vanillaHorizontal = FlexiblePCBSettings.VANILLA_ITEM_PICK_UP_RANGE_HORIZONTAL;

        double vertical = FlexiblePCBSettings.itemPickUpRangeVertical;
        double vanillaVertical = FlexiblePCBSettings.VANILLA_ITEM_PICK_UP_RANGE_VERTICAL;

        if (horizontal != vanillaHorizontal) {
            double verticalExpand = vertical == vanillaVertical ? vanillaVertical : vertical;

            box = this.getBoundingBox().expand(horizontal, verticalExpand, horizontal);

            return box;
        }
        else if (vertical != vanillaVertical) {
            box = this.getBoundingBox().expand(vanillaHorizontal, vertical, vanillaHorizontal);

            return box;
        }

        return box;
    }
}
