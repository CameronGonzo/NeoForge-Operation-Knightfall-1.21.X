package net.uhhitscam.knightfall.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.uhhitscam.knightfall.component.*;
import net.uhhitscam.knightfall.entity.ModEntities;
import net.uhhitscam.knightfall.entity.custom.*;
import net.uhhitscam.knightfall.event.ModClientEvents;
import net.uhhitscam.knightfall.item.ModItems;
import net.uhhitscam.knightfall.network.*;
import net.uhhitscam.knightfall.sound.ModSounds;
import net.uhhitscam.knightfall.util.Repulse;
import net.uhhitscam.knightfall.util.WeaponSoundsUtil;
import net.uhhitscam.knightfall.util.WeaponTimingUtil;
import net.uhhitscam.knightfall.util.WeaponZoomUtil;

import java.util.*;

public class ProjectileItem extends Item {
    private final float projectileSpeed;
    private final int max_ammo;
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

    private final Map<UUID, Float> recoilMap = new HashMap<>();

    public ProjectileItem(Properties properties, float projectileSpeed, int max_ammo, int burstRate, int scatterShots,
                          EnumMap<FiringMode, ProjectileWeaponStats> stats, @org.jetbrains.annotations.Nullable BeamWeaponStats beamStats, List<FiringMode> firingModes, ProjectileWeaponUI ui, FiringMode defaultFiringMode, AmmoType typAmmoType,
                          WeaponClassification classification, WeaponName projectileWeaponName) {
        super(properties);
        this.projectileSpeed = projectileSpeed;
        this.max_ammo = max_ammo;
        this.burstRate = burstRate;
        this.scatterShots = scatterShots;
        this.stats = stats;
        this.beamStats = beamStats;
        this.firingModes = firingModes;
        this.ui = ui;
        this.defaultFiringMode = defaultFiringMode;
        this.typAmmoType = typAmmoType;
        this.classification = classification;
        this.projectileWeaponName = projectileWeaponName;
    }

    public ProjectileWeaponStats getStats(FiringMode firingMode) {
        return this.stats.get(firingMode);
    }

    public ProjectileWeaponUI getUI() {
        return ui;
    }

    @org.jetbrains.annotations.Nullable
    public BeamWeaponStats getBeamStats() {
        return this.beamStats;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }

    public void mainHandFiring(Player player) {
        if (player.getMainHandItem().getItem() instanceof ProjectileItem) {
            firingProjectileLogic(player.level(), player, true);
        }
    }

    public void offHandFiring(Player player) {
        if (player.getOffhandItem().getItem() instanceof ProjectileItem) {
            firingProjectileLogic(player.level(), player, false);
        }
    }

