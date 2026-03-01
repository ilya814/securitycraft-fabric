package net.geforcemods.securitycraft.items;

import net.geforcemods.securitycraft.blockentities.MineBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.ExplosionBehavior;

import java.util.List;

public class MineRemoteAccessTool extends Item {

    public enum Action { NONE, ARM, DEFUSE, DETONATE }

    public MineRemoteAccessTool(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        if (world.isClient || player == null) return ActionResult.PASS;

        if (world.getBlockEntity(pos) instanceof MineBlockEntity mine) {
            if (!mine.isOwnedBy(player) && !player.isCreative()) {
                player.sendMessage(Text.translatable("message.securitycraft.not_owner"), true);
                return ActionResult.FAIL;
            }
            // Bind mine to tool
            ItemStack stack = context.getStack();
            NbtCompound nbt = stack.getOrCreateNbt();
            nbt.putLong("boundMine", pos.asLong());
            nbt.putString("dimension", world.getRegistryKey().getValue().toString());
            player.sendMessage(Text.translatable("message.securitycraft.mrat.bound",
                pos.getX(), pos.getY(), pos.getZ()).formatted(Formatting.GREEN), true);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient) return TypedActionResult.pass(player.getStackInHand(hand));
        ItemStack stack = player.getStackInHand(hand);
        NbtCompound nbt = stack.getNbt();
        if (nbt == null || !nbt.contains("boundMine")) {
            player.sendMessage(Text.translatable("message.securitycraft.mrat.not_bound"), true);
            return TypedActionResult.fail(stack);
        }

        BlockPos minePos = BlockPos.fromLong(nbt.getLong("boundMine"));
        if (world.getBlockEntity(minePos) instanceof MineBlockEntity mine) {
            if (player.isSneaking()) {
                // Detonate
                player.sendMessage(Text.translatable("message.securitycraft.mrat.detonated"), true);
                world.createExplosion(null, minePos.getX() + 0.5, minePos.getY() + 0.5, minePos.getZ() + 0.5,
                    4f, true, ExplosionBehavior.DEFAULT);
                world.setBlockState(minePos, net.minecraft.block.Blocks.AIR.getDefaultState());
            } else {
                // Toggle arm/defuse
                boolean newArmed = !mine.isArmed();
                mine.setArmed(newArmed);
                player.sendMessage(Text.translatable(newArmed
                    ? "message.securitycraft.mrat.armed"
                    : "message.securitycraft.mrat.defused"), true);
            }
            return TypedActionResult.success(stack);
        }
        player.sendMessage(Text.translatable("message.securitycraft.mrat.mine_gone"), true);
        return TypedActionResult.fail(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        NbtCompound nbt = stack.getNbt();
        if (nbt != null && nbt.contains("boundMine")) {
            BlockPos pos = BlockPos.fromLong(nbt.getLong("boundMine"));
            tooltip.add(Text.translatable("tooltip.securitycraft.mrat.bound",
                pos.getX(), pos.getY(), pos.getZ()).formatted(Formatting.GREEN));
        } else {
            tooltip.add(Text.translatable("tooltip.securitycraft.mrat.unbound").formatted(Formatting.GRAY));
        }
        tooltip.add(Text.translatable("tooltip.securitycraft.mrat.right_click").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("tooltip.securitycraft.mrat.sneak_right_click").formatted(Formatting.GRAY));
    }
}
