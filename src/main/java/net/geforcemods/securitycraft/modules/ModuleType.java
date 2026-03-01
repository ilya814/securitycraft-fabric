package net.geforcemods.securitycraft.modules;

import net.minecraft.item.Item;
import net.geforcemods.securitycraft.SCContent;

import java.util.function.Supplier;

public enum ModuleType {

    ALLOWLIST("allowlist"),
    DENYLIST("denylist"),
    REDSTONE("redstone"),
    SMART("smart"),
    SPEED("speed"),
    DISGUISE("disguise"),
    HARMING("harming"),
    STORAGE("storage"),
    COVERT("covert"),
    BLANKING("blanking"),
    SCHEDULE("schedule"),
    CROWD_CONTROL("crowd_control");

    private final String name;

    ModuleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Item asItem() {
        return switch (this) {
            case ALLOWLIST -> SCContent.ALLOWLIST_MODULE;
            case DENYLIST -> SCContent.DENYLIST_MODULE;
            case REDSTONE -> SCContent.REDSTONE_MODULE;
            case SMART -> SCContent.SMART_MODULE;
            case SPEED -> SCContent.SPEED_MODULE;
            case DISGUISE -> SCContent.DISGUISE_MODULE;
            case HARMING -> SCContent.HARMING_MODULE;
            case STORAGE -> SCContent.STORAGE_MODULE;
            case COVERT -> SCContent.COVERT_MODULE;
            case BLANKING -> SCContent.BLANKING_MODULE;
            case SCHEDULE -> SCContent.SCHEDULE_MODULE;
            case CROWD_CONTROL -> SCContent.CROWD_CONTROL_MODULE;
        };
    }
}
