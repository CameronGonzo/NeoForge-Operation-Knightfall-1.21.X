package net.uhhitscam.knightfall.item.custom.projectile;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;
import net.uhhitscam.knightfall.component.AmmoData;
import net.uhhitscam.knightfall.component.AmmoTypeData;
import net.uhhitscam.knightfall.component.FireCoolDownData;
import net.uhhitscam.knightfall.component.FiringModeData;
import net.uhhitscam.knightfall.component.ModDataComponentTypes;
import net.uhhitscam.knightfall.component.OverheatData;
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

import java.util.List;

public class ProjectileItem extends Item {
    private static final double TARGETING_RANGE = 128.0;
    private static final int DC15S_SIDEARM_RECHARGE_INTERVAL_TICKS = 20;
    private static final int OVERHEAT_PENALTY = 20;
    private static final int OVERHEAT_COOLING_DELAY_TICKS = 20;

    private final ProjectileWeaponDefinition definition;
    private final WeaponName projectileWeaponName;

    public ProjectileItem(Properties properties, ProjectileWeaponDefinition definition) {
        super(properties);
        this.definition = definition;
        this.projectileWeaponName = definition.weaponName();
    }

    public int getMaxAmmo() {
        return getMaxAmmo(ItemStack.EMPTY);
    }

    public double getHeldMovementSpeedMultiplier() {
        return getHeldMovementSpeedMultiplier(ItemStack.EMPTY);
    }

    public float getDualWieldInaccuracyPenalty() {
        return getDualWieldInaccuracyPenalty(ItemStack.EMPTY);
    }

    public float getDualWieldInaccuracyMultiplier() {
        return getDualWieldInaccuracyMultiplier(ItemStack.EMPTY);
    }

    public float getDualWieldRecoilPenalty() {
        return getDualWieldRecoilPenalty(ItemStack.EMPTY);
    }

    public float getDualWieldRecoilMultiplier() {
        return getDualWieldRecoilMultiplier(ItemStack.EMPTY);
    }

    public WeaponClassification getClassification() {
        return getClassification(ItemStack.EMPTY);
    }

    public List<FiringMode> getFiringModes() {
        return getFiringModes(ItemStack.EMPTY);
    }

    public int getOverheatCoolingIntervalTicks() {
        return getOverheatCoolingIntervalTicks(ItemStack.EMPTY);
    }

    public int getOverheatCoolingAmount() {
        return getOverheatCoolingAmount(ItemStack.EMPTY);
    }

    public int getMaxOverheat() {
        return getMaxOverheat(ItemStack.EMPTY);
    }

    public boolean usesOverheat() {
        return usesOverheat(ItemStack.EMPTY);
    }

    public BeamWeaponStats getBeamStats() {
        return getBeamStats(ItemStack.EMPTY);
    }

    public ProjectileWeaponUI getUI() {
        return getUI(ItemStack.EMPTY);
    }

    public ProjectileWeaponStats getStats(FiringMode firingMode) {
        return getStats(ItemStack.EMPTY, firingMode);
    }

    public int getBurstRate() {
        return getBurstRate(ItemStack.EMPTY);
    }

    public int getChargeThreshold() {
        return getChargeThreshold(ItemStack.EMPTY);
    }

    public long getEquipTime() {
        return getEquipTime(ItemStack.EMPTY);
    }

    public long getSwitchTime(FiringMode firingMode) {
        return getSwitchTime(ItemStack.EMPTY, firingMode);
    }

    public long getReloadTime(FiringMode firingMode) {
        return getReloadTime(ItemStack.EMPTY, firingMode);
    }

    public float debugValue(ItemStack stack, String key, float fallback) {
        return this instanceof DebugBlasterItem ? DebugBlasterItem.settings(stack).decimal(key) : fallback;
    }

    public int getBurstShotCount(ItemStack stack) {
        return getDefinition(stack).burstShotCount();
    }

    public ProjectileWeaponDefinition getDefinition(ItemStack stack) {
        return definition;
    }

