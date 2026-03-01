package net.geforcemods.securitycraft.items;

import net.geforcemods.securitycraft.api.IPasswordProtected;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class CodebreakerItem extends Item {

    private static final float BREAK_CHANCE = 0.33f;

    public CodebreakerItem(Settings settings) {
        super(settings.maxDamage(3));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        if (world.isClient || player == null) return ActionResult.PASS;

        if (world.getBlockEntity(pos) instanceof IPasswordProtected ppb) {
            if (!ppb.isPasscodeSet()) {
                player.sendMessage(Text.translatable("message.securitycraft.codebreaker.no_passcode"), true);
                return ActionResult.FAIL;
            }

            if (Math.random() < BREAK_CHANCE) {
                // Success!
                if (player instanceof ServerPlayerEntity sp) {
                    ppb.onPasscodeActivated(sp);
                }
                player.sendMessage(Text.translatable("message.securitycraft.codebreaker.success")
                    .formatted(Formatting.GREEN), true);
            } else {
                player.sendMessage(Text.translatable("message.securitycraft.codebreaker.fail")
                    .formatted(Formatting.RED), true);
            }

            if (!player.isCreative()) {
                context.getStack().damage(1, player, PlayerEntity.getSlotForHand(context.getHand()));
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.securitycraft.codebreaker.chance").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("tooltip.securitycraft.codebreaker.uses",
            stack.getMaxDamage() - stack.getDamage()).formatted(Formatting.GRAY));
    }
}
