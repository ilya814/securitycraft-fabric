package net.geforcemods.securitycraft.blockentities;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.api.IModuleInventory;
import net.geforcemods.securitycraft.modules.ModuleType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;
import java.util.UUID;

public class SecurityCameraBlockEntity extends OwnableBlockEntity implements IModuleInventory {

    private String customName = "";
    private UUID viewingPlayer = null;
    private float cameraYaw = 0f;
    private float cameraPitch = 0f;
    private final EnumSet<ModuleType> enabledModules = EnumSet.noneOf(ModuleType.class);

    public SecurityCameraBlockEntity(BlockPos pos, BlockState state) {
        super(SCContent.SECURITY_CAMERA_BLOCK_ENTITY, pos, state);
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String name) {
        this.customName = name;
        markDirty();
    }

    public boolean isBeingViewed() {
        return viewingPlayer != null;
    }

    public UUID getViewingPlayer() {
        return viewingPlayer;
    }

    public void startViewing(PlayerEntity player) {
        this.viewingPlayer = player.getUuid();
        markDirty();
    }

    public void stopViewing() {
        this.viewingPlayer = null;
        markDirty();
    }

    public float getCameraYaw() {
        return cameraYaw;
    }

    public void setCameraYaw(float yaw) {
        this.cameraYaw = yaw;
        markDirty();
    }

    public float getCameraPitch() {
        return cameraPitch;
    }

    public void setCameraPitch(float pitch) {
        this.cameraPitch = pitch;
        markDirty();
    }

    @Override
    public EnumSet<ModuleType> acceptedModules() {
        return EnumSet.of(ModuleType.REDSTONE, ModuleType.ALLOWLIST, ModuleType.SMART, ModuleType.COVERT);
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
        nbt.putString("customName", customName);
        nbt.putFloat("cameraYaw", cameraYaw);
        nbt.putFloat("cameraPitch", cameraPitch);
        if (viewingPlayer != null) nbt.putUuid("viewingPlayer", viewingPlayer);
        int moduleBits = 0;
        for (ModuleType type : enabledModules) moduleBits |= (1 << type.ordinal());
        nbt.putInt("modules", moduleBits);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        customName = nbt.getString("customName");
        cameraYaw = nbt.getFloat("cameraYaw");
        cameraPitch = nbt.getFloat("cameraPitch");
        if (nbt.contains("viewingPlayer")) viewingPlayer = nbt.getUuid("viewingPlayer");
        enabledModules.clear();
        int moduleBits = nbt.getInt("modules");
        for (ModuleType type : ModuleType.values()) {
            if ((moduleBits & (1 << type.ordinal())) != 0) enabledModules.add(type);
        }
    }
}
