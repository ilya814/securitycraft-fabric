package net.geforcemods.securitycraft.blocks;

import net.geforcemods.securitycraft.api.Owner;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ReinforcedBlock extends Block {

    private Owner owner = Owner.EMPTY;

    public ReinforcedBlock(Settings settings) {
        // Reinforced blocks are blast-resistant and hard
        super(settings.hardness(-1.0f).resistance(3600000.0f).allowsSpawning((state, world, pos, type) -> false));
    }

    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        // Unbreakable by non-owners in survival
        if (!player.isCreative()) {
            return -1.0f;
        }
        return super.calcBlockBreakingDelta(state, player, world, pos);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    protected boolean canBeReplaced(BlockState state, net.minecraft.item.ItemPlacementContext context) {
        return false;
    }
}
