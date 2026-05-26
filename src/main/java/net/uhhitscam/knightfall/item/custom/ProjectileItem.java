package net.uhhitscam.knightfall.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
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
import net.uhhitscam.knightfall.component.AmmoData;
import net.uhhitscam.knightfall.component.AmmoTypeData;
import net.uhhitscam.knightfall.component.ExtraFiringRateData;
import net.uhhitscam.knightfall.component.FireCoolDownData;
import net.uhhitscam.knightfall.component.FiringModeData;
import net.uhhitscam.knightfall.component.ModDataComponentTypes;
import net.uhhitscam.knightfall.component.ReloadNSwitchCoolDownData;
import net.uhhitscam.knightfall.entity.ModEntities;
import net.uhhitscam.knightfall.entity.custom.CeramicSlugEntity;
import net.uhhitscam.knightfall.entity.custom.ExplosiveTippedSteelSlugEntity;
import net.uhhitscam.knightfall.entity.custom.FlechetteEntity;
import net.uhhitscam.knightfall.entity.custom.FlechetteSpreadCanEntity;
import net.uhhitscam.knightfall.entity.custom.FlechetteToxicEntity;
import net.uhhitscam.knightfall.entity.custom.FlechetteToxicSpreadCanEntity;
import net.uhhitscam.knightfall.entity.custom.IonTippedSteelSlugEntity;
import net.uhhitscam.knightfall.entity.custom.IonizedTibannaBlasterBoltEntity;
import net.uhhitscam.knightfall.entity.custom.MagnetizedSigBlasterBoltEntity;
import net.uhhitscam.knightfall.entity.custom.PlasticSlugEntity;
import net.uhhitscam.knightfall.entity.custom.PoisonTippedSteelSlugEntity;
import net.uhhitscam.knightfall.entity.custom.RazorSteelSlugEntity;
import net.uhhitscam.knightfall.entity.custom.SigBlasterBoltEntity;
import net.uhhitscam.knightfall.entity.custom.SkevonBlasterBoltEntity;
import net.uhhitscam.knightfall.entity.custom.SpinSealedTibannaBlasterBoltEntity;
import net.uhhitscam.knightfall.entity.custom.SteelSlugEntity;
import net.uhhitscam.knightfall.entity.custom.StunBlasterBoltEntity;
import net.uhhitscam.knightfall.entity.custom.TibannaBlasterBoltEntity;
import net.uhhitscam.knightfall.entity.custom.TibannaXBlasterBoltEntity;
import net.uhhitscam.knightfall.item.ModItems;
import net.uhhitscam.knightfall.network.PayloadRegister;
import net.uhhitscam.knightfall.network.SSGasAmmoPacket;
import net.uhhitscam.knightfall.network.SSGiveItemPacket;
import net.uhhitscam.knightfall.network.SSReloadPacket;
import net.uhhitscam.knightfall.network.SSFiringModePacket;
import net.uhhitscam.knightfall.sound.ModSounds;
import net.uhhitscam.knightfall.util.Repulse;
import net.uhhitscam.knightfall.util.WeaponSoundsUtil;
import net.uhhitscam.knightfall.util.WeaponZoomUtil;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class ProjectileItem extends Item {
    private static final int BURST_SHOT_COUNT = 3;
    private static final int DC15S_SIDEARM_RECHARGE_INTERVAL_TICKS = 20;
    private static final float MAX_RECOIL = 20.0f;
    private static final float RECOIL_DECAY = 0.8f;
    private static final float MIN_RECOIL_TO_KEEP = 0.01f;
    private static final double DEFAULT_PROJECTILE_SIDE_OFFSET = 0.27;
    private static final double DEFAULT_PROJECTILE_HEIGHT_OFFSET = -0.1;

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

    private final Map<UUID, Float> recoilMap = new HashMap<>();

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
        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }

    public void mainHandFiring(Player player) {
        if (player.getMainHandItem().getItem() instanceof ProjectileItem) {
            fireHeldWeapon(player.level(), player, true);
        }
    }

    public void offHandFiring(Player player) {
        if (player.getOffhandItem().getItem() instanceof ProjectileItem) {
            fireHeldWeapon(player.level(), player, false);
        }
    }

    private void fireHeldWeapon(Level level, Player player, boolean mainHand) {
        ItemStack stack = getHeldWeaponStack(player, mainHand);
        FiringMode firingMode = getFiringMode(stack);

        if (isReloadOrSwitchOnCooldown(stack, level)) {
            return;
        }

        if (usesFireCooldown(firingMode) && isFireOnCooldown(stack, level)) {
            return;
        }

        int currentAmmo = getAmmo(stack);
        if (isOutOfAmmo(currentAmmo, firingMode)) {
            handleNoAmmo(level, player, stack);
            return;
        }

        if (level.isClientSide) {
            return;
        }

        if (firingMode == FiringMode.REPULSE) {
            fireRepulse(level, player, stack, firingMode);
            return;
        }

        AmmoType currentAmmoType = getAmmoType(stack);

        switch (firingMode) {
            case BURST -> fireBurst(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode);
            case SCATTER -> fireScatter(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode);
            case CHARGENSHOOT -> fireChargeAndShoot(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode);
            case BEAM -> {
                // Beam start/stop behavior is controlled by client input and SSBeamPacket.
            }
            default -> fireSingleShot(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode);
        }

        if (usesFireCooldown(firingMode)) {
            setFireCooldown(level, stack, firingMode);
        }
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

    private void fireBurst(Level level, Player player, ItemStack stack, int currentAmmo, AmmoType currentAmmoType, boolean mainHand, FiringMode firingMode) {
        ExtraFiringRateData extraFiringRateData = getExtraFiringRateData(stack, mainHand);

        if (extraFiringRateData.shotsFired() == 0) {
            fireBolt(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode, 0);
            extraFiringRateData = new ExtraFiringRateData(level.getGameTime() + burstRate, 1, mainHand);
        } else {
            extraFiringRateData = new ExtraFiringRateData(
                    extraFiringRateData.cooldownEndTime(),
                    extraFiringRateData.shotsFired() + 1,
                    extraFiringRateData.mainHand()
            );
        }

        stack.set(ModDataComponentTypes.EXTRA_FIRING_RATE, extraFiringRateData);
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

        if (shouldSpawnProjectile(player)) {
            setProjectileVelocity(player, projectile, currentAmmoType, currentStats, shot);
            level.addFreshEntity(projectile);
        }

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
        }

        return switch (ammoType) {
            case IONIZED_TIBANNA -> new IonizedTibannaBlasterBoltEntity(ModEntities.IONIZED_TIBANNA_BLASTER_BOLT.get(), level, player, projectileSpeed, currentStats.damage(), classification, projectileWeaponName, explosiveShot, concussiveShot);
            case SPIN_SEALED_TIBANNA -> new SpinSealedTibannaBlasterBoltEntity(ModEntities.SPIN_SEALED_TIBANNA_BLASTER_BOLT.get(), level, player, projectileSpeed, currentStats.damage(), classification, projectileWeaponName, explosiveShot, concussiveShot);
            case TIBANNAX -> new TibannaXBlasterBoltEntity(ModEntities.TIBANNAX_BLASTER_BOLT.get(), level, player, projectileSpeed, currentStats.damage(), classification, projectileWeaponName, explosiveShot, concussiveShot);
            case SIG -> new SigBlasterBoltEntity(ModEntities.SIG_BLASTER_BOLT.get(), level, player, projectileSpeed, currentStats.damage(), classification, projectileWeaponName, explosiveShot, concussiveShot);
            case MAGNETIZED_SIG -> new MagnetizedSigBlasterBoltEntity(ModEntities.MAGNETIZED_SIG_BLASTER_BOLT.get(), level, player, projectileSpeed, currentStats.damage(), classification, projectileWeaponName, explosiveShot, concussiveShot);
            case SKEVON -> new SkevonBlasterBoltEntity(ModEntities.SKEVON_BLASTER_BOLT.get(), level, player, projectileSpeed, currentStats.damage(), classification, projectileWeaponName, explosiveShot, concussiveShot);
            default -> new TibannaBlasterBoltEntity(ModEntities.TIBANNA_BLASTER_BOLT.get(), level, player, projectileSpeed, currentStats.damage(), classification, projectileWeaponName, explosiveShot, concussiveShot);
        };
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
        ProjectileOffset offset = getProjectileOffset(player, mainHand);
        double yawRadians = Math.toRadians(player.getYRot());
        double sideMultiplier = mainHand ? -1.0 : 1.0;

        projectile.setPos(
                player.getX() + sideMultiplier * Math.cos(yawRadians) * offset.closeness(),
                player.getEyeY() + offset.height(),
                player.getZ() + sideMultiplier * Math.sin(yawRadians) * offset.closeness()
        );
    }

    private ProjectileOffset getProjectileOffset(Player player, boolean mainHand) {
        Item mainItem = player.getMainHandItem().getItem();
        Item offItem = player.getOffhandItem().getItem();
        boolean isMainWeapon = mainItem instanceof ProjectileItem;
        boolean isOffWeapon = offItem instanceof ProjectileItem;

        if ((isMainWeapon ^ isOffWeapon) && player.isShiftKeyDown()) {
            if (mainHand && mainItem instanceof ProjectileItem mainWeapon && WeaponZoomUtil.getScopeTexture(mainWeapon, player.getMainHandItem()) != null) {
                return new ProjectileOffset(0, 0);
            }

            if (!mainHand && offItem instanceof ProjectileItem offWeapon && WeaponZoomUtil.getScopeTexture(offWeapon, player.getOffhandItem()) != null) {
                return new ProjectileOffset(0, 0);
            }
        }

        return new ProjectileOffset(DEFAULT_PROJECTILE_SIDE_OFFSET, DEFAULT_PROJECTILE_HEIGHT_OFFSET);
    }

    private boolean shouldSpawnProjectile(Player player) {
        ItemStack mainStack = player.getMainHandItem();
        ItemStack offStack = player.getOffhandItem();

        boolean mainCanSpawn = mainStack.getItem() instanceof ProjectileItem mainWeapon && mainWeapon.getFiringMode(mainStack) != FiringMode.BEAM;
        boolean offCanSpawn = offStack.getItem() instanceof ProjectileItem offWeapon && offWeapon.getFiringMode(offStack) != FiringMode.BEAM;

        return mainCanSpawn || offCanSpawn;
    }

    private void setProjectileVelocity(Player player, Snowball projectile, AmmoType currentAmmoType, ProjectileWeaponStats currentStats, int shot) {
        Vec3 forward = player.getLookAngle().normalize();
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
        Random random = new Random();

        return direction
                .add(right.scale((random.nextDouble() - 0.5) * accuracyFactor))
                .add(cameraUp.scale((random.nextDouble() - 0.5) * accuracyFactor))
                .normalize();
    }

    private float getAccuracyFactor(Player player, AmmoType currentAmmoType, ProjectileWeaponStats currentStats) {
        float baseInaccuracy = isSpreadFlechetteAmmo(currentAmmoType) ? 1.8f : currentStats.inaccuracy();
        return player.isShiftKeyDown() ? baseInaccuracy * 0.8f / 100 : baseInaccuracy / 100;
    }

    private void applyShotRecoil(Player player, ProjectileWeaponStats currentStats) {
        Item mainItem = player.getMainHandItem().getItem();
        Item offItem = player.getOffhandItem().getItem();
        boolean isMainWeapon = mainItem instanceof ProjectileItem;
        boolean isOffWeapon = offItem instanceof ProjectileItem;
        float recoil = player.isShiftKeyDown() && (isOffWeapon ^ isMainWeapon) ? currentStats.recoil() * 0.4f : currentStats.recoil();
        applyRecoil(player, recoil);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player) {
            tickRecoil(player);
        }

        if (level.isClientSide || !(entity instanceof Player player)) {
            return;
        }

        rechargeDc15sSidearm(stack, level, player);
        tickBurstFire(stack, level, player);
    }

    private void tickRecoil(Player player) {
        UUID playerId = player.getUUID();
        Float recoilAmount = recoilMap.get(playerId);

        if (recoilAmount == null) {
            return;
        }

        float decayedRecoil = recoilAmount * RECOIL_DECAY;
        float recoilEffect = recoilAmount - decayedRecoil;
        float newPitch = Mth.clamp(player.getXRot() - recoilEffect, -90.0f, 90.0f);
        player.setXRot(newPitch);

        if (decayedRecoil < MIN_RECOIL_TO_KEEP) {
            recoilMap.remove(playerId);
        } else {
            recoilMap.put(playerId, decayedRecoil);
        }
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

    private void tickBurstFire(ItemStack stack, Level level, Player player) {
        ExtraFiringRateData extraFiringRateData = stack.get(ModDataComponentTypes.EXTRA_FIRING_RATE);
        if (extraFiringRateData == null) {
            return;
        }

        if (getFiringMode(stack) == FiringMode.BURST
                && extraFiringRateData.shotsFired() > 0
                && extraFiringRateData.shotsFired() < BURST_SHOT_COUNT
                && level.getGameTime() >= extraFiringRateData.cooldownEndTime()) {
            fireBolt(level, player, stack, getAmmo(stack), getAmmoType(stack), extraFiringRateData.mainHand(), getFiringMode(stack), 0);
            extraFiringRateData = extraFiringRateData
                    .withCooldownEndTime(level.getGameTime() + 2)
                    .withShotsFired(extraFiringRateData.shotsFired() + 1);
            stack.set(ModDataComponentTypes.EXTRA_FIRING_RATE, extraFiringRateData);
        }

        if (extraFiringRateData.shotsFired() >= BURST_SHOT_COUNT) {
            stack.set(ModDataComponentTypes.EXTRA_FIRING_RATE, new ExtraFiringRateData(0, 0, extraFiringRateData.mainHand()));
        }
    }

    public void applyRecoil(Player player, float recoil) {
        UUID playerId = player.getUUID();
        float currentRecoil = recoilMap.getOrDefault(playerId, 0.0f);
        recoilMap.put(playerId, Math.min(currentRecoil + recoil, MAX_RECOIL));
    }

    public int getAmmo(ItemStack stack) {
        AmmoData data = stack.get(ModDataComponentTypes.AMMO.get());
        return data != null ? data.ammo() : 0;
    }

    public ReloadNSwitchCoolDownData getReloadNSwitchCooldownData(ItemStack stack) {
        ReloadNSwitchCoolDownData data = stack.get(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN);
        if (data == null) {
            data = new ReloadNSwitchCoolDownData(0);
            stack.set(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN, data);
        }
        return data;
    }

    public FireCoolDownData getFireCoolDownData(ItemStack stack) {
        FireCoolDownData data = stack.get(ModDataComponentTypes.FIRE_COOLDOWN);
        if (data == null) {
            data = new FireCoolDownData(0);
            stack.set(ModDataComponentTypes.FIRE_COOLDOWN, data);
        }
        return data;
    }

    public ExtraFiringRateData getExtraFiringRateData(ItemStack stack, boolean mainHand) {
        ExtraFiringRateData data = stack.get(ModDataComponentTypes.EXTRA_FIRING_RATE);
        if (data == null) {
            data = new ExtraFiringRateData(0, 0, mainHand);
            stack.set(ModDataComponentTypes.EXTRA_FIRING_RATE, data);
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
        if (data == null) {
            initializeFiringMode(stack);
            data = stack.get(ModDataComponentTypes.FIRING_MODE.get());
        }
        return FiringMode.valueOf(data.firingMode());
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

    public int reload(Player player, ItemStack stack, boolean mainHand, int additionalAmmoToConsume) {
        int currentAmmo = getAmmo(stack);
        AmmoType currentAmmoType = getAmmoType(stack);
        FiringMode firingMode = getFiringMode(stack);

        if (currentAmmo >= maxAmmo || getReloadNSwitchCooldownData(stack).isOnCooldown(player.level())) {
            return 0;
        }

        ReloadResult reloadResult = tryReloadFromInventory(player, stack, currentAmmo, currentAmmoType, mainHand, firingMode, additionalAmmoToConsume);
        if (reloadResult.reloaded()) {
            return reloadResult.ammoToConsume();
        }

        return tryCreativeReload(player, stack, currentAmmo, currentAmmoType, mainHand, firingMode) ? 0 : 0;
    }

    private ReloadResult tryReloadFromInventory(Player player, ItemStack weaponStack, int currentAmmo, AmmoType currentAmmoType, boolean mainHand, FiringMode firingMode, int additionalAmmoToConsume) {
        for (int slotIndex = 0; slotIndex < player.getInventory().items.size(); slotIndex++) {
            ItemStack ammoStack = player.getInventory().items.get(slotIndex);
            Item item = ammoStack.getItem();
            player.inventoryMenu.broadcastChanges();

            if (classification == WeaponClassification.SLUGTHROWER && item instanceof SlugItem slugItem) {
                if (tryReloadSlug(player, weaponStack, ammoStack, slugItem, currentAmmo, currentAmmoType, mainHand, firingMode, additionalAmmoToConsume)) {
                    int ammoToConsume = getItemAmmoToConsume(player, ammoStack, currentAmmo, additionalAmmoToConsume);
                    weaponStack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(slugItem.getAmmoType().name()));
                    return ReloadResult.success(ammoToConsume);
                }
            } else if (classification == WeaponClassification.FLECHETTE && item instanceof FlechetteCanisterItem flechetteItem) {
                if (tryReloadFlechette(player, weaponStack, ammoStack, flechetteItem, currentAmmo, currentAmmoType, mainHand, firingMode, additionalAmmoToConsume)) {
                    int ammoToConsume = getItemAmmoToConsume(player, ammoStack, currentAmmo, additionalAmmoToConsume);
                    weaponStack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(flechetteItem.getAmmoType().name()));
                    return ReloadResult.success(ammoToConsume);
                }
            } else if (usesGasAmmo() && item instanceof GasItem gasItem) {
                if (tryReloadGas(player, weaponStack, ammoStack, gasItem, currentAmmo, slotIndex, currentAmmoType, mainHand, firingMode)) {
                    return ReloadResult.success(0);
                }
            }
        }

        return ReloadResult.failed();
    }

    private int getItemAmmoToConsume(Player player, ItemStack ammoStack, int currentAmmo, int additionalAmmoToConsume) {
        if (player.isCreative()) {
            return maxAmmo - currentAmmo;
        }

        return Math.min(maxAmmo - currentAmmo, ammoStack.getCount() - additionalAmmoToConsume);
    }

    private boolean usesGasAmmo() {
        return classification != WeaponClassification.FLECHETTE && classification != WeaponClassification.SLUGTHROWER;
    }

    private boolean tryCreativeReload(Player player, ItemStack stack, int currentAmmo, AmmoType currentAmmoType, boolean mainHand, FiringMode firingMode) {
        if (!player.isCreative()) {
            return false;
        }

        AmmoType assumedType = currentAmmoType != AmmoType.NONE ? currentAmmoType : typAmmoType;
        if (assumedType == null || assumedType == AmmoType.NONE || !canUseAmmoType(assumedType)) {
            return false;
        }

        int ammoNeeded = maxAmmo - currentAmmo;
        if (ammoNeeded <= 0) {
            return false;
        }

        int newAmmo = currentAmmo + ammoNeeded;
        setAmmo(stack, newAmmo);
        stack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(assumedType.name()));
        PayloadRegister.sendToServer(new SSReloadPacket(stack, newAmmo, assumedType.name(), mainHand));
        playReloadSound(player, firingMode, 0.3F);
        return true;
    }

    private boolean canUseAmmoType(AmmoType ammoType) {
        return switch (classification) {
            case SLUGTHROWER -> AmmoType.getSlugTypes().contains(ammoType);
            case FLECHETTE -> AmmoType.getFlechetteTypes().contains(ammoType);
            default -> AmmoType.getGasTypes().contains(ammoType);
        };
    }

    private boolean tryReloadGas(Player player, ItemStack weaponStack, ItemStack ammoStack, GasItem gasItem, int currentAmmo, int slotIndex, AmmoType currentAmmoType, boolean mainHand, FiringMode firingMode) {
        int gasAmmo = gasItem.getAmmo(ammoStack);
        if (gasAmmo <= 0) {
            return false;
        }

        AmmoType gasType = gasItem.getAmmoType();
        if (currentAmmoType != AmmoType.NONE && currentAmmoType != gasType) {
            return false;
        }

        int ammoToReload = player.isCreative() ? maxAmmo - currentAmmo : Math.min(maxAmmo - currentAmmo, gasAmmo);
        int newAmmo = currentAmmo + ammoToReload;
        setAmmo(weaponStack, newAmmo);
        PayloadRegister.sendToServer(new SSReloadPacket(weaponStack, newAmmo, gasType.toString(), mainHand));

        if (!player.isCreative()) {
            gasAmmo -= ammoToReload;
            gasItem.setAmmo(ammoStack, gasAmmo);
            PayloadRegister.sendToServer(new SSGasAmmoPacket(ammoStack, gasAmmo, slotIndex));
        }

        player.inventoryMenu.broadcastChanges();
        playReloadSound(player, firingMode, 0.5F);
        return true;
    }

    private boolean tryReloadSlug(Player player, ItemStack weaponStack, ItemStack ammoStack, SlugItem slugItem, int currentAmmo, AmmoType currentAmmoType, boolean mainHand, FiringMode firingMode, int additionalAmmoToReload) {
        int slugAmmo = ammoStack.getCount() - additionalAmmoToReload;
        AmmoType slugType = slugItem.getAmmoType();

        if (slugAmmo <= 0 || currentAmmoType != AmmoType.NONE && currentAmmoType != slugType) {
            return false;
        }

        int ammoToReload = player.isCreative() ? maxAmmo - currentAmmo : Math.min(maxAmmo - currentAmmo, slugAmmo);
        int newAmmo = currentAmmo + ammoToReload;
        setAmmo(weaponStack, newAmmo);
        PayloadRegister.sendToServer(new SSReloadPacket(weaponStack, newAmmo, slugType.toString(), mainHand));
        playReloadSound(player, firingMode, 0.5F);
        return true;
    }

    private boolean tryReloadFlechette(Player player, ItemStack weaponStack, ItemStack ammoStack, FlechetteCanisterItem flechetteCanisterItem, int currentAmmo, AmmoType currentAmmoType, boolean mainHand, FiringMode firingMode, int additionalAmmoToReload) {
        int flechetteAmmo = ammoStack.getCount() - additionalAmmoToReload;
        AmmoType flechetteType = flechetteCanisterItem.getAmmoType();

        if (flechetteAmmo <= 0 || currentAmmoType != AmmoType.NONE && currentAmmoType != flechetteType) {
            return false;
        }

        int ammoToReload = player.isCreative() ? maxAmmo - currentAmmo : Math.min(maxAmmo - currentAmmo, flechetteAmmo);
        int newAmmo = currentAmmo + ammoToReload;
        setAmmo(weaponStack, newAmmo);
        PayloadRegister.sendToServer(new SSReloadPacket(weaponStack, newAmmo, flechetteType.toString(), mainHand));
        playReloadSound(player, firingMode, 0.5F);
        return true;
    }

    private void playReloadSound(Player player, FiringMode firingMode, float volume) {
        SoundEvent reloadSound = WeaponSoundsUtil.getWeaponReloadSound(projectileWeaponName, firingMode, classification);
        player.playSound(reloadSound, volume, 1.0F);
    }

    public void unload(Player player, ItemStack stack, boolean mainHand) {
        int currentAmmo = getAmmo(stack);
        AmmoType currentAmmoType = getAmmoType(stack);

        if (currentAmmo <= 0) {
            player.displayClientMessage(Component.translatable("item.knightfall.projectileWeapon.no_ammo_to_unload"), true);
            return;
        }

        if (classification == WeaponClassification.FLECHETTE || classification == WeaponClassification.SLUGTHROWER) {
            giveUnloadedAmmo(player, currentAmmoType, currentAmmo);
        } else {
            spawnUnloadSmoke(player, mainHand);
        }

        playUnloadSound(player, stack);
        setAmmo(stack, 0);
        PayloadRegister.sendToServer(new SSReloadPacket(stack, 0, currentAmmoType.toString(), mainHand));
        player.inventoryMenu.broadcastChanges();
    }

    private void giveUnloadedAmmo(Player player, AmmoType currentAmmoType, int currentAmmo) {
        ItemStack unloadedAmmo = getUnloadedAmmoStack(currentAmmoType, currentAmmo);
        PayloadRegister.sendToServer(new SSGiveItemPacket(unloadedAmmo));
        player.inventoryMenu.broadcastChanges();
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
        ProjectileOffset offset = getProjectileOffset(player, mainHand);
        double yawRadians = Math.toRadians(player.getYRot());
        double pitchRadians = Math.toRadians(player.getXRot());
        double sideMultiplier = mainHand ? -1.0 : 1.0;

        double x = player.getX()
                + sideMultiplier * Math.cos(yawRadians) * offset.closeness()
                - Math.sin(yawRadians);
        double y = player.getEyeY()
                + offset.height()
                - Math.sin(pitchRadians) * 1.2;
        double z = player.getZ()
                + sideMultiplier * Math.sin(yawRadians) * offset.closeness()
                + Math.cos(yawRadians);

        player.level().addParticle(
                ParticleTypes.LARGE_SMOKE,
                x,
                y,
                z,
                0,
                0.05 + player.level().random.nextDouble() * 0.05,
                0
        );
    }

    private void playUnloadSound(Player player, ItemStack stack) {
        SoundEvent unloadSound = WeaponSoundsUtil.getWeaponUnloadSound(getFiringMode(stack), classification);
        player.playSound(unloadSound, 0.5F, 1.0F);
    }

    public void startCooldown(Player player, ItemStack stack, WeaponCooldownAction action) {
        Level level = player.level();
        int currentAmmo = getAmmo(stack);

        if (action == WeaponCooldownAction.RELOAD && currentAmmo >= maxAmmo) {
            return;
        }

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

    public void switchFiringMode(Player player, ItemStack stack, boolean mainHand) {
        ReloadNSwitchCoolDownData cooldownData = getReloadNSwitchCooldownData(stack);
        if (cooldownData.isOnCooldown(player.level())) {
            return;
        }

        FiringMode currentFiringMode = getFiringMode(stack);
        int index = firingModes.indexOf(currentFiringMode);
        if (index == -1) {
            index = 0;
        }

        FiringMode nextMode = firingModes.get((index + 1) % firingModes.size());
        FiringModeData newData = stack.get(ModDataComponentTypes.FIRING_MODE.get()).withFiringMode(nextMode.toString());
        stack.set(ModDataComponentTypes.FIRING_MODE.get(), newData);

        PayloadRegister.sendToServer(new SSFiringModePacket(stack, nextMode.toString(), mainHand));
        SoundEvent switchSound = WeaponSoundsUtil.getWeaponSwitchFireMode(projectileWeaponName, currentFiringMode);
        player.playSound(switchSound, 0.5F, 1.0F);
    }

    @Override
    public Component getName(ItemStack stack) {
        ChatFormatting color = ChatFormatting.WHITE;
        int currentAmmo = getAmmo(stack);

        if (currentAmmo <= 0) {
            stack.set(ModDataComponentTypes.AMMO_TYPE.get(), null);
        } else {
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

        if (Screen.hasShiftDown()) {
            appendAmmoTypeTooltip(weaponStack, tooltip);
        } else {
            tooltip.add(Component.translatable("tooltip.knightfall.blaster.shift"));
        }

        super.appendHoverText(weaponStack, context, tooltip, flag);
    }

    private void appendAmmoTypeTooltip(ItemStack weaponStack, List<Component> tooltip) {
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

    private record ProjectileOffset(double closeness, double height) {
    }

    private record ReloadResult(boolean reloaded, int ammoToConsume) {
        private static ReloadResult success(int ammoToConsume) {
            return new ReloadResult(true, ammoToConsume);
        }

        private static ReloadResult failed() {
            return new ReloadResult(false, 0);
        }
    }
}
