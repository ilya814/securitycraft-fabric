package net.geforcemods.securitycraft.api;

import net.geforcemods.securitycraft.modules.ModuleType;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;

public interface IModuleInventory {

    EnumSet<ModuleType> acceptedModules();

    boolean isModuleEnabled(ModuleType type);

    void addModule(ModuleType type);

    void removeModule(ModuleType type);

    default boolean acceptsModule(ModuleType type) {
        return acceptedModules().contains(type);
    }
}
