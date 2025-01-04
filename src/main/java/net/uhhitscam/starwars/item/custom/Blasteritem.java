package net.uhhitscam.starwars.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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

import java.util.*;

public class Blasteritem extends Item{
    private final float inaccuracy;
    private final float bolt_speed;
    private final int max_ammo;
    private final int blasterDamage;
    private final int fireRate;
    private FiringMode firingMode;
    private final float recoil;

    public enum FiringMode {
        SEMI_AUTO,
        BURST,
        FULL_AUTO
    }

    private final Map<UUID, Long> lastFireTime = new HashMap<>();

    public Blasteritem(Properties properties, float bolt_speed, float inaccuracy, int max_ammo, int blasterDamage,
                       int fireRate, FiringMode firingMode, float recoil) {
        super(properties);
        this.bolt_speed = bolt_speed;
        this.inaccuracy = inaccuracy;
        this.max_ammo = max_ammo;
        this.blasterDamage = blasterDamage;
        this.fireRate = fireRate;
        this.firingMode = firingMode;
        this.recoil = recoil;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        System.out.println("InteractionResultHolder use");
        ItemStack itemstack = player.getItemInHand(hand);
        int currentAmmo = getAmmo(itemstack);
        long currentTime = System.currentTimeMillis();
        long lastTime = lastFireTime.getOrDefault(player.getUUID(), 0L);
        int shots = 1;
        Timer timer = new Timer();
        System.out.println("current ammo: " + currentAmmo);

        if  (currentAmmo > 0 && !level.isClientSide) {
            System.out.println("not client side and ammo is over 0");
            String currentGasType = getGasType(itemstack);

            if ((currentTime - lastTime) < fireRate * 50) {
                System.out.println("hold on buddy");
                return InteractionResultHolder.fail(itemstack);
            }

            lastFireTime.put(player.getUUID(), currentTime);

            switch (firingMode) {
                case SEMI_AUTO, FULL_AUTO -> shots = 1;
                case BURST -> shots = Math.min(3, currentAmmo);
            }

            for (int i = 0; i < shots; i++) {
                System.out.println("processing shot");
                System.out.println("current ammo: " + currentAmmo);
                if (currentAmmo > 0 && shots > 1) {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            fireBolt(level, player, itemstack, currentAmmo, currentGasType);
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
            System.out.println("yay item used");

            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        } else {
            System.out.println("either is client or no ammo found");
            // Feedback for empty blaster
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);

            return InteractionResultHolder.fail(itemstack);
        }
    }

    private void fireBolt(Level level, Player player, ItemStack itemstack, int currentAmmo, String currentGasType) {
        System.out.println("fireBolt");
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

            if (this.recoil != 0) {
                applyRecoil(player);
            }
        } else {
            // Feedback for empty blaster
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    private void applyRecoil(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            float horizontalRecoil = (float) (player.level().getRandom().nextDouble() - 0.5) * 1.0f; // Side-to-side recoil

            // Apply recoil
            player.setXRot(player.getXRot() - this.recoil);
            player.setYRot(player.getYRot() + horizontalRecoil);

            // Sync head rotation (for visuals)
            player.setYHeadRot(player.getYRot());

            // Synchronize the changes with the client
            serverPlayer.connection.send(new ClientboundRotateHeadPacket(serverPlayer, (byte) (player.getYRot() * 256 / 360)));
            serverPlayer.connection.teleport(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
        }
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

        boolean foundGasItem = false; // Flag to track if any gas item was found

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

    @Override
    public Component getName(ItemStack stack) {
        ChatFormatting color;

        int currentAmmo = getAmmo(stack);
        if (currentAmmo <= 0) {
            // No ammo, default to white
            color = ChatFormatting.WHITE;
        } else {
            String gasType = null;

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
                case null, default ->
                    // Fallback to white
                        ChatFormatting.WHITE;
            };
        }

        return super.getName(stack).copy().withStyle(style -> style.withColor(color));
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