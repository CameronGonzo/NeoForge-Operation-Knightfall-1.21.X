package net.uhhitscam.starwars.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.uhhitscam.starwars.OperationKnightfall;
import net.uhhitscam.starwars.effect.ModEffects;
import net.uhhitscam.starwars.gui.HudClient;
import net.uhhitscam.starwars.item.custom.BlasterItem;
import net.uhhitscam.starwars.network.PayloadRegister;
import net.uhhitscam.starwars.network.SSFireBlasterPacket;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

@EventBusSubscriber(modid = OperationKnightfall.MODID, value = Dist.CLIENT)
public class ModClientEvents {
    public static boolean firing = false; // Track the firing state outside of the method
    private static Timer fullAutoTimer = new Timer(); // Timer for scheduling full-auto firing

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onRenderGui(RenderGuiEvent.Post event) {
        HudClient.onRenderHUD(event.getGuiGraphics());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        HudClient.onClientTick();
    }

    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseButton.Pre event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player == null) return;

        //Check if a GUI screen is open
        if (minecraft.screen != null) {
            return; //Exit if the player is in a GUI (e.g., inventory, pause menu)
        }

        //Check if the right mouse button is clicked
        if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT && event.getAction() == GLFW.GLFW_PRESS) {
            if (minecraft.screen != null || player.hasEffect(ModEffects.STUN_EFFECT)) {
                return; //Exit if the player is in a GUI
            }

            // Check if the player is interacting with an entity or block
            HitResult hitResult = minecraft.hitResult;
            if (hitResult.getType() == HitResult.Type.ENTITY && !player.isShiftKeyDown()) {
                EntityHitResult entityHitResult = (EntityHitResult) hitResult;
                Entity entity = entityHitResult.getEntity();
                if (entity instanceof Villager || entity instanceof WanderingTrader) {
                    return; // Exit if interacting with a villager
                }
            }

            ItemStack mainHandItem = player.getMainHandItem();
            firing = true;

            if (mainHandItem.getItem() instanceof BlasterItem) {
                BlasterItem blasterItem = (BlasterItem) mainHandItem.getItem();
                if ("FULL_AUTO".equals(blasterItem.getFiringMode(mainHandItem))) {
                    scheduleFullAutoFiring(player, mainHandItem, true);
                } else {
                    PayloadRegister.sendToServer(new SSFireBlasterPacket(mainHandItem, "blasterGasType", false, true));
                }
            }
        }

        // Check if the right mouse button is released
        if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT && event.getAction() == GLFW.GLFW_RELEASE) {
            ItemStack mainHandItem = player.getMainHandItem();

            if (mainHandItem.getItem() instanceof BlasterItem) {
                BlasterItem blasterItem = (BlasterItem) mainHandItem.getItem();
                if ("FULL_AUTO".equals(blasterItem.getFiringMode(mainHandItem))) {
                    firing = false;
                    fullAutoTimer.cancel();
                    fullAutoTimer = new Timer(); // Reset the timer
                }
            }
        }

        // Check if the left mouse button is clicked
        if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT && event.getAction() == GLFW.GLFW_PRESS) {
            if (minecraft.screen != null || player.hasEffect(ModEffects.STUN_EFFECT)) {
                return; //Exit if the player is in a GUI
            }

            boolean punching = false;
            ItemStack offHandItem = player.getOffhandItem();

            if (offHandItem.getItem() instanceof BlasterItem) {
                firing = true;
                // Check if the player is targeting an entity
                HitResult hitResult = minecraft.hitResult;
                if (hitResult instanceof EntityHitResult) {
                    EntityHitResult entityHitResult = (EntityHitResult) hitResult;
                    Entity target = entityHitResult.getEntity();
                    if (player.distanceTo(target) <= 3 && !player.isShiftKeyDown()) {
                        punching = true;
                    }
                }
            }

            if (!punching && offHandItem.getItem() instanceof BlasterItem) {
                BlasterItem blasterItem = (BlasterItem) offHandItem.getItem();
                if ("FULL_AUTO".equals(blasterItem.getFiringMode(offHandItem))) {
                    scheduleFullAutoFiring(player, offHandItem, false);
                } else {
                    PayloadRegister.sendToServer(new SSFireBlasterPacket(offHandItem, "blasterGasType", false, false));
                }
                event.setCanceled(true); // Prevent default swinging action
            }
        }

        // Check if the left mouse button is released
        if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT && event.getAction() == GLFW.GLFW_RELEASE) {
            ItemStack offHandItem = player.getOffhandItem();

            if (offHandItem.getItem() instanceof BlasterItem) {
                BlasterItem blasterItem = (BlasterItem) offHandItem.getItem();
                if ("FULL_AUTO".equals(blasterItem.getFiringMode(offHandItem))) {
                    firing = false;
                    fullAutoTimer.cancel();
                    fullAutoTimer = new Timer(); // Reset the timer
                }
            }
        }
    }

    private static void scheduleFullAutoFiring(LocalPlayer player, ItemStack heldItem, boolean mainHand) {
        fullAutoTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (firing) {
                    PayloadRegister.sendToServer(new SSFireBlasterPacket(heldItem, "blasterGasType", true, mainHand));
                    scheduleFullAutoFiring(player, heldItem, mainHand); // Schedule the next firing
                }
            }
        }, 50); // Delay between shots in milliseconds
    }

    private static HitResult getPlayerHitResult(Player player) {
        // Maximum reach for players
        double reach = 20.0;

        // Start and end points for the ray trace
        Vec3 start = player.getEyePosition(1.0F);
        Vec3 look = player.getViewVector(1.0F);
        Vec3 end = start.add(look.scale(reach));

        // Perform ray trace for entities
        AABB searchBox = new AABB(start, end).inflate(1.0);
        List<Entity> entities = player.getCommandSenderWorld().getEntities(player, searchBox, entity -> !entity.isSpectator() && entity.isPickable());

        EntityHitResult closestEntityHitResult = null;
        double closestDistance = reach;

        for (Entity entity : entities) {
            AABB entityBox = entity.getBoundingBox().inflate(entity.getPickRadius());
            Optional<Vec3> optionalHit = entityBox.clip(start, end);

            if (optionalHit.isPresent()) {
                double distance = start.distanceTo(optionalHit.get());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestEntityHitResult = new EntityHitResult(entity, optionalHit.get());
                }
            }
        }

        if (closestEntityHitResult != null) {
            return closestEntityHitResult;
        }

        // If no entities are hit, perform a block ray trace
        return player.getCommandSenderWorld().clip(new ClipContext(start, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
    }

    @SubscribeEvent
    public static void onKeyboardInput(InputEvent.Key event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player == null) return;

        //Check if a GUI screen is open
        if (minecraft.screen != null) {
            return; //Exit if the player is in a GUI (e.g., inventory, pause menu)
        }

//        //Check if the W key is pressed
//        if (event.getKey() == GLFW.GLFW_KEY_SPACE && event.getAction() == GLFW.GLFW_PRESS) {
//            System.out.println("press Space");
//            if (player.hasEffect(ModEffects.STUN_EFFECT)) {
//                System.out.println("has stun");
//            }
//        }
//
//        //Check if the W key is released
//        if (event.getKey() == GLFW.GLFW_KEY_SPACE && event.getAction() == GLFW.GLFW_RELEASE) {
//            System.out.println("W released");
//        }
    }
}