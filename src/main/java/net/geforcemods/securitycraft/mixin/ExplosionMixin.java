package net.geforcemods.securitycraft.mixin;

import net.geforcemods.securitycraft.blocks.ReinforcedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Explosion.class)
public class ExplosionMixin {
    // Reinforced blocks handle their own resistance via hardness=-1 and resistance=3600000
    // This mixin is a placeholder for any additional explosion handling needed
}
