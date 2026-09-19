package net.uhhitscam.knightfall.item.custom.projectile;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.uhhitscam.knightfall.component.AmmoData;
import net.uhhitscam.knightfall.component.ModDataComponentTypes;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GasItem extends Item {
    private final int burnTime;
    private final int maxAmmo;
    private final AmmoType ammoType;

    public GasItem(Properties pProperties, int burnTime, int maxAmmo, AmmoType ammoType) {
        super(pProperties);
        this.burnTime = burnTime;
        this.ammoType = ammoType;
        this.maxAmmo = maxAmmo;
    }

    public AmmoType getAmmoType() {
        return ammoType;
    }

    public int getAmmo(ItemStack stack) {
        AmmoData data = stack.get(ModDataComponentTypes.AMMO.get());
        if (data == null) {
            setAmmo(stack, maxAmmo);
            return maxAmmo;
        }
        return data.ammo();
    }


    public void setAmmo(ItemStack stack, int ammo) {
        stack.set(ModDataComponentTypes.AMMO.get(), new AmmoData(ammo));
    }

    @Override
    public Component getName(ItemStack stack) {
        ChatFormatting color = switch (ammoType) {
            case TIBANNA -> ChatFormatting.RED;
            case IONIZED_TIBANNA -> ChatFormatting.BLUE;
            case SPIN_SEALED_TIBANNA -> ChatFormatting.GREEN;
            case TIBANNAX -> ChatFormatting.GRAY;
            case SIG -> ChatFormatting.YELLOW;
            case MAGNETIZED_SIG -> ChatFormatting.DARK_PURPLE;
            case SKEVON -> ChatFormatting.GOLD;
            case null, default -> ChatFormatting.WHITE;
        };

        return super.getName(stack).copy().withStyle(style -> style.withColor(color));
    }

    public void appendBaseTooltip(ItemStack pStack, java.util.function.Consumer<Component> pTooltip) {
        pTooltip.accept(Component.literal("Gas Ammo: " + getAmmo(pStack) + "/" + maxAmmo));
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType, net.minecraft.world.level.block.entity.FuelValues fuelValues) {
        return this.burnTime;
    }
}