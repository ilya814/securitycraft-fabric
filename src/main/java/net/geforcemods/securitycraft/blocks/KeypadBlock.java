package net.geforcemods.securitycraft.blocks;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.api.IOwnable;
import net.geforcemods.securitycraft.api.IPasswordProtected;
import net.geforcemods.securitycraft.blockentities.KeypadBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class KeypadBlock extends OwnableBlock {

    public static final BooleanProperty POWERED = Properties.POWERED;

    public KeypadBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
            .with(HorizontalFacingBlock.FACING, Direction.NORTH)
            .with(POWERED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<net.minecraft.block.Block, BlockState> builder) {
        builder.add(HorizontalFacingBlock.FACING, POWERED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(HorizontalFacingBlock.FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                               BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;

        if (world.getBlockEntity(pos) instanceof KeypadBlockEntity be) {
            // Owner can reconfigure
            if (be.isOwnedBy(player)) {
                player.sendMessage(Text.translatable("gui.securitycraft.keypad.configure"), false);
                // In full implementation: open config screen via networking
                return ActionResult.SUCCESS;
            }

            // Non-owner: open passcode entry
            if (player instanceof ServerPlayerEntity sp) {
                if (!be.isPasscodeSet()) {
                    player.sendMessage(Text.translatable("message.securitycraft.keypad.no_passcode"), true);
                    return ActionResult.SUCCESS;
                }
                // In full implementation: open passcode screen via networking
                SCContent.openKeypadScreen(sp, pos);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return state.get(POWERED);
    }

    @Override
    public int getWeakRedstonePower(BlockState state, net.minecraft.world.BlockView world, BlockPos pos, Direction direction) {
        return state.get(POWERED) ? 15 : 0;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new KeypadBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return null;
    }
}
