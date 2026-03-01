package net.geforcemods.securitycraft.blockentities;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.api.IModuleInventory;
import net.geforcemods.securitycraft.modules.ModuleType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;

public class SentryBlockEntity extends OwnableBlockEntity implements IModuleInventory {

    public enum Mode { CAMOUFLAGE, AGGRESSIVE, TURRET }

    private Mode mode = Mode.AGGRESSIVE;
    private float targetYaw = 0f;
    private int cooldown = 0;
    private final EnumSet<ModuleType> enabledModules = EnumSet.noneOf(ModuleType.class);
    public static final int RANGE = 16;
    public static final int SHOOT_COOLDOWN = 20;

    public SentryBlockEntity(BlockPos pos, BlockState state) {
        super(SCContent.SENTRY_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, SentryBlockEntity be) {
        if (world.isClient) return;
        if (be.cooldown > 0) {
            be.cooldown--;
            return;
        }
        if (be.mode == Mode.CAMOUFLAGE) return;

        Box range = new Box(pos).expand(RANGE);
        List<LivingEntity> targets = world.getEntitiesByClass(LivingEntity.class, range, entity -> {
            if (entity instanceof PlayerEntity player) {
                return !be.isOwnedBy(player)
                    && !player.isCreative()
                    && !player.isSpectator()
                    && (be.mode == Mode.AGGRESSIVE || be.mode == Mode.TURRET);
            }
            return entity instanceof MobEntity
                && (be.isModuleEnabled(ModuleType.CROWD_CONTROL));
        });

        if (!targets.isEmpty()) {
            LivingEntity target = targets.get(0);
            double dx = target.getX() - pos.getX();
            double dz = target.getZ() - pos.getZ();
            be.targetYaw = (float) Math.toDegrees(Math.atan2(-dx, dz));

            // Shoot snowball-like projectile at target
            if (!world.isClient) {
                net.minecraft.entity.projectile.SnowballEntity bullet =
                    new net.minecraft.entity.projectile.SnowballEntity(world,
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                double velX = target.getX() - (pos.getX() + 0.5);
                double velY = target.getY() + target.getHeight() / 2.0 - (pos.getY() + 0.5);
                double velZ = target.getZ() - (pos.getZ() + 0.5);
                bullet.setVelocity(velX, velY, velZ, 1.6f, 0f);
                world.spawnEntity(bullet);
                be.cooldown = SHOOT_COOLDOWN;
                be.markDirty();
            }
        }
    }

    public Mode getMode() { return mode; }
    public void setMode(Mode mode) { this.mode = mode; markDirty(); }
    public float getTargetYaw() { return targetYaw; }

    @Override
    public EnumSet<ModuleType> acceptedModules() {
        return EnumSet.of(ModuleType.ALLOWLIST, ModuleType.SPEED, ModuleType.HARMING, ModuleType.CROWD_CONTROL, ModuleType.COVERT);
    }

    @Override
    public boolean isModuleEnabled(ModuleType type) { return enabledModules.contains(type); }

    @Override
    public void addModule(ModuleType type) {
        if (acceptsModule(type)) { enabledModules.add(type); markDirty(); }
    }

    @Override
    public void removeModule(ModuleType type) { enabledModules.remove(type); markDirty(); }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup reg) {
        super.writeNbt(nbt, reg);
        nbt.putString("mode", mode.name());
        nbt.putFloat("targetYaw", targetYaw);
        nbt.putInt("cooldown", cooldown);
        int bits = 0;
        for (ModuleType t : enabledModules) bits |= (1 << t.ordinal());
        nbt.putInt("modules", bits);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup reg) {
        super.readNbt(nbt, reg);
        try { mode = Mode.valueOf(nbt.getString("mode")); } catch (Exception e) { mode = Mode.AGGRESSIVE; }
        targetYaw = nbt.getFloat("targetYaw");
        cooldown = nbt.getInt("cooldown");
        enabledModules.clear();
        int bits = nbt.getInt("modules");
        for (ModuleType t : ModuleType.values()) {
            if ((bits & (1 << t.ordinal())) != 0) enabledModules.add(t);
        }
    }
}
