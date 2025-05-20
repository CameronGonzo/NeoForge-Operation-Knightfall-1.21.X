package net.uhhitscam.starwars.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
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
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.starwars.component.*;
import net.uhhitscam.starwars.entity.ModEntities;
import net.uhhitscam.starwars.entity.custom.*;
import net.uhhitscam.starwars.item.ModItems;
import net.uhhitscam.starwars.network.PayloadRegister;
import net.uhhitscam.starwars.network.SSFiringModePacket;
import net.uhhitscam.starwars.network.SSGasAmmoPacket;
import net.uhhitscam.starwars.network.SSReloadPacket;
import net.uhhitscam.starwars.sound.ModSounds;
import net.uhhitscam.starwars.util.BlasterSoundsUtil;
import net.uhhitscam.starwars.util.BlasterTimingUtil;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BlasterItem extends Item {
    private final float bolt_speed;
    private final int max_ammo;
    private final int burstRate;
    private final int semiFireRate;
    private final int burstFireRate;
    private final int fullFireRate;
    private final int chargedFireRate;
    private final int repulseFireRate;
    private final int sniperFireRate;
    private final int stunFireRate = 15;
    private final List<String> firingModes;
    private final float semiRecoil;
    private final float burstRecoil;
    private final float fullRecoil;
    private final float chargedRecoil;
    private final float repulseRecoil;
    private final float sniperRecoil;
    private final int stunRecoil = 10;
    private final String typFiringMode;
    private final float semiInaccuracy;
    private final float burstInaccuracy;
    private final float fullInaccuracy;
    private final float chargedInaccuracy;
    private final float repulseInaccuracy;
    private final float sniperInaccuracy;
    private final String typGasType;
    private final int semiDamage;
    private final int burstDamage;
    private final int fullDamage;
    private final int chargedDamage;
    private final int repulseDamage;
    private final int sniperDamage;
    private final String classification;

    private final Map<UUID, Float> recoilMap = new HashMap<>();

    public BlasterItem(Properties properties, float bolt_speed, int max_ammo, int burstRate,
                       int semiFireRate, int burstFireRate, int fullFireRate, int chargedFireRate, int repulseFireRate, int sniperFireRate, List<String> firingModes,
                       float semiRecoil, float burstRecoil, float fullRecoil, float chargedRecoil, float repulseRecoil, float sniperRecoil, String typFiringMode,
                       float semiInaccuracy, float burstInaccuracy, float fullInaccuracy, float chargedInaccuracy, float repulseInaccuracy, float sniperInaccuracay, String typGasType,
                       int semiDamage, int burstDamage, int fullDamage, int chargedDamage, int repulseDamage, int sniperDamage, String classification) {
        super(properties);
        this.bolt_speed = bolt_speed;
        this.max_ammo = max_ammo;
        this.burstRate = burstRate;
        this.semiFireRate = semiFireRate;
        this.burstFireRate = burstFireRate;
        this.fullFireRate = fullFireRate;
        this.chargedFireRate = chargedFireRate;
        this.repulseFireRate = repulseFireRate;
        this.sniperFireRate = sniperFireRate;
        this.firingModes = firingModes;
        this.semiRecoil = semiRecoil * 10;
        this.burstRecoil = burstRecoil * 10;
        this.fullRecoil = fullRecoil * 10;
        this.chargedRecoil = chargedRecoil * 10;
        this.repulseRecoil = repulseRecoil * 10;
        this.sniperRecoil = sniperRecoil * 10;
        this.typFiringMode = typFiringMode;
        this.semiInaccuracy = semiInaccuracy;
        this.burstInaccuracy = burstInaccuracy;
        this.fullInaccuracy = fullInaccuracy;
        this.chargedInaccuracy = chargedInaccuracy;
        this.repulseInaccuracy = repulseInaccuracy;
        this.sniperInaccuracy = sniperInaccuracay;
        this.semiDamage = semiDamage;
        this.burstDamage = burstDamage;
        this.fullDamage = fullDamage;
        this.chargedDamage = chargedDamage;
        this.repulseDamage = repulseDamage;
        this.sniperDamage = sniperDamage;
        this.typGasType = typGasType;
        this.classification = classification;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }

    public void mainHandFiring(Player player) {
        if (player.getMainHandItem().getItem() instanceof BlasterItem) {
            firingBlaster(player.level(), player, true);
        }
    }

    public void offHandFiring(Player player) {
        if (player.getOffhandItem().getItem() instanceof BlasterItem) {
            firingBlaster(player.level(), player, false);
        }
    }

    private void firingBlaster(Level level, Player player, boolean mainHand) {
        ItemStack itemstack = mainHand ? player.getMainHandItem() : player.getOffhandItem();
        int currentAmmo = getAmmo(itemstack);

        ReloadNSwitchCoolDownData ReloadNSwitchCoolDownData = itemstack.get(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN);

        if (ReloadNSwitchCoolDownData == null) {
            ReloadNSwitchCoolDownData = new ReloadNSwitchCoolDownData(0);
            itemstack.set(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN, ReloadNSwitchCoolDownData);
        }

        if (ReloadNSwitchCoolDownData.isOnCooldown(player.level())) {

            return;
        }

        FireCoolDownData FireCoolDownData = itemstack.get(ModDataComponentTypes.FIRE_COOLDOWN);
        if (currentAmmo <= 0) {
            level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), ModSounds.FOLEY_NO_AMMO.get(), SoundSource.NEUTRAL, 0.5F, 1.0F);
            return;
        }

        if (FireCoolDownData == null) {
            FireCoolDownData = new FireCoolDownData(0);
            itemstack.set(ModDataComponentTypes.FIRE_COOLDOWN, FireCoolDownData);
        }

        ExtraFiringRateData extraFiringRateData = itemstack.get(ModDataComponentTypes.EXTRA_FIRING_RATE);
        if (extraFiringRateData == null) {
            extraFiringRateData = new ExtraFiringRateData(0, 0, mainHand);
            itemstack.set(ModDataComponentTypes.EXTRA_FIRING_RATE, extraFiringRateData);
        }

        if (FireCoolDownData.isOnCooldown(level)) {
            //The item is still on cooldown
            return;
        }

        if (level.isClientSide) {
            return; //Prevent firing logic on the client side
        }

        String currentGasType = getGasType(itemstack);
        String firingMode = getFiringMode(itemstack);

        if (firingMode.equals("SCATTER")) {
            for (int shots = 0; shots < 5; shots++) {
                fireBolt(level, player, itemstack, currentAmmo, currentGasType, mainHand);
            }
        } else if (firingMode.equals("BURST")) {
            if (extraFiringRateData.shotsFired() == 0) {
                // First shot fires immediately when player presses fire
                fireBolt(level, player, itemstack, currentAmmo, currentGasType, mainHand);
                extraFiringRateData = new ExtraFiringRateData(level.getGameTime() + burstRate, 1, mainHand);
            } else {
                // Preserve the correct hand when modifying shots fired
                extraFiringRateData = new ExtraFiringRateData(
                        extraFiringRateData.cooldownEndTime(),
                        extraFiringRateData.shotsFired() + 1,
                        extraFiringRateData.mainHand() // Preserve the original hand
                );
            }

            itemstack.set(ModDataComponentTypes.EXTRA_FIRING_RATE, extraFiringRateData);
        } else {
            fireBolt(level, player, itemstack, currentAmmo, currentGasType, mainHand);
        }

        //Set the new cooldown time
        long cooldownEndTime = level.getGameTime() + getCoolDown(firingMode);
        itemstack.set(ModDataComponentTypes.FIRE_COOLDOWN, FireCoolDownData.withFireCoolDownEndTime(cooldownEndTime));
    }

    public int getCoolDown(String firingMode) {
        return switch (firingMode) {
            case "SEMI_AUTO", "SCATTER" -> semiFireRate;
            case "BURST" -> burstFireRate;
            case "FULL_AUTO" -> fullFireRate;
            case "CHARGED" -> chargedFireRate;
            case "REPULSE" -> repulseFireRate;
            case "SNIPER" -> sniperFireRate;
            case "STUN" -> stunFireRate;
            default -> 50; //fallback just incase
        };
    }

    //use to add animation when you use item
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE; //Disables use animation
    }

    public String getFiringMode(ItemStack itemstack) {
        FiringModeData firingModeData = itemstack.get(ModDataComponentTypes.FIRING_MODE.get());
        if (firingModeData == null) {
            initializeFiringMode(itemstack);
            firingModeData = itemstack.get(ModDataComponentTypes.FIRING_MODE.get());
        }
        return firingModeData.firingMode();
    }

    private void initializeFiringMode(ItemStack itemstack) {
        FiringModeData firingModeData = new FiringModeData(typFiringMode);
        itemstack.set(ModDataComponentTypes.FIRING_MODE.get(), firingModeData);
    }


    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false; //Prevents the item from triggering reequip animations
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    private void fireBolt(Level level, Player player, ItemStack itemstack, int currentAmmo, String currentGasType, boolean mainHand) {
        float recoil;
        int blasterDamage;
        float blasterInaccuracy;

        //Retrieve the firing mode from the FiringModeData component
        FiringModeData firingModeData = itemstack.get(ModDataComponentTypes.FIRING_MODE.get());
        if (firingModeData == null) {
            initializeFiringMode(itemstack);
            itemstack.set(ModDataComponentTypes.FIRING_MODE.get(), firingModeData);
        }
        String firingMode = firingModeData.firingMode();

        switch (firingMode) {
            case "SEMI_AUTO", "SCATTER" -> blasterDamage = semiDamage;
            case "BURST" -> blasterDamage = burstDamage;
            case "FULL_AUTO" -> blasterDamage = fullDamage;
            case "CHARGED" -> blasterDamage = chargedDamage;
            case "REPULSE" -> blasterDamage = repulseDamage;
            case "SNIPER" -> blasterDamage = sniperDamage;
            default -> blasterDamage = 0;
        }

        if (currentAmmo > 0) {
            Snowball bolt;
            Snowball specific_bolt;
            if (firingMode.equals("STUN")) {
                specific_bolt = new StunBlasterBoltEntity(ModEntities.STUN_BLASTER_BOLT.get(), level, player, 1.5F);
            } else {
                specific_bolt = switch (currentGasType) {
                    case "TIBANNA_GAS" ->
                            new TibannaBlasterBoltEntity(ModEntities.TIBANNA_BLASTER_BOLT.get(), level, player, this.bolt_speed, blasterDamage, currentGasType, this.classification);
                    case "IONIZED_TIBANNA_GAS" ->
                            new IonizedTibannaBlasterBoltEntity(ModEntities.IONIZED_TIBANNA_BLASTER_BOLT.get(), level, player, this.bolt_speed, blasterDamage, currentGasType, this.classification);
                    case "SPIN_SEALED_TIBANNA_GAS" ->
                            new SpinSealedTibannaBlasterBoltEntity(ModEntities.SPIN_SEALED_TIBANNA_BLASTER_BOLT.get(), level, player, this.bolt_speed, blasterDamage, currentGasType, this.classification);
                    case "TIBANNAX_GAS" ->
                            new TibannaXBlasterBoltEntity(ModEntities.TIBANNAX_BLASTER_BOLT.get(), level, player, this.bolt_speed, blasterDamage, currentGasType, this.classification);
                    case "SIG_GAS" ->
                            new SigBlasterBoltEntity(ModEntities.SIG_BLASTER_BOLT.get(), level, player, this.bolt_speed, blasterDamage, currentGasType, this.classification);
                    case "MAGNETIZED_SIG_GAS" ->
                            new MagnetizedSigBlasterBoltEntity(ModEntities.MAGNETIZED_SIG_BLASTER_BOLT.get(), level, player, this.bolt_speed, blasterDamage, currentGasType, this.classification);
                    case "SKEVON_GAS" ->
                            new SkevonBlasterBoltEntity(ModEntities.SKEVON_BLASTER_BOLT.get(), level, player, this.bolt_speed, blasterDamage, currentGasType, this.classification);
                    default ->
                            new TibannaBlasterBoltEntity(ModEntities.TIBANNA_BLASTER_BOLT.get(), level, player, this.bolt_speed, blasterDamage, currentGasType, this.classification); //Fallback if all fails
                };
            }
            bolt = specific_bolt;

            bolt.setOwner(player);
            if (mainHand) {
                bolt.setPos(
                        player.getX() - (Math.cos(Math.toRadians(player.getYRot())) * 0.4),
                        player.getEyeY(),
                        player.getZ() - (Math.sin(Math.toRadians(player.getYRot())) * 0.4)
                );
            } else {
                bolt.setPos(
                        player.getX() - (Math.cos(Math.toRadians(player.getYRot())) * -0.4),
                        player.getEyeY(),
                        player.getZ() - (Math.sin(Math.toRadians(player.getYRot())) * -0.4)
                );
            }

            double pitch = Math.toRadians(-player.getXRot());
            double yaw = Math.toRadians(-player.getYRot());

            double xVelocity = Math.cos(pitch) * Math.sin(yaw);
            double yVelocity = Math.sin(pitch);
            double zVelocity = Math.cos(pitch) * Math.cos(yaw);

            switch (firingMode) {
                case "SEMI_AUTO", "SCATTER" -> blasterInaccuracy = semiInaccuracy;
                case "BURST" -> blasterInaccuracy = burstInaccuracy;
                case "FULL_AUTO" -> blasterInaccuracy = fullInaccuracy;
                case "CHARGED" -> blasterInaccuracy = chargedInaccuracy;
                case "REPULSE" -> blasterInaccuracy = repulseInaccuracy;
                case "SNIPER" -> blasterInaccuracy = sniperInaccuracy;
                default -> blasterInaccuracy = 1F;
            }

            double accuracyFactor = blasterInaccuracy / 100; //Lower value = more accurate
            Random random = new Random();
            xVelocity += (random.nextDouble() - 0.5) * accuracyFactor;
            yVelocity += (random.nextDouble() - 0.5) * accuracyFactor;
            zVelocity += (random.nextDouble() - 0.5) * accuracyFactor;

            Vec3 velocity = new Vec3(xVelocity, yVelocity, zVelocity).normalize().scale(this.bolt_speed); //Use custom velocity
            bolt.setDeltaMovement(velocity);


            SoundEvent blasterFireSound;

            if (firingMode.equals("STUN")) {
                blasterFireSound = BlasterSoundsUtil.getBlasterFireSound((String) BuiltInRegistries.ITEM.getKey(itemstack.getItem()).getPath(), firingMode);
            } else {
                blasterFireSound = BlasterSoundsUtil.getBlasterFireSound((String) BuiltInRegistries.ITEM.getKey(itemstack.getItem()).getPath(), firingMode);
            }

            level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), blasterFireSound, SoundSource.NEUTRAL, 0.5F, 1.0F);
            level.addFreshEntity(bolt);

            setAmmo(itemstack, currentAmmo - 1);

            player.awardStat(Stats.ITEM_USED.get(this));

            switch (firingMode) {
                case "SEMI_AUTO", "SCATTER" -> recoil = semiRecoil;
                case "BURST" -> recoil = burstRecoil;
                case "FULL_AUTO" -> recoil = fullRecoil;
                case "CHARGED" -> recoil = chargedRecoil;
                case "REPULSE" -> recoil = repulseRecoil;
                case "SNIPER" -> recoil = sniperRecoil;
                case "STUN" -> recoil = stunRecoil;
                default -> recoil = 1;
            }

            applyRecoil(player, recoil / 5);
        } else {
            level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), ModSounds.FOLEY_NO_AMMO.get(), SoundSource.NEUTRAL, 0.5F, 1.0F);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player) {
            UUID playerId = player.getUUID();
            if (recoilMap.containsKey(playerId)) {
                Float recoilAmount = recoilMap.get(playerId); // Safely retrieve the value

                if (recoilAmount != null) { // Ensure the value is not null
                    // Gradual recovery
                    recoilAmount *= 0.8f; // Adjust this factor for faster/slower recovery

                    // Apply the recoil effect to the player's pitch
                    float currentPitch = player.getXRot();
                    float recoilEffect = recoilMap.get(playerId) - recoilAmount; // Calculate the difference
                    float newPitch = currentPitch - recoilEffect;

                    // Clamp the pitch
                    newPitch = Math.max(-90.0f, Math.min(90.0f, newPitch));
                    player.setXRot(newPitch);

                    // Update the recoil map
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

        if (getFiringMode(stack).equals("BURST") && extraFiringRateData.shotsFired() > 0 && extraFiringRateData.shotsFired() < 3) {
            if (level.getGameTime() >= extraFiringRateData.cooldownEndTime()) {
                fireBolt(level, player, stack, getAmmo(stack), getGasType(stack), extraFiringRateData.mainHand());

                extraFiringRateData = extraFiringRateData.withCooldownEndTime(level.getGameTime() + 2)
                        .withShotsFired(extraFiringRateData.shotsFired() + 1);

                stack.set(ModDataComponentTypes.EXTRA_FIRING_RATE, extraFiringRateData);
            }
        }

        // Reset burst after 3 shots
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

        //Cap the total recoil to a reasonable maximum
        totalRecoil = Math.min(totalRecoil, 20.0f); //Adjust max value if needed
        recoilMap.put(playerId, totalRecoil);
    }

    public int getAmmo(ItemStack stack) {
        GasAmmoData data = stack.get(ModDataComponentTypes.GAS_AMMO.get());
        return data != null ? data.ammo() : 0;
    }

    public void setAmmo(ItemStack stack, int ammo) {
        stack.set(ModDataComponentTypes.GAS_AMMO.get(), new GasAmmoData(ammo));

        //Sync to ensure the state is updated client-side
        if (stack.getEntityRepresentation() instanceof Player player) {
            player.inventoryMenu.broadcastChanges();
        }
    }

    private String getGasType(ItemStack stack) {
        return stack.get(ModDataComponentTypes.GAS_TYPE.get()).gasType();
    }

    public void reload(Player player, ItemStack blasterStack, boolean mainHand) {
        int currentAmmo = getAmmo(blasterStack);
        GasTypeData blasterGasTypeData = blasterStack.get(ModDataComponentTypes.GAS_TYPE.get());
        String currentGasType = (blasterGasTypeData != null) ? blasterGasTypeData.gasType() : null;
        String blasterFireMode = getFiringMode(blasterStack);

        if (currentAmmo >= max_ammo) {
            player.displayClientMessage(Component.translatable("item.starwars.blaster.full_ammo"), true);
            return;
        }

        ReloadNSwitchCoolDownData ReloadNSwitchCoolDownData = blasterStack.get(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN);

        if (ReloadNSwitchCoolDownData == null) {
            ReloadNSwitchCoolDownData = new ReloadNSwitchCoolDownData(0);
            blasterStack.set(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN, ReloadNSwitchCoolDownData);
        }

        if (ReloadNSwitchCoolDownData.isOnCooldown(player.level())) {

            return;
        }

        boolean foundGasItem = false;

        for (int i = 0; i < player.getInventory().items.size(); i++) {
            ItemStack stack = player.getInventory().items.get(i);

            if (stack.getItem() instanceof GasItem gasItem) {
                int gasAmmo = gasItem.getAmmo(stack);

                if (gasAmmo > 0) {
                    foundGasItem = true;
                    String gasType = gasItem.getGasType();

                    if (currentAmmo <= 0 || (currentGasType != null && currentGasType.equals(gasType))) {
                        int ammoNeeded = max_ammo - currentAmmo;
                        int ammoToReload;

                        if (!player.isCreative()) {
                            ammoToReload = Math.min(ammoNeeded, gasAmmo);
                        } else {
                            ammoToReload = ammoNeeded;
                        }

                        currentAmmo += ammoToReload;
                        setAmmo(blasterStack, currentAmmo);
                        PayloadRegister.sendToServer(new SSReloadPacket(blasterStack, currentAmmo, gasType, mainHand));

                        if (!player.isCreative()) {
                            gasAmmo -= ammoToReload;
                            gasItem.setAmmo(stack, gasAmmo);
                            PayloadRegister.sendToServer(new SSGasAmmoPacket(stack, gasAmmo, i));
                        }

                        //Sync inventory changes
                        player.inventoryMenu.broadcastChanges();

                        SoundEvent blasterReloadSound = BlasterSoundsUtil.getBlasterReloadSound((String) BuiltInRegistries.ITEM.getKey(blasterStack.getItem()).getPath(), blasterFireMode);
                        player.playSound(blasterReloadSound, 0.5F, 1.0F);
                    }
                }
            }
        }

        if (player.isCreative() && (currentAmmo <= 0)) {
            setAmmo(blasterStack, max_ammo);
            PayloadRegister.sendToServer(new SSReloadPacket(blasterStack, max_ammo, typGasType, mainHand));
            player.inventoryMenu.broadcastChanges();
            SoundEvent blasterReloadSound = BlasterSoundsUtil.getBlasterReloadSound((String) BuiltInRegistries.ITEM.getKey(blasterStack.getItem()).getPath(), blasterFireMode);
            player.playSound(blasterReloadSound, 0.5F, 1.0F);
        } else if (player.isCreative() && currentAmmo != max_ammo) {
            int ammoNeeded = max_ammo - currentAmmo;
            currentAmmo += ammoNeeded;
            setAmmo(blasterStack, currentAmmo);
            PayloadRegister.sendToServer(new SSReloadPacket(blasterStack, currentAmmo, currentGasType, mainHand));
            player.inventoryMenu.broadcastChanges();
            SoundEvent blasterReloadSound = BlasterSoundsUtil.getBlasterReloadSound((String) BuiltInRegistries.ITEM.getKey(blasterStack.getItem()).getPath(), blasterFireMode);
            player.playSound(blasterReloadSound, 0.2F, 1.0F);
        } else if (!foundGasItem) {
            player.displayClientMessage(Component.translatable("item.starwars.blaster.no_gas_items"), true);
        } else {
            player.displayClientMessage(Component.translatable("item.starwars.blaster.no_compatible_gas"), true);
        }
    }

    public void startCooldown(Player player, ItemStack blasterStack, boolean reloading) {
        Level level = player.level();
        int currentAmmo = getAmmo(blasterStack);

        if (reloading & currentAmmo >= max_ammo) {
            return;
        }

        ReloadNSwitchCoolDownData ReloadNSwitchCoolDownData = blasterStack.get(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN);

        // Initialize cooldown data if it's missing
        if (ReloadNSwitchCoolDownData == null) {
            ReloadNSwitchCoolDownData = new ReloadNSwitchCoolDownData(0);
            blasterStack.set(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN, ReloadNSwitchCoolDownData);
        }

        if (ReloadNSwitchCoolDownData.isOnCooldown(player.level())) {

            return;
        }

        // Apply new cooldown
        long ReloadNSwitchCoolDownEndTime = level.getGameTime() + BlasterTimingUtil.getBlasterSwitchTime((String) BuiltInRegistries.ITEM.getKey(blasterStack.getItem()).getPath(), getFiringMode(blasterStack));
        blasterStack.set(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN, ReloadNSwitchCoolDownData.withReloadNSwitchCoolDownEndTime(ReloadNSwitchCoolDownEndTime));
    }

    public void switchFiringMode(Player player, ItemStack blasterStack, boolean mainHand) {
        ReloadNSwitchCoolDownData ReloadNSwitchCoolDownData = blasterStack.get(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN);

        if (ReloadNSwitchCoolDownData == null) {
            ReloadNSwitchCoolDownData = new ReloadNSwitchCoolDownData(0);
            blasterStack.set(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN, ReloadNSwitchCoolDownData);
        }

        if (ReloadNSwitchCoolDownData.isOnCooldown(player.level())) {

            return;
        }

        //Retrieve the FiringModeData component from the blaster stack.
        FiringModeData firingModeData = blasterStack.get(ModDataComponentTypes.FIRING_MODE.get());
        if (firingModeData == null) {
            //If no firing mode is currently set, initialize it to the first mode in the list.
            firingModeData = new FiringModeData(typFiringMode);
            blasterStack.set(ModDataComponentTypes.FIRING_MODE.get(), firingModeData);
        }

        String currentFiringMode = firingModeData.firingMode();

        //Find the index of the current firing mode.
        int currentModeIndex = firingModes.indexOf(currentFiringMode);
        if (currentModeIndex == -1) {
            //If the current mode is invalid, default to the first mode.
            currentModeIndex = 0;
        }

        //Cycle to the next firing mode.
        int nextModeIndex = (currentModeIndex + 1) % firingModes.size();
        String nextFiringMode = firingModes.get(nextModeIndex);

        //Update the FiringModeData component with the new firing mode.
        FiringModeData updatedFiringModeData = firingModeData.withFiringMode(nextFiringMode);
        blasterStack.set(ModDataComponentTypes.FIRING_MODE.get(), updatedFiringModeData);
        PayloadRegister.sendToServer(new SSFiringModePacket(blasterStack, nextFiringMode, mainHand));

        SoundEvent blasterSwitchFireModeSound = BlasterSoundsUtil.getBlasterSwitchFireMode((String) BuiltInRegistries.ITEM.getKey(blasterStack.getItem()).getPath(), currentFiringMode);
        player.playSound(blasterSwitchFireModeSound, 0.5F, 1.0F);
    }

    @Override
    public Component getName(ItemStack stack) {
        ChatFormatting color;

        String gasType = null;
        int currentAmmo = getAmmo(stack);
        color = ChatFormatting.WHITE;
        if (currentAmmo <= 0) {
            //No ammo, default to white
            color = ChatFormatting.WHITE;
            stack.set(ModDataComponentTypes.GAS_TYPE.get(), null);
        } else {
            //Safely retrieve the gas type
            if (stack.get(ModDataComponentTypes.GAS_TYPE.get()) != null) {
                gasType = stack.get(ModDataComponentTypes.GAS_TYPE.get()).gasType();
            }

            //Assign colors based on the gas type
            color = switch (gasType) {
                case "TIBANNA_GAS" -> ChatFormatting.RED;
                case "IONIZED_TIBANNA_GAS" -> ChatFormatting.BLUE;
                case "SPIN_SEALED_TIBANNA_GAS" -> ChatFormatting.GREEN;
                case "TIBANNAX_GAS" -> ChatFormatting.GRAY;
                case "SIG_GAS" -> ChatFormatting.YELLOW;
                case "MAGNETIZED_SIG_GAS" -> ChatFormatting.DARK_PURPLE;
                case "SKEVON_GAS" -> ChatFormatting.GOLD;
                case null, default -> ChatFormatting.WHITE; // Fallback to white
            };
        }

        ChatFormatting finalColor = color;
        return super.getName(stack).copy().withStyle(style -> style.withColor(finalColor));
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.literal("Ammo: " + getAmmo(pStack) + "/" + max_ammo));
        super.appendHoverText(pStack, pContext, pTooltip, pFlag);
    }

    public int getMaxAmmo() {
        return max_ammo;
    }
}