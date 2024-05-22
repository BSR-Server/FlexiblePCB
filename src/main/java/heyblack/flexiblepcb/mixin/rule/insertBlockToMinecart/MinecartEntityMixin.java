package heyblack.flexiblepcb.mixin.rule.insertBlockToMinecart;

import heyblack.flexiblepcb.FlexiblePCBSettings;
import heyblack.flexiblepcb.util.rule.insertBlockToMinecart.InsertBlockToMinecart;
import net.fabricmc.fabric.api.event.client.player.ClientPickBlockCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecartEntity.class)
public abstract class MinecartEntityMixin extends AbstractMinecartEntity
{
    protected MinecartEntityMixin(EntityType<?> entityType, World world)
    {
        super(entityType, world);
    }

    @Inject(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;startRiding(Lnet/minecraft/entity/Entity;)Z"), cancellable = true)
    public void tryInsert(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (FlexiblePCBSettings.insertBlockToMinecart) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (InsertBlockToMinecart.canInsert(itemStack)) {
                if (this.hasPassengers()) {
                    cir.cancel();
                }

                // considering this rule is in "survival" category, the item's inventory will not be considered
                if (itemStack.getItem() == Items.CHEST) {
                    convert(this, Type.CHEST, itemStack);
                }
                if (itemStack.getItem() == Items.HOPPER) {
                    convert(this, Type.HOPPER, itemStack);
                }
                if (itemStack.getItem() == Items.FURNACE) {
                    convert(this, Type.FURNACE, itemStack);
                }
                if (itemStack.getItem() == Items.TNT) {
                    convert(this, Type.TNT, itemStack);
                }

                cir.setReturnValue(ActionResult.PASS);
            }
        }
    }

    private void convert(AbstractMinecartEntity original, AbstractMinecartEntity.Type type, ItemStack itemStack) {
        AbstractMinecartEntity newMinecart = AbstractMinecartEntity.create(
                original.getEntityWorld(),
                original.getX(),
                original.getY(),
                original.getZ(),
                type
        );

        World world = original.getEntityWorld();

        newMinecart.setVelocity(original.getVelocity());
        newMinecart.setCustomName(original.getCustomName());
        newMinecart.setFireTicks(original.getFireTicks());
        newMinecart.setGlowing(original.isGlowing());

        original.remove();
        world.spawnEntity(newMinecart);
        itemStack.decrement(1);
    }
}
