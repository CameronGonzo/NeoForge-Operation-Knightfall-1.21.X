package net.uhhitscam.starwars.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.starwars.component.*;
import net.uhhitscam.starwars.entity.ModEntities;
import net.uhhitscam.starwars.entity.custom.*;
import net.uhhitscam.starwars.network.PayloadRegister;
import net.uhhitscam.starwars.network.SSFiringModePacket;
import net.uhhitscam.starwars.network.SSGasAmmoPacket;
import net.uhhitscam.starwars.network.SSReloadPacket;
import net.uhhitscam.starwars.sound.ModSounds;
import net.uhhitscam.starwars.util.BlasterSoundsUtil;
import net.uhhitscam.starwars.util.BlasterTimingUtil;
import net.uhhitscam.starwars.util.BlasterZoomUtil;

import java.util.*;

public class BlasterItem extends Item {
    private final float bolt_speed;
    private final int max_ammo;
    private final int burstRate;
    private final int scatterShots;
    private final EnumMap<FiringMode, BlasterStats> stats;
    private final List<FiringMode> firingModes;
    private final FiringMode defaultFiringMode;
    private final GasType typGasType;
    private final Classification classification;
    private final BlasterName blasterName;

    private final Map<UUID, Float> recoilMap = new HashMap<>();

    public BlasterItem(Properties properties, float bolt_speed, int max_ammo, int burstRate, int scatterShots,
                       EnumMap<FiringMode, BlasterStats> stats, List<FiringMode> firingModes, FiringMode defaultFiringMode, GasType typGasType,
                       Classification classification, BlasterName blasterName) {
        super(properties);
        this.bolt_speed = bolt_speed;
        this.max_ammo = max_ammo;
        this.burstRate = burstRate;
        this.scatterShots = scatterShots;
        this.stats = stats;
        this.firingModes = firingModes;
        this.defaultFiringMode = defaultFiringMode;
        this.typGasType = typGasType;
        this.classification = classification;
        this.blasterName = blasterName;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }

    public void mainHandFiring(Player player) {
        if (player.getMainHandItem().getItem() instanceof BlasterItem) {
            firingBlasterLogic(player.level(), player, true);
        }
    }

    public void offHandFiring(Player player) {
        if (player.getOffhandItem().getItem() instanceof BlasterItem) {
            firingBlasterLogic(player.level(), player, false);
        }
    }

