package net.geforcemods.securitycraft.blocks;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.blockentities.RiftStabilizerBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RiftStabilizerBlock extends OwnableBlock {

    public RiftStabilizerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RiftStabilizerBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return null;
    }

    /**
     * Checks if a rift stabilizer owned by 'ownerUUID' is blocking teleportation at 'target'.
     */
    public static boolean isBlockingTeleport(World world, BlockPos target, String ownerUUID) {
        int range = RiftStabilizerBlockEntity.RANGE;
        for (BlockPos check : BlockPos.iterate(
            target.add(-range, -range, -range),
            target.add(range, range, range))) {
            if (world.getBlockState(check).getBlock() instanceof RiftStabilizerBlock) {
                if (world.getBlockEntity(check) instanceof RiftStabilizerBlockEntity be) {
                    if (!be.getOwner().getUUID().equals(ownerUUID)) {
                        return true; // Owned by someone else, blocks teleport
                    }
                }
            }
        }
        return false;
    }
}
