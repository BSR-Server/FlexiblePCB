package heyblack.flexiblepcb.mixin.carpet.command.playerSwitchItem;

import carpet.commands.PlayerCommand;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import static net.minecraft.server.command.CommandManager.literal;

@Mixin(PlayerCommand.class)
public class PlayerCommandMixin {
    @ModifyReceiver(
            method = "register",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/brigadier/builder/RequiredArgumentBuilder;suggests(Lcom/mojang/brigadier/suggestion/SuggestionProvider;)Lcom/mojang/brigadier/builder/RequiredArgumentBuilder;",
                    remap = false
            ),
            remap = false
    )
    private static RequiredArgumentBuilder<ServerCommandSource, ?> addCommand(RequiredArgumentBuilder<ServerCommandSource, ?> builder, SuggestionProvider<?> provider) {
        return builder.then(literal("switch").executes(PlayerCommandMixin::switchItem));
    }

    @Unique
    private static int switchItem(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if (context.getSource().getEntity() instanceof ServerPlayerEntity) {
            PlayerEntity fake = context.getSource().getMinecraftServer().getPlayerManager().getPlayer(StringArgumentType.getString(context, "player"));

            if (fake != null) {
                PlayerEntity player = context.getSource().getPlayer();

                ItemStack fakeItem = fake.getMainHandStack();
                ItemStack playerItem = player.getMainHandStack();

                fake.setStackInHand(Hand.MAIN_HAND, playerItem);
                player.setStackInHand(Hand.MAIN_HAND, fakeItem);
            }

            return 1;
        }

        return 0;
    }
}
