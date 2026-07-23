package net.uhhitscam.knightfall.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;
import net.uhhitscam.knightfall.component.AmmoData;
import net.uhhitscam.knightfall.component.AmmoTypeData;
import net.uhhitscam.knightfall.component.FireCoolDownData;
import net.uhhitscam.knightfall.component.FiringModeData;
import net.uhhitscam.knightfall.component.ModDataComponentTypes;
import net.uhhitscam.knightfall.component.ReloadNSwitchCoolDownData;
import net.uhhitscam.knightfall.entity.ModEntities;
import net.uhhitscam.knightfall.entity.custom.*;
import net.uhhitscam.knightfall.event.ProjectileWeaponUseEvent;
import net.uhhitscam.knightfall.item.ModItems;
import net.uhhitscam.knightfall.network.CSProjectileWeaponRecoilPacket;
import net.uhhitscam.knightfall.network.PayloadRegister;
import net.uhhitscam.knightfall.sound.ModSounds;
import net.uhhitscam.knightfall.util.Repulse;
import net.uhhitscam.knightfall.util.WeaponAimRules;
import net.uhhitscam.knightfall.util.WeaponSoundsUtil;
import net.uhhitscam.knightfall.util.WeaponTargeting;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;

public class ProjectileItem extends Item {
    private static final double TARGETING_RANGE = 128.0;
    private static final int DC15S_SIDEARM_RECHARGE_INTERVAL_TICKS = 20;

    private final float projectileSpeed;
    private final int maxAmmo;
    private final int burstRate;
    private final int scatterShots;
    private final EnumMap<FiringMode, ProjectileWeaponStats> stats;
    private final BeamWeaponStats beamStats;
    private final List<FiringMode> firingModes;
    private final FiringMode defaultFiringMode;
    private final AmmoType typAmmoType;
    private final WeaponClassification classification;
    private final WeaponName projectileWeaponName;
    private final ProjectileWeaponUI ui;
    private final ProjectileWeaponTiming timing;

    public ProjectileItem(Properties properties, ProjectileWeaponDefinition definition) {
        super(properties);
        this.projectileSpeed = definition.projectileSpeed();
        this.maxAmmo = definition.maxAmmo();
        this.burstRate = definition.burstRate();
        this.scatterShots = definition.scatterShots();
        this.stats = definition.stats();
        this.beamStats = definition.beamStats();
        this.firingModes = definition.firingModes();
        this.ui = definition.ui();
        this.defaultFiringMode = definition.defaultFiringMode();
        this.typAmmoType = definition.ammoType();
        this.classification = definition.classification();
        this.projectileWeaponName = definition.weaponName();
        this.timing = definition.timing();
    }

    public long getReloadTime(FiringMode firingMode) {
        return timing.reloadTicks(firingMode);
    }

    public long getSwitchTime(FiringMode firingMode) {
        return timing.switchTicks(firingMode);
    }

    public long getEquipTime() {
        return timing.equipTicks();
    }

    public int getChargeThreshold() {
        return timing.chargeThresholdTicks();
    }

    public int getBurstRate() {
        return burstRate;
    }

    public ProjectileWeaponStats getStats(FiringMode firingMode) {
        return stats.get(firingMode);
    }

    public ProjectileWeaponUI getUI() {
        return ui;
    }

    @Nullable
    public BeamWeaponStats getBeamStats() {
        return beamStats;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResultHolder.pass(stack);
        }

        if (level.isClientSide) {
            NeoForge.EVENT_BUS.post(new ProjectileWeaponUseEvent(player));
        }

