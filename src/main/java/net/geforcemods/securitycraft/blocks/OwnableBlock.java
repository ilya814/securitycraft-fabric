package net.geforcemods.securitycraft.blocks;

import net.geforcemods.securitycraft.api.IOwnable;
import net.geforcemods.securitycraft.api.Owner;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class OwnableBlock extends Block implements BlockEntityProvider {

    public OwnableBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient && placer instanceof PlayerEntity player) {
            if (world.getBlockEntity(pos) instanceof IOwnable ownable) {
                ownable.setOwner(new Owner(player.getName().getString(), player.getUuidAsString()));
            }
        }
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && !player.isCreative()) {
            if (world.getBlockEntity(pos) instanceof IOwnable ownable) {
                if (!ownable.isOwnedBy(player)) {
                    player.sendMessage(Text.translatable("message.securitycraft.not_owner"), true);
                    return state;
                }
            }
        }
        return super.onBreak(world, pos, state, player);
    }
}
