package net.uhhitscam.knightfall.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.uhhitscam.knightfall.item.custom.FiringMode;
import net.uhhitscam.knightfall.item.custom.ProjectileItem;
import net.uhhitscam.knightfall.item.custom.ScopeTexture;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class WeaponAimRules {
    private static final float AIMED_SPREAD_MULTIPLIER = 0.8F;
    private static final float AIMED_RECOIL_MULTIPLIER = 0.4F;
    private static final double DEFAULT_MUZZLE_SIDE_OFFSET = 0.27;
    private static final double DEFAULT_MUZZLE_HEIGHT_OFFSET = -0.1;
    private static final double DEFAULT_BEAM_SIDE_OFFSET = 0.14;

    private WeaponAimRules() {}

    public static boolean isDualWielding(Player player) {
        return isProjectileWeapon(player.getMainHandItem()) && isProjectileWeapon(player.getOffhandItem());
    }

    public static boolean isAiming(Player player) {
        return player.isShiftKeyDown()
                && !isDualWielding(player)
                && (isProjectileWeapon(player.getMainHandItem()) || isProjectileWeapon(player.getOffhandItem()));
    }

    public static boolean isAimingHand(Player player, boolean mainHand) {
        if (!isAiming(player)) {
            return false;
        }

        return isProjectileWeapon(mainHand ? player.getMainHandItem() : player.getOffhandItem());
    }

    public static ItemStack getAimedStack(Player player) {
        if (!isAiming(player)) {
            return ItemStack.EMPTY;
        }

        return isProjectileWeapon(player.getMainHandItem()) ? player.getMainHandItem() : player.getOffhandItem();
    }

    public static float spreadMultiplier(Player player) {
        return isAiming(player) ? AIMED_SPREAD_MULTIPLIER : 1.0F;
    }

    public static float recoilMultiplier(Player player) {
        return isAiming(player) ? AIMED_RECOIL_MULTIPLIER : 1.0F;
    }

    public static WeaponSelection getUiWeapon(Player player) {
        WeaponSelection main = fromStack(player.getMainHandItem());
        WeaponSelection off = fromStack(player.getOffhandItem());

        if (main == null) {
            return off;
        }
        if (off == null) {
            return main;
        }

        return getZoomFactor(main) >= getZoomFactor(off) ? main : off;
    }

    public static float getZoomFactor(Player player) {
        if (!isAiming(player)) {
            return 1.0F;
        }

        WeaponSelection selection = getUiWeapon(player);
        return selection == null ? 1.0F : getZoomFactor(selection);
    }

    @Nullable
    public static ResourceLocation getCrosshairTexture(Player player) {
        WeaponSelection selection = getUiWeapon(player);
        return selection == null ? null : selection.weapon().getUI().crosshair().texture();
    }

    @Nullable
    public static ResourceLocation getScopeTexture(Player player) {
        if (!isAiming(player)) {
            return null;
        }

        WeaponSelection selection = getUiWeapon(player);
        if (selection == null) {
            return null;
        }

        FiringMode firingMode = selection.weapon().getFiringMode(selection.stack());
        ScopeTexture scope = selection.weapon().getUI().scope(firingMode);
        return scope == null ? null : scope.texture();
    }

    public static MuzzleOffset getMuzzleOffset(Player player, boolean mainHand) {
        if (isAimingHand(player, mainHand)) {
            ItemStack stack = mainHand ? player.getMainHandItem() : player.getOffhandItem();
            if (stack.getItem() instanceof ProjectileItem weapon
                    && weapon.getUI().scope(weapon.getFiringMode(stack)) != null) {
                return new MuzzleOffset(0.0, 0.0);
            }
        }

        return new MuzzleOffset(DEFAULT_MUZZLE_SIDE_OFFSET, DEFAULT_MUZZLE_HEIGHT_OFFSET);
    }

    public static Vec3 getMuzzlePosition(Player player, boolean mainHand, float partialTick) {
        return getMuzzlePosition(player, mainHand, partialTick, getMuzzleOffset(player, mainHand));
    }

    public static Vec3 getBeamMuzzlePosition(Player player, boolean mainHand, float partialTick) {
        MuzzleOffset offset = isScopedAiming(player, mainHand)
                ? new MuzzleOffset(0.0, 0.0)
                : new MuzzleOffset(DEFAULT_BEAM_SIDE_OFFSET, DEFAULT_MUZZLE_HEIGHT_OFFSET);
        return getMuzzlePosition(player, mainHand, partialTick, offset);
    }

    private static boolean isScopedAiming(Player player, boolean mainHand) {
        if (!isAimingHand(player, mainHand)) {
            return false;
        }

        ItemStack stack = mainHand ? player.getMainHandItem() : player.getOffhandItem();
        return stack.getItem() instanceof ProjectileItem weapon
                && weapon.getUI().scope(weapon.getFiringMode(stack)) != null;
    }

    private static Vec3 getMuzzlePosition(
            Player player,
            boolean mainHand,
            float partialTick,
            MuzzleOffset offset
    ) {
        double x = Mth.lerp(partialTick, player.xo, player.getX());
        double y = Mth.lerp(partialTick, player.yo, player.getY()) + player.getEyeHeight();
        double z = Mth.lerp(partialTick, player.zo, player.getZ());
        float yaw = Mth.rotLerp(partialTick, player.yRotO, player.getYRot());
        double yawRadians = Math.toRadians(yaw);
        double sideMultiplier = mainHand ? -1.0 : 1.0;

        Vec3 sideOffset = new Vec3(
                sideMultiplier * Math.cos(yawRadians) * offset.side(),
                offset.height(),
                sideMultiplier * Math.sin(yawRadians) * offset.side()
        );
        double forwardOffset = offset.side() == 0.0 ? 0.1 : 0.2;
        return new Vec3(x, y, z).add(sideOffset).add(player.getViewVector(partialTick).scale(forwardOffset));
    }

    private static float getZoomFactor(WeaponSelection selection) {
        FiringMode firingMode = selection.weapon().getFiringMode(selection.stack());
        return selection.weapon().getUI().zoom(firingMode);
    }

    @Nullable
    private static WeaponSelection fromStack(ItemStack stack) {
        return stack.getItem() instanceof ProjectileItem weapon ? new WeaponSelection(weapon, stack) : null;
    }

    private static boolean isProjectileWeapon(ItemStack stack) {
        return stack.getItem() instanceof ProjectileItem;
    }

    public record WeaponSelection(ProjectileItem weapon, ItemStack stack) {}

    public record MuzzleOffset(double side, double height) {}
}
