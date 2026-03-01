package net.geforcemods.securitycraft.mixin;

import net.geforcemods.securitycraft.blocks.RiftStabilizerBlock;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(method = "teleport(Lnet/minecraft/server/world/ServerWorld;DDDFF)Z",
        at = @At("HEAD"), cancellable = true)
    private void sc_checkRiftStabilizer(
        net.minecraft.server.world.ServerWorld world, double destX, double destY, double destZ,
        float yaw, float pitch,
        CallbackInfoReturnable<Boolean> cir) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if (player.isCreative()) return;
        BlockPos dest = new BlockPos((int) destX, (int) destY, (int) destZ);
        if (RiftStabilizerBlock.isBlockingTeleport(world, dest, player.getUuidAsString())) {
            player.sendMessage(net.minecraft.text.Text.translatable(
                "message.securitycraft.rift_stabilizer.blocked"), true);
            cir.setReturnValue(false);
        }
    }
}
