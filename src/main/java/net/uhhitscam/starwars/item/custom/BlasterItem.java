package net.uhhitscam.starwars.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.starwars.component.FiringModeData;
import net.uhhitscam.starwars.component.GasAmmoData;
import net.uhhitscam.starwars.component.GasTypeData;
import net.uhhitscam.starwars.component.ModDataComponentTypes;
import net.uhhitscam.starwars.entity.custom.*;
import net.uhhitscam.starwars.network.PayloadRegister;
import net.uhhitscam.starwars.network.SSFiringModePacket;
import net.uhhitscam.starwars.network.SSGasAmmoPacket;
import net.uhhitscam.starwars.network.SSReloadPacket;

import java.util.*;

public class BlasterItem extends Item{
    private final float inaccuracy;
    private final float bolt_speed;
    private final int max_ammo;
    private final int blasterDamage;
    private final int semiFireRate;
    private final int burstFireRate;
    private final int fullFireRate;
    private final List<String> firingModes;
    private final float semiRecoil;
    private final float burstRecoil;
    private final float fullRecoil;

    private final Map<UUID, Float> recoilMap = new HashMap<>();
    private final Map<UUID, Long> lastFireTime = new HashMap<>();

    public BlasterItem(Properties properties, float bolt_speed, float inaccuracy, int max_ammo, int blasterDamage,
                       int semiFireRate, int burstFireRate, int fullFireRate, List<String> firingModes, float semiRecoil, float burstRecoil, float fullRecoil) {
        super(properties);
        this.bolt_speed = bolt_speed;
        this.inaccuracy = inaccuracy;
        this.max_ammo = max_ammo;
        this.blasterDamage = blasterDamage;
        this.semiFireRate = semiFireRate;
        this.burstFireRate = burstFireRate;
        this.fullFireRate = fullFireRate;
        this.firingModes = firingModes;
        this.semiRecoil = semiRecoil * 10;
        this.burstRecoil = burstRecoil * 10;
        this.fullRecoil = fullRecoil * 10;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        System.out.println("made it to use method");
        ItemStack itemstack = player.getItemInHand(hand);

        // Retrieve the firing mode from the FiringModeData component
        FiringModeData firingModeData = itemstack.get(ModDataComponentTypes.FIRING_MODE.get());
        System.out.println("the firingModeData is " + firingModeData);
        if (firingModeData == null) {
            System.out.println("FiringModeData is missing, defaulting to SEMI_AUTO");
            firingModeData = new FiringModeData("SEMI_AUTO");
            itemstack.set(ModDataComponentTypes.FIRING_MODE.get(), firingModeData);
        }
        String firingMode = firingModeData.firingMode();
        System.out.println("firingMode is " + firingMode);

        if (!firingMode.equals("SEMI_AUTO")) {
            System.out.println("it is not semi auto");
            if (!level.isClientSide) {
                System.out.println("you are in server side");
                firingBlaster(level, player, itemstack);
                return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
            }
        }
        return InteractionResultHolder.fail(itemstack);
    }

    public void mainHandFiring(Player player, ItemStack itemstack) {
        System.out.println("made it to mainHandFiring method");

        // Retrieve the firing mode from the FiringModeData component
        FiringModeData firingModeData = itemstack.get(ModDataComponentTypes.FIRING_MODE.get());
        System.out.println("the firingModeData is " + firingModeData);
        if (firingModeData == null) {
            System.out.println("FiringModeData is missing, defaulting to SEMI_AUTO");
            firingModeData = new FiringModeData("SEMI_AUTO");
            itemstack.set(ModDataComponentTypes.FIRING_MODE.get(), firingModeData);
        }
        String firingMode = firingModeData.firingMode();
        System.out.println("firingMode is " + firingMode);

        if (firingMode.equals("SEMI_AUTO")) {
            Level level = player.level();
            if (!level.isClientSide) {
                System.out.println("you are in Server side");
                firingBlaster(level, player, itemstack);
            }
        }
    }

//    @Override
//    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
//        return super.onLeftClickEntity(stack, player, entity);
//    }

//    public void offHandFiring(ItemStack blaster, Player player) {
//        if (!player.level().isClientSide) {
//            firingBlaster(player.level(), player, blaster);
//        }
//    }

