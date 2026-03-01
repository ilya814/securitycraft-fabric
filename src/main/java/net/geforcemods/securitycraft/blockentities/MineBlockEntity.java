package net.geforcemods.securitycraft.blockentities;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.api.IModuleInventory;
import net.geforcemods.securitycraft.modules.ModuleType;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class MineBlockEntity extends OwnableBlockEntity implements IModuleInventory {

    private boolean armed = true;
    private final EnumSet<ModuleType> enabledModules = EnumSet.noneOf(ModuleType.class);

    public MineBlockEntity(BlockPos pos, BlockState state) {
        super(SCContent.MINE_BLOCK_ENTITY, pos, state);
    }

    public boolean isArmed() { return armed; }
    public void setArmed(boolean armed) { this.armed = armed; markDirty(); }

    @Override
    public EnumSet<ModuleType> acceptedModules() {
        return EnumSet.of(ModuleType.ALLOWLIST);
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
        nbt.putBoolean("armed", armed);
        int bits = 0;
        for (ModuleType t : enabledModules) bits |= (1 << t.ordinal());
        nbt.putInt("modules", bits);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup reg) {
        super.readNbt(nbt, reg);
        armed = !nbt.contains("armed") || nbt.getBoolean("armed");
        enabledModules.clear();
        int bits = nbt.getInt("modules");
        for (ModuleType t : ModuleType.values()) {
            if ((bits & (1 << t.ordinal())) != 0) enabledModules.add(t);
        }
    }
}
