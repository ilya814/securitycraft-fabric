package net.geforcemods.securitycraft.blocks;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.blockentities.SentryBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SentryBlock extends OwnableBlock {

    public SentryBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                               BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;
        if (world.getBlockEntity(pos) instanceof SentryBlockEntity be) {
            if (!be.isOwnedBy(player)) return ActionResult.FAIL;
            // Cycle mode: AGGRESSIVE -> CAMOUFLAGE -> TURRET -> AGGRESSIVE
            SentryBlockEntity.Mode next = switch (be.getMode()) {
                case AGGRESSIVE -> SentryBlockEntity.Mode.CAMOUFLAGE;
                case CAMOUFLAGE -> SentryBlockEntity.Mode.TURRET;
                case TURRET -> SentryBlockEntity.Mode.AGGRESSIVE;
            };
            be.setMode(next);
            player.sendMessage(net.minecraft.text.Text.translatable(
                "message.securitycraft.sentry.mode." + next.name().toLowerCase()), true);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SentryBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient) return null;
        return checkType(type, SCContent.SENTRY_BLOCK_ENTITY, SentryBlockEntity::tick);
    }

    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(
        BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }
}
