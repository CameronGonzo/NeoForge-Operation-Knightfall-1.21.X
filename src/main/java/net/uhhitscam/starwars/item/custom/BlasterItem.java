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
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.starwars.component.FiringModeData;
import net.uhhitscam.starwars.component.GasAmmoData;
import net.uhhitscam.starwars.component.GasTypeData;
import net.uhhitscam.starwars.component.CoolDownData;
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
        ItemStack itemstack = player.getMainHandItem();
        String firingMode = getFiringMode(itemstack);

        if (firingMode.equals("FULL_AUTO")) {
            firingBlaster(level, player, itemstack);
        }
        return InteractionResultHolder.fail(itemstack);
    }

    public void mainHandFiring(Player player, ItemStack itemstack, InteractionHand hand) {
        System.out.println("mainHandFiring method");
        String firingMode = getFiringMode(itemstack);

        if (firingMode.equals("SEMI_AUTO") || firingMode.equals("SCATTER") || firingMode.equals("BURST")) {
            firingBlaster(player.level(), player, itemstack);
        }
    }

    private void firingBlaster(Level level, Player player, ItemStack itemstack) {
        int currentAmmo = getAmmo(itemstack);

        if (currentAmmo <= 0) {
            System.out.println("No ammo left to fire.");
            //Feedback for empty blaster
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
            return;
        }

        CoolDownData cooldownData = itemstack.get(ModDataComponentTypes.COOLDOWN);
        if (cooldownData == null) {
            cooldownData = new CoolDownData(0);
            itemstack.set(ModDataComponentTypes.COOLDOWN, cooldownData);
        }

        if (cooldownData.isOnCooldown(level)) {
            //The item is still on cooldown
            return;
        }

        if (level.isClientSide) {
            return; //Prevent firing logic on the client side
        }

        String currentGasType = getGasType(itemstack);
        String firingMode = getFiringMode(itemstack);

        int shots = switch (firingMode) {
            case "BURST" -> Math.min(3, currentAmmo);
            case "SCATTER", "SEMI_AUTO", "FULL_AUTO" -> 1;
            default -> 1;
        };

        for (int i = 0; i < shots; i++) {
            int delay = i * 100; //Delay between shots for burst and scatter modes
            int finalAmmo = currentAmmo - i; //Calculate remaining ammo for each shot

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

        //Set the new cooldown time
        long cooldownEndTime = level.getGameTime() + getCoolDown(firingMode);
        itemstack.set(ModDataComponentTypes.COOLDOWN, cooldownData.withCooldownEndTime(cooldownEndTime));
    }

    public int getCoolDown(String firingMode) {
        return switch (firingMode) {
            case "SEMI_AUTO" -> semiFireRate;
            case "BURST" -> burstFireRate;
            case "FULL_AUTO" -> fullFireRate;
            case "SCATTER" -> semiFireRate;
            default -> 50; //fallback just incase
        };
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
        String defaultFiringMode = firingModes.get(0); //First index in the firingModes list
        FiringModeData firingModeData = new FiringModeData(defaultFiringMode);
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

    private void fireBolt(Level level, Player player, ItemStack itemstack, int currentAmmo, String currentGasType) {
        float recoil;

        //Retrieve the firing mode from the FiringModeData component
        FiringModeData firingModeData = itemstack.get(ModDataComponentTypes.FIRING_MODE.get());
        if (firingModeData == null) {
            System.out.println("FiringModeData is missing, defaulting to SEMI_AUTO");
            firingModeData = new FiringModeData("SEMI_AUTO");
            itemstack.set(ModDataComponentTypes.FIRING_MODE.get(), firingModeData);
        }
        String firingMode = firingModeData.firingMode();

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

            double accuracyFactor = this.inaccuracy / 100; //Lower value = more accurate
            xVelocity += (level.getRandom().nextDouble() - 0.5) * accuracyFactor;
            yVelocity += (level.getRandom().nextDouble() - 0.5) * accuracyFactor;
            zVelocity += (level.getRandom().nextDouble() - 0.5) * accuracyFactor;

            Vec3 velocity = new Vec3(xVelocity, yVelocity, zVelocity).normalize().scale(this.bolt_speed); //Use custom velocity
            bolt.setDeltaMovement(velocity);

            level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            level.addFreshEntity(bolt);

            setAmmo(itemstack, currentAmmo - 1);

            player.awardStat(Stats.ITEM_USED.get(this));

            switch (firingMode) {
                case "SEMI_AUTO", "SCATTER" -> recoil = semiRecoil;
                case "BURST" -> recoil = burstRecoil;
                case "FULL_AUTO" -> recoil = fullRecoil;
                default -> recoil = 1;
            }

            applyRecoil(player, recoil / 5);
            System.out.println("RecoilMap: " + recoilMap);
            System.out.println("Applying recoil: " + recoil);
        } else {
            //Feedback for empty blaster
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player && recoilMap.containsKey(player.getUUID())) {
            UUID playerId = player.getUUID();
            float recoilAmount = recoilMap.get(playerId);

            //Gradual recovery
            recoilAmount *= 0.8f; //Adjust this factor for faster/slower recovery

            //Apply the recoil effect to the player's pitch
            float currentPitch = player.getXRot();
            float recoilEffect = recoilMap.get(playerId) - recoilAmount; //Calculate the difference
            float newPitch = currentPitch - recoilEffect;

            //Clamp the pitch
            newPitch = Math.max(-90.0f, Math.min(90.0f, newPitch));
            player.setXRot(newPitch);

            //Update the recoil map
            if (recoilAmount < 0.01f) {
                recoilMap.remove(playerId);
            } else {
                recoilMap.put(playerId, recoilAmount);
            }
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

    public void reload(Player player, ItemStack blasterStack) {
        int currentAmmo = getAmmo(blasterStack);
        GasTypeData blasterGasTypeData = blasterStack.get(ModDataComponentTypes.GAS_TYPE.get());
        String currentGasType = (blasterGasTypeData != null) ? blasterGasTypeData.gasType() : null;

        if (currentAmmo >= max_ammo) {
            player.displayClientMessage(Component.translatable("item.starwars.blaster.full_ammo"), true);
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
                        int ammoToReload = Math.min(ammoNeeded, gasAmmo);

                        currentAmmo += ammoToReload;
                        gasAmmo -= ammoToReload;

                        //Update the blaster's ammo
                        setAmmo(blasterStack, currentAmmo);
                        gasItem.setAmmo(stack, gasAmmo);

                        //Notify the server about the ammo update
                        PayloadRegister.sendToServer(new SSReloadPacket(blasterStack, currentAmmo, gasType));
                        PayloadRegister.sendToServer(new SSGasAmmoPacket(stack, gasAmmo, i));

                        //Sync inventory changes
                        player.inventoryMenu.broadcastChanges();

                        player.displayClientMessage(Component.translatable("item.starwars.blaster.reloaded"), true);
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.ANVIL_USE, SoundSource.PLAYERS, 1.0F, 1.0F);

                        //If full, exit the loop
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

        //Retrieve the FiringModeData component from the blaster stack.
        FiringModeData firingModeData = blasterStack.get(ModDataComponentTypes.FIRING_MODE.get());
        if (firingModeData == null) {
            //If no firing mode is currently set, initialize it to the first mode in the list.
            firingModeData = new FiringModeData(firingModes.get(0));
            blasterStack.set(ModDataComponentTypes.FIRING_MODE.get(), firingModeData);
        }

        String currentFiringMode = firingModeData.firingMode();
        System.out.println("Current firing mode: " + currentFiringMode);

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
        PayloadRegister.sendToServer(new SSFiringModePacket(blasterStack, nextFiringMode));

        System.out.println("Switched to firing mode: " + nextFiringMode);

        if (blasterStack.getEntityRepresentation() instanceof Player player) {
            //Notify the player about the new firing mode.
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