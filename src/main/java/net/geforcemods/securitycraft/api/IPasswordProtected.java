package net.geforcemods.securitycraft.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public interface IPasswordProtected {

    String getPasscode();

    void setPasscode(String passcode);

    default boolean isPasscodeSet() {
        String code = getPasscode();
        return code != null && !code.isEmpty();
    }

    default boolean isCorrectPasscode(String code) {
        return getPasscode() != null && getPasscode().equals(code);
    }

    void onPasscodeActivated(ServerPlayerEntity player);

    default boolean canBypass(PlayerEntity player) {
        return false;
    }
}
