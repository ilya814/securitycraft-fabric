package net.geforcemods.securitycraft.items;

import net.geforcemods.securitycraft.SCContent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

public class UniversalBlockReinforcer extends Item {

    private final int level;

    // Maps vanilla blocks to their reinforced SecurityCraft variants
    public static final Map<Block, Block> REINFORCEMENT_MAP = Map.ofEntries(
        Map.entry(Blocks.STONE, SCContent.REINFORCED_STONE),
        Map.entry(Blocks.COBBLESTONE, SCContent.REINFORCED_COBBLESTONE),
        Map.entry(Blocks.STONE_BRICKS, SCContent.REINFORCED_STONE_BRICKS),
        Map.entry(Blocks.OAK_PLANKS, SCContent.REINFORCED_OAK_PLANKS),
        Map.entry(Blocks.SPRUCE_PLANKS, SCContent.REINFORCED_SPRUCE_PLANKS),
        Map.entry(Blocks.BIRCH_PLANKS, SCContent.REINFORCED_BIRCH_PLANKS),
        Map.entry(Blocks.JUNGLE_PLANKS, SCContent.REINFORCED_JUNGLE_PLANKS),
        Map.entry(Blocks.ACACIA_PLANKS, SCContent.REINFORCED_ACACIA_PLANKS),
        Map.entry(Blocks.DARK_OAK_PLANKS, SCContent.REINFORCED_DARK_OAK_PLANKS),
        Map.entry(Blocks.GLASS, SCContent.REINFORCED_GLASS),
        Map.entry(Blocks.IRON_BARS, SCContent.REINFORCED_IRON_BARS),
        Map.entry(Blocks.OBSIDIAN, SCContent.REINFORCED_OBSIDIAN),
        Map.entry(Blocks.DIRT, SCContent.REINFORCED_DIRT),
        Map.entry(Blocks.GRAVEL, SCContent.REINFORCED_GRAVEL),
        Map.entry(Blocks.SAND, SCContent.REINFORCED_SAND),
        Map.entry(Blocks.BRICKS, SCContent.REINFORCED_BRICKS),
        Map.entry(Blocks.NETHER_BRICKS, SCContent.REINFORCED_NETHER_BRICKS),
        Map.entry(Blocks.IRON_BLOCK, SCContent.REINFORCED_IRON_BLOCK)
    );

    public UniversalBlockReinforcer(Settings settings, int level) {
        super(settings.maxDamage(level == 1 ? 100 : level == 2 ? 250 : 500));
        this.level = level;
    }

    public int getLevel() { return level; }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        if (world.isClient || player == null) return ActionResult.PASS;

        BlockState state = world.getBlockState(pos);
        Block reinforced = REINFORCEMENT_MAP.get(state.getBlock());

        if (reinforced == null) {
            player.sendMessage(Text.translatable("message.securitycraft.ubr.not_reinforceable"), true);
            return ActionResult.FAIL;
        }

        world.setBlockState(pos, reinforced.getDefaultState());
        player.sendMessage(Text.translatable("message.securitycraft.ubr.reinforced"), true);
        if (!player.isCreative()) context.getStack().damage(1, player, PlayerEntity.getSlotForHand(context.getHand()));
        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.securitycraft.ubr.level", level).formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("tooltip.securitycraft.ubr.uses",
            stack.getMaxDamage() - stack.getDamage(), stack.getMaxDamage()).formatted(Formatting.GRAY));
    }
}
