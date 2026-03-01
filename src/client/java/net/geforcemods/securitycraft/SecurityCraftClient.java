package net.geforcemods.securitycraft;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.geforcemods.securitycraft.network.SCPackets;
import net.minecraft.client.render.RenderLayer;

public class SecurityCraftClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Register render layers for translucent blocks
        BlockRenderLayerMap.INSTANCE.putBlock(SCContent.REINFORCED_GLASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(SCContent.LASER_MINE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(SCContent.FAKE_WATER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(SCContent.FAKE_LAVA, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(SCContent.REINFORCED_WHITE_STAINED_GLASS, RenderLayer.getCutout());

        // Register client-side network handlers
        registerClientPackets();

        SecurityCraft.LOGGER.info("SecurityCraft client initialized!");
    }

    private void registerClientPackets() {
        // S2C packet handlers would go here (screen open packets etc.)
    }
}
