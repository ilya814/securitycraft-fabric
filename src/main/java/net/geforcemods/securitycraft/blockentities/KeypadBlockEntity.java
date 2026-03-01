package net.geforcemods.securitycraft.blockentities;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.api.IModuleInventory;
import net.geforcemods.securitycraft.api.IPasswordProtected;
import net.geforcemods.securitycraft.api.Owner;
import net.geforcemods.securitycraft.modules.ModuleType;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class KeypadBlockEntity extends OwnableBlockEntity implements IPasswordProtected, IModuleInventory {

    private String passcode = "";
    private final EnumSet<ModuleType> enabledModules = EnumSet.noneOf(ModuleType.class);

    public KeypadBlockEntity(BlockPos pos, BlockState state) {
        super(SCContent.KEYPAD_BLOCK_ENTITY, pos, state);
    }

    @Override
    public String getPasscode() {
        return passcode;
    }

    @Override
    public void setPasscode(String passcode) {
        this.passcode = passcode;
        markDirty();
    }

    @Override
    public void onPasscodeActivated(ServerPlayerEntity player) {
        // Signal the keypad block to open the door / emit redstone
        if (world != null) {
            world.setBlockBreakingInfo(-1, pos, 0);
        }
        player.sendMessage(Text.translatable("message.securitycraft.keypad.access_granted"), true);
    }

    @Override
    public EnumSet<ModuleType> acceptedModules() {
        return EnumSet.of(
            ModuleType.ALLOWLIST,
            ModuleType.DENYLIST,
            ModuleType.REDSTONE,
            ModuleType.SMART,
            ModuleType.COVERT
        );
    }

    @Override
    public boolean isModuleEnabled(ModuleType type) {
        return enabledModules.contains(type);
    }

    @Override
    public void addModule(ModuleType type) {
        if (acceptsModule(type)) {
            enabledModules.add(type);
            markDirty();
        }
    }

    @Override
    public void removeModule(ModuleType type) {
        enabledModules.remove(type);
        markDirty();
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putString("passcode", passcode);
        int moduleBits = 0;
        for (ModuleType type : enabledModules) {
            moduleBits |= (1 << type.ordinal());
        }
        nbt.putInt("modules", moduleBits);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        passcode = nbt.getString("passcode");
        enabledModules.clear();
        int moduleBits = nbt.getInt("modules");
        for (ModuleType type : ModuleType.values()) {
            if ((moduleBits & (1 << type.ordinal())) != 0) {
                enabledModules.add(type);
            }
        }
    }
}
