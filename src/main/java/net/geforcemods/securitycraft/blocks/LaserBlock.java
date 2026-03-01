package net.geforcemods.securitycraft.blocks;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.blockentities.MineBlockEntity;
import net.minecraft.block.BlockRenderType;
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

public class LaserBlock extends OwnableBlock {

    private final float explosionPower;

    public LaserBlock(Settings settings, float explosionPower) {
        super(settings);
        this.explosionPower = explosionPower;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (world.isClient) return;
        if (!(entity instanceof LivingEntity living)) return;
        if (world.getBlockEntity(pos) instanceof MineBlockEntity be) {
            if (!be.isArmed()) return;
            if (entity instanceof PlayerEntity player && be.isOwnedBy(player)) return;
            if (be.isModuleEnabled(net.geforcemods.securitycraft.modules.ModuleType.ALLOWLIST)) {
                // check allowlist - simplified
            }
            be.setArmed(false);
            world.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                explosionPower, true, ExplosionBehavior.DEFAULT);
            world.setBlockState(pos, net.minecraft.block.Blocks.AIR.getDefaultState());
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
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
