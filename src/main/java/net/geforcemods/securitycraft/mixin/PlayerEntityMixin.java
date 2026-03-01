package net.geforcemods.securitycraft.mixin;

import net.geforcemods.securitycraft.blocks.RiftStabilizerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "eatFood", at = @At("HEAD"), cancellable = true)
    private void sc_preventChorusFruitTeleport(
        net.minecraft.world.World world, net.minecraft.item.ItemStack stack,
        org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<net.minecraft.item.ItemStack> cir) {
        // Chorus fruit teleportation is checked in ServerPlayerEntityMixin
    }
}