    //use to add animation when you use item
//    @Override
//    public UseAnim getUseAnimation(ItemStack stack) {
//        return UseAnim.NONE; // Disables use animation
//    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false; // Prevents the item from triggering reequip animations
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

//    private void firingBlaster(Level level, Player player, ItemStack itemstack) {
//        System.out.println("made it to firingBlaster method");
//        int currentAmmo = getAmmo(itemstack);
//        int fireRate;
//        long currentTime = System.currentTimeMillis();
//        long lastTime = lastFireTime.getOrDefault(player.getUUID(), 0L);
//        int shots = 1;
//        Timer timer = new Timer();
//
//        System.out.println("Before firing: Current Ammo = " + currentAmmo);
//
//        if  (currentAmmo > 0 && !level.isClientSide) {
//            System.out.println("current Ammo > 0 and is server side");
//            String currentGasType = getGasType(itemstack);
//
//            // Retrieve the firing mode from the FiringModeData component
//            FiringModeData firingModeData = itemstack.get(ModDataComponentTypes.FIRING_MODE.get());
//            if (firingModeData == null) {
//                System.out.println("FiringModeData is missing, defaulting to SEMI_AUTO");
//                firingModeData = new FiringModeData("SEMI_AUTO");
//                itemstack.set(ModDataComponentTypes.FIRING_MODE.get(), firingModeData);
//            }
//            String firingMode = firingModeData.firingMode();
//            System.out.println("firingMode is " + firingMode);
//
//            switch (firingMode) {
//                case "SEMI_AUTO", "SCATTER" -> fireRate = semiFireRate;
//                case "BURST" -> fireRate = burstFireRate;
//                case "FULL_AUTO" -> fireRate = fullFireRate;
//                default -> fireRate = 0;
//            }
//            System.out.println("fireRate is " + fireRate);
//            System.out.println("firingMode is " + firingMode);
//
//            if ((currentTime - lastTime) < fireRate * 50) {
//                return;
//            }
//
//            lastFireTime.put(player.getUUID(), currentTime);
//
//            switch (firingMode) {
//                case "SEMI_AUTO", "FULL_AUTO", "SCATTER" -> shots = 1;
//                case "BURST" -> shots = Math.min(3, currentAmmo);
//            }
//
//            for (int i = 0; i < shots; i++) {
//                if (currentAmmo > 0) {
//                    if (shots > 1) {
//                        int finalAmmo = currentAmmo; // Capture updated ammo for this task
//                        timer.schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                if (firingMode.equals("SCATTER")) {
//                                    for (int scatter_bolt = 0; scatter_bolt < 5; scatter_bolt++) {
//                                        fireBolt(level, player, itemstack, finalAmmo, currentGasType);
//                                    }
//                                } else {
//                                    fireBolt(level, player, itemstack, finalAmmo, currentGasType);
//                                }
//                            }
//                        }, i * 100); // Delay each shot by 100ms
//                    } else {
//                        fireBolt(level, player, itemstack, currentAmmo, currentGasType);
//                    }
//                    currentAmmo--; // Decrement ammo
//                    setAmmo(itemstack, currentAmmo); // Update the ammo in the ItemStack
//                } else {
//                    // Feedback for empty blaster
//                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
//                            SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
//                }
//            }
//
//            player.awardStat(Stats.ITEM_USED.get(this));
//        } else {
//            System.out.println("No ammo left to fire.");
//
//            // Feedback for empty blaster
//            level.playSound(null, player.getX(), player.getY(), player.getZ(),
//                    SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
//        }
//    }

