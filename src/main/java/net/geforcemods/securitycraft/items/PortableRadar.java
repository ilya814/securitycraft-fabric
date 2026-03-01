package net.geforcemods.securitycraft.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class PortableRadar extends Item {

    private static final int RANGE = 32;

    public PortableRadar(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) return TypedActionResult.pass(user.getStackInHand(hand));

        Box range = new Box(user.getBlockPos()).expand(RANGE);
        List<PlayerEntity> nearby = world.getEntitiesByClass(PlayerEntity.class, range,
            p -> !p.equals(user) && !p.isSpectator());

        if (nearby.isEmpty()) {
            user.sendMessage(Text.translatable("message.securitycraft.radar.no_players")
                .formatted(Formatting.GREEN), false);
        } else {
            user.sendMessage(Text.translatable("message.securitycraft.radar.found", nearby.size())
                .formatted(Formatting.YELLOW), false);
            for (PlayerEntity p : nearby) {
                int dist = (int) user.distanceTo(p);
                user.sendMessage(Text.literal(" - " + p.getName().getString() + " (" + dist + "m)")
                    .formatted(Formatting.GRAY), false);
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.securitycraft.radar", RANGE).formatted(Formatting.GRAY));
    }
}
