package net.geforcemods.securitycraft.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.SecurityCraft;
import net.geforcemods.securitycraft.api.IPasswordProtected;
import net.geforcemods.securitycraft.blockentities.KeypadBlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class SCPackets {

    // C2S: Player submits passcode at keypad
    public record PasscodePacket(BlockPos pos, String passcode) implements CustomPayload {
        public static final CustomPayload.Id<PasscodePacket> ID = new CustomPayload.Id<>(
            Identifier.of(SecurityCraft.MOD_ID, "passcode"));
        public static final PacketCodec<PacketByteBuf, PasscodePacket> CODEC =
            PacketCodec.tuple(BlockPos.PACKET_CODEC, PasscodePacket::pos,
                PacketCodecs.STRING, PasscodePacket::passcode,
                PasscodePacket::new);
        @Override public CustomPayload.Id<? extends CustomPayload> getId() { return ID; }
    }

    // C2S: Owner sets passcode
    public record SetPasscodePacket(BlockPos pos, String passcode) implements CustomPayload {
        public static final CustomPayload.Id<SetPasscodePacket> ID = new CustomPayload.Id<>(
            Identifier.of(SecurityCraft.MOD_ID, "set_passcode"));
        public static final PacketCodec<PacketByteBuf, SetPasscodePacket> CODEC =
            PacketCodec.tuple(BlockPos.PACKET_CODEC, SetPasscodePacket::pos,
                PacketCodecs.STRING, SetPasscodePacket::passcode,
                SetPasscodePacket::new);
        @Override public CustomPayload.Id<? extends CustomPayload> getId() { return ID; }
    }

    // C2S: Camera view direction update
    public record CameraViewPacket(BlockPos pos, float yaw, float pitch) implements CustomPayload {
        public static final CustomPayload.Id<CameraViewPacket> ID = new CustomPayload.Id<>(
            Identifier.of(SecurityCraft.MOD_ID, "camera_view"));
        public static final PacketCodec<PacketByteBuf, CameraViewPacket> CODEC =
            PacketCodec.tuple(BlockPos.PACKET_CODEC, CameraViewPacket::pos,
                PacketCodecs.FLOAT, CameraViewPacket::yaw,
                PacketCodecs.FLOAT, CameraViewPacket::pitch,
                CameraViewPacket::new);
        @Override public CustomPayload.Id<? extends CustomPayload> getId() { return ID; }
    }

    // C2S: Stop viewing camera
    public record StopViewingCameraPacket(BlockPos pos) implements CustomPayload {
        public static final CustomPayload.Id<StopViewingCameraPacket> ID = new CustomPayload.Id<>(
            Identifier.of(SecurityCraft.MOD_ID, "stop_camera"));
        public static final PacketCodec<PacketByteBuf, StopViewingCameraPacket> CODEC =
            PacketCodec.tuple(BlockPos.PACKET_CODEC, StopViewingCameraPacket::pos,
                StopViewingCameraPacket::new);
        @Override public CustomPayload.Id<? extends CustomPayload> getId() { return ID; }
    }

    public static void registerServerPackets() {
        PayloadTypeRegistry.playC2S().register(PasscodePacket.ID, PasscodePacket.CODEC);
        PayloadTypeRegistry.playC2S().register(SetPasscodePacket.ID, SetPasscodePacket.CODEC);
        PayloadTypeRegistry.playC2S().register(CameraViewPacket.ID, CameraViewPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(StopViewingCameraPacket.ID, StopViewingCameraPacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(PasscodePacket.ID, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayerEntity player = context.player();
                BlockPos pos = payload.pos();
                if (player.getWorld().getBlockEntity(pos) instanceof KeypadBlockEntity be) {
                    if (!be.isPasscodeSet()) return;
                    if (be.isCorrectPasscode(payload.passcode())) {
                        be.onPasscodeActivated(player);
                        // Activate redstone / open door
                        player.getWorld().setBlockState(pos,
                            player.getWorld().getBlockState(pos)
                                .with(net.geforcemods.securitycraft.blocks.KeypadBlock.POWERED, true));
                        // Schedule deactivation
                        player.getWorld().scheduleBlockTick(pos,
                            player.getWorld().getBlockState(pos).getBlock(), 40);
                    } else {
                        player.sendMessage(net.minecraft.text.Text.translatable(
                            "message.securitycraft.keypad.wrong_passcode"), true);
                    }
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(SetPasscodePacket.ID, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayerEntity player = context.player();
                BlockPos pos = payload.pos();
                if (player.getWorld().getBlockEntity(pos) instanceof IPasswordProtected ppb) {
                    if (ppb instanceof net.geforcemods.securitycraft.api.IOwnable ownable
                        && ownable.isOwnedBy(player)) {
                        ppb.setPasscode(payload.passcode());
                        player.sendMessage(net.minecraft.text.Text.translatable(
                            "message.securitycraft.passcode_set"), true);
                    }
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(CameraViewPacket.ID, (payload, context) -> {
            context.server().execute(() -> {
                if (context.player().getWorld().getBlockEntity(payload.pos())
                    instanceof net.geforcemods.securitycraft.blockentities.SecurityCameraBlockEntity cam) {
                    cam.setCameraYaw(payload.yaw());
                    cam.setCameraPitch(payload.pitch());
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(StopViewingCameraPacket.ID, (payload, context) -> {
            context.server().execute(() -> {
                if (context.player().getWorld().getBlockEntity(payload.pos())
                    instanceof net.geforcemods.securitycraft.blockentities.SecurityCameraBlockEntity cam) {
                    cam.stopViewing();
                }
            });
        });
    }
}
