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
import net.uhhitscam.starwars.component.GasAmmoData;
import net.uhhitscam.starwars.component.GasTypeData;
import net.uhhitscam.starwars.component.ModDataComponentTypes;
import net.uhhitscam.starwars.entity.custom.*;
import net.uhhitscam.starwars.network.PayloadRegister;
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
    private String firingMode;
    private final float semiRecoil;
    private final float burstRecoil;
    private final float fullRecoil;
    private int firingModeIndex = 0;

    private final Map<UUID, Long> lastFireTime = new HashMap<>();
    private final Map<UUID, Float> recoilMap = new HashMap<>();

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
        this.firingMode = firingModes.getFirst();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            if ("SEMI_AUTO".equals(this.firingMode)) {
                player.startUsingItem(hand);
                firingBlaster(level, player, itemstack);
                return InteractionResultHolder.consume(itemstack);
            } else {
                firingBlaster(level, player, itemstack);
                return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
            }
        }
        return InteractionResultHolder.fail(itemstack);
    }

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

    private void firingBlaster(Level level, Player player, ItemStack itemstack) {
        int currentAmmo = getAmmo(itemstack);
        int fireRate;
        long currentTime = System.currentTimeMillis();
        long lastTime = lastFireTime.getOrDefault(player.getUUID(), 0L);
        int shots = 1;
        Timer timer = new Timer();

        if  (currentAmmo > 0 && !level.isClientSide) {
            String currentGasType = getGasType(itemstack);

            switch (firingMode) {
                case "SEMI_AUTO", "SCATTER" -> fireRate = semiFireRate;
                case "BURST" -> fireRate = burstFireRate;
                case "FULL_AUTO" -> fireRate = fullFireRate;
                default -> fireRate = 0;
            }

            if ((currentTime - lastTime) < fireRate * 50) {
                return;
            }

            lastFireTime.put(player.getUUID(), currentTime);

            switch (firingMode) {
                case "SEMI_AUTO", "FULL_AUTO", "SCATTER" -> shots = 1;
                case "BURST" -> shots = Math.min(3, currentAmmo);
            }

            for (int i = 0; i < shots; i++) {
                if (currentAmmo > 0 && shots > 1) {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (firingMode.equals("SCATTER")) {
                                for (int scatter_bolt = 0; scatter_bolt < 5; scatter_bolt++) {
                                    fireBolt(level, player, itemstack, currentAmmo, currentGasType);
                                }
                            } else {
                                fireBolt(level, player, itemstack, currentAmmo, currentGasType);
                            }
                        }
                    }, i * 100); // Delay each shot by 200ms
                } else if (currentAmmo > 0 && shots <= 1) {
                    fireBolt(level, player, itemstack, currentAmmo, currentGasType);
                } else {
                    // Feedback for empty blaster
                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
                }
            }

            player.awardStat(Stats.ITEM_USED.get(this));
        } else {
            // Feedback for empty blaster
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    private void fireBolt(Level level, Player player, ItemStack itemstack, int currentAmmo, String currentGasType) {
        float recoil;

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
        // Add recoil to the map or increase the current value
        recoilMap.put(player.getUUID(), recoilMap.getOrDefault(player.getUUID(), 0.0f) + totalRecoil);
    }

    public void applyRecoil(Player player, float recoil) {
        // Get the current pitch (up/down) and yaw (left/right)
        float currentPitch = player.getXRot();

        // Apply vertical recoil (adjust pitch upwards)
        float newPitch = currentPitch - recoil;

        // Smoothly set the player's new rotation
        player.setXRot(newPitch);
    }

    public int getAmmo(ItemStack stack) {
        GasAmmoData data = stack.get(ModDataComponentTypes.GAS_AMMO.get());
        return data != null ? data.ammo() : 0;
    }

    public void setAmmo(ItemStack stack, int ammo) {
        stack.set(ModDataComponentTypes.GAS_AMMO.get(), new GasAmmoData(ammo));
    }

    private String getGasType(ItemStack stack) {
        return stack.get(ModDataComponentTypes.GAS_TYPE.get()).gasType();
    }

    public void reload(Player player, ItemStack blasterStack) {
        int currentAmmo = getAmmo(blasterStack);
        GasTypeData blasterGasTypeData = blasterStack.get(ModDataComponentTypes.GAS_TYPE.get());
        String currentGasType = (blasterGasTypeData != null) ? blasterGasTypeData.gasType() : null;

        // Check if the blaster is full
        if (currentAmmo >= max_ammo) {
            player.displayClientMessage(Component.translatable("item.starwars.blaster.full_ammo"), true);
            return;
        }

        boolean foundGasItem = false;

        // If the blaster has zero ammo, it can accept any GasItem
        if (currentAmmo == 0) {
            for (ItemStack stack : player.getInventory().items) {
                if (stack.getItem() instanceof GasItem gasItem) {
                    int gasAmmo = gasItem.getAmmo(stack);

                    if (gasAmmo > 0) {
                        foundGasItem = true; // Mark that a gas item was found
                        int ammoNeeded = max_ammo - currentAmmo;
                        int ammoToReload = Math.min(ammoNeeded, gasAmmo);

                        // Update ammo for both BlasterItem and GasItem
                        setAmmo(blasterStack, currentAmmo + ammoToReload);
                        gasItem.setAmmo(stack, gasAmmo - ammoToReload);

                        // Set the GasTypeData on the BlasterItem for the first time
                        String gasType = gasItem.getGasType(); // Assuming you have a method to get the gas type from the GasItem
                        blasterStack.set(ModDataComponentTypes.GAS_TYPE.get(), new GasTypeData(gasType));

                        //Update serverside in regard to the ammo in the blaster
                        PayloadRegister.sendToServer(new SSReloadPacket(blasterStack, currentAmmo + ammoToReload, gasType));

                        player.displayClientMessage(Component.translatable("item.starwars.blaster.reloaded"), true);
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.ANVIL_USE,
                                SoundSource.PLAYERS, 1.0F, 1.0F);

                        return;
                    }
                }
            }
        }

        // Handle gas type match when ammo is not zero
        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem() instanceof GasItem gasItem) {
                int gasAmmo = gasItem.getAmmo(stack);

                if (gasAmmo > 0) {
                    foundGasItem = true;
                    String gasType = gasItem.getGasType();

                    if (currentGasType != null && !currentGasType.equals(gasType)) {
                        continue; // Skip mismatched gas types
                    }

                    int ammoNeeded = max_ammo - currentAmmo;
                    int ammoToReload = Math.min(ammoNeeded, gasAmmo);

                    setAmmo(blasterStack, currentAmmo + ammoToReload);
                    gasItem.setAmmo(stack, gasAmmo - ammoToReload);

                    PayloadRegister.sendToServer(new SSReloadPacket(blasterStack, currentAmmo + ammoToReload, gasType));

                    player.displayClientMessage(Component.translatable("item.starwars.blaster.reloaded"), true);
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.ANVIL_USE,
                            SoundSource.PLAYERS, 1.0F, 1.0F);

                    return;
                }
            }
        }

        if (!foundGasItem) {
            player.displayClientMessage(Component.translatable("item.starwars.blaster.no_gas_items"), true);
        } else {
            player.displayClientMessage(Component.translatable("item.starwars.blaster.no_compatible_gas"), true);
        }
    }

    public void switchFiringMode() {
        //Cycle through all firing modes available with the blaster
        firingModeIndex = (firingModeIndex + 1) % firingModes.size();

        this.firingMode = firingModes.get(firingModeIndex);
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