    public long getReloadTime(ItemStack stack, FiringMode firingMode) {
        return getDefinition(stack).timing().reloadTicks(firingMode);
    }

    public long getSwitchTime(ItemStack stack, FiringMode firingMode) {
        return getDefinition(stack).timing().switchTicks(firingMode);
    }

    public long getEquipTime(ItemStack stack) {
        return getDefinition(stack).timing().equipTicks();
    }

    public int getChargeThreshold(ItemStack stack) {
        return getDefinition(stack).timing().chargeThresholdTicks();
    }

    public int getBurstRate(ItemStack stack) {
        return getDefinition(stack).burstRate();
    }

    public ProjectileWeaponStats getStats(ItemStack stack, FiringMode firingMode) {
        return getDefinition(stack).stats().get(firingMode);
    }

    public ProjectileWeaponUI getUI(ItemStack stack) {
        return getDefinition(stack).ui();
    }

    @Nullable
    public BeamWeaponStats getBeamStats(ItemStack stack) {
        return getDefinition(stack).beamStats();
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        if (level.isClientSide()) {
            NeoForge.EVENT_BUS.post(new ProjectileWeaponUseEvent(player));
        }

        return InteractionResult.FAIL;
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

        if (isOverheated(stack, level)) {
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

        addShotOverheat(stack, level, firingMode);

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
        Repulse.execute(level, player, debugValue(stack, "repulse_radius", 7), debugValue(stack, "repulse_strength", 2.5F));
        playFireSound(level, player, firingMode);
        player.awardStat(Stats.ITEM_USED.get(this));
        applyShotRecoil(stack, player, getRequiredStats(stack, firingMode));
        setFireCooldown(level, stack, firingMode);
    }

    private void fireScatter(Level level, Player player, ItemStack stack, int currentAmmo, AmmoType currentAmmoType, boolean mainHand, FiringMode firingMode) {
        if (isSpreadFlechetteAmmo(currentAmmoType)) {
            fireSingleShot(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode);
            return;
        }

        for (int shot = 0; shot < getDefinition(stack).scatterShots(); shot++) {
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
        ProjectileWeaponStats currentStats = getRequiredStats(stack, firingMode);
        long cooldownEndTime = level.getGameTime() + currentStats.fireRate();
        stack.set(ModDataComponentTypes.FIRE_COOLDOWN, getFireCoolDownData(stack).withFireCoolDownEndTime(cooldownEndTime));
    }

    private ProjectileWeaponStats getRequiredStats(ItemStack stack, FiringMode firingMode) {
        ProjectileWeaponStats currentStats = getDefinition(stack).stats().get(firingMode);
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
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.NONE;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    private void fireBolt(Level level, Player player, ItemStack stack, int currentAmmo, AmmoType currentAmmoType, boolean mainHand, FiringMode firingMode, int shot) {
        ProjectileWeaponStats currentStats = getRequiredStats(stack, firingMode);

        if (currentAmmo <= 0) {
            playNoAmmoSound(level, player);
            setFireCooldown(level, stack, firingMode);
            return;
        }

        Snowball projectile = createProjectile(stack, level, player, currentAmmoType, currentStats, firingMode);
        projectile.setOwner(player);
        positionProjectile(player, projectile, mainHand);

        Vec3 aimDirection = getMuzzleToCrosshairDirection(level, player, projectile.position());
        setProjectileVelocity(stack, player, projectile, currentAmmoType, currentStats, shot, aimDirection);
        level.addFreshEntity(projectile);

        setAmmo(stack, currentAmmo - 1);
        player.awardStat(Stats.ITEM_USED.get(this));

        if (firingMode == FiringMode.BURST) {
            playFireSound(level, player, firingMode);
        }

        applyShotRecoil(stack, player, currentStats);
    }

    private Snowball createProjectile(ItemStack stack, Level level, Player player, AmmoType currentAmmoType, ProjectileWeaponStats currentStats, FiringMode firingMode) {
        boolean explosiveShot = isExplosiveShot(firingMode);
        boolean concussiveShot = isConcussiveShot();

        if (AmmoType.getGasTypes().contains(currentAmmoType)) {
            return createGasProjectile(stack, level, player, currentAmmoType, currentStats, firingMode, explosiveShot, concussiveShot);
        }

        if (AmmoType.getSlugTypes().contains(currentAmmoType)) {
            return createSlugProjectile(stack, level, player, currentAmmoType, currentStats);
        }

        return createFlechetteProjectile(stack, level, player, currentAmmoType, currentStats);
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

    private Snowball createGasProjectile(ItemStack stack, Level level, Player player, AmmoType ammoType, ProjectileWeaponStats currentStats, FiringMode firingMode, boolean explosiveShot, boolean concussiveShot) {
        if (firingMode == FiringMode.STUN) {
            var stun = new StunBlasterBoltEntity(ModEntities.STUN_BLASTER_BOLT.get(), level);
            if (this instanceof DebugBlasterItem) stun.configureDebug(DebugBlasterItem.settings(stack));
            return stun;
        } else if (getProjectileWeaponName().equals(WeaponName.SONIC_BLASTER) || getProjectileWeaponName().equals(WeaponName.SONIC_BLASTER_PISTOL)) {
            return new SonicBoltEntity(ModEntities.SONIC_BOLT.get(), level, player, 1.4F, 18);
        }

        var bolt = new BlasterBoltEntity(
                ModEntities.BLASTER_BOLT.get(),
                level,
                player,
                BlasterBoltEntity.BoltType.fromAmmoType(ammoType),
                getDefinition(stack).projectileSpeed(),
                currentStats.damage(),
                getDefinition(stack).classification(),
                explosiveShot,
                concussiveShot
        );
        if (this instanceof DebugBlasterItem) bolt.configureDebug(DebugBlasterItem.settings(stack));
        return bolt;
    }

    private Snowball createSlugProjectile(ItemStack stack, Level level, Player player, AmmoType ammoType, ProjectileWeaponStats currentStats) {
        return switch (ammoType) {
            case EXPLOSIVE_TIPPED_STEEL_SLUG -> new ExplosiveTippedSteelSlugEntity(ModEntities.EXPLOSIVE_TIPPED_STEEL_SLUG.get(), level, player, getDefinition(stack).projectileSpeed(), currentStats.damage(), getDefinition(stack).classification(), projectileWeaponName);
            case POISON_TIPPED_STEEL_SLUG -> new PoisonTippedSteelSlugEntity(ModEntities.POISON_TIPPED_STEEL_SLUG.get(), level, player, getDefinition(stack).projectileSpeed(), currentStats.damage(), getDefinition(stack).classification(), projectileWeaponName);
            case ION_TIPPED_STEEL_SLUG -> new IonTippedSteelSlugEntity(ModEntities.ION_TIPPED_STEEL_SLUG.get(), level, player, getDefinition(stack).projectileSpeed(), currentStats.damage(), getDefinition(stack).classification(), projectileWeaponName);
            case RAZOR_STEEL_SLUG -> new RazorSteelSlugEntity(ModEntities.RAZOR_STEEL_SLUG.get(), level, player, getDefinition(stack).projectileSpeed(), currentStats.damage(), getDefinition(stack).classification(), projectileWeaponName);
            case PLASTIC_SLUG -> new PlasticSlugEntity(ModEntities.PLASTIC_SLUG.get(), level, player, getDefinition(stack).projectileSpeed(), currentStats.damage(), getDefinition(stack).classification());
            case CERAMIC_SLUG -> new CeramicSlugEntity(ModEntities.CERAMIC_SLUG.get(), level, player, getDefinition(stack).projectileSpeed(), currentStats.damage(), getDefinition(stack).classification());
            default -> new SteelSlugEntity(ModEntities.STEEL_SLUG.get(), level, player, getDefinition(stack).projectileSpeed(), currentStats.damage(), getDefinition(stack).classification(), projectileWeaponName);
        };
    }

    private Snowball createFlechetteProjectile(ItemStack stack, Level level, Player player, AmmoType ammoType, ProjectileWeaponStats currentStats) {
        return switch (ammoType) {
            case FLECHETTE_SPREAD_CAN -> new FlechetteSpreadCanEntity(ModEntities.FLECHETTE_SPREAD_CAN.get(), level, player, getDefinition(stack).projectileSpeed(), currentStats.damage());
            case FLECHETTE_TOXIC_SPREAD_CAN -> new FlechetteToxicSpreadCanEntity(ModEntities.FLECHETTE_TOXIC_SPREAD_CAN.get(), level, player, getDefinition(stack).projectileSpeed(), currentStats.damage());
            case FLECHETTE_TOXIC_CAN -> new FlechetteToxicEntity(ModEntities.FLECHETTE_TOXIC.get(), level, player, getDefinition(stack).projectileSpeed(), currentStats.damage(), 50);
            default -> new FlechetteEntity(ModEntities.FLECHETTE.get(), level, player, getDefinition(stack).projectileSpeed(), currentStats.damage(), 50);
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

    private void setProjectileVelocity(ItemStack stack, Player player, Snowball projectile, AmmoType currentAmmoType, ProjectileWeaponStats currentStats, int shot, Vec3 forward) {
        Vec3 worldUp = new Vec3(0, 1, 0);
        Vec3 right = forward.cross(worldUp);

        if (right.lengthSqr() < 1.0e-6) {
            float yawRad = (float) Math.toRadians(player.getYRot());
            Vec3 horizontalForward = new Vec3(-Mth.sin(yawRad), 0.0, Mth.cos(yawRad)).normalize();
            right = horizontalForward.cross(worldUp);
        }

        right = right.normalize();
        Vec3 cameraUp = right.cross(forward).normalize();
        Vec3 direction = applyShotSpread(stack, player, currentAmmoType, currentStats, shot, forward, right, cameraUp);

        WeaponAimRules.setProjectileMotion(projectile, direction.scale(getDefinition(stack).projectileSpeed()));
    }

    private Vec3 applyShotSpread(ItemStack stack, Player player, AmmoType currentAmmoType, ProjectileWeaponStats currentStats, int shot, Vec3 forward, Vec3 right, Vec3 cameraUp) {
        double angle = shot == 0 ? 0.0 : shot == 2 ? -0.10 : 0.10;
        Vec3 direction = forward.scale(Math.cos(angle)).add(right.scale(Math.sin(angle)));

        float accuracyFactor = getAccuracyFactor(stack, player, currentAmmoType, currentStats);

        return direction
                .add(right.scale((player.getRandom().nextDouble() - 0.5) * accuracyFactor))
                .add(cameraUp.scale((player.getRandom().nextDouble() - 0.5) * accuracyFactor))
                .normalize();
    }

    private float getAccuracyFactor(ItemStack stack, Player player, AmmoType currentAmmoType, ProjectileWeaponStats currentStats) {
        float baseInaccuracy = isSpreadFlechetteAmmo(currentAmmoType) ? 1.8f : currentStats.inaccuracy();
        return WeaponAimRules.adjustInaccuracy(player, this, stack, baseInaccuracy) / 100;
    }

    private void applyShotRecoil(ItemStack stack, Player player, ProjectileWeaponStats currentStats) {
        if (player instanceof ServerPlayer serverPlayer) {
            float recoil = WeaponAimRules.adjustRecoil(player, this, stack, currentStats.recoil());
            PayloadRegister.sendToPlayer(serverPlayer, new CSProjectileWeaponRecoilPacket(recoil));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, net.minecraft.server.level.ServerLevel level, Entity entity, net.minecraft.world.entity.EquipmentSlot slot) {
        if (level.isClientSide() || !(entity instanceof Player player)) {
            return;
        }

        updateOverheat(stack, level);
        rechargeDc15sSidearm(stack, level, player);
    }

    private void rechargeDc15sSidearm(ItemStack stack, Level level, Player player) {
        if (projectileWeaponName != WeaponName.DC15S_SIDEARM || level.getGameTime() % DC15S_SIDEARM_RECHARGE_INTERVAL_TICKS != 0L) {
            return;
        }

        int currentAmmo = getAmmo(stack);
        if (currentAmmo >= getDefinition(stack).maxAmmo()) {
            return;
        }

        setAmmo(stack, Math.min(getDefinition(stack).maxAmmo(), currentAmmo + 1));

        AmmoType currentType = getAmmoType(stack);
        if (currentType == AmmoType.NONE && getDefinition(stack).ammoType() != null && getDefinition(stack).ammoType() != AmmoType.NONE) {
            stack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(getDefinition(stack).ammoType().name()));
        }

        player.inventoryMenu.broadcastChanges();
    }

    public int getAmmo(ItemStack stack) {
        AmmoData data = stack.get(ModDataComponentTypes.AMMO.get());
        return data != null ? data.ammo() : 0;
    }

    public boolean usesOverheat(ItemStack stack) {
        return getDefinition(stack).maxOverheat() > 0;
    }

    public int getMaxOverheat(ItemStack stack) {
        return getDefinition(stack).maxOverheat();
    }

    public int getOverheatCoolingAmount(ItemStack stack) {
        return getDefinition(stack).overheatCoolingAmount();
    }

    public int getOverheatCoolingIntervalTicks(ItemStack stack) {
        return getDefinition(stack).overheatCoolingIntervalTicks();
    }

    public int getOverheat(ItemStack stack, Level level) {
        if (!usesOverheat(stack)) {
            return 0;
        }

        return resolveOverheatData(stack, getOverheatData(stack), level.getGameTime()).heat();
    }

    public boolean isOverheated(ItemStack stack, Level level) {
        return usesOverheat(stack) && getOverheat(stack, level) >= getDefinition(stack).maxOverheat();
    }

    public boolean addBeamOverheat(ItemStack stack, Level level) {
        if (getDefinition(stack).beamStats() == null) {
            return false;
        }

        return addOverheat(stack, level, getDefinition(stack).beamStats().overheatPerPulse());
    }

    private void addShotOverheat(ItemStack stack, Level level, FiringMode firingMode) {
        if (!usesOverheat(stack) || firingMode == FiringMode.REPULSE) {
            return;
        }

        addOverheat(stack, level, getRequiredStats(stack, firingMode).overheatPerShot());
    }

    private boolean addOverheat(ItemStack stack, Level level, int amount) {
        if (!usesOverheat(stack) || amount <= 0) {
            return false;
        }

        OverheatData currentData = updateOverheat(stack, level);
        if (currentData.heat() >= getDefinition(stack).maxOverheat()) {
            return true;
        }

        int newHeat = currentData.heat() + amount;
        if (newHeat >= getDefinition(stack).maxOverheat()) {
            newHeat += (int) debugValue(stack, "overheat_penalty", OVERHEAT_PENALTY);
        }

        stack.set(
                ModDataComponentTypes.OVERHEAT.get(),
                new OverheatData(newHeat, level.getGameTime() + (int) debugValue(stack, "cooling_delay", OVERHEAT_COOLING_DELAY_TICKS))
        );
        return newHeat >= getDefinition(stack).maxOverheat();
    }

    private OverheatData updateOverheat(ItemStack stack, Level level) {
        OverheatData currentData = getOverheatData(stack);

        if (!usesOverheat(stack)) {
            OverheatData clearedData = new OverheatData(0, 0);
            if (!clearedData.equals(currentData)) {
                stack.set(ModDataComponentTypes.OVERHEAT.get(), clearedData);
            }
            return clearedData;
        }

        OverheatData resolvedData = resolveOverheatData(stack, currentData, level.getGameTime());

        if (!resolvedData.equals(currentData)) {
            stack.set(ModDataComponentTypes.OVERHEAT.get(), resolvedData);
        }

        return resolvedData;
    }

    private OverheatData getOverheatData(ItemStack stack) {
        OverheatData data = stack.get(ModDataComponentTypes.OVERHEAT.get());
        return data != null ? data : new OverheatData(0, 0);
    }

    private OverheatData resolveOverheatData(ItemStack stack, OverheatData data, long gameTime) {
        if (data.heat() <= 0 || data.nextCoolingTick() <= 0 || gameTime < data.nextCoolingTick()
                || getOverheatCoolingAmount(stack) <= 0) {
            return data;
        }

        long coolingSteps = 1L + (gameTime - data.nextCoolingTick()) / getDefinition(stack).overheatCoolingIntervalTicks();
        long maximumUsefulSteps = ((long) data.heat() + getDefinition(stack).overheatCoolingAmount() - 1L) / getDefinition(stack).overheatCoolingAmount();
        long appliedCoolingSteps = Math.min(coolingSteps, maximumUsefulSteps);
        long heatReduction = appliedCoolingSteps * getDefinition(stack).overheatCoolingAmount();
        int cooledHeat = (int) (data.heat() - heatReduction);
        long nextCoolingTick = cooledHeat > 0
                ? data.nextCoolingTick() + coolingSteps * getDefinition(stack).overheatCoolingIntervalTicks()
                : 0L;
        return new OverheatData(cooledHeat, nextCoolingTick);
    }

    public boolean isActionOnCooldown(ServerPlayer player, ItemStack stack) {
        return getReloadNSwitchCooldownData(stack).isOnCooldown(player.level());
    }

    public boolean isActionOnCooldown(Level level, ItemStack stack) {
        return getReloadNSwitchCooldownData(stack).isOnCooldown(level);
    }

    private ReloadNSwitchCoolDownData getReloadNSwitchCooldownData(ItemStack stack) {
        return stack.getOrDefault(
                ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN,
                new ReloadNSwitchCoolDownData(0)
        );
    }

    private FireCoolDownData getFireCoolDownData(ItemStack stack) {
        return stack.getOrDefault(ModDataComponentTypes.FIRE_COOLDOWN, new FireCoolDownData(0));
    }

    public void setAmmo(ItemStack stack, int ammo) {
        stack.set(ModDataComponentTypes.AMMO.get(), new AmmoData(ammo));
    }

    public static AmmoType getAmmoType(ItemStack stack) {
        AmmoTypeData data = stack.get(ModDataComponentTypes.AMMO_TYPE.get());
        if (data == null || data.ammoType() == null || data.ammoType().isBlank()) {
            return AmmoType.NONE;
        }

        try {
            return AmmoType.valueOf(data.ammoType());
        } catch (IllegalArgumentException e) {
            return AmmoType.NONE;
        }
    }

    public FiringMode getFiringMode(ItemStack stack) {
        FiringModeData data = stack.get(ModDataComponentTypes.FIRING_MODE.get());
        if (data == null || data.firingMode() == null || data.firingMode().isBlank()) {
            return getDefinition(stack).defaultFiringMode();
        }

        try {
            FiringMode firingMode = FiringMode.valueOf(data.firingMode());
            if (getDefinition(stack).firingModes().contains(firingMode)) {
                return firingMode;
            }
        } catch (IllegalArgumentException ignored) {
        }

        return getDefinition(stack).defaultFiringMode();
    }

    public List<FiringMode> getFiringModes(ItemStack stack) {
        return getDefinition(stack).firingModes();
    }

    public WeaponClassification getClassification(ItemStack stack) {
        return getDefinition(stack).classification();
    }

    public float getDualWieldRecoilMultiplier(ItemStack stack) {
        return getDefinition(stack).dualWieldRecoilMultiplier();
    }

    public float getDualWieldRecoilPenalty(ItemStack stack) {
        return getDefinition(stack).dualWieldRecoilPenalty();
    }

    public float getDualWieldInaccuracyMultiplier(ItemStack stack) {
        return getDefinition(stack).dualWieldInaccuracyMultiplier();
    }

    public float getDualWieldInaccuracyPenalty(ItemStack stack) {
        return getDefinition(stack).dualWieldInaccuracyPenalty();
    }

    public double getHeldMovementSpeedMultiplier(ItemStack stack) {
        return getDefinition(stack).heldMovementSpeedMultiplier();
    }

    public WeaponName getProjectileWeaponName() {
        return projectileWeaponName;
    }

    public int getMaxAmmo(ItemStack stack) {
        return getDefinition(stack).maxAmmo();
    }

    public boolean reload(ServerPlayer player, ItemStack stack) {
        if (projectileWeaponName == WeaponName.DC15S_SIDEARM
                || getAmmo(stack) >= getDefinition(stack).maxAmmo()
                || getReloadNSwitchCooldownData(stack).blocksReload(player.level())) {
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
        playReloadSound(stack, player, getFiringMode(stack), 0.5F);
        player.inventoryMenu.broadcastChanges();
        return true;
    }

    private boolean tryReloadFromInventory(Player player, ItemStack weaponStack, int currentAmmo, AmmoType currentAmmoType) {
        for (int slotIndex = 0; slotIndex < net.minecraft.world.entity.player.Inventory.INVENTORY_SIZE; slotIndex++) {
            ItemStack ammoStack = player.getInventory().getItem(slotIndex);
            Item item = ammoStack.getItem();

            if (getDefinition(weaponStack).classification() == WeaponClassification.SLUGTHROWER && item instanceof SlugItem slugItem) {
                if (reloadItemAmmo(player, weaponStack, ammoStack, slugItem.getAmmoType(), currentAmmo, currentAmmoType)) {
                    return true;
                }
            } else if (getDefinition(weaponStack).classification() == WeaponClassification.FLECHETTE && item instanceof FlechetteCanisterItem flechetteItem) {
                if (reloadItemAmmo(player, weaponStack, ammoStack, flechetteItem.getAmmoType(), currentAmmo, currentAmmoType)) {
                    return true;
                }
            } else if (usesGasAmmo(weaponStack) && item instanceof GasItem gasItem) {
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
                ? getDefinition(weaponStack).maxAmmo() - currentAmmo
                : Math.min(getDefinition(weaponStack).maxAmmo() - currentAmmo, ammoStack.getCount());

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
                ? getDefinition(weaponStack).maxAmmo() - currentAmmo
                : Math.min(getDefinition(weaponStack).maxAmmo() - currentAmmo, gasAmmo);

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

    private boolean usesGasAmmo(ItemStack stack) {
        return getDefinition(stack).classification() != WeaponClassification.FLECHETTE && getDefinition(stack).classification() != WeaponClassification.SLUGTHROWER;
    }

    private boolean tryCreativeReload(ItemStack stack, int currentAmmo, AmmoType currentAmmoType) {
        AmmoType assumedType = currentAmmoType != AmmoType.NONE ? currentAmmoType : getDefinition(stack).ammoType();
        if (assumedType == null || assumedType == AmmoType.NONE || !canUseAmmoType(stack, assumedType)) {
            return false;
        }

        setAmmo(stack, getDefinition(stack).maxAmmo());
        stack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(assumedType.name()));
        return currentAmmo < getDefinition(stack).maxAmmo();
    }

    private boolean canUseAmmoType(ItemStack stack, AmmoType ammoType) {
        return switch (getDefinition(stack).classification()) {
            case SLUGTHROWER -> AmmoType.getSlugTypes().contains(ammoType);
            case FLECHETTE -> AmmoType.getFlechetteTypes().contains(ammoType);
            default -> AmmoType.getGasTypes().contains(ammoType);
        };
    }

    private void playReloadSound(ItemStack stack, Player player, FiringMode firingMode, float volume) {
        SoundEvent reloadSound = WeaponSoundsUtil.getWeaponReloadSound(projectileWeaponName, firingMode, getDefinition(stack).classification());
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), reloadSound, SoundSource.PLAYERS, volume, 1.0F);
    }

    public boolean unload(ServerPlayer player, ItemStack stack, boolean mainHand) {
        if (getReloadNSwitchCooldownData(stack).isOnCooldown(player.level())) {
            return false;
        }

        int currentAmmo = getAmmo(stack);
        AmmoType currentAmmoType = getAmmoType(stack);

        if (currentAmmo <= 0) {
            player.sendSystemMessage(Component.translatable("item.knightfall.projectileWeapon.no_ammo_to_unload"), true);
            return false;
        }

        if (getDefinition(stack).classification() == WeaponClassification.FLECHETTE || getDefinition(stack).classification() == WeaponClassification.SLUGTHROWER) {
            giveUnloadedAmmo(stack, player, currentAmmoType, currentAmmo);
        } else {
            spawnUnloadSmoke(player, mainHand);
        }

        playUnloadSound(player, stack);
        setAmmo(stack, 0);
        stack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(AmmoType.NONE.name()));
        player.inventoryMenu.broadcastChanges();
        return true;
    }

    private void giveUnloadedAmmo(ItemStack stack, Player player, AmmoType currentAmmoType, int currentAmmo) {
        ItemStack unloadedAmmo = getUnloadedAmmoStack(stack, currentAmmoType, currentAmmo);
        if (!player.getInventory().add(unloadedAmmo) && !unloadedAmmo.isEmpty()) {
            player.drop(unloadedAmmo, false);
        }
    }

    private ItemStack getUnloadedAmmoStack(ItemStack stack, AmmoType ammoType, int amount) {
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
            default -> getDefinition(stack).classification() == WeaponClassification.SLUGTHROWER
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
        SoundEvent unloadSound = WeaponSoundsUtil.getWeaponUnloadSound(getFiringMode(stack), getDefinition(stack).classification());
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), unloadSound, SoundSource.PLAYERS, 0.5F, 1.0F);
    }

    public void startCooldown(ServerPlayer player, ItemStack stack, WeaponCooldownAction action) {
        Level level = player.level();

        ReloadNSwitchCoolDownData cooldownData = getReloadNSwitchCooldownData(stack);

        boolean reloadReplacingEquipCooldown = action == WeaponCooldownAction.RELOAD
                && cooldownData.isActiveEquipCooldown(level);
        if (cooldownData.isOnCooldown(level) && !reloadReplacingEquipCooldown) {
            return;
        }

        FiringMode firingMode = getFiringMode(stack);

        long cooldownDuration = switch (action) {
            case RELOAD -> getReloadTime(stack, firingMode);
            case SWITCH -> getSwitchTime(stack, firingMode);
            case EQUIP -> getEquipTime(stack);
        };

        long cooldownEndTime = level.getGameTime() + cooldownDuration;

        stack.set(
                ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN,
                cooldownData.withCooldown(cooldownEndTime, action)
        );
    }

    public boolean switchFiringMode(ServerPlayer player, ItemStack stack) {
        ReloadNSwitchCoolDownData cooldownData = getReloadNSwitchCooldownData(stack);
        if (cooldownData.isOnCooldown(player.level()) || getDefinition(stack).firingModes().size() <= 1) {
            return false;
        }

        FiringMode currentFiringMode = getFiringMode(stack);
        int index = getDefinition(stack).firingModes().indexOf(currentFiringMode);
        if (index == -1) {
            index = 0;
        }

        FiringMode nextMode = getDefinition(stack).firingModes().get((index + 1) % getDefinition(stack).firingModes().size());
        stack.set(ModDataComponentTypes.FIRING_MODE.get(), new FiringModeData(nextMode.name()));

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

    public void appendBaseTooltip(ItemStack weaponStack, java.util.function.Consumer<Component> tooltip) {
        tooltip.accept(Component.literal("Ammo: " + getAmmo(weaponStack) + "/" + getDefinition(weaponStack).maxAmmo()));
        tooltip.accept(Component.translatable("tooltip.knightfall.blaster.shift"));
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