    private void firingBlasterLogic(Level level, Player player, boolean mainHand) {
        ItemStack stack = mainHand ? player.getMainHandItem() : player.getOffhandItem();

        ReloadNSwitchCoolDownData ReloadNSwitchCoolDownData = getReloadNSwitchCooldownData(stack);
        if (ReloadNSwitchCoolDownData.isOnCooldown(player.level())) {
            return;
        }

        int currentAmmo = getAmmo(stack);
        if (currentAmmo <= 0) {
            level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), ModSounds.FOLEY_NO_AMMO.get(), SoundSource.NEUTRAL, 0.5F, 1.0F);
            return;
        }

        FireCoolDownData FireCoolDownData = getFireCoolDownData(stack);
        if (FireCoolDownData.isOnCooldown(player.level())) {
            return;
        }

        if (level.isClientSide) {
            return;
        }

        GasType currentGasType = getGasType(stack);
        FiringMode firingMode = getFiringMode(stack);
        ExtraFiringRateData extraFiringRateData = getExtraFiringRateData(stack, mainHand);

        if (firingMode.equals(FiringMode.BURST)) {
            if (extraFiringRateData.shotsFired() == 0) {
                fireBolt(level, player, stack, currentAmmo, currentGasType, mainHand, firingMode);
                extraFiringRateData = new ExtraFiringRateData(level.getGameTime() + burstRate, 1, mainHand);
            } else {
                extraFiringRateData = new ExtraFiringRateData(extraFiringRateData.cooldownEndTime(),
                        extraFiringRateData.shotsFired() + 1, extraFiringRateData.mainHand()
                );
            }

            stack.set(ModDataComponentTypes.EXTRA_FIRING_RATE, extraFiringRateData);
        } else if (firingMode.equals(FiringMode.SCATTER)){
            for (int shots = 0; shots < scatterShots; shots ++) {
                fireBolt(level, player, stack, currentAmmo, currentGasType, mainHand, firingMode);
            }
        } else {
            fireBolt(level, player, stack, currentAmmo, currentGasType, mainHand, firingMode);
        }

        BlasterStats currentStats = stats.get(firingMode);
        long cooldownEndTime = level.getGameTime() + currentStats.fireRate();
        stack.set(ModDataComponentTypes.FIRE_COOLDOWN, getFireCoolDownData(stack).withFireCoolDownEndTime(cooldownEndTime));
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

    private void fireBolt(Level level, Player player, ItemStack stack, int currentAmmo, GasType currentGasType, boolean mainHand, FiringMode firingMode) {
        BlasterStats currentStats = stats.get(firingMode);

        if (currentAmmo > 0) {
            Snowball bolt;
            Snowball specific_bolt;
            if (firingMode.equals(FiringMode.STUN)) {
                specific_bolt = new StunBlasterBoltEntity(ModEntities.STUN_BLASTER_BOLT.get(), level, player, 1.5F);
            } else {
                specific_bolt = switch (currentGasType) {
                    case GasType.IONIZED_TIBANNA ->
                            new IonizedTibannaBlasterBoltEntity(ModEntities.IONIZED_TIBANNA_BLASTER_BOLT.get(), level, player, this.bolt_speed, currentStats.damage(), currentGasType, this.classification);
                    case GasType.SPIN_SEALED_TIBANNA ->
                            new SpinSealedTibannaBlasterBoltEntity(ModEntities.SPIN_SEALED_TIBANNA_BLASTER_BOLT.get(), level, player, this.bolt_speed, currentStats.damage(), currentGasType, this.classification);
                    case GasType.TIBANNAX ->
                            new TibannaXBlasterBoltEntity(ModEntities.TIBANNAX_BLASTER_BOLT.get(), level, player, this.bolt_speed, currentStats.damage(), currentGasType, this.classification);
                    case GasType.SIG ->
                            new SigBlasterBoltEntity(ModEntities.SIG_BLASTER_BOLT.get(), level, player, this.bolt_speed, currentStats.damage(), currentGasType, this.classification);
                    case GasType.MAGNETIZED_SIG ->
                            new MagnetizedSigBlasterBoltEntity(ModEntities.MAGNETIZED_SIG_BLASTER_BOLT.get(), level, player, this.bolt_speed, currentStats.damage(), currentGasType, this.classification);
                    case GasType.SKEVON ->
                            new SkevonBlasterBoltEntity(ModEntities.SKEVON_BLASTER_BOLT.get(), level, player, this.bolt_speed, currentStats.damage(), currentGasType, this.classification);
                    default ->
                            new TibannaBlasterBoltEntity(ModEntities.TIBANNA_BLASTER_BOLT.get(), level, player, this.bolt_speed, currentStats.damage(), currentGasType, this.classification);
                };
            }
            bolt = specific_bolt;
            bolt.setOwner(player);
            Item mainItem = player.getMainHandItem().getItem();
            Item offItem = player.getOffhandItem().getItem();

            boolean isMainBlaster = mainItem instanceof BlasterItem;
            boolean isOffBlaster = offItem instanceof BlasterItem;
            double closeness = 0.27;
            double height = -0.1;
            if ((isMainBlaster ^ isOffBlaster) && player.isShiftKeyDown()) {
                if (mainHand && isMainBlaster) {
                    BlasterItem blasterMain = (BlasterItem) mainItem;
                    if (BlasterZoomUtil.getScopeTexture(blasterMain, player.getMainHandItem()) != null) {   //main hand only has scope
                        closeness = 0;
                        height = 0;
                    }
                } else if (!mainHand && isOffBlaster) {
                    BlasterItem blasterOff = (BlasterItem) offItem;
                    if (BlasterZoomUtil.getScopeTexture(blasterOff, player.getOffhandItem()) != null) {   //off hand only has scope
                        closeness = 0;
                        height = 0;
                    }
                }
            }
            if (mainHand) {
                bolt.setPos(
                        player.getX() - (Math.cos(Math.toRadians(player.getYRot())) * closeness),
                        player.getEyeY() + height,
                        player.getZ() - (Math.sin(Math.toRadians(player.getYRot())) * closeness)
                );
            } else {
                bolt.setPos(
                        player.getX() + (Math.cos(Math.toRadians(player.getYRot())) * closeness),
                        player.getEyeY() + height,
                        player.getZ() + (Math.sin(Math.toRadians(player.getYRot())) * closeness)
                );
            }

            double pitch = Math.toRadians(-player.getXRot());
            double yaw = Math.toRadians(-player.getYRot());
            float accuracyFactor = player.isShiftKeyDown() ? currentStats.inaccuracy() * 0.8f / 100 : currentStats.inaccuracy() / 100;
            Random random = new Random();
            double xVelocity = Math.cos(pitch) * Math.sin(yaw) + (random.nextDouble() - 0.5f) * accuracyFactor;
            double yVelocity = Math.sin(pitch) + (random.nextDouble() - 0.5f) * accuracyFactor;
            double zVelocity = Math.cos(pitch) * Math.cos(yaw) + (random.nextDouble() - 0.5f) * accuracyFactor;
            Vec3 velocity = new Vec3(xVelocity, yVelocity, zVelocity).normalize().scale(this.bolt_speed);
            bolt.setDeltaMovement(velocity);
            level.addFreshEntity(bolt);

            SoundEvent blasterFireSound;
            blasterFireSound = BlasterSoundsUtil.getBlasterFireSound(blasterName, firingMode);
            level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), blasterFireSound, SoundSource.NEUTRAL, 0.5F, 1.0F);

            setAmmo(stack, currentAmmo - 1);

            player.awardStat(Stats.ITEM_USED.get(this));

            float recoil = (player.isShiftKeyDown() && (isOffBlaster ^ isMainBlaster)) ? currentStats.recoil() * 0.4f : currentStats.recoil();
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
                    recoilAmount *= 0.8f; //speed of recovery

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

        ExtraFiringRateData extraFiringRateData = stack.get(ModDataComponentTypes.EXTRA_FIRING_RATE);
        if (extraFiringRateData == null) return;

        if (getFiringMode(stack).equals(FiringMode.BURST) && extraFiringRateData.shotsFired() > 0 && extraFiringRateData.shotsFired() < 3) {
            if (level.getGameTime() >= extraFiringRateData.cooldownEndTime()) {
                fireBolt(level, player, stack, getAmmo(stack), getGasType(stack), extraFiringRateData.mainHand(), getFiringMode(stack));

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
        GasAmmoData data = stack.get(ModDataComponentTypes.GAS_AMMO.get());
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
        stack.set(ModDataComponentTypes.GAS_AMMO.get(), new GasAmmoData(ammo));
        if (stack.getEntityRepresentation() instanceof Player player) {
            player.inventoryMenu.broadcastChanges();
        }
    }

    private GasType getGasType(ItemStack stack) {
        return GasType.valueOf(stack.get(ModDataComponentTypes.GAS_TYPE.get()).gasType());
    }

    public FiringMode getFiringMode(ItemStack stack) {
        FiringModeData data = stack.get(ModDataComponentTypes.FIRING_MODE.get());
        if (data == null) {
            initializeFiringMode(stack);
            data = stack.get(ModDataComponentTypes.FIRING_MODE.get());
        }
        return FiringMode.valueOf(data.firingMode());
    }

    public Classification getClassification() {
        return classification;
    }

    public BlasterName getBlasterName() {
        return blasterName;
    }

    public int getMaxAmmo() {
        return max_ammo;
    }

    public void reload(Player player, ItemStack blasterStack, boolean mainHand) {
        int currentAmmo = getAmmo(blasterStack);
        GasTypeData blasterGasTypeData = blasterStack.get(ModDataComponentTypes.GAS_TYPE.get());
        GasType currentGasType = (blasterGasTypeData != null) ? GasType.valueOf(blasterGasTypeData.gasType()) : null;
        FiringMode blasterFireMode = getFiringMode(blasterStack);

        if (currentAmmo >= max_ammo) {
            player.displayClientMessage(Component.translatable("item.starwars.blaster.full_ammo"), true);
            return;
        }

        ReloadNSwitchCoolDownData reloadCooldown = blasterStack.get(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN);
        if (reloadCooldown == null) {
            reloadCooldown = new ReloadNSwitchCoolDownData(0);
            blasterStack.set(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN, reloadCooldown);
        }

        if (reloadCooldown.isOnCooldown(player.level())) {
            return;
        }

        boolean foundGasItem = false;
        boolean reloaded = false;

        for (int i = 0; i < player.getInventory().items.size(); i++) {
            ItemStack stack = player.getInventory().items.get(i);
            if (!(stack.getItem() instanceof GasItem gasItem)) continue;

            int gasAmmo = gasItem.getAmmo(stack);
            if (gasAmmo <= 0) continue;

            foundGasItem = true;
            GasType gasType = GasType.valueOf(gasItem.getGasType());

            boolean compatible = currentGasType == null || currentGasType.equals(gasType);

            if (!compatible) continue;

            int ammoNeeded = max_ammo - currentAmmo;
            int ammoToReload = player.isCreative() ? ammoNeeded : Math.min(ammoNeeded, gasAmmo);

            currentAmmo += ammoToReload;
            setAmmo(blasterStack, currentAmmo);
            PayloadRegister.sendToServer(new SSReloadPacket(blasterStack, currentAmmo, gasType.toString(), mainHand));

            if (!player.isCreative()) {
                gasAmmo -= ammoToReload;
                gasItem.setAmmo(stack, gasAmmo);
                PayloadRegister.sendToServer(new SSGasAmmoPacket(stack, gasAmmo, i));
            }

            player.inventoryMenu.broadcastChanges();

            SoundEvent reloadSound = BlasterSoundsUtil.getBlasterReloadSound(blasterName, blasterFireMode);
            player.playSound(reloadSound, 0.5F, 1.0F);

            reloaded = true;
            break;
        }

        // Creative-only fallback
        if (!reloaded && player.isCreative()) {
            int ammoNeeded = max_ammo - currentAmmo;
            if (ammoNeeded > 0) {
                currentAmmo += ammoNeeded;
                setAmmo(blasterStack, currentAmmo);

                String gasTypeToSend = (currentGasType != null) ? currentGasType.toString() : typGasType.toString();
                PayloadRegister.sendToServer(new SSReloadPacket(blasterStack, currentAmmo, gasTypeToSend, mainHand));

                player.inventoryMenu.broadcastChanges();

                SoundEvent reloadSound = BlasterSoundsUtil.getBlasterReloadSound(blasterName, blasterFireMode);
                player.playSound(reloadSound, 0.3F, 1.0F);

                return;
            }
        }

        if (!foundGasItem) {
            player.displayClientMessage(Component.translatable("item.starwars.blaster.no_gas_items"), true);
        } else if (!reloaded) {
            player.displayClientMessage(Component.translatable("item.starwars.blaster.no_compatible_gas"), true);
        }
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
            ReloadNSwitchCoolDownEndTime = level.getGameTime() + BlasterTimingUtil.getBlasterReloadTime(blasterName, getFiringMode(stack));
        } else {
            ReloadNSwitchCoolDownEndTime = level.getGameTime() + BlasterTimingUtil.getBlasterSwitchTime(blasterName, getFiringMode(stack));
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

        SoundEvent switchSound = BlasterSoundsUtil.getBlasterSwitchFireMode(blasterName, currentFiringMode);
        player.playSound(switchSound, 0.5F, 1.0F);
    }

    @Override
    public Component getName(ItemStack stack) {
        ChatFormatting color;

        GasType gasType = null;
        int currentAmmo = getAmmo(stack);
        color = ChatFormatting.WHITE;
        if (currentAmmo <= 0) {
            stack.set(ModDataComponentTypes.GAS_TYPE.get(), null);
        } else {
            if (stack.get(ModDataComponentTypes.GAS_TYPE.get()) != null) {
                gasType = GasType.valueOf(stack.get(ModDataComponentTypes.GAS_TYPE.get()).gasType());
            }

            color = switch (gasType) {
                case GasType.TIBANNA -> ChatFormatting.RED;
                case GasType.IONIZED_TIBANNA -> ChatFormatting.BLUE;
                case GasType.SPIN_SEALED_TIBANNA -> ChatFormatting.GREEN;
                case GasType.TIBANNAX -> ChatFormatting.GRAY;
                case GasType.SIG -> ChatFormatting.YELLOW;
                case GasType.MAGNETIZED_SIG -> ChatFormatting.DARK_PURPLE;
                case GasType.SKEVON -> ChatFormatting.GOLD;
                case null -> ChatFormatting.WHITE;
            };
        }

        ChatFormatting finalColor = color;
        return super.getName(stack).copy().withStyle(style -> style.withColor(finalColor));
    }

    @Override
    public void appendHoverText(ItemStack blasterStack, TooltipContext pContext, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.literal("Ammo: " + getAmmo(blasterStack) + "/" + max_ammo));
        if(Screen.hasShiftDown()){
            //nothing yet
        } else {
            pTooltip.add(Component.translatable("tooltip.starwars.blaster.shift"));
        }
        super.appendHoverText(blasterStack, pContext, pTooltip, pFlag);
    }
}