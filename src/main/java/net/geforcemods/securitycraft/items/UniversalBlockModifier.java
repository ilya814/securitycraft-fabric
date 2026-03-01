package net.geforcemods.securitycraft.items;

import net.geforcemods.securitycraft.api.IModuleInventory;
import net.geforcemods.securitycraft.api.IOwnable;
import net.geforcemods.securitycraft.modules.ModuleType;
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

public class UniversalBlockModifier extends Item {

    public UniversalBlockModifier(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        if (world.isClient || player == null) return ActionResult.PASS;

        if (world.getBlockEntity(pos) instanceof IOwnable ownable) {
            if (!ownable.isOwnedBy(player) && !player.isCreative()) {
                player.sendMessage(Text.translatable("message.securitycraft.not_owner"), true);
                return ActionResult.FAIL;
            }
            // Open the configuration screen
            player.sendMessage(Text.translatable("message.securitycraft.ubm.open"), false);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.securitycraft.ubm").formatted(Formatting.GRAY));
    }
}