    private void firingProjectileLogic(Level level, Player player, boolean mainHand) {
        ItemStack stack = mainHand ? player.getMainHandItem() : player.getOffhandItem();
        FiringMode firingMode = getFiringMode(stack);

        ReloadNSwitchCoolDownData ReloadNSwitchCoolDownData = getReloadNSwitchCooldownData(stack);
        if (ReloadNSwitchCoolDownData.isOnCooldown(player.level())) {
            return;
        }

        if (!firingMode.equals(FiringMode.BEAM)) {
            FireCoolDownData FireCoolDownData = getFireCoolDownData(stack);
            if (FireCoolDownData.isOnCooldown(player.level())) {
                return;
            }
        }

        int currentAmmo = getAmmo(stack);
        if (currentAmmo <= 0 && !firingMode.equals(FiringMode.REPULSE)) {
            stack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(AmmoType.NONE.name()));
            level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), ModSounds.FOLEY_NO_AMMO.get(), SoundSource.NEUTRAL, 0.5F, 1.0F);
            if (mainHand) {
                ModClientEvents.mainFiring = false;
            } else {
                ModClientEvents.offFiring = false;
            }
            return;
        }

        if (level.isClientSide) {
            return;
        }

        if (firingMode.equals(FiringMode.REPULSE)) {
            Repulse.execute(level, player, 7, 2.5);

            SoundEvent blasterFireSound;
            blasterFireSound = WeaponSoundsUtil.getWeaponFireSound(projectileWeaponName, firingMode);
            level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), blasterFireSound, SoundSource.NEUTRAL, 0.5F, 1.0F);

            player.awardStat(Stats.ITEM_USED.get(this));

            boolean isMainWeapon = player.getMainHandItem().getItem() instanceof ProjectileItem;
            boolean isOffWeapon = player.getOffhandItem().getItem() instanceof ProjectileItem;
            ProjectileWeaponStats currentStats = stats.get(firingMode);
            float recoil = (player.isShiftKeyDown() && (isOffWeapon ^ isMainWeapon)) ? currentStats.recoil() * 0.4f : currentStats.recoil();
            applyRecoil(player, recoil);

            long cooldownEndTime = level.getGameTime() + currentStats.fireRate();
            stack.set(ModDataComponentTypes.FIRE_COOLDOWN, getFireCoolDownData(stack).withFireCoolDownEndTime(cooldownEndTime));
            return;
        }

        AmmoType currentAmmoType = getAmmoType(stack);
        ExtraFiringRateData extraFiringRateData = getExtraFiringRateData(stack, mainHand);

        if (firingMode.equals(FiringMode.BURST)) {
            if (extraFiringRateData.shotsFired() == 0) {
                fireBolt(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode, 0);
                extraFiringRateData = new ExtraFiringRateData(level.getGameTime() + burstRate, 1, mainHand);
            } else {
                extraFiringRateData = new ExtraFiringRateData(extraFiringRateData.cooldownEndTime(),
                        extraFiringRateData.shotsFired() + 1, extraFiringRateData.mainHand()
                );
            }

            stack.set(ModDataComponentTypes.EXTRA_FIRING_RATE, extraFiringRateData);
        } else if (firingMode.equals(FiringMode.SCATTER) && !currentAmmoType.equals(AmmoType.FLECHETTE_SPREAD_CAN) && !currentAmmoType.equals(AmmoType.FLECHETTE_TOXIC_SPREAD_CAN)){
            for (int shots = 0; shots < scatterShots; shots ++) {
                fireBolt(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode, 0);
            }

            SoundEvent blasterFireSound = WeaponSoundsUtil.getWeaponFireSound(projectileWeaponName, firingMode);
            level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), blasterFireSound, SoundSource.NEUTRAL, 0.5F, 1.0F);
        } else if (getProjectileWeaponName().equals(WeaponName.BOWCASTER) && firingMode.equals(FiringMode.CHARGENSHOOT)) {
            for (int shots = 0; shots < 3; shots ++) {
                fireBolt(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode, shots);
            }

            SoundEvent blasterFireSound = WeaponSoundsUtil.getWeaponFireSound(projectileWeaponName, firingMode);
            level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), blasterFireSound, SoundSource.NEUTRAL, 0.5F, 1.0F);
        } else if (!firingMode.equals(FiringMode.BEAM)){
            fireBolt(level, player, stack, currentAmmo, currentAmmoType, mainHand, firingMode, 0);

            SoundEvent blasterFireSound = WeaponSoundsUtil.getWeaponFireSound(projectileWeaponName, firingMode);
            level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), blasterFireSound, SoundSource.NEUTRAL, 0.5F, 1.0F);
        }

        if (!firingMode.equals(FiringMode.BEAM)) {
            ProjectileWeaponStats currentStats = stats.get(firingMode);
            long cooldownEndTime = level.getGameTime() + currentStats.fireRate();
            stack.set(ModDataComponentTypes.FIRE_COOLDOWN, getFireCoolDownData(stack).withFireCoolDownEndTime(cooldownEndTime));
        }
    }

    //Will be added later
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    //Will be added later
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    private void initializeFiringMode(ItemStack itemstack) {
        FiringModeData firingModeData = new FiringModeData(defaultFiringMode.toString());
        itemstack.set(ModDataComponentTypes.FIRING_MODE.get(), firingModeData);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    private void fireBolt(Level level, Player player, ItemStack stack, int currentAmmo, AmmoType currentAmmoType, boolean mainHand, FiringMode firingMode, int shot) {
        ProjectileWeaponStats currentStats = stats.get(firingMode);
        boolean explosiveShot = (firingMode.equals(FiringMode.CHARGENSHOOT) || firingMode.equals(FiringMode.CHARGENSHOOTONRELEASE) || getProjectileWeaponName().equals(WeaponName.DC15LE) || getProjectileWeaponName().equals(WeaponName.BOWCASTER)) && !getProjectileWeaponName().equals(WeaponName.Z6_ROTARY);
        boolean concussiveShot = projectileWeaponName.equals(WeaponName.LJ40) || projectileWeaponName.equals(WeaponName.LJ50) || projectileWeaponName.equals(WeaponName.W90);

        if (currentAmmo > 0) {
            Snowball projectile;
            Snowball specificProjectile;

            if (AmmoType.getGasTypes().contains(currentAmmoType)) {
                if (firingMode.equals(FiringMode.STUN)) {
                    specificProjectile = new StunBlasterBoltEntity(ModEntities.STUN_BLASTER_BOLT.get(), level, player, 1.5F);
                } else {
                    specificProjectile = switch (currentAmmoType) {
                        case AmmoType.IONIZED_TIBANNA ->
                                new IonizedTibannaBlasterBoltEntity(ModEntities.IONIZED_TIBANNA_BLASTER_BOLT.get(), level, player, this.projectileSpeed, currentStats.damage(), this.classification, explosiveShot, concussiveShot);
                        case AmmoType.SPIN_SEALED_TIBANNA ->
                                new SpinSealedTibannaBlasterBoltEntity(ModEntities.SPIN_SEALED_TIBANNA_BLASTER_BOLT.get(), level, player, this.projectileSpeed, currentStats.damage(), this.classification, explosiveShot, concussiveShot);
                        case AmmoType.TIBANNAX ->
                                new TibannaXBlasterBoltEntity(ModEntities.TIBANNAX_BLASTER_BOLT.get(), level, player, this.projectileSpeed, currentStats.damage(), this.classification, explosiveShot, concussiveShot);
                        case AmmoType.SIG ->
                                new SigBlasterBoltEntity(ModEntities.SIG_BLASTER_BOLT.get(), level, player, this.projectileSpeed, currentStats.damage(), this.classification, explosiveShot, concussiveShot);
                        case AmmoType.MAGNETIZED_SIG ->
                                new MagnetizedSigBlasterBoltEntity(ModEntities.MAGNETIZED_SIG_BLASTER_BOLT.get(), level, player, this.projectileSpeed, currentStats.damage(), this.classification, explosiveShot, concussiveShot);
                        case AmmoType.SKEVON ->
                                new SkevonBlasterBoltEntity(ModEntities.SKEVON_BLASTER_BOLT.get(), level, player, this.projectileSpeed, currentStats.damage(), this.classification, explosiveShot, concussiveShot);
                        default ->
                                new TibannaBlasterBoltEntity(ModEntities.TIBANNA_BLASTER_BOLT.get(), level, player, this.projectileSpeed, currentStats.damage(), this.classification, explosiveShot, concussiveShot);
                    };
                }
            } else if (AmmoType.getSlugTypes().contains(currentAmmoType)) {
                specificProjectile = switch (currentAmmoType) {
                    case AmmoType.EXPLOSIVE_TIPPED_STEEL_SLUG ->
                            new ExplosiveTippedSteelSlugEntity(ModEntities.EXPLOSIVE_TIPPED_STEEL_SLUG.get(), level, player, this.projectileSpeed, currentStats.damage(), this.classification, projectileWeaponName);
                    case AmmoType.POISON_TIPPED_STEEL_SLUG ->
                            new PoisonTippedSteelSlugEntity(ModEntities.POISON_TIPPED_STEEL_SLUG.get(), level, player, this.projectileSpeed, currentStats.damage(), this.classification, projectileWeaponName);
                    case AmmoType.ION_TIPPED_STEEL_SLUG ->
                            new IonTippedSteelSlugEntity(ModEntities.ION_TIPPED_STEEL_SLUG.get(), level, player, this.projectileSpeed, currentStats.damage(), this.classification, projectileWeaponName);
                    case AmmoType.RAZOR_STEEL_SLUG ->
                            new RazorSteelSlugEntity(ModEntities.RAZOR_STEEL_SLUG.get(), level, player, this.projectileSpeed, currentStats.damage(), this.classification, projectileWeaponName);
                    case AmmoType.PLASTIC_SLUG ->
                            new PlasticSlugEntity(ModEntities.PLASTIC_SLUG.get(), level, player, this.projectileSpeed, currentStats.damage(), this.classification);
                    case AmmoType.CERAMIC_SLUG ->
                            new CeramicSlugEntity(ModEntities.CERAMIC_SLUG.get(), level, player, this.projectileSpeed, currentStats.damage(), this.classification);
                    default ->
                            new SteelSlugEntity(ModEntities.STEEL_SLUG.get(), level, player, this.projectileSpeed, currentStats.damage(), this.classification, projectileWeaponName);
                };
            } else {
                specificProjectile = switch (currentAmmoType) {
                    case AmmoType.FLECHETTE_SPREAD_CAN ->
                            new FlechetteSpreadCanEntity(ModEntities.FLECHETTE_SPREAD_CAN.get(), level, player, this.projectileSpeed, currentStats.damage());
                    case AmmoType.FLECHETTE_TOXIC_SPREAD_CAN ->
                            new FlechetteToxicSpreadCanEntity(ModEntities.FLECHETTE_TOXIC_SPREAD_CAN.get(), level, player, this.projectileSpeed, currentStats.damage());
                    case AmmoType.FLECHETTE_TOXIC_CAN ->
                            new FlechetteToxicEntity(ModEntities.FLECHETTE_TOXIC.get(), level, player, this.projectileSpeed, currentStats.damage(), 50);
                    default ->
                            new FlechetteEntity(ModEntities.FLECHETTE.get(), level, player, this.projectileSpeed, currentStats.damage(), 50);
                };
            }
            projectile = specificProjectile;
            projectile.setOwner(player);
            Item mainItem = player.getMainHandItem().getItem();
            Item offItem = player.getOffhandItem().getItem();

            boolean isMainWeapon = mainItem instanceof ProjectileItem;
            boolean isOffWeapon = offItem instanceof ProjectileItem;
            double closeness = 0.27;
            double height = -0.1;
            if ((isMainWeapon ^ isOffWeapon) && player.isShiftKeyDown()) {
                if (mainHand && isMainWeapon) {
                    ProjectileItem weaponMain = (ProjectileItem) mainItem;
                    if (WeaponZoomUtil.getScopeTexture(weaponMain, player.getMainHandItem()) != null) {
                        closeness = 0;
                        height = 0;
                    }
                } else if (!mainHand && isOffWeapon) {
                    ProjectileItem weaponOff = (ProjectileItem) offItem;
                    if (WeaponZoomUtil.getScopeTexture(weaponOff, player.getOffhandItem()) != null) {
                        closeness = 0;
                        height = 0;
                    }
                }
            }
            if (mainHand) {
                projectile.setPos(
                        player.getX() - (Math.cos(Math.toRadians(player.getYRot())) * closeness),
                        player.getEyeY() + height,
                        player.getZ() - (Math.sin(Math.toRadians(player.getYRot())) * closeness)
                );
            } else {
                projectile.setPos(
                        player.getX() + (Math.cos(Math.toRadians(player.getYRot())) * closeness),
                        player.getEyeY() + height,
                        player.getZ() + (Math.sin(Math.toRadians(player.getYRot())) * closeness)
                );
            }

            float accuracyFactor;

            if (currentAmmoType.equals(AmmoType.FLECHETTE_SPREAD_CAN) || currentAmmoType.equals(AmmoType.FLECHETTE_TOXIC_SPREAD_CAN)) {
                accuracyFactor = player.isShiftKeyDown() ? 1.8f * 0.8f / 100 : 1.8f / 100;
            } else {
                accuracyFactor = player.isShiftKeyDown() ? currentStats.inaccuracy() * 0.8f / 100 : currentStats.inaccuracy() / 100;
            }

            if ((mainItem instanceof ProjectileItem mainPItem && !FiringMode.BEAM.equals(mainPItem.getFiringMode(stack))) || (offItem instanceof ProjectileItem offPItem && !FiringMode.BEAM.equals(offPItem.getFiringMode(stack)))) {
                Vec3 forward = player.getLookAngle().normalize();
                Vec3 worldUp = new Vec3(0, 1, 0);
                Vec3 right = forward.cross(worldUp);

                if (right.lengthSqr() < 1.0e-6) {
                    float yawRad = (float) Math.toRadians(player.getYRot());
                    Vec3 horizForward = new Vec3(-Mth.sin(yawRad), 0.0, Mth.cos(yawRad)).normalize();
                    right = horizForward.cross(worldUp);
                }

                right = right.normalize();
                Vec3 camUp = right.cross(forward).normalize();

                double angle = (shot == 0) ? 0.0 : (shot == 2) ? -0.10 : 0.10;

                double cos = Math.cos(angle);
                double sin = Math.sin(angle);

                Vec3 dir = forward.scale(cos).add(right.scale(sin));

                Random random = new Random();

                dir = dir.add(right.scale((random.nextDouble() - 0.5) * accuracyFactor)).add(camUp.scale((random.nextDouble() - 0.5) * accuracyFactor)).normalize();

                Vec3 velocity = dir.scale(this.projectileSpeed);
                projectile.setDeltaMovement(velocity);
                level.addFreshEntity(projectile);
            }

            setAmmo(stack, currentAmmo - 1);
            player.awardStat(Stats.ITEM_USED.get(this));

            if(firingMode.equals(FiringMode.BURST)) {
                SoundEvent blasterFireSound;
                blasterFireSound = WeaponSoundsUtil.getWeaponFireSound(projectileWeaponName, firingMode);
                level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), blasterFireSound, SoundSource.NEUTRAL, 0.5F, 1.0F);
            }

            float recoil = (player.isShiftKeyDown() && (isOffWeapon ^ isMainWeapon)) ? currentStats.recoil() * 0.4f : currentStats.recoil();
            applyRecoil(player, recoil);
        } else {
            level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), ModSounds.FOLEY_NO_AMMO.get(), SoundSource.NEUTRAL, 0.5F, 1.0F);

            long cooldownEndTime = level.getGameTime() + currentStats.fireRate();
            stack.set(ModDataComponentTypes.FIRE_COOLDOWN, getFireCoolDownData(stack).withFireCoolDownEndTime(cooldownEndTime));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player) {
            UUID playerId = player.getUUID();
            if (recoilMap.containsKey(playerId)) {
                Float recoilAmount = recoilMap.get(playerId);

                if (recoilAmount != null) {
                    recoilAmount *= 0.8f;

                    float recoilEffect = recoilMap.get(playerId) - recoilAmount;
                    float newPitch = player.getXRot() - recoilEffect;

                    newPitch = Math.max(-90.0f, Math.min(90.0f, newPitch));
                    player.setXRot(newPitch);

                    if (recoilAmount < 0.01f) {
                        recoilMap.remove(playerId);
                    } else {
                        recoilMap.put(playerId, recoilAmount);
                    }
                }
            }
        }

        if (level.isClientSide || !(entity instanceof Player player)) return;

        if (this.projectileWeaponName.equals(WeaponName.DC15S_SIDEARM)) {
            if ((level.getGameTime() % 20L) == 0L) {
                int currentAmmo = getAmmo(stack);
                if (currentAmmo < max_ammo) {
                    int newAmmo = Math.min(max_ammo, currentAmmo + 1);
                    setAmmo(stack, newAmmo);

                    AmmoType curType = getAmmoType(stack);
                    if (curType == AmmoType.NONE && typAmmoType != null && typAmmoType != AmmoType.NONE) {
                        stack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(typAmmoType.name()));
                    }

                    player.inventoryMenu.broadcastChanges();
                }
            }
        }

        ExtraFiringRateData extraFiringRateData = stack.get(ModDataComponentTypes.EXTRA_FIRING_RATE);
        if (extraFiringRateData == null) return;

        if (getFiringMode(stack).equals(FiringMode.BURST) && extraFiringRateData.shotsFired() > 0 && extraFiringRateData.shotsFired() < 3) {
            if (level.getGameTime() >= extraFiringRateData.cooldownEndTime()) {
                fireBolt(level, player, stack, getAmmo(stack), getAmmoType(stack), extraFiringRateData.mainHand(), getFiringMode(stack), 0);

                extraFiringRateData = extraFiringRateData.withCooldownEndTime(level.getGameTime() + 2).withShotsFired(extraFiringRateData.shotsFired() + 1);

                stack.set(ModDataComponentTypes.EXTRA_FIRING_RATE, extraFiringRateData);
            }
        }

        if (extraFiringRateData.shotsFired() >= 3) {
            boolean mainHand = extraFiringRateData.mainHand();
            extraFiringRateData = new ExtraFiringRateData(0, 0, mainHand);
            stack.set(ModDataComponentTypes.EXTRA_FIRING_RATE, extraFiringRateData);
        }
    }

    public void applyRecoil(Player player, float recoil) {
        UUID playerId = player.getUUID();
        float currentRecoil = recoilMap.getOrDefault(playerId, 0.0f);
        float totalRecoil = currentRecoil + recoil;

        totalRecoil = Math.min(totalRecoil, 20.0f);
        recoilMap.put(playerId, totalRecoil);
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
        return max_ammo;
    }

    public int reload(Player player, ItemStack stack, boolean mainHand, int additionalAmmoToConsume) {
        int currentAmmo = getAmmo(stack);
        AmmoType currentAmmoType = getAmmoType(stack);
        FiringMode firingMode = getFiringMode(stack);
        int ammoToConsume = 0;

        if (currentAmmo >= max_ammo) {
            return 0;
        }

        ReloadNSwitchCoolDownData reloadCooldown = stack.get(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN);
        if (reloadCooldown == null) {
            reloadCooldown = new ReloadNSwitchCoolDownData(0);
            stack.set(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN, reloadCooldown);
        }

        if (reloadCooldown.isOnCooldown(player.level())) {
            return 0;
        }

        boolean reloaded = false;

        for (int i = 0; i < player.getInventory().items.size(); i++) {
            ItemStack ammoStack = player.getInventory().items.get(i);
            Item item = ammoStack.getItem();
            player.inventoryMenu.broadcastChanges();

             if (item instanceof SlugItem slugItem && classification.equals(WeaponClassification.SLUGTHROWER)) {
                if (tryReloadSlug(player, stack, ammoStack, slugItem, currentAmmo, i, currentAmmoType, mainHand, firingMode, additionalAmmoToConsume)) {
                    stack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(slugItem.getAmmoType().name()));
                    reloaded = true;
                    ammoToConsume = (player.isCreative() ? (max_ammo - currentAmmo) : Math.min((max_ammo - currentAmmo), ((ammoStack.getCount() - additionalAmmoToConsume))));
                    break;
                }
            } else if (item instanceof FlechetteCanisterItem flechetteItem && classification.equals(WeaponClassification.FLECHETTE)) {
                if (tryReloadFlechette(player, stack, ammoStack, flechetteItem, currentAmmo, i, currentAmmoType, mainHand, firingMode, additionalAmmoToConsume)) {
                    stack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(flechetteItem.getAmmoType().name()));
                    reloaded = true;
                    ammoToConsume = (player.isCreative() ? (max_ammo - currentAmmo) : Math.min((max_ammo - currentAmmo), ((ammoStack.getCount() - additionalAmmoToConsume))));
                    break;
                }
            } else if (item instanceof GasItem gasItem && !classification.equals(WeaponClassification.FLECHETTE) && !classification.equals(WeaponClassification.SLUGTHROWER)) {
                if (tryReloadGas(player, stack, ammoStack, gasItem, currentAmmo, i, currentAmmoType, mainHand, firingMode)) {
                    reloaded = true;
                    break;
                }
            }
        }

        if (!reloaded && player.isCreative()) {
            AmmoType assumedType = (currentAmmoType != AmmoType.NONE) ? currentAmmoType : typAmmoType;

            if (assumedType == null || assumedType == AmmoType.NONE) {
                return 0;
            }

            boolean canAssumeType = switch (classification) {
                case SLUGTHROWER -> AmmoType.getSlugTypes().contains(assumedType);
                case FLECHETTE   -> AmmoType.getFlechetteTypes().contains(assumedType);
                default          -> AmmoType.getGasTypes().contains(assumedType);
            };

            if (!canAssumeType) {
                return 0;
            }

            int ammoNeeded = max_ammo - currentAmmo;
            if (ammoNeeded > 0) {
                int newAmmo = currentAmmo + ammoNeeded;

                setAmmo(stack, newAmmo);
                stack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(assumedType.name()));
                PayloadRegister.sendToServer(new SSReloadPacket(stack, newAmmo, assumedType.name(), mainHand));

                SoundEvent reloadSound = WeaponSoundsUtil.getWeaponReloadSound(projectileWeaponName, firingMode, classification);
                player.playSound(reloadSound, 0.3F, 1.0F);

                return 0;
            }
        }
        return ammoToConsume;
    }

    private boolean tryReloadGas(Player player, ItemStack projectileWeaponStack, ItemStack ammoStack, GasItem gasItem,
                                  int currentAmmo, int slotIndex, AmmoType currentAmmoType, boolean mainHand, FiringMode firingMode) {
        int gasAmmo = gasItem.getAmmo(ammoStack);
        if (gasAmmo <= 0) return false;

        AmmoType gasType = gasItem.getAmmoType();
        if (currentAmmoType != AmmoType.NONE && currentAmmoType != gasType) return false;

        int ammoNeeded = max_ammo - currentAmmo;
        int ammoToReload = player.isCreative() ? ammoNeeded : Math.min(ammoNeeded, gasAmmo);

        currentAmmo += ammoToReload;
        setAmmo(projectileWeaponStack, currentAmmo);
        PayloadRegister.sendToServer(new SSReloadPacket(projectileWeaponStack, currentAmmo, gasType.toString(), mainHand));

        if (!player.isCreative()) {
            gasAmmo -= ammoToReload;
            gasItem.setAmmo(ammoStack, gasAmmo);
            PayloadRegister.sendToServer(new SSGasAmmoPacket(ammoStack, gasAmmo, slotIndex));
        }

        player.inventoryMenu.broadcastChanges();

        SoundEvent reloadSound = WeaponSoundsUtil.getWeaponReloadSound(projectileWeaponName, firingMode, classification);
        player.playSound(reloadSound, 0.5F, 1.0F);
        return true;
    }

    private boolean tryReloadSlug(Player player, ItemStack projectileWeaponStack, ItemStack ammoStack, SlugItem slugItem,
                                 int currentAmmo, int slotIndex, AmmoType currentAmmoType, boolean mainHand, FiringMode firingMode, int additionalAmmoToReload) {
        int slugAmmo = ammoStack.getCount() - additionalAmmoToReload;

        AmmoType slugType = slugItem.getAmmoType();
        if (currentAmmoType != AmmoType.NONE && !currentAmmoType.equals(slugType)) return false;

        int ammoNeeded = max_ammo - currentAmmo;
        int ammoToReload = player.isCreative() ? ammoNeeded : Math.min(ammoNeeded, slugAmmo);

        currentAmmo += ammoToReload;
        setAmmo(projectileWeaponStack, currentAmmo);
        PayloadRegister.sendToServer(new SSReloadPacket(projectileWeaponStack, currentAmmo, slugType.toString(), mainHand));

        SoundEvent reloadSound = WeaponSoundsUtil.getWeaponReloadSound(projectileWeaponName, firingMode, classification);
        player.playSound(reloadSound, 0.5F, 1.0F);
        return true;
    }

    private boolean tryReloadFlechette(Player player, ItemStack projectileWeaponStack, ItemStack ammoStack, FlechetteCanisterItem flechetteCanisterItem,
                                 int currentAmmo, int slotIndex, AmmoType currentAmmoType, boolean mainHand, FiringMode firingMode, int additionalAmmoToReload) {
        int flechetteCanisterAmmo = ammoStack.getCount() - additionalAmmoToReload;

        AmmoType flechetteCanisterType = flechetteCanisterItem.getAmmoType();
        if (currentAmmoType != AmmoType.NONE && !currentAmmoType.equals(flechetteCanisterType)) return false;

        int ammoNeeded = max_ammo - currentAmmo;
        int ammoToReload = player.isCreative() ? ammoNeeded : Math.min(ammoNeeded, flechetteCanisterAmmo);

        currentAmmo += ammoToReload;
        setAmmo(projectileWeaponStack, currentAmmo);
        PayloadRegister.sendToServer(new SSReloadPacket(projectileWeaponStack, currentAmmo, flechetteCanisterType.toString(), mainHand));

        SoundEvent reloadSound = WeaponSoundsUtil.getWeaponReloadSound(projectileWeaponName, firingMode, classification);
        player.playSound(reloadSound, 0.5F, 1.0F);
        return true;
    }

    public void unload(Player player, ItemStack stack, boolean mainHand){
        int currentAmmo = getAmmo(stack);
        AmmoType currentAmmoType = getAmmoType(stack);

        if (currentAmmo <= 0) {
            player.displayClientMessage(Component.translatable("item.knightfall.projectileWeapon.no_ammo_to_unload"), true);
            return;
        }

        ItemStack unloadedAmmo;
        if (classification.equals(WeaponClassification.FLECHETTE)) {
            if (currentAmmoType.equals(AmmoType.FLECHETTE_TOXIC_SPREAD_CAN)) {
                unloadedAmmo = new ItemStack(ModItems.FLECHETTE_TOXIC_SPREAD_CANISTER.get(), currentAmmo);
            } else if (currentAmmoType.equals(AmmoType.FLECHETTE_TOXIC_CAN)) {
                unloadedAmmo = new ItemStack(ModItems.FLECHETTE_TOXIC_CANISTER.get(), currentAmmo);
            } else if (currentAmmoType.equals(AmmoType.FLECHETTE_SPREAD_CAN)) {
                unloadedAmmo = new ItemStack(ModItems.FLECHETTE_SPREAD_CANISTER.get(), currentAmmo);
            } else {
                unloadedAmmo = new ItemStack(ModItems.FLECHETTE_CANISTER.get(), currentAmmo);
            }
            PayloadRegister.sendToServer(new SSGiveItemPacket(unloadedAmmo));
            player.inventoryMenu.broadcastChanges();
            SoundEvent unloadSound = WeaponSoundsUtil.getWeaponUnloadSound(getFiringMode(stack), classification);
            player.playSound(unloadSound, 0.5F, 1.0F);
        } else if (classification.equals(WeaponClassification.SLUGTHROWER)) {
            if (currentAmmoType.equals(AmmoType.PLASTIC_SLUG)) {
                unloadedAmmo = new ItemStack(ModItems.PLASTIC_SLUG.get(), currentAmmo);
            } else if (currentAmmoType.equals(AmmoType.CERAMIC_SLUG)) {
                unloadedAmmo = new ItemStack(ModItems.CERAMIC_SLUG.get(), currentAmmo);
            } else if (currentAmmoType.equals(AmmoType.RAZOR_STEEL_SLUG)) {
                unloadedAmmo = new ItemStack(ModItems.RAZOR_STEEL_SLUG.get(), currentAmmo);
            } else if (currentAmmoType.equals(AmmoType.POISON_TIPPED_STEEL_SLUG)) {
                unloadedAmmo = new ItemStack(ModItems.POISON_TIPPED_STEEL_SLUG.get(), currentAmmo);
            } else if (currentAmmoType.equals(AmmoType.EXPLOSIVE_TIPPED_STEEL_SLUG)) {
                unloadedAmmo = new ItemStack(ModItems.EXPLOSIVE_TIPPED_STEEL_SLUG.get(), currentAmmo);
            } else if (currentAmmoType.equals(AmmoType.ION_TIPPED_STEEL_SLUG)) {
                unloadedAmmo = new ItemStack(ModItems.ION_TIPPED_STEEL_SLUG.get(), currentAmmo);
            } else {
                unloadedAmmo = new ItemStack(ModItems.STEEL_SLUG.get(), currentAmmo);
            }
            PayloadRegister.sendToServer(new SSGiveItemPacket(unloadedAmmo));
            player.inventoryMenu.broadcastChanges();
            SoundEvent unloadSound = WeaponSoundsUtil.getWeaponUnloadSound(getFiringMode(stack), classification);
            player.playSound(unloadSound, 0.5F, 1.0F);
        } else {
            Item mainItem = player.getMainHandItem().getItem();
            Item offItem = player.getOffhandItem().getItem();
            boolean isMainWeapon = mainItem instanceof ProjectileItem;
            boolean isOffWeapon = offItem instanceof ProjectileItem;
            double closeness = 0.27;
            double height = -0.1;
            double x;
            double y;
            double z;
            if ((isMainWeapon ^ isOffWeapon) && player.isShiftKeyDown()) {
                if (mainHand && isMainWeapon) {
                    ProjectileItem weaponMain = (ProjectileItem) mainItem;
                    if (WeaponZoomUtil.getScopeTexture(weaponMain, player.getMainHandItem()) != null) {
                        closeness = 0;
                        height = 0;
                    }
                } else if (!mainHand && isOffWeapon) {
                    ProjectileItem weaponOff = (ProjectileItem) offItem;
                    if (WeaponZoomUtil.getScopeTexture(weaponOff, player.getOffhandItem()) != null) {
                        closeness = 0;
                        height = 0;
                    }
                }
            }
            if (mainHand) {
                x = player.getX() - (Math.cos(Math.toRadians(player.getYRot())) * closeness) - (Math.sin(Math.toRadians(player.getYRot())) * 1);
                y = player.getEyeY() + height - (Math.sin(Math.toRadians(player.getXRot())) * 1.2);
                z = player.getZ() - (Math.sin(Math.toRadians(player.getYRot())) * closeness) + (Math.cos(Math.toRadians(player.getYRot())) * 1);
            } else {
                x = player.getX() + (Math.cos(Math.toRadians(player.getYRot())) * closeness) - (Math.sin(Math.toRadians(player.getYRot())) * 1);
                y = player.getEyeY() + height - (Math.sin(Math.toRadians(player.getXRot())) * 1.2);
                z = player.getZ() + (Math.sin(Math.toRadians(player.getYRot())) * closeness) + (Math.cos(Math.toRadians(player.getYRot())) * 1);
            }
            player.level().addParticle(
                    ParticleTypes.LARGE_SMOKE,
                    x, y, z,
                    0, 0.05 + player.level().random.nextDouble() * 0.05, 0);
            SoundEvent unloadSound = WeaponSoundsUtil.getWeaponUnloadSound(getFiringMode(stack), classification);
            player.playSound(unloadSound, 0.5F, 1.0F);
        }
        setAmmo(stack, 0);
        PayloadRegister.sendToServer(new SSReloadPacket(stack, 0, currentAmmoType.toString(), mainHand));
        player.inventoryMenu.broadcastChanges();
    }

    public void startCooldown(Player player, ItemStack stack, boolean reloading) {
        Level level = player.level();
        int currentAmmo = getAmmo(stack);

        if (reloading & currentAmmo >= max_ammo) {
            return;
        }

        ReloadNSwitchCoolDownData ReloadNSwitchCoolDownData = getReloadNSwitchCooldownData(stack);
        if (ReloadNSwitchCoolDownData.isOnCooldown(player.level())) {
            return;
        }

        long ReloadNSwitchCoolDownEndTime;
        if (reloading) {
            ReloadNSwitchCoolDownEndTime = level.getGameTime() + WeaponTimingUtil.getProjectileWeaponReloadTime(projectileWeaponName, getFiringMode(stack));
        } else {
            ReloadNSwitchCoolDownEndTime = level.getGameTime() + WeaponTimingUtil.getProjectileWeaponSwitchTime(projectileWeaponName, getFiringMode(stack));
        }
        stack.set(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN, ReloadNSwitchCoolDownData.withReloadNSwitchCoolDownEndTime(ReloadNSwitchCoolDownEndTime));
    }

    public void switchFiringMode(Player player, ItemStack stack, boolean mainHand) {
        ReloadNSwitchCoolDownData cooldownData = getReloadNSwitchCooldownData(stack);
        if (cooldownData.isOnCooldown(player.level())) return;

        FiringMode currentFiringMode = getFiringMode(stack);
        int index = firingModes.indexOf(currentFiringMode);
        if (index == -1) index = 0;

        FiringMode nextMode = firingModes.get((index + 1) % firingModes.size());

        FiringModeData newData = stack.get(ModDataComponentTypes.FIRING_MODE.get()).withFiringMode(nextMode.toString());
        stack.set(ModDataComponentTypes.FIRING_MODE.get(), newData);

        PayloadRegister.sendToServer(new SSFiringModePacket(stack, nextMode.toString(), mainHand));

        SoundEvent switchSound = WeaponSoundsUtil.getWeaponSwitchFireMode(projectileWeaponName, currentFiringMode);
        player.playSound(switchSound, 0.5F, 1.0F);
    }

    @Override
    public Component getName(ItemStack stack) {
        ChatFormatting color;

        AmmoType gasType = null;
        int currentAmmo = getAmmo(stack);
        color = ChatFormatting.WHITE;
        if (currentAmmo <= 0) {
            stack.set(ModDataComponentTypes.AMMO_TYPE.get(), null);
        } else {
            if (stack.get(ModDataComponentTypes.AMMO_TYPE.get()) != null) {
                gasType = AmmoType.valueOf(stack.get(ModDataComponentTypes.AMMO_TYPE.get()).ammoType());
            }

            color = switch (gasType) {
                case AmmoType.TIBANNA -> ChatFormatting.RED;
                case AmmoType.IONIZED_TIBANNA -> ChatFormatting.BLUE;
                case AmmoType.SPIN_SEALED_TIBANNA -> ChatFormatting.GREEN;
                case AmmoType.TIBANNAX -> ChatFormatting.GRAY;
                case AmmoType.SIG -> ChatFormatting.YELLOW;
                case AmmoType.MAGNETIZED_SIG -> ChatFormatting.DARK_PURPLE;
                case AmmoType.SKEVON -> ChatFormatting.GOLD;
                case null, default -> ChatFormatting.WHITE;
            };
        }

        ChatFormatting finalColor = color;
        return super.getName(stack).copy().withStyle(style -> style.withColor(finalColor));
    }

    @Override
    public void appendHoverText(ItemStack weaponStack, TooltipContext pContext, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.literal("Ammo: " + getAmmo(weaponStack) + "/" + max_ammo));
        if(Screen.hasShiftDown()){
            var ammoComp = weaponStack.get(ModDataComponentTypes.AMMO_TYPE.get());

            if (ammoComp == null || ammoComp.ammoType() == null || ammoComp.ammoType().isBlank()) {
                pTooltip.add(Component.literal("No Ammo"));
            } else {
                try {
                    AmmoType type = AmmoType.valueOf(ammoComp.ammoType());
                    pTooltip.add(Component.literal("Ammo Type: " + type.toString()));
                } catch (IllegalArgumentException ex) {
                    pTooltip.add(Component.literal("No Ammo"));
                }
            }
        } else {
            pTooltip.add(Component.translatable("tooltip.knightfall.blaster.shift"));
        }
        super.appendHoverText(weaponStack, pContext, pTooltip, pFlag);
    }
}