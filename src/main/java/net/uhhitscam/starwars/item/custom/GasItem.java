package net.uhhitscam.starwars.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeType;
import net.uhhitscam.starwars.component.GasAmmoData;
import net.uhhitscam.starwars.component.ModDataComponentTypes;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GasItem extends Item {
    private final int burnTime;
    private final int maxAmmo;
    private final String gasType;

    public GasItem(Properties pProperties, int burnTime, int maxAmmo, String gasType) {
        super(pProperties);
        this.burnTime = burnTime;
        this.gasType = gasType;
        this.maxAmmo = maxAmmo;
    }

    // Getter for the gas type
    public String getGasType() {
        System.out.println("made it to gasItem getGasType");
        return gasType;
    }

    public int getAmmo(ItemStack stack) {
        GasAmmoData data = stack.get(ModDataComponentTypes.GAS_AMMO.get());
        if (data == null) {
            setAmmo(stack, maxAmmo); // Initialize with maxAmmo
            return maxAmmo;
        }
        return data.ammo();
    }

    public void setAmmo(ItemStack stack, int ammo) {
        System.out.println("made it to gasItem setAmmo");
        stack.set(ModDataComponentTypes.GAS_AMMO.get(), new GasAmmoData(ammo));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.FAIL;
        }

        ItemStack gasStack = context.getItemInHand();
        int gasAmmo = getAmmo(gasStack);

        if (gasAmmo > 0) {
            for (ItemStack stack : player.getInventory().items) {
                if (stack.getItem() instanceof BlasterItem blaster) {
                    int blasterAmmo = blaster.getAmmo(stack);
                    int ammoNeeded = blaster.getMaxAmmo() - blasterAmmo;

                    if (ammoNeeded > 0) {
                        int ammoToReload = Math.min(ammoNeeded, gasAmmo);

                        // Update ammo for both GasItem and BlasterItem
                        blaster.setAmmo(stack, blasterAmmo + ammoToReload);
                        setAmmo(gasStack, gasAmmo - ammoToReload);
                        context.getLevel().playSound(null, player.getX(), player.getY(), player.getZ(),
                                net.minecraft.sounds.SoundEvents.ANVIL_USE,
                                net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
                        break;
                    }
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public Component getName(ItemStack stack) {
        ChatFormatting color = switch (gasType) {
            case "TIBANNA_GAS" -> ChatFormatting.RED;
            case "IONIZED_TIBANNA_GAS" -> ChatFormatting.BLUE;
            case "SPIN_SEALED_TIBANNA_GAS" -> ChatFormatting.GREEN;
            case "TIBANNAX_GAS" -> ChatFormatting.GRAY;
            case "SIG_GAS" -> ChatFormatting.YELLOW;
            case "MAGNETIZED_SIG_GAS" -> ChatFormatting.DARK_PURPLE;
            case "SKEVON_GAS" -> ChatFormatting.GOLD;
            case null, default -> ChatFormatting.WHITE; // Fallback to white
        };

        return super.getName(stack).copy().withStyle(style -> style.withColor(color));
    }

    @Override
    public void appendHoverText(ItemStack pStack,TooltipContext pContext, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.literal("Gas Ammo: " + getAmmo(pStack) + "/" + maxAmmo));
        super.appendHoverText(pStack, pContext, pTooltip, pFlag);
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return this.burnTime;
    }
}