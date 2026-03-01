package net.geforcemods.securitycraft.api;

import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

public class Owner {

    private String name;
    private String uuid;

    public static final Owner EMPTY = new Owner("", "");

    public Owner(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public Owner(String name, UUID uuid) {
        this(name, uuid.toString());
    }

    public String getName() {
        return name;
    }

    public String getUUID() {
        return uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public boolean isOwner(net.minecraft.entity.player.PlayerEntity player) {
        return player.getUuidAsString().equals(uuid) || player.getName().getString().equals(name);
    }

    public boolean isEmpty() {
        return (name == null || name.isEmpty()) && (uuid == null || uuid.isEmpty());
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("ownerName", name != null ? name : "");
        nbt.putString("ownerUUID", uuid != null ? uuid : "");
        return nbt;
    }

    public static Owner fromNbt(NbtCompound nbt) {
        return new Owner(
            nbt.getString("ownerName"),
            nbt.getString("ownerUUID")
        );
    }

    @Override
    public String toString() {
        return "Owner{name='" + name + "', uuid='" + uuid + "'}";
    }
}