        return InteractionResultHolder.fail(stack);
    }

    public boolean fireServer(ServerPlayer player, ItemStack expectedStack, boolean mainHand, boolean burstFollowup) {
        Level level = player.level();
        ItemStack stack = getHeldWeaponStack(player, mainHand);

        if (stack != expectedStack || stack.getItem() != this) {
            return false;
        }

        FiringMode firingMode = getFiringMode(stack);

        if (isReloadOrSwitchOnCooldown(stack, level)) {
            return false;
        }

        if (!burstFollowup && usesFireCooldown(firingMode) && isFireOnCooldown(stack, level)) {
            return false;
        }

        int currentAmmo = getAmmo(stack);
        if (isOutOfAmmo(currentAmmo, firingMode)) {
            handleNoAmmo(level, player, stack);
            return false;
        }

        if (firingMode == FiringMode.REPULSE) {
            fireRepulse(level, player, stack, firingMode);
            return true;
        }

        AmmoType currentAmmoType = getAmmoType(stack);

        switch (firingMode) {
            case BURST -> fireBurstShot(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode);
            case SCATTER -> fireScatter(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode);
            case CHARGENSHOOT -> fireChargeAndShoot(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode);
            case BEAM -> { return false; }
            default -> fireSingleShot(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode);
        }

        if (!burstFollowup && usesFireCooldown(firingMode)) {
            setFireCooldown(level, stack, firingMode);
        }

        return true;
    }

    private ItemStack getHeldWeaponStack(Player player, boolean mainHand) {
        return mainHand ? player.getMainHandItem() : player.getOffhandItem();
    }

    private boolean isReloadOrSwitchOnCooldown(ItemStack stack, Level level) {
        return getReloadNSwitchCooldownData(stack).isOnCooldown(level);
    }

    private boolean isFireOnCooldown(ItemStack stack, Level level) {
        return getFireCoolDownData(stack).isOnCooldown(level);
    }

    private boolean usesFireCooldown(FiringMode firingMode) {
        return firingMode != FiringMode.BEAM;
    }

    private boolean isOutOfAmmo(int currentAmmo, FiringMode firingMode) {
        return currentAmmo <= 0 && firingMode != FiringMode.REPULSE;
    }

    private void handleNoAmmo(Level level, Player player, ItemStack stack) {
        stack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(AmmoType.NONE.name()));
        playNoAmmoSound(level, player);
    }

    private void fireRepulse(Level level, Player player, ItemStack stack, FiringMode firingMode) {
        Repulse.execute(level, player, 7, 2.5);
        playFireSound(level, player, firingMode);
        player.awardStat(Stats.ITEM_USED.get(this));
        applyShotRecoil(player, getRequiredStats(firingMode));
        setFireCooldown(level, stack, firingMode);
    }

    private void fireScatter(Level level, Player player, ItemStack stack, int currentAmmo, AmmoType currentAmmoType, boolean mainHand, FiringMode firingMode) {
        if (isSpreadFlechetteAmmo(currentAmmoType)) {
            fireSingleShot(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode);
            return;
        }

        for (int shot = 0; shot < scatterShots; shot++) {
            fireBolt(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode, 0);
        }

        playFireSound(level, player, firingMode);
    }

    private void fireBurstShot(Level level, Player player, ItemStack stack, int currentAmmo, AmmoType currentAmmoType, boolean mainHand, FiringMode firingMode) {
        fireBolt(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode, 0);
    }

    private void fireChargeAndShoot(Level level, Player player, ItemStack stack, int currentAmmo, AmmoType currentAmmoType, boolean mainHand, FiringMode firingMode) {
        if (projectileWeaponName == WeaponName.BOWCASTER) {
            for (int shot = 0; shot < 3; shot++) {
                fireBolt(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode, shot);
            }

            playFireSound(level, player, firingMode);
            return;
        }

        fireSingleShot(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode);
    }

    private void fireSingleShot(Level level, Player player, ItemStack stack, int currentAmmo, AmmoType currentAmmoType, boolean mainHand, FiringMode firingMode) {
        fireBolt(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode, 0);
        playFireSound(level, player, firingMode);
    }

    private boolean isSpreadFlechetteAmmo(AmmoType ammoType) {
        return ammoType == AmmoType.FLECHETTE_SPREAD_CAN || ammoType == AmmoType.FLECHETTE_TOXIC_SPREAD_CAN;
    }

    private void setFireCooldown(Level level, ItemStack stack, FiringMode firingMode) {
        ProjectileWeaponStats currentStats = getRequiredStats(firingMode);
        long cooldownEndTime = level.getGameTime() + currentStats.fireRate();
        stack.set(ModDataComponentTypes.FIRE_COOLDOWN, getFireCoolDownData(stack).withFireCoolDownEndTime(cooldownEndTime));
    }

    private ProjectileWeaponStats getRequiredStats(FiringMode firingMode) {
        ProjectileWeaponStats currentStats = stats.get(firingMode);
        if (currentStats == null) {
            throw new IllegalStateException(projectileWeaponName + " is missing stats for firing mode " + firingMode);
        }
        return currentStats;
    }

    private void playFireSound(Level level, Player player, FiringMode firingMode) {
        SoundEvent fireSound = WeaponSoundsUtil.getWeaponFireSound(projectileWeaponName, firingMode);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), fireSound, SoundSource.NEUTRAL, 0.5F, 1.0F);
    }

    private void playNoAmmoSound(Level level, Player player) {
        level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.FOLEY_NO_AMMO.get(), SoundSource.NEUTRAL, 0.5F, 1.0F);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    private void initializeFiringMode(ItemStack itemStack) {
        itemStack.set(ModDataComponentTypes.FIRING_MODE.get(), new FiringModeData(defaultFiringMode.toString()));
    }

    private void fireBolt(Level level, Player player, ItemStack stack, int currentAmmo, AmmoType currentAmmoType, boolean mainHand, FiringMode firingMode, int shot) {
        ProjectileWeaponStats currentStats = getRequiredStats(firingMode);

        if (currentAmmo <= 0) {
            playNoAmmoSound(level, player);
            setFireCooldown(level, stack, firingMode);
            return;
        }

        Snowball projectile = createProjectile(level, player, currentAmmoType, currentStats, firingMode);
        projectile.setOwner(player);
        positionProjectile(player, projectile, mainHand);

        Vec3 aimDirection = getMuzzleToCrosshairDirection(level, player, projectile.position());
        setProjectileVelocity(player, projectile, currentAmmoType, currentStats, shot, aimDirection);
        level.addFreshEntity(projectile);

        setAmmo(stack, currentAmmo - 1);
        player.awardStat(Stats.ITEM_USED.get(this));

        if (firingMode == FiringMode.BURST) {
            playFireSound(level, player, firingMode);
        }

        applyShotRecoil(player, currentStats);
    }

    private Snowball createProjectile(Level level, Player player, AmmoType currentAmmoType, ProjectileWeaponStats currentStats, FiringMode firingMode) {
        boolean explosiveShot = isExplosiveShot(firingMode);
        boolean concussiveShot = isConcussiveShot();

        if (AmmoType.getGasTypes().contains(currentAmmoType)) {
            return createGasProjectile(level, player, currentAmmoType, currentStats, firingMode, explosiveShot, concussiveShot);
        }

        if (AmmoType.getSlugTypes().contains(currentAmmoType)) {
            return createSlugProjectile(level, player, currentAmmoType, currentStats);
        }

        return createFlechetteProjectile(level, player, currentAmmoType, currentStats);
    }

    private boolean isExplosiveShot(FiringMode firingMode) {
        return (firingMode == FiringMode.CHARGENSHOOT
                || firingMode == FiringMode.CHARGENSHOOTONRELEASE
                || projectileWeaponName == WeaponName.DC15LE
                || projectileWeaponName == WeaponName.BOWCASTER)
                && projectileWeaponName != WeaponName.Z6_ROTARY;
    }

    private boolean isConcussiveShot() {
        return projectileWeaponName == WeaponName.LJ40
                || projectileWeaponName == WeaponName.LJ50
                || projectileWeaponName == WeaponName.W90;
    }

    private Snowball createGasProjectile(Level level, Player player, AmmoType ammoType, ProjectileWeaponStats currentStats, FiringMode firingMode, boolean explosiveShot, boolean concussiveShot) {
        if (firingMode == FiringMode.STUN) {
            return new StunBlasterBoltEntity(ModEntities.STUN_BLASTER_BOLT.get(), level, player, 1.5F);
        } else if (getProjectileWeaponName().equals(WeaponName.SONIC_BLASTER) || getProjectileWeaponName().equals(WeaponName.SONIC_BLASTER_PISTOL)) {
            return new SonicBoltEntity(ModEntities.SONIC_BOLT.get(), level, player, 1.4F, 18);
        }

        return new BlasterBoltEntity(
                ModEntities.BLASTER_BOLT.get(),
                level,
                player,
                BlasterBoltEntity.BoltType.fromAmmoType(ammoType),
                projectileSpeed,
                currentStats.damage(),
                classification,
                explosiveShot,
                concussiveShot
        );
    }

    private Snowball createSlugProjectile(Level level, Player player, AmmoType ammoType, ProjectileWeaponStats currentStats) {
        return switch (ammoType) {
            case EXPLOSIVE_TIPPED_STEEL_SLUG -> new ExplosiveTippedSteelSlugEntity(ModEntities.EXPLOSIVE_TIPPED_STEEL_SLUG.get(), level, player, projectileSpeed, currentStats.damage(), classification, projectileWeaponName);
            case POISON_TIPPED_STEEL_SLUG -> new PoisonTippedSteelSlugEntity(ModEntities.POISON_TIPPED_STEEL_SLUG.get(), level, player, projectileSpeed, currentStats.damage(), classification, projectileWeaponName);
            case ION_TIPPED_STEEL_SLUG -> new IonTippedSteelSlugEntity(ModEntities.ION_TIPPED_STEEL_SLUG.get(), level, player, projectileSpeed, currentStats.damage(), classification, projectileWeaponName);
            case RAZOR_STEEL_SLUG -> new RazorSteelSlugEntity(ModEntities.RAZOR_STEEL_SLUG.get(), level, player, projectileSpeed, currentStats.damage(), classification, projectileWeaponName);
            case PLASTIC_SLUG -> new PlasticSlugEntity(ModEntities.PLASTIC_SLUG.get(), level, player, projectileSpeed, currentStats.damage(), classification);
            case CERAMIC_SLUG -> new CeramicSlugEntity(ModEntities.CERAMIC_SLUG.get(), level, player, projectileSpeed, currentStats.damage(), classification);
            default -> new SteelSlugEntity(ModEntities.STEEL_SLUG.get(), level, player, projectileSpeed, currentStats.damage(), classification, projectileWeaponName);
        };
    }

    private Snowball createFlechetteProjectile(Level level, Player player, AmmoType ammoType, ProjectileWeaponStats currentStats) {
        return switch (ammoType) {
            case FLECHETTE_SPREAD_CAN -> new FlechetteSpreadCanEntity(ModEntities.FLECHETTE_SPREAD_CAN.get(), level, player, projectileSpeed, currentStats.damage());
            case FLECHETTE_TOXIC_SPREAD_CAN -> new FlechetteToxicSpreadCanEntity(ModEntities.FLECHETTE_TOXIC_SPREAD_CAN.get(), level, player, projectileSpeed, currentStats.damage());
            case FLECHETTE_TOXIC_CAN -> new FlechetteToxicEntity(ModEntities.FLECHETTE_TOXIC.get(), level, player, projectileSpeed, currentStats.damage(), 50);
            default -> new FlechetteEntity(ModEntities.FLECHETTE.get(), level, player, projectileSpeed, currentStats.damage(), 50);
        };
    }

    private void positionProjectile(Player player, Snowball projectile, boolean mainHand) {
        Vec3 muzzlePosition = WeaponAimRules.getMuzzlePosition(player, mainHand, 1.0F);
        projectile.setPos(muzzlePosition.x, muzzlePosition.y, muzzlePosition.z);
    }

    private Vec3 getMuzzleToCrosshairDirection(Level level, Player player, Vec3 muzzlePosition) {
        Vec3 cameraPosition = player.getEyePosition();
        Vec3 crosshairPosition = WeaponTargeting.findHit(
                level, player, cameraPosition, player.getLookAngle(), TARGETING_RANGE
        ).endPosition();
        Vec3 direction = crosshairPosition.subtract(muzzlePosition);
        return direction.lengthSqr() < 1.0e-6 ? player.getLookAngle().normalize() : direction.normalize();
    }

    private void setProjectileVelocity(Player player, Snowball projectile, AmmoType currentAmmoType, ProjectileWeaponStats currentStats, int shot, Vec3 forward) {
        Vec3 worldUp = new Vec3(0, 1, 0);
        Vec3 right = forward.cross(worldUp);

        if (right.lengthSqr() < 1.0e-6) {
            float yawRad = (float) Math.toRadians(player.getYRot());
            Vec3 horizontalForward = new Vec3(-Mth.sin(yawRad), 0.0, Mth.cos(yawRad)).normalize();
            right = horizontalForward.cross(worldUp);
        }

        right = right.normalize();
        Vec3 cameraUp = right.cross(forward).normalize();
        Vec3 direction = applyShotSpread(player, currentAmmoType, currentStats, shot, forward, right, cameraUp);

        projectile.setDeltaMovement(direction.scale(projectileSpeed));
    }

    private Vec3 applyShotSpread(Player player, AmmoType currentAmmoType, ProjectileWeaponStats currentStats, int shot, Vec3 forward, Vec3 right, Vec3 cameraUp) {
        double angle = shot == 0 ? 0.0 : shot == 2 ? -0.10 : 0.10;
        Vec3 direction = forward.scale(Math.cos(angle)).add(right.scale(Math.sin(angle)));

        float accuracyFactor = getAccuracyFactor(player, currentAmmoType, currentStats);

        return direction
                .add(right.scale((player.getRandom().nextDouble() - 0.5) * accuracyFactor))
                .add(cameraUp.scale((player.getRandom().nextDouble() - 0.5) * accuracyFactor))
                .normalize();
    }

    private float getAccuracyFactor(Player player, AmmoType currentAmmoType, ProjectileWeaponStats currentStats) {
        float baseInaccuracy = isSpreadFlechetteAmmo(currentAmmoType) ? 1.8f : currentStats.inaccuracy();
        return baseInaccuracy * WeaponAimRules.spreadMultiplier(player) / 100;
    }

    private void applyShotRecoil(Player player, ProjectileWeaponStats currentStats) {
        if (player instanceof ServerPlayer serverPlayer) {
            float recoil = currentStats.recoil() * WeaponAimRules.recoilMultiplier(player);
            PayloadRegister.sendToPlayer(serverPlayer, new CSProjectileWeaponRecoilPacket(recoil));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (level.isClientSide || !(entity instanceof Player player)) {
            return;
        }

        rechargeDc15sSidearm(stack, level, player);
    }

    private void rechargeDc15sSidearm(ItemStack stack, Level level, Player player) {
        if (projectileWeaponName != WeaponName.DC15S_SIDEARM || level.getGameTime() % DC15S_SIDEARM_RECHARGE_INTERVAL_TICKS != 0L) {
            return;
        }

        int currentAmmo = getAmmo(stack);
        if (currentAmmo >= maxAmmo) {
            return;
        }

        setAmmo(stack, Math.min(maxAmmo, currentAmmo + 1));

        AmmoType currentType = getAmmoType(stack);
        if (currentType == AmmoType.NONE && typAmmoType != null && typAmmoType != AmmoType.NONE) {
            stack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(typAmmoType.name()));
        }

        player.inventoryMenu.broadcastChanges();
    }

    public int getAmmo(ItemStack stack) {
        AmmoData data = stack.get(ModDataComponentTypes.AMMO.get());
        return data != null ? data.ammo() : 0;
    }

    public boolean isActionOnCooldown(ServerPlayer player, ItemStack stack) {
        return getReloadNSwitchCooldownData(stack).isOnCooldown(player.level());
    }

    public boolean isActionOnCooldown(Level level, ItemStack stack) {
        return getReloadNSwitchCooldownData(stack).isOnCooldown(level);
    }

    private ReloadNSwitchCoolDownData getReloadNSwitchCooldownData(ItemStack stack) {
        ReloadNSwitchCoolDownData data = stack.get(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN);
        if (data == null) {
            data = new ReloadNSwitchCoolDownData(0);
            stack.set(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN, data);
        }
        return data;
    }

    private FireCoolDownData getFireCoolDownData(ItemStack stack) {
        FireCoolDownData data = stack.get(ModDataComponentTypes.FIRE_COOLDOWN);
        if (data == null) {
            data = new FireCoolDownData(0);
            stack.set(ModDataComponentTypes.FIRE_COOLDOWN, data);
        }
        return data;
    }

    public void setAmmo(ItemStack stack, int ammo) {
        stack.set(ModDataComponentTypes.AMMO.get(), new AmmoData(ammo));
        if (stack.getEntityRepresentation() instanceof Player player) {
            player.inventoryMenu.broadcastChanges();
        }
    }

    public static AmmoType getAmmoType(ItemStack stack) {
        AmmoTypeData data = stack.get(ModDataComponentTypes.AMMO_TYPE.get());
        if (data == null || data.ammoType() == null || data.ammoType().isBlank()) {
            stack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(AmmoType.NONE.name()));
            return AmmoType.NONE;
        }

        try {
            return AmmoType.valueOf(data.ammoType());
        } catch (IllegalArgumentException e) {
            stack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(AmmoType.NONE.name()));
            return AmmoType.NONE;
        }
    }

    public FiringMode getFiringMode(ItemStack stack) {
        FiringModeData data = stack.get(ModDataComponentTypes.FIRING_MODE.get());
        if (data == null || data.firingMode() == null || data.firingMode().isBlank()) {
            initializeFiringMode(stack);
            return defaultFiringMode;
        }

        try {
            FiringMode firingMode = FiringMode.valueOf(data.firingMode());
            if (firingModes.contains(firingMode)) {
                return firingMode;
            }
        } catch (IllegalArgumentException ignored) {
        }

        initializeFiringMode(stack);
        return defaultFiringMode;
    }

    public List<FiringMode> getFiringModes() {
        return firingModes;
    }

    public WeaponClassification getClassification() {
        return classification;
    }

    public WeaponName getProjectileWeaponName() {
        return projectileWeaponName;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public boolean reload(ServerPlayer player, ItemStack stack) {
        if (projectileWeaponName == WeaponName.DC15S_SIDEARM
                || getAmmo(stack) >= maxAmmo
                || getReloadNSwitchCooldownData(stack).isOnCooldown(player.level())) {
            return false;
        }

        int currentAmmo = getAmmo(stack);
        AmmoType currentAmmoType = getAmmoType(stack);
        boolean reloaded = tryReloadFromInventory(player, stack, currentAmmo, currentAmmoType);

        if (!reloaded && player.isCreative()) {
            reloaded = tryCreativeReload(stack, currentAmmo, currentAmmoType);
        }

        if (!reloaded) {
            return false;
        }

        startCooldown(player, stack, WeaponCooldownAction.RELOAD);
        playReloadSound(player, getFiringMode(stack), 0.5F);
        player.inventoryMenu.broadcastChanges();
        return true;
    }

    private boolean tryReloadFromInventory(Player player, ItemStack weaponStack, int currentAmmo, AmmoType currentAmmoType) {
        for (int slotIndex = 0; slotIndex < player.getInventory().items.size(); slotIndex++) {
            ItemStack ammoStack = player.getInventory().items.get(slotIndex);
            Item item = ammoStack.getItem();

            if (classification == WeaponClassification.SLUGTHROWER && item instanceof SlugItem slugItem) {
                if (reloadItemAmmo(player, weaponStack, ammoStack, slugItem.getAmmoType(), currentAmmo, currentAmmoType)) {
                    return true;
                }
            } else if (classification == WeaponClassification.FLECHETTE && item instanceof FlechetteCanisterItem flechetteItem) {
                if (reloadItemAmmo(player, weaponStack, ammoStack, flechetteItem.getAmmoType(), currentAmmo, currentAmmoType)) {
                    return true;
                }
            } else if (usesGasAmmo() && item instanceof GasItem gasItem) {
                if (reloadGasAmmo(player, weaponStack, ammoStack, gasItem, slotIndex, currentAmmo, currentAmmoType)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean reloadItemAmmo(Player player, ItemStack weaponStack, ItemStack ammoStack, AmmoType ammoType, int currentAmmo, AmmoType currentAmmoType) {
        if (ammoStack.isEmpty() || currentAmmoType != AmmoType.NONE && currentAmmoType != ammoType) {
            return false;
        }

        int ammoToReload = player.isCreative()
                ? maxAmmo - currentAmmo
                : Math.min(maxAmmo - currentAmmo, ammoStack.getCount());

        if (ammoToReload <= 0) {
            return false;
        }

        setAmmo(weaponStack, currentAmmo + ammoToReload);
        weaponStack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(ammoType.name()));

        if (!player.isCreative()) {
            ammoStack.shrink(ammoToReload);
        }

        return true;
    }

    private boolean reloadGasAmmo(Player player, ItemStack weaponStack, ItemStack gasStack, GasItem gasItem, int slotIndex, int currentAmmo, AmmoType currentAmmoType) {
        AmmoType gasType = gasItem.getAmmoType();
        if (currentAmmoType != AmmoType.NONE && currentAmmoType != gasType) {
            return false;
        }

        int gasAmmo = gasItem.getAmmo(gasStack);
        int ammoToReload = player.isCreative()
                ? maxAmmo - currentAmmo
                : Math.min(maxAmmo - currentAmmo, gasAmmo);

        if (ammoToReload <= 0) {
            return false;
        }

        setAmmo(weaponStack, currentAmmo + ammoToReload);
        weaponStack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(gasType.name()));

        if (!player.isCreative()) {
            int remainingGas = gasAmmo - ammoToReload;
            gasItem.setAmmo(gasStack, remainingGas);

            if (remainingGas <= 0) {
                player.getInventory().setItem(slotIndex, new ItemStack(ModItems.GAS_CARTRIDGE.get()));
            }
        }

        return true;
    }

    private boolean usesGasAmmo() {
        return classification != WeaponClassification.FLECHETTE && classification != WeaponClassification.SLUGTHROWER;
    }

    private boolean tryCreativeReload(ItemStack stack, int currentAmmo, AmmoType currentAmmoType) {
        AmmoType assumedType = currentAmmoType != AmmoType.NONE ? currentAmmoType : typAmmoType;
        if (assumedType == null || assumedType == AmmoType.NONE || !canUseAmmoType(assumedType)) {
            return false;
        }

        setAmmo(stack, maxAmmo);
        stack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(assumedType.name()));
        return currentAmmo < maxAmmo;
    }

    private boolean canUseAmmoType(AmmoType ammoType) {
        return switch (classification) {
            case SLUGTHROWER -> AmmoType.getSlugTypes().contains(ammoType);
            case FLECHETTE -> AmmoType.getFlechetteTypes().contains(ammoType);
            default -> AmmoType.getGasTypes().contains(ammoType);
        };
    }

    private void playReloadSound(Player player, FiringMode firingMode, float volume) {
        SoundEvent reloadSound = WeaponSoundsUtil.getWeaponReloadSound(projectileWeaponName, firingMode, classification);
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), reloadSound, SoundSource.PLAYERS, volume, 1.0F);
    }

    public boolean unload(ServerPlayer player, ItemStack stack, boolean mainHand) {
        if (getReloadNSwitchCooldownData(stack).isOnCooldown(player.level())) {
            return false;
        }

        int currentAmmo = getAmmo(stack);
        AmmoType currentAmmoType = getAmmoType(stack);

        if (currentAmmo <= 0) {
            player.displayClientMessage(Component.translatable("item.knightfall.projectileWeapon.no_ammo_to_unload"), true);
            return false;
        }

        if (classification == WeaponClassification.FLECHETTE || classification == WeaponClassification.SLUGTHROWER) {
            giveUnloadedAmmo(player, currentAmmoType, currentAmmo);
        } else {
            spawnUnloadSmoke(player, mainHand);
        }

        playUnloadSound(player, stack);
        setAmmo(stack, 0);
        stack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(AmmoType.NONE.name()));
        player.inventoryMenu.broadcastChanges();
        return true;
    }

    private void giveUnloadedAmmo(Player player, AmmoType currentAmmoType, int currentAmmo) {
        ItemStack unloadedAmmo = getUnloadedAmmoStack(currentAmmoType, currentAmmo);
        if (!player.getInventory().add(unloadedAmmo) && !unloadedAmmo.isEmpty()) {
            player.drop(unloadedAmmo, false);
        }
    }

    private ItemStack getUnloadedAmmoStack(AmmoType ammoType, int amount) {
        return switch (ammoType) {
            case FLECHETTE_TOXIC_SPREAD_CAN -> new ItemStack(ModItems.FLECHETTE_TOXIC_SPREAD_CANISTER.get(), amount);
            case FLECHETTE_TOXIC_CAN -> new ItemStack(ModItems.FLECHETTE_TOXIC_CANISTER.get(), amount);
            case FLECHETTE_SPREAD_CAN -> new ItemStack(ModItems.FLECHETTE_SPREAD_CANISTER.get(), amount);
            case FLECHETTE_CAN -> new ItemStack(ModItems.FLECHETTE_CANISTER.get(), amount);
            case PLASTIC_SLUG -> new ItemStack(ModItems.PLASTIC_SLUG.get(), amount);
            case CERAMIC_SLUG -> new ItemStack(ModItems.CERAMIC_SLUG.get(), amount);
            case RAZOR_STEEL_SLUG -> new ItemStack(ModItems.RAZOR_STEEL_SLUG.get(), amount);
            case POISON_TIPPED_STEEL_SLUG -> new ItemStack(ModItems.POISON_TIPPED_STEEL_SLUG.get(), amount);
            case EXPLOSIVE_TIPPED_STEEL_SLUG -> new ItemStack(ModItems.EXPLOSIVE_TIPPED_STEEL_SLUG.get(), amount);
            case ION_TIPPED_STEEL_SLUG -> new ItemStack(ModItems.ION_TIPPED_STEEL_SLUG.get(), amount);
            case STEEL_SLUG -> new ItemStack(ModItems.STEEL_SLUG.get(), amount);
            default -> classification == WeaponClassification.SLUGTHROWER
                    ? new ItemStack(ModItems.STEEL_SLUG.get(), amount)
                    : new ItemStack(ModItems.FLECHETTE_CANISTER.get(), amount);
        };
    }

    private void spawnUnloadSmoke(Player player, boolean mainHand) {
        Vec3 position = WeaponAimRules.getMuzzlePosition(player, mainHand, 1.0F)
                .add(player.getLookAngle().scale(0.8));

        if (player.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.LARGE_SMOKE,
                    position.x,
                    position.y,
                    position.z,
                    1,
                    0,
                    0.05,
                    0,
                    0.01
            );
        }
    }

    private void playUnloadSound(Player player, ItemStack stack) {
        SoundEvent unloadSound = WeaponSoundsUtil.getWeaponUnloadSound(getFiringMode(stack), classification);
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), unloadSound, SoundSource.PLAYERS, 0.5F, 1.0F);
    }

    public void startCooldown(ServerPlayer player, ItemStack stack, WeaponCooldownAction action) {
        Level level = player.level();

        ReloadNSwitchCoolDownData cooldownData = getReloadNSwitchCooldownData(stack);

        if (cooldownData.isOnCooldown(level)) {
            return;
        }

        FiringMode firingMode = getFiringMode(stack);

        long cooldownDuration = switch (action) {
            case RELOAD -> getReloadTime(firingMode);
            case SWITCH -> getSwitchTime(firingMode);
            case EQUIP -> getEquipTime();
        };

        long cooldownEndTime = level.getGameTime() + cooldownDuration;

        stack.set(
                ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN,
                cooldownData.withReloadNSwitchCoolDownEndTime(cooldownEndTime)
        );
    }

    public boolean switchFiringMode(ServerPlayer player, ItemStack stack) {
        ReloadNSwitchCoolDownData cooldownData = getReloadNSwitchCooldownData(stack);
        if (cooldownData.isOnCooldown(player.level()) || firingModes.size() <= 1) {
            return false;
        }

        FiringMode currentFiringMode = getFiringMode(stack);
        int index = firingModes.indexOf(currentFiringMode);
        if (index == -1) {
            index = 0;
        }

        FiringMode nextMode = firingModes.get((index + 1) % firingModes.size());
        FiringModeData newData = stack.get(ModDataComponentTypes.FIRING_MODE.get()).withFiringMode(nextMode.toString());
        stack.set(ModDataComponentTypes.FIRING_MODE.get(), newData);

        startCooldown(player, stack, WeaponCooldownAction.SWITCH);
        SoundEvent switchSound = WeaponSoundsUtil.getWeaponSwitchFireMode(projectileWeaponName, currentFiringMode);
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), switchSound, SoundSource.PLAYERS, 0.5F, 1.0F);
        player.inventoryMenu.broadcastChanges();
        return true;
    }

    @Override
    public Component getName(ItemStack stack) {
        ChatFormatting color = ChatFormatting.WHITE;
        int currentAmmo = getAmmo(stack);

        if (currentAmmo > 0) {
            AmmoType ammoType = getDisplayAmmoType(stack);
            color = getAmmoColor(ammoType);
        }

        ChatFormatting finalColor = color;
        return super.getName(stack).copy().withStyle(style -> style.withColor(finalColor));
    }

    @Nullable
    private AmmoType getDisplayAmmoType(ItemStack stack) {
        AmmoTypeData ammoTypeData = stack.get(ModDataComponentTypes.AMMO_TYPE.get());
        if (ammoTypeData == null || ammoTypeData.ammoType() == null || ammoTypeData.ammoType().isBlank()) {
            return null;
        }

        try {
            return AmmoType.valueOf(ammoTypeData.ammoType());
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    private ChatFormatting getAmmoColor(@Nullable AmmoType ammoType) {
        return switch (ammoType) {
            case TIBANNA -> ChatFormatting.RED;
            case IONIZED_TIBANNA -> ChatFormatting.BLUE;
            case SPIN_SEALED_TIBANNA -> ChatFormatting.GREEN;
            case TIBANNAX -> ChatFormatting.GRAY;
            case SIG -> ChatFormatting.YELLOW;
            case MAGNETIZED_SIG -> ChatFormatting.DARK_PURPLE;
            case SKEVON -> ChatFormatting.GOLD;
            case null, default -> ChatFormatting.WHITE;
        };
    }

    @Override
    public void appendHoverText(ItemStack weaponStack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Ammo: " + getAmmo(weaponStack) + "/" + maxAmmo));
        tooltip.add(Component.translatable("tooltip.knightfall.blaster.shift"));
        super.appendHoverText(weaponStack, context, tooltip, flag);
    }

    public void appendAmmoTypeTooltip(ItemStack weaponStack, List<Component> tooltip) {
        AmmoTypeData ammoTypeData = weaponStack.get(ModDataComponentTypes.AMMO_TYPE.get());

        if (ammoTypeData == null || ammoTypeData.ammoType() == null || ammoTypeData.ammoType().isBlank()) {
            tooltip.add(Component.literal("No Ammo"));
            return;
        }

        try {
            tooltip.add(Component.literal("Ammo Type: " + AmmoType.valueOf(ammoTypeData.ammoType())));
        } catch (IllegalArgumentException ignored) {
            tooltip.add(Component.literal("No Ammo"));
        }
    }

}
