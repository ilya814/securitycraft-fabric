package net.geforcemods.securitycraft.blockentities;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.api.IModuleInventory;
import net.geforcemods.securitycraft.modules.ModuleType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class InventoryScannerBlockEntity extends OwnableBlockEntity implements IModuleInventory {

    // Slots 0-8 are "forbidden items", slot 9+ is contraband storage
    private final SimpleInventory inventory = new SimpleInventory(18);
    private boolean confiscateItems = false;
    private final EnumSet<ModuleType> enabledModules = EnumSet.noneOf(ModuleType.class);

    public InventoryScannerBlockEntity(BlockPos pos, BlockState state) {
        super(SCContent.INVENTORY_SCANNER_BLOCK_ENTITY, pos, state);
    }

    public SimpleInventory getInventory() {
        return inventory;
    }

    public boolean isConfiscatingItems() {
        return confiscateItems;
    }

    public void setConfiscateItems(boolean confiscate) {
        this.confiscateItems = confiscate;
        markDirty();
    }

    public static void tick(World world, BlockPos pos, BlockState state, InventoryScannerBlockEntity be) {
        if (world.isClient) return;
        // Only scan every second
        if (world.getTime() % 20 != 0) return;

        Box scanBox = new Box(pos).expand(0, 0, 0)
            .union(new Box(pos.up()));

        List<PlayerEntity> players = world.getEntitiesByClass(PlayerEntity.class,
            new Box(pos).expand(0.5, 1, 0.5), p -> !be.isOwnedBy(p));

        for (PlayerEntity player : players) {
            List<ItemStack> forbidden = new ArrayList<>();

            for (int i = 0; i < 9; i++) {
                ItemStack forbiddenItem = be.inventory.getStack(i);
                if (forbiddenItem.isEmpty()) continue;

                for (int j = 0; j < player.getInventory().size(); j++) {
                    ItemStack playerStack = player.getInventory().getStack(j);
                    if (!playerStack.isEmpty() && ItemStack.areItemsEqual(playerStack, forbiddenItem)) {
                        forbidden.add(playerStack);
                        if (be.confiscateItems) {
                            player.getInventory().removeStack(j);
                        }
                    }
                }
            }

            if (!forbidden.isEmpty()) {
                player.sendMessage(Text.translatable("message.securitycraft.inventory_scanner.detected"), true);
                if (!(player instanceof ServerPlayerEntity sp)) continue;
                if (!be.confiscateItems) {
                    // Block passage - push player back
                    player.teleport(player.getX(), player.getY(), player.getZ());
                }
            }
        }
    }

    @Override
    public EnumSet<ModuleType> acceptedModules() {
        return EnumSet.of(ModuleType.REDSTONE, ModuleType.ALLOWLIST, ModuleType.STORAGE, ModuleType.COVERT);
    }

    @Override
    public boolean isModuleEnabled(ModuleType type) { return enabledModules.contains(type); }

    @Override
    public void addModule(ModuleType type) {
        if (acceptsModule(type)) { enabledModules.add(type); markDirty(); }
    }

    @Override
    public void removeModule(ModuleType type) { enabledModules.remove(type); markDirty(); }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup reg) {
        super.writeNbt(nbt, reg);
        nbt.putBoolean("confiscate", confiscateItems);
        NbtCompound inv = new NbtCompound();
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                inv.put(String.valueOf(i), stack.encode(reg));
            }
        }
        nbt.put("inventory", inv);
        int bits = 0;
        for (ModuleType t : enabledModules) bits |= (1 << t.ordinal());
        nbt.putInt("modules", bits);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup reg) {
        super.readNbt(nbt, reg);
        confiscateItems = nbt.getBoolean("confiscate");
        if (nbt.contains("inventory")) {
            NbtCompound inv = nbt.getCompound("inventory");
            for (int i = 0; i < inventory.size(); i++) {
                if (inv.contains(String.valueOf(i))) {
                    inventory.setStack(i, ItemStack.fromNbtOrEmpty(reg, inv.getCompound(String.valueOf(i))));
                }
            }
        }
        enabledModules.clear();
        int bits = nbt.getInt("modules");
        for (ModuleType t : ModuleType.values()) {
            if ((bits & (1 << t.ordinal())) != 0) enabledModules.add(t);
        }
    }
}
