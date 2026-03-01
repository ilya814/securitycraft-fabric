package net.geforcemods.securitycraft.blockentities;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.api.IModuleInventory;
import net.geforcemods.securitycraft.modules.ModuleType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;

public class TrophySystemBlockEntity extends OwnableBlockEntity implements IModuleInventory {

    public static final int RANGE = 8;
    public static final int COOLDOWN_TICKS = 40;
    private int cooldown = 0;
    private final EnumSet<ModuleType> enabledModules = EnumSet.noneOf(ModuleType.class);

    public TrophySystemBlockEntity(BlockPos pos, BlockState state) {
        super(SCContent.TROPHY_SYSTEM_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, TrophySystemBlockEntity be) {
        if (world.isClient) return;
        if (be.cooldown > 0) { be.cooldown--; return; }

        Box range = new Box(pos).expand(RANGE);
        List<ProjectileEntity> projectiles = world.getEntitiesByClass(
            ProjectileEntity.class, range, p -> true);

        for (ProjectileEntity proj : projectiles) {
            if (proj.getOwner() != null && be.isOwnedBy(proj.getOwner() instanceof net.minecraft.entity.player.PlayerEntity pl ? pl : null ? (net.minecraft.entity.player.PlayerEntity) proj.getOwner() : null)) {
                continue;
            }
            // Explode the projectile
            world.createExplosion(null, proj.getX(), proj.getY(), proj.getZ(), 1.0f, false, net.minecraft.world.explosion.ExplosionBehavior.DEFAULT);
            proj.discard();
            be.cooldown = COOLDOWN_TICKS;
            be.markDirty();
            break;
        }
    }

    @Override
    public EnumSet<ModuleType> acceptedModules() {
        return EnumSet.of(ModuleType.ALLOWLIST, ModuleType.REDSTONE);
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
        nbt.putInt("cooldown", cooldown);
        int bits = 0;
        for (ModuleType t : enabledModules) bits |= (1 << t.ordinal());
        nbt.putInt("modules", bits);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup reg) {
        super.readNbt(nbt, reg);
        cooldown = nbt.getInt("cooldown");
        enabledModules.clear();
        int bits = nbt.getInt("modules");
        for (ModuleType t : ModuleType.values()) {
            if ((bits & (1 << t.ordinal())) != 0) enabledModules.add(t);
        }
    }
}
