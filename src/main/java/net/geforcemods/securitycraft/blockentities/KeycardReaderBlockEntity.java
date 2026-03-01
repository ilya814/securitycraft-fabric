package net.geforcemods.securitycraft.blockentities;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.api.IModuleInventory;
import net.geforcemods.securitycraft.items.KeycardItem;
import net.geforcemods.securitycraft.modules.ModuleType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;

public class KeycardReaderBlockEntity extends OwnableBlockEntity implements IModuleInventory {

    private int requiredLevel = 1;
    private boolean powered = false;
    private int powerTimer = 0;
    private final EnumSet<ModuleType> enabledModules = EnumSet.noneOf(ModuleType.class);

    public KeycardReaderBlockEntity(BlockPos pos, BlockState state) {
        super(SCContent.KEYCARD_READER_BLOCK_ENTITY, pos, state);
    }

    public int getRequiredLevel() { return requiredLevel; }
    public void setRequiredLevel(int level) { this.requiredLevel = Math.max(1, Math.min(5, level)); markDirty(); }
    public boolean isPowered() { return powered; }

    public boolean tryUseKeycard(PlayerEntity player) {
        ItemStack held = player.getMainHandStack();
        if (held.getItem() instanceof KeycardItem kc) {
            if (kc.getLevel() >= requiredLevel) {
                if (isModuleEnabled(ModuleType.ALLOWLIST)) {
                    // Check allowlist... simplified here
                }
                activateSignal(player);
                return true;
            } else {
                if (player instanceof ServerPlayerEntity sp) {
                    sp.sendMessage(Text.translatable("message.securitycraft.keycard_reader.wrong_level"), true);
                }
                return false;
            }
        }
        return false;
    }

    public void activateSignal(PlayerEntity player) {
        powered = true;
        powerTimer = 60; // 3 seconds
        markDirty();
        if (world != null) {
            world.updateNeighbors(pos, getCachedState().getBlock());
            world.scheduleBlockTick(pos, getCachedState().getBlock(), 60);
        }
        if (player instanceof ServerPlayerEntity sp) {
            sp.sendMessage(Text.translatable("message.securitycraft.keycard_reader.access_granted"), true);
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, KeycardReaderBlockEntity be) {
        if (world.isClient || !be.powered) return;
        be.powerTimer--;
        if (be.powerTimer <= 0) {
            be.powered = false;
            be.powerTimer = 0;
            world.updateNeighbors(pos, state.getBlock());
            be.markDirty();
        }
    }

    @Override
    public EnumSet<ModuleType> acceptedModules() {
        return EnumSet.of(ModuleType.ALLOWLIST, ModuleType.DENYLIST, ModuleType.REDSTONE, ModuleType.COVERT);
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
        nbt.putInt("requiredLevel", requiredLevel);
        nbt.putBoolean("powered", powered);
        nbt.putInt("powerTimer", powerTimer);
        int bits = 0;
        for (ModuleType t : enabledModules) bits |= (1 << t.ordinal());
        nbt.putInt("modules", bits);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup reg) {
        super.readNbt(nbt, reg);
        requiredLevel = nbt.getInt("requiredLevel");
        powered = nbt.getBoolean("powered");
        powerTimer = nbt.getInt("powerTimer");
        enabledModules.clear();
        int bits = nbt.getInt("modules");
        for (ModuleType t : ModuleType.values()) {
            if ((bits & (1 << t.ordinal())) != 0) enabledModules.add(t);
        }
    }
}
