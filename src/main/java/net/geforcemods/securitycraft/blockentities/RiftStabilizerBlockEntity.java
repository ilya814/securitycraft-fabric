package net.geforcemods.securitycraft.blockentities;

import net.geforcemods.securitycraft.SCContent;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class RiftStabilizerBlockEntity extends OwnableBlockEntity {

    public static final int RANGE = 8;

    public RiftStabilizerBlockEntity(BlockPos pos, BlockState state) {
        super(SCContent.RIFT_STABILIZER_BLOCK_ENTITY, pos, state);
    }
}
