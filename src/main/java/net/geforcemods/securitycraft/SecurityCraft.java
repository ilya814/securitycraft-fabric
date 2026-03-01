package net.geforcemods.securitycraft;

import net.fabricmc.api.ModInitializer;
import net.geforcemods.securitycraft.network.SCPackets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityCraft implements ModInitializer {

    public static final String MOD_ID = "securitycraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("SecurityCraft initializing...");

        SCContent.registerAll();
        SCPackets.registerServerPackets();

        LOGGER.info("SecurityCraft initialized!");
    }
}
