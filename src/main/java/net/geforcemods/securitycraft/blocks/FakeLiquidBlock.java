package net.geforcemods.securitycraft.blocks;

import net.geforcemods.securitycraft.blockentities.OwnableBlockEntity;
import net.geforcemods.securitycraft.api.Owner;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.geforcemods.securitycraft.SCContent;

public class FakeLiquidBlock extends OwnableBlock {

    public enum LiquidType { WATER, LAVA }

    private final LiquidType liquidType;

    public FakeLiquidBlock(Settings settings, LiquidType type) {
        super(settings);
        this.liquidType = type;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world.isClient) return;
        if (!(entity instanceof LivingEntity living)) return;
        if (entity instanceof PlayerEntity player) {
            if (world.getBlockEntity(pos) instanceof OwnableBlockEntity be) {
                if (be.isOwnedBy(player)) return;
            }
        }
        // Damage the entity
        if (liquidType == LiquidType.LAVA) {
            entity.setOnFireFor(5);
            entity.damage(entity.getDamageSources().inFire(), 4.0f);
        } else {
            entity.damage(entity.getDamageSources().generic(), 2.0f);
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new OwnableBlockEntity(SCContent.FAKE_LIQUID_BLOCK_ENTITY, pos, state) {};
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return null;
    }
}
