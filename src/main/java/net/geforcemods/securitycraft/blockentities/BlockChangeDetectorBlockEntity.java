package net.geforcemods.securitycraft.blockentities;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.api.IModuleInventory;
import net.geforcemods.securitycraft.modules.ModuleType;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class BlockChangeDetectorBlockEntity extends OwnableBlockEntity implements IModuleInventory {

    public record LogEntry(long time, String playerName, BlockPos changedPos, String blockName) {
        public NbtCompound toNbt() {
            NbtCompound nbt = new NbtCompound();
            nbt.putLong("time", time);
            nbt.putString("player", playerName);
            nbt.putLong("pos", changedPos.asLong());
            nbt.putString("block", blockName);
            return nbt;
        }
        public static LogEntry fromNbt(NbtCompound nbt) {
            return new LogEntry(
                nbt.getLong("time"),
                nbt.getString("player"),
                BlockPos.fromLong(nbt.getLong("pos")),
                nbt.getString("block")
            );
        }
    }

    private int range = 5;
    private final List<LogEntry> log = new ArrayList<>();
    private final EnumSet<ModuleType> enabledModules = EnumSet.noneOf(ModuleType.class);

    public BlockChangeDetectorBlockEntity(BlockPos pos, BlockState state) {
        super(SCContent.BLOCK_CHANGE_DETECTOR_BLOCK_ENTITY, pos, state);
    }

    public int getRange() { return range; }
    public void setRange(int range) { this.range = Math.max(1, Math.min(16, range)); markDirty(); }

    public List<LogEntry> getLog() { return log; }

    public void addLogEntry(LogEntry entry) {
        log.add(0, entry);
        if (log.size() > 100) log.remove(log.size() - 1);
        markDirty();
    }

    @Override
    public EnumSet<ModuleType> acceptedModules() {
        return EnumSet.of(ModuleType.REDSTONE, ModuleType.SMART);
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
        nbt.putInt("range", range);
        NbtList logList = new NbtList();
        for (LogEntry e : log) logList.add(e.toNbt());
        nbt.put("log", logList);
        int bits = 0;
        for (ModuleType t : enabledModules) bits |= (1 << t.ordinal());
        nbt.putInt("modules", bits);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup reg) {
        super.readNbt(nbt, reg);
        range = nbt.getInt("range");
        log.clear();
        NbtList logList = nbt.getList("log", 10);
        for (int i = 0; i < logList.size(); i++) log.add(LogEntry.fromNbt(logList.getCompound(i)));
        enabledModules.clear();
        int bits = nbt.getInt("modules");
        for (ModuleType t : ModuleType.values()) {
            if ((bits & (1 << t.ordinal())) != 0) enabledModules.add(t);
        }
    }
}
