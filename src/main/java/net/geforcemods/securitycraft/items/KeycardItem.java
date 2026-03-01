package net.geforcemods.securitycraft.items;

import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class KeycardItem extends Item {

    private final int level;

    public KeycardItem(Settings settings, int level) {
        super(settings);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.securitycraft.keycard.level", level).formatted(Formatting.GRAY));
    }
}