    private void firingBlaster(Level level, Player player, ItemStack itemstack) {
        System.out.println("made it to firingBlaster method");

        int currentAmmo = getAmmo(itemstack);
        long currentTime = System.currentTimeMillis();
        long lastTime = lastFireTime.getOrDefault(player.getUUID(), 0L);

        if (currentAmmo <= 0) {
            System.out.println("No ammo left to fire.");
            // Feedback for empty blaster
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
            return;
        }

        if (level.isClientSide) {
            return; // Prevent firing logic on the client side
        }

        String currentGasType = getGasType(itemstack);
        FiringModeData firingModeData = itemstack.get(ModDataComponentTypes.FIRING_MODE.get());
        if (firingModeData == null) {
            System.out.println("FiringModeData is missing, defaulting to SEMI_AUTO");
            firingModeData = new FiringModeData("SEMI_AUTO");
            itemstack.set(ModDataComponentTypes.FIRING_MODE.get(), firingModeData);
        }
        String firingMode = firingModeData.firingMode();
        System.out.println("firingMode is " + firingMode);

        int fireRate = switch (firingMode) {
            case "SEMI_AUTO", "SCATTER" -> semiFireRate;
            case "BURST" -> burstFireRate;
            case "FULL_AUTO" -> fullFireRate;
            default -> 0;
        };

        if ((currentTime - lastTime) < fireRate * 50) {
            return; // Enforce fire rate delay
        }

        lastFireTime.put(player.getUUID(), currentTime);

        int shots = switch (firingMode) {
            case "BURST" -> Math.min(3, currentAmmo);
            case "SCATTER", "SEMI_AUTO", "FULL_AUTO" -> 1;
            default -> 1;
        };

        for (int i = 0; i < shots; i++) {
            int delay = i * 100; // Delay between shots for burst and scatter modes
            int finalAmmo = currentAmmo - i; // Calculate remaining ammo for each shot

            if (finalAmmo > 0) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (firingMode.equals("SCATTER")) {
                            for (int scatterBolt = 0; scatterBolt < 5; scatterBolt++) {
                                fireBolt(level, player, itemstack, finalAmmo, currentGasType);
                            }
                        } else {
                            fireBolt(level, player, itemstack, finalAmmo, currentGasType);
                        }
                    }
                }, delay);
            } else {
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
    }

    private void fireBolt(Level level, Player player, ItemStack itemstack, int currentAmmo, String currentGasType) {
        System.out.println("made it to fireBolt method");
        System.out.println("currentAmmo is " + currentAmmo);
        float recoil;

        // Retrieve the firing mode from the FiringModeData component
        FiringModeData firingModeData = itemstack.get(ModDataComponentTypes.FIRING_MODE.get());
        if (firingModeData == null) {
            System.out.println("FiringModeData is missing, defaulting to SEMI_AUTO");
            firingModeData = new FiringModeData("SEMI_AUTO");
            itemstack.set(ModDataComponentTypes.FIRING_MODE.get(), firingModeData);
        }
        String firingMode = firingModeData.firingMode();
        System.out.println("firingMode is " + firingMode);

        if (currentAmmo > 0) {
            Snowball bolt = switch (currentGasType) {
                case "TIBANNA_GAS" ->
                        new TibannaBlasterBoltEntity(level, player, this.bolt_speed, this.blasterDamage, currentGasType);
                case "IONIZED_TIBANNA_GAS" ->
                        new IonizedTibannaBlasterBoltEntity(level, player, this.bolt_speed, this.blasterDamage, currentGasType);
                case "SPIN_SEALED_TIBANNA_GAS" ->
                        new SpinSealedTibannaBlasterBoltEntity(level, player, this.bolt_speed, this.blasterDamage, currentGasType);
                case "TIBANNAX_GAS" ->
                        new TibannaXBlasterBoltEntity(level, player, this.bolt_speed, this.blasterDamage, currentGasType);
                case "SIG_GAS" ->
                        new SigBlasterBoltEntity(level, player, this.bolt_speed, this.blasterDamage, currentGasType);
                case "MAGNETIZED_SIG_GAS" ->
                        new MagnetizedSigBlasterBoltEntity(level, player, this.bolt_speed, this.blasterDamage, currentGasType);
                case "SKEVON_GAS" ->
                        new SkevonBlasterBoltEntity(level, player, this.bolt_speed, this.blasterDamage, currentGasType);
                default ->
                        new TibannaBlasterBoltEntity(level, player, this.bolt_speed, this.blasterDamage, currentGasType); //Fallback if all fails

            };

            bolt.setOwner(player);
            bolt.setPos(
                    player.getX() - (Math.cos(Math.toRadians(player.getYRot())) * 0.2),
                    player.getEyeY() - 0.15,
                    player.getZ() - (Math.sin(Math.toRadians(player.getYRot())) * 0.2)
            );

            double pitch = Math.toRadians(-player.getXRot());
            double yaw = Math.toRadians(-player.getYRot());

            double xVelocity = Math.cos(pitch) * Math.sin(yaw);
            double yVelocity = Math.sin(pitch);
            double zVelocity = Math.cos(pitch) * Math.cos(yaw);

            double accuracyFactor = this.inaccuracy / 100; // Lower value = more accurate
            xVelocity += (level.getRandom().nextDouble() - 0.5) * accuracyFactor;
            yVelocity += (level.getRandom().nextDouble() - 0.5) * accuracyFactor;
            zVelocity += (level.getRandom().nextDouble() - 0.5) * accuracyFactor;

            Vec3 velocity = new Vec3(xVelocity, yVelocity, zVelocity).normalize().scale(this.bolt_speed); // Use custom velocity
            bolt.setDeltaMovement(velocity);

            level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            level.addFreshEntity(bolt);

            setAmmo(itemstack, currentAmmo - 1);
            player.awardStat(Stats.ITEM_USED.get(this));
            System.out.println("firingMode before applying recoil is " + firingMode);

            if (this.semiRecoil != 0 || this.burstRecoil != 0 || this.fullRecoil != 0) {
                switch (firingMode) {
                    case "SEMI_AUTO", "SCATTER" -> recoil = semiRecoil;
                    case "BURST" -> recoil = burstRecoil;
                    case "FULL_AUTO" -> recoil = fullRecoil;
                    default -> recoil = 0F;
                }
                addRecoil(player, recoil);
            }
        } else {
            // Feedback for empty blaster
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player && recoilMap.containsKey(player.getUUID())) {
            // Get current recoil amount
            float recoilAmount = recoilMap.get(player.getUUID());

            // Apply a small amount of recoil this tick
            applyRecoil(player, recoilAmount * 0.2f); // Adjust pitch smoothly

            // Decrease recoil amount over time
            recoilAmount *= 0.4f; // Reduce recoil (damping factor)

            if (recoilAmount < 0.01f) {
                // Remove player from map if recoil is negligible
                recoilMap.remove(player.getUUID());
            } else {
                recoilMap.put(player.getUUID(), recoilAmount);
            }
        }
    }

    public void addRecoil(Player player, float totalRecoil) {
        // Get the current recoil, add the new recoil, and ensure it's within the limits
        float currentRecoil = recoilMap.getOrDefault(player.getUUID(), 0.0f);
        recoilMap.put(player.getUUID(), Math.min(currentRecoil + totalRecoil, 5.0f)); // Cap at 5.0f for smoother recoil
    }

    public void applyRecoil(Player player, float recoil) {
        // Get the current pitch (up/down) of the player
        float currentPitch = player.getXRot();

        // Apply vertical recoil (adjust pitch upwards)
        float newPitch = currentPitch - recoil;

        // Clamp the new pitch to ensure the player doesn't tilt too far
        newPitch = Math.max(-90.0f, Math.min(90.0f, newPitch));

        // Smoothly set the player's new rotation (you can adjust smoothness if needed)
        player.setXRot(newPitch);
    }

    public int getAmmo(ItemStack stack) {
        GasAmmoData data = stack.get(ModDataComponentTypes.GAS_AMMO.get());
        return data != null ? data.ammo() : 0;
    }

    public void setAmmo(ItemStack stack, int ammo) {
        System.out.println("made it to setAmmo method");
        stack.set(ModDataComponentTypes.GAS_AMMO.get(), new GasAmmoData(ammo));

        // Sync to ensure the state is updated client-side
        if (stack.getEntityRepresentation() instanceof Player player) {
            player.inventoryMenu.broadcastChanges();
        }
    }

    private String getGasType(ItemStack stack) {
        System.out.println("made it to getGasType method");
        return stack.get(ModDataComponentTypes.GAS_TYPE.get()).gasType();
    }

    public void reload(Player player, ItemStack blasterStack) {
        System.out.println("made it to reload method");
        int currentAmmo = getAmmo(blasterStack);
        GasTypeData blasterGasTypeData = blasterStack.get(ModDataComponentTypes.GAS_TYPE.get());
        String currentGasType = (blasterGasTypeData != null) ? blasterGasTypeData.gasType() : null;
        System.out.println("gas type of the blaster is " + currentGasType);

        System.out.println("Before reload: Current Ammo = " + currentAmmo);

        if (currentAmmo >= max_ammo) {
            player.displayClientMessage(Component.translatable("item.starwars.blaster.full_ammo"), true);
            return;
        }

        boolean foundGasItem = false;

        for (int i = 0; i < player.getInventory().items.size(); i++) {
            ItemStack stack = player.getInventory().items.get(i);

            if (stack.getItem() instanceof GasItem gasItem) {
                int gasAmmo = gasItem.getAmmo(stack);
                System.out.println("found gasItem with ammo of " + gasAmmo);

                if (gasAmmo > 0) {
                    System.out.println("ammo is > 0");
                    foundGasItem = true;
                    String gasType = gasItem.getGasType();
                    System.out.println("The gas type of the gasItem is " + gasType);

                    if (currentAmmo <= 0 || (currentGasType != null && currentGasType.equals(gasType))) {
                        System.out.println("either the blaster is empty or the gas type of the gasItem matches the blaster");
                        int ammoNeeded = max_ammo - currentAmmo;
                        int ammoToReload = Math.min(ammoNeeded, gasAmmo);

                        // Update the blaster's ammo
                        setAmmo(blasterStack, currentAmmo + ammoToReload);
                        gasItem.setAmmo(stack, gasAmmo - ammoToReload);
                        currentAmmo += ammoToReload;
                        gasAmmo -= ammoToReload;

                        System.out.println("Reloaded Ammo = " + (currentAmmo));
                        System.out.println("Remaining GasItem Ammo = " + (gasAmmo));

                        // Notify the server about the ammo update
                        PayloadRegister.sendToServer(new SSReloadPacket(blasterStack, currentAmmo, gasType));
                        PayloadRegister.sendToServer(new SSGasAmmoPacket(stack, gasAmmo));

                        // Sync inventory changes
                        player.inventoryMenu.broadcastChanges();

                        player.displayClientMessage(Component.translatable("item.starwars.blaster.reloaded"), true);
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.ANVIL_USE, SoundSource.PLAYERS, 1.0F, 1.0F);

                        // If full, exit the loop
                        if (currentAmmo >= max_ammo) {
                            break;
                        }
                    }
                }
            }
        }

        if (!foundGasItem) {
            player.displayClientMessage(Component.translatable("item.starwars.blaster.no_gas_items"), true);
        } else if (currentAmmo < max_ammo) {
            player.displayClientMessage(Component.translatable("item.starwars.blaster.no_compatible_gas"), true);
        }
    }

    public void switchFiringMode(ItemStack blasterStack) {
        System.out.println("Switching firing mode");

        // Retrieve the FiringModeData component from the blaster stack.
        FiringModeData firingModeData = blasterStack.get(ModDataComponentTypes.FIRING_MODE.get());
        if (firingModeData == null) {
            // If no firing mode is currently set, initialize it to the first mode in the list.
            firingModeData = new FiringModeData(firingModes.get(0));
            blasterStack.set(ModDataComponentTypes.FIRING_MODE.get(), firingModeData);
        }

        String currentFiringMode = firingModeData.firingMode();
        System.out.println("Current firing mode: " + currentFiringMode);

        // Find the index of the current firing mode.
        int currentModeIndex = firingModes.indexOf(currentFiringMode);
        if (currentModeIndex == -1) {
            // If the current mode is invalid, default to the first mode.
            currentModeIndex = 0;
        }

        // Cycle to the next firing mode.
        int nextModeIndex = (currentModeIndex + 1) % firingModes.size();
        String nextFiringMode = firingModes.get(nextModeIndex);

        // Update the FiringModeData component with the new firing mode.
        FiringModeData updatedFiringModeData = firingModeData.withFiringMode(nextFiringMode);
        blasterStack.set(ModDataComponentTypes.FIRING_MODE.get(), updatedFiringModeData);
        PayloadRegister.sendToServer(new SSFiringModePacket(blasterStack, nextFiringMode));

        System.out.println("Switched to firing mode: " + nextFiringMode);

        if (blasterStack.getEntityRepresentation() instanceof Player player) {
            // Notify the player about the new firing mode.
            player.displayClientMessage(Component.translatable("item.starwars.blaster.firing_mode_changed", nextFiringMode), true);
        }
        System.out.println("firingMode is " + blasterStack.get(ModDataComponentTypes.FIRING_MODE.get()));
    }

    @Override
    public Component getName(ItemStack stack) {
        ChatFormatting color;

        String gasType = null;
        int currentAmmo = getAmmo(stack);
        color = ChatFormatting.WHITE;
        if (currentAmmo <= 0) {
            // No ammo, default to white
            color = ChatFormatting.WHITE;
            stack.set(ModDataComponentTypes.GAS_TYPE.get(), null);
        } else {
            // Safely retrieve the gas type
            if (stack.get(ModDataComponentTypes.GAS_TYPE.get()) != null) {
                gasType = stack.get(ModDataComponentTypes.GAS_TYPE.get()).gasType();
            }

            // Assign colors based on the gas type
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