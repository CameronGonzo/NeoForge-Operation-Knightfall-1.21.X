package net.uhhitscam.knightfall.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.gui.components.debug.DebugScreenEntryStatus;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.entity.ModEntities;
import net.uhhitscam.knightfall.entity.custom.GrenadeEntity;
import net.uhhitscam.knightfall.event.BlurEffectEventHandler;
import net.uhhitscam.knightfall.gui.HudClient;
import net.uhhitscam.knightfall.item.ModItems;
import net.uhhitscam.knightfall.util.ThermalVisionUtil;

/** Opt-in integration check; test sources are excluded from the release jar. */
@EventBusSubscriber(modid = OperationKnightfall.MODID, value = Dist.CLIENT)
public final class ClientMigrationSmokeTest {
    private static final org.slf4j.Logger LOGGER = com.mojang.logging.LogUtils.getLogger();
    private static int ticks;
    private static int worldTicks;
    private static boolean started;
    private static int nextEntityId = 1_000_000;

    @SubscribeEvent
    public static void tick(ClientTickEvent.Post event) {
        if (!Boolean.getBoolean("knightfall.clientSmoke")) return;
        if ((HudClient.WHITE >>> 24) == 0 || (HudClient.RED >>> 24) == 0 || (HudClient.OUTLINE_COLOR >>> 24) == 0) {
            throw new IllegalStateException("HUD text colors must include a visible alpha channel");
        }
        Minecraft mc = Minecraft.getInstance();
        if (++ticks > 2400) throw new IllegalStateException("Client smoke test timed out before completion");
        if (!started && ticks > 80 && mc.gui.screen() != null) {
            started = true;
            mc.createWorldOpenFlows().createFreshLevel("smoke-" + System.currentTimeMillis(),
                    new LevelSettings("26.2 migration smoke test", GameType.CREATIVE,
                            LevelSettings.DifficultySettings.DEFAULT, true, WorldDataConfiguration.DEFAULT),
                    new WorldOptions(262, false, false), WorldPresets::createTestWorldDimensions, new TitleScreen());
        }
        if (mc.level == null || mc.player == null) return;
        worldTicks++;
        if (worldTicks == 1) {
            mc.debugEntries.setStatus(DebugScreenEntries.ENTITY_HITBOXES, DebugScreenEntryStatus.ALWAYS_ON);
        }
        if (worldTicks == 40) {
            int items = 0;
            for (var item : BuiltInRegistries.ITEM) {
                if (!BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(OperationKnightfall.MODID)) continue;
                var stack = new ItemStack(item);
                for (var context : ItemDisplayContext.values()) {
                    var state = new ItemStackRenderState();
                    mc.getItemModelResolver().updateForLiving(state, stack, context, mc.player);
                    if (state.isEmpty()) throw new IllegalStateException("Empty item model: " + item);
                }
                items++;
            }
            LOGGER.info("CLIENT_SMOKE: resolved {} registered item models in every display context", items);
            mc.player.setYRot(0);
            mc.player.setXRot(0);
            mc.player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, new ItemStack(ModItems.A280.get()));
            var cow = EntityTypes.COW.create(mc.level, EntitySpawnReason.COMMAND);
            cow.setId(nextEntityId++);
            cow.setPos(mc.player.position().add(0, 0, 4));
            cow.setNoAi(true);
            mc.level.addEntity(cow);
        }
        if (worldTicks >= 50 && worldTicks <= 150 && worldTicks % 10 == 0) {
            int index = 0;
            for (var holder : ModEntities.ENTITY_TYPES.getEntries()) {
                var entity = holder.get().create(mc.level, EntitySpawnReason.COMMAND);
                if (entity == null) throw new IllegalStateException("Cannot create " + holder.getId());
                entity.setId(nextEntityId++);
                entity.setPos(mc.player.position().add((index % 6 - 2.5) * 0.6, 1 + index / 6 * 0.4, 3));
                entity.setNoGravity(true);
                if (entity instanceof net.uhhitscam.knightfall.entity.custom.BlasterBeamEndpointEntity beam) beam.setOwner(mc.player);
                if (entity instanceof GrenadeEntity grenade) grenade.setItem(new ItemStack(ModItems.THERMAL_DETONATOR.get()));
                mc.level.addEntity(entity);
                index++;
            }
            ThermalVisionUtil.setThermalActive(true);
            var origin = mc.player.position().add(0, 1, 3);
            for (var particle : net.uhhitscam.knightfall.particle.ModParticles.PARTICLE_TYPES.getEntries()) {
                mc.level.addParticle((net.minecraft.core.particles.SimpleParticleType) particle.get(),
                        origin.x, origin.y, origin.z, 0, 0.03, 0);
            }
            net.uhhitscam.knightfall.event.FaceAlignedParticleClient.add(
                    new org.joml.Vector3f((float) origin.x, (float) origin.y, (float) origin.z),
                    net.minecraft.core.Direction.NORTH, net.uhhitscam.knightfall.event.FaceAlignedParticleType.SONIC_RIPPLE, 1);
        }
        if (worldTicks == 160) BlurEffectEventHandler.trigger(10, 20, 4);
        if (worldTicks == 220) {
            if (!HudClient.hasExtractedHud()) {
                throw new IllegalStateException("Knightfall HUD layer was never extracted");
            }
            var bolt = ModEntities.BLASTER_BOLT.get().create(mc.level, EntitySpawnReason.COMMAND);
            if (bolt == null || !bolt.shouldRenderAtSqrDistance(1.0)) {
                throw new IllegalStateException("A new nearby blaster bolt must be eligible for rendering immediately");
            }
            ThermalVisionUtil.setThermalActive(false);
            mc.debugEntries.setStatus(DebugScreenEntries.ENTITY_HITBOXES, DebugScreenEntryStatus.NEVER);
            LOGGER.info("CLIENT_SMOKE: PASS - world, item models, entity renderers, HUD layer, thermal overlay and blur ran without a crash");
            mc.stop();
        }
    }
}
