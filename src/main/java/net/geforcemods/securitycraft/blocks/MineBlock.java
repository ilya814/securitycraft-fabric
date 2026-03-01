package net.geforcemods.securitycraft.blocks;

import net.geforcemods.securitycraft.blockentities.MineBlockEntity;
import net.geforcemods.securitycraft.modules.ModuleType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.ExplosionBehavior;

/**
 * A mine block that looks like a vanilla block but explodes when non-owners break or step on it.
 */
public class MineBlock extends OwnableBlock {

    private final float explosionPower;
    private final Block disguisedAs;

    public MineBlock(Settings settings, Block disguisedAs, float explosionPower) {
        super(settings);
        this.disguisedAs = disguisedAs;
        this.explosionPower = explosionPower;
    }

    public Block getDisguisedAs() { return disguisedAs; }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            if (world.getBlockEntity(pos) instanceof MineBlockEntity be) {
                if (be.isArmed() && !be.isOwnedBy(player) && !player.isCreative()) {
                    be.setArmed(false);
                    world.createExplosion(player, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        explosionPower, true, ExplosionBehavior.DEFAULT);
                }
            }
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MineBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return null;
    }
}
