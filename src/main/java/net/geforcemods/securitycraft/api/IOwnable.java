package net.geforcemods.securitycraft.api;

import net.minecraft.entity.player.PlayerEntity;

public interface IOwnable {

    Owner getOwner();

    void setOwner(Owner owner);

    default void setOwner(String name, String uuid) {
        setOwner(new Owner(name, uuid));
    }

    default boolean isOwnedBy(PlayerEntity player) {
        return getOwner().isOwner(player);
    }

    default boolean hasOwner() {
        return getOwner() != null && !getOwner().isEmpty();
    }
}
