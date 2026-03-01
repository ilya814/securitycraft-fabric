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

public class ModuleItem extends Item {

    private final ModuleType moduleType;

    public ModuleItem(Settings settings, ModuleType moduleType) {
        super(settings);
        this.moduleType = moduleType;
    }

    public ModuleType getModuleType() { return moduleType; }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        if (world.isClient || player == null) return ActionResult.PASS;

        if (world.getBlockEntity(pos) instanceof IModuleInventory modInv
            && world.getBlockEntity(pos) instanceof IOwnable ownable) {
            if (!ownable.isOwnedBy(player) && !player.isCreative()) {
                player.sendMessage(Text.translatable("message.securitycraft.not_owner"), true);
                return ActionResult.FAIL;
            }
            if (!modInv.acceptsModule(moduleType)) {
                player.sendMessage(Text.translatable("message.securitycraft.module.not_accepted",
                    Text.translatable("item.securitycraft." + moduleType.getName() + "_module")), true);
                return ActionResult.FAIL;
            }
            if (modInv.isModuleEnabled(moduleType)) {
                player.sendMessage(Text.translatable("message.securitycraft.module.already_installed"), true);
                return ActionResult.FAIL;
            }
            modInv.addModule(moduleType);
            if (!player.isCreative()) player.getInventory().removeStack(
                player.getInventory().getSlotWithStack(context.getStack()), 1);
            player.sendMessage(Text.translatable("message.securitycraft.module.installed",
                Text.translatable("item.securitycraft." + moduleType.getName() + "_module"))
                .formatted(Formatting.GREEN), true);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.securitycraft.module." + moduleType.getName()).formatted(Formatting.GRAY));
    }
}
