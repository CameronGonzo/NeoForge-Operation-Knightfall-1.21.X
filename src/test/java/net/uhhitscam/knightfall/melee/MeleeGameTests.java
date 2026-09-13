package net.uhhitscam.knightfall.melee;

import net.uhhitscam.knightfall.item.custom.melee.MeleeLightsaber;
import net.uhhitscam.knightfall.item.custom.melee.MeleeProperty;
import net.uhhitscam.knightfall.item.custom.melee.MeleeTuning;
import net.uhhitscam.knightfall.item.custom.melee.MeleeWeaponDefinition;
import net.uhhitscam.knightfall.item.custom.melee.MeleeWeaponForm;
import net.uhhitscam.knightfall.item.custom.melee.MeleeWeaponItem;


import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.entity.custom.MeleeProjectileEntity;
import net.uhhitscam.knightfall.event.MeleeWeaponServerEvents;
import net.uhhitscam.knightfall.network.SSMeleeInputPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.uhhitscam.knightfall.item.custom.melee.MeleeAttackTrait.*;
import static net.uhhitscam.knightfall.item.custom.melee.MeleeInput.*;

@GameTestHolder(OperationKnightfall.MODID)
@PrefixGameTestTemplate(false)
@EventBusSubscriber(modid = OperationKnightfall.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class MeleeGameTests {
    private static final Map<String, Item> ITEMS = new HashMap<>();

    @SubscribeEvent
    public static void register(RegisterEvent event) {
        if (!Boolean.getBoolean("knightfall.gameTests")) return;
        event.register(Registries.ITEM, registry -> {
            fixture(registry, "thrust", MeleeWeaponForm.builder(6, 1.6).bind(LEFT_CLICK, THRUST).properties(MeleeProperty.FLURRY).build());
            fixture(registry, "simple", MeleeWeaponForm.builder(6, 1.6).bind(LEFT_CLICK, SIMPLE_HIT).build());
            fixture(registry, "mixed", MeleeWeaponForm.builder(6, 1.6).bind(RIGHT_CLICK, THRUST).bind(HOLD_RIGHT_CLICK, UPPERCUT).build());
            fixture(registry, "sweep", MeleeWeaponForm.builder(6, 1.6).bind(RIGHT_CLICK, SWEEP).build());
            fixture(registry, "block", MeleeWeaponForm.builder(6, 1.6).bind(HOLD_RIGHT_CLICK, BLOCK).build());
            fixture(registry, "break", MeleeWeaponForm.builder(6, 1.6).bind(LEFT_CLICK, BREAK_BLOCK).build());
            fixture(registry, "parry", MeleeWeaponForm.builder(6, 1.6).bind(RIGHT_CLICK, PARRY).build());
            fixture(registry, "throw", MeleeWeaponForm.builder(6, 1.6).bind(HOLD_RIGHT_CLICK, THROW).build());
            fixture(registry, "quick", MeleeWeaponForm.builder(6, 1.6).bind(RIGHT_CLICK, QUICK_THROW).build());
            fixture(registry, "whip", MeleeWeaponForm.builder(6, 1.6).bind(RIGHT_CLICK, WHIP_PULL).build());
            fixture(registry, "stagger", MeleeWeaponForm.builder(6, 1.6).bind(LEFT_CLICK, SIMPLE_HIT).properties(MeleeProperty.STAGGER, MeleeProperty.FIRE_DAMAGE).build());
            fixture(registry, "fall", MeleeWeaponForm.builder(6, 1.6).bind(FALL_HOLD_RIGHT_CLICK, SLAM_GROUND).build());
            fixture(registry, "levitate", MeleeWeaponForm.builder(6, 1.6).bind(HOLD_RIGHT_CLICK, LEVITATE).build());
            fixture(registry, "steady", MeleeWeaponForm.builder(6, 1.6).bind(HOLD_RIGHT_CLICK, STEADY).build());
            fixture(registry, "spin", MeleeWeaponForm.builder(6, 1.6).bind(HOLD_RIGHT_CLICK, SPIN).build());
            fixture(registry, "slash", MeleeWeaponForm.builder(6, 1.6).bind(LEFT_CLICK, SLASH).build());
            fixture(registry, "knockback", MeleeWeaponForm.builder(6, 1.6).bind(RIGHT_CLICK, KNOCKBACK).build());
            fixture(registry, "charge", MeleeWeaponForm.builder(6, 1.6).bind(RUN_HOLD_RIGHT_CLICK, CHARGE).build());
            fixture(registry, "dash", MeleeWeaponForm.builder(6, 1.6).bind(RIGHT_CLICK, DASH).build());
            fixture(registry, "blaster", MeleeWeaponForm.builder(6, 1.6).bind(HOLD_RIGHT_CLICK, BLASTER_SHOT).build());
            var base = MeleeWeaponForm.builder(3, 2).bind(LEFT_CLICK, SIMPLE_HIT).bind(RIGHT_CLICK, SWITCH).build();
            var extended = MeleeWeaponForm.builder(8, 1).bind(LEFT_CLICK, THRUST).bind(RIGHT_CLICK, SWITCH).build();
            var definition = MeleeWeaponDefinition.builder("melee_test_switch").form(base).alternateForm(extended).build();
            Item item = new MeleeWeaponItem(definition.itemProperties(), definition);
            registry.register(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, definition.registryName()), item);
            ITEMS.put("switch", item);
            fixture(registry, "resistant", MeleeWeaponForm.builder(6, 1.6).bind(HOLD_RIGHT_CLICK, BLOCK)
                    .properties(MeleeProperty.LIGHTSABER_RESISTANT).build());
            var cortosis = MeleeWeaponDefinition.builder("melee_test_cortosis")
                    .form(MeleeWeaponForm.builder(6, 1.6).bind(HOLD_RIGHT_CLICK, BLOCK).properties(MeleeProperty.DISABLE_LIGHTSABERS).build())
                    .tuning(new MeleeTuning(4, 1, 60, 4, 1.5, 0.6, 1, 60, 12)).build();
            fixtureDefinition(registry, "cortosis", cortosis);
            for (String name : new String[]{"electric", "shockwhip"}) {
                var form = name.equals("electric") ? MeleeWeaponForm.builder(6, 1.6).bind(LEFT_CLICK, SIMPLE_HIT).properties(MeleeProperty.ELECTRIC_SHOCK).build()
                        : MeleeWeaponForm.builder(6, 1.6).bind(RIGHT_CLICK, WHIP_SHOCK).build();
                fixtureDefinition(registry, name, MeleeWeaponDefinition.builder("melee_test_" + name).form(form)
                        .electricEffect(context -> { context.target().getPersistentData().putBoolean("test_shocked", true); return true; }).build());
            }
            Item saber = new TestSaber();
            registry.register(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "melee_test_saber"), saber);
            ITEMS.put("saber", saber);
        });
    }

    private static void fixture(RegisterEvent.RegisterHelper<Item> registry, String name, MeleeWeaponForm form) {
        var definition = MeleeWeaponDefinition.builder("melee_test_" + name).form(form).build();
        fixtureDefinition(registry, name, definition);
    }

    private static void fixtureDefinition(RegisterEvent.RegisterHelper<Item> registry, String name, MeleeWeaponDefinition definition) {
        Item item = new MeleeWeaponItem(definition.itemProperties(), definition);
        registry.register(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, definition.registryName()), item);
        ITEMS.put(name, item);
    }

    private static ServerPlayer player(GameTestHelper helper, String weapon) {
        helper.getLevel().getServer().setPvpAllowed(true);
        var cookie = CommonListenerCookie.createInitial(new com.mojang.authlib.GameProfile(UUID.randomUUID(), "melee-test"), false);
        var player = new ServerPlayer(helper.getLevel().getServer(), helper.getLevel(), cookie.gameProfile(), cookie.clientInformation());
        var connection = new Connection(PacketFlow.SERVERBOUND);
        new io.netty.channel.embedded.EmbeddedChannel(connection);
        helper.getLevel().getServer().getPlayerList().placeNewPlayer(connection, player, cookie);
        // A mock has no mod handshake or remote client. Keep the real server combat pipeline;
        // swallow outbound presentation packets in this test-only listener.
        player.connection = new net.minecraft.server.network.ServerGamePacketListenerImpl(
                helper.getLevel().getServer(), connection, player, cookie) {
            @Override public void send(net.minecraft.network.protocol.Packet<?> packet) { }
        };
        player.setGameMode(GameType.SURVIVAL);
        player.setNoGravity(true);
        player.setPos(helper.absoluteVec(new Vec3(4, 2, 4)));
        player.setYRot(0);
        player.setXRot(0);
        player.setOnGround(true);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ITEMS.get(weapon)));
        // The embedded connection is not in the server's network tick list.
        helper.onEachTick(() -> { if (!player.isRemoved()) player.doTick(); });
        return player;
    }

    private static LivingEntity target(GameTestHelper helper, float x, float z) {
        var target = helper.spawn(EntityType.HUSK, x, 2, z);
        target.setNoAi(true);
        target.setNoGravity(true);
        return target;
    }

    private static void done(GameTestHelper helper, ServerPlayer... players) {
        for (ServerPlayer player : players) {
            helper.getLevel().getEntitiesOfClass(MeleeProjectileEntity.class, player.getBoundingBox().inflate(128), e -> e.getOwner() == player)
                    .forEach(Entity::discard);
            helper.getLevel().getServer().getPlayerList().remove(player);
        }
        helper.succeed();
    }

    @GameTest(template = "melee_empty")
    public static void thrustReachAndWalls(GameTestHelper h) {
        ServerPlayer p = player(h, "thrust");
        LivingEntity target = target(h, 4, 7.7F);
        h.runAfterDelay(20, () -> {
            float health = target.getHealth();
            MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.ATTACK);
            h.assertTrue(target.getHealth() < health, "Thrust must reach beyond three blocks");
            target.invulnerableTime = 0;
            float damagedHealth = target.getHealth();
            h.setBlock(new BlockPos(4, 3, 6), Blocks.STONE);
            h.runAfterDelay(2, () -> {
                MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.ATTACK);
                h.assertTrue(target.getHealth() == damagedHealth, "Thrust must not reach through a wall");
                done(h, p);
            });
        });
    }

    @GameTest(template = "melee_empty")
    public static void mixedTapAndHold(GameTestHelper h) {
        ServerPlayer p = player(h, "mixed");
        LivingEntity target = target(h, 4, 6);
        h.runAfterDelay(20, () -> {
            MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.PRESS);
            h.assertTrue(target.getHealth() == 20, "Mixed input must not fire on press");
            MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.RELEASE);
            h.assertTrue(target.getHealth() < 20, "Short tap fires on release");
            h.runAfterDelay(20, () -> {
                target.setPos(h.absoluteVec(new Vec3(4, 2, 6)));
                target.setDeltaMovement(Vec3.ZERO);
                target.invulnerableTime = 0;
                MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.PRESS);
                h.runAfterDelay(11, () -> {
                    h.assertTrue(target.getDeltaMovement().y > 0, "Held uppercut must lift the target");
                    float health = target.getHealth();
                    MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.RELEASE);
                    h.assertTrue(target.getHealth() == health, "Hold release must not also fire tap");
                    done(h, p);
                });
            });
        });
    }

    @GameTest(template = "melee_empty")
    public static void sweepHitsBehind(GameTestHelper h) {
        ServerPlayer p = player(h, "sweep");
        LivingEntity front = target(h, 4, 6), back = target(h, 4, 2);
        h.runAfterDelay(20, () -> {
            MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.PRESS);
            h.assertTrue(front.getHealth() < 20 && back.getHealth() < 20, "Sweep hits both sides");
            done(h, p);
        });
    }

    @GameTest(template = "melee_empty")
    public static void customBlockBreaks(GameTestHelper h) {
        ServerPlayer p = player(h, "break"), defender = player(h, "block");
        defender.setPos(h.absoluteVec(new Vec3(4, 2, 6)));
        defender.setYRot(180);
        defender.setYHeadRot(180);
        h.runAfterDelay(70, () -> {
            MeleeWeaponServerEvents.handleInput(defender, SSMeleeInputPacket.PRESS);
            float health = defender.getHealth();
            defender.hurt(p.damageSources().playerAttack(p), 4);
            h.assertTrue(defender.getHealth() == health, "Blocking stops incoming front damage");
            MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.ATTACK);
            h.assertTrue(defender.getCooldowns().isOnCooldown(ITEMS.get("block")), "Break block disables the melee weapon");
            h.assertTrue(defender.getHealth() < health, "Breaker damages the unblocked defender");
            done(h, p, defender);
        });
    }

    @GameTest(template = "melee_empty")
    public static void vanillaShieldBreaks(GameTestHelper h) {
        ServerPlayer p = player(h, "break"), defender = player(h, "simple");
        defender.setPos(h.absoluteVec(new Vec3(4, 2, 6)));
        defender.setYRot(180);
        defender.setYHeadRot(180);
        defender.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.SHIELD));
        defender.startUsingItem(InteractionHand.OFF_HAND);
        h.runAfterDelay(70, () -> {
            MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.ATTACK);
            h.assertTrue(defender.getCooldowns().isOnCooldown(Items.SHIELD), "Break block disables vanilla shields");
            done(h, p, defender);
        });
    }

    @GameTest(template = "melee_empty")
    public static void parryCountersOnce(GameTestHelper h) {
        ServerPlayer defender = player(h, "parry");
        LivingEntity attacker = target(h, 4, 6);
        h.runAfterDelay(70, () -> {
            MeleeWeaponServerEvents.handleInput(defender, SSMeleeInputPacket.PRESS);
            float health = defender.getHealth();
            defender.hurt(attacker.damageSources().mobAttack(attacker), 4);
            h.assertTrue(defender.getHealth() == health && attacker.getHealth() < 20, "Parry cancels and counters incoming damage");
            done(h, defender);
        });
    }

    @GameTest(template = "melee_empty")
    public static void throwsPreserveInventoryRules(GameTestHelper h) {
        ServerPlayer thrower = player(h, "throw"), quick = player(h, "quick");
        quick.setPos(h.absoluteVec(new Vec3(2, 2, 4)));
        MeleeWeaponServerEvents.handleInput(thrower, SSMeleeInputPacket.PRESS);
        MeleeWeaponServerEvents.handleInput(quick, SSMeleeInputPacket.PRESS);
        h.assertTrue(!quick.getMainHandItem().isEmpty(), "Quick throw keeps the source item");
        h.runAfterDelay(12, () -> {
            MeleeWeaponServerEvents.handleInput(thrower, SSMeleeInputPacket.RELEASE);
            h.assertTrue(thrower.getMainHandItem().isEmpty(), "Charged throw removes one inventory item");
            var projectiles = h.getLevel().getEntitiesOfClass(MeleeProjectileEntity.class, h.getBounds().inflate(32));
            h.assertTrue(projectiles.stream().anyMatch(e -> e.getOwner() == thrower && e.pickup == net.minecraft.world.entity.projectile.AbstractArrow.Pickup.ALLOWED), "Thrown weapon is recoverable");
            h.assertTrue(projectiles.stream().filter(e -> e.getOwner() == quick).allMatch(e -> e.pickup == net.minecraft.world.entity.projectile.AbstractArrow.Pickup.DISALLOWED), "Quick throws cannot duplicate items through pickup");
            done(h, thrower, quick);
        });
    }

    @GameTest(template = "melee_empty")
    public static void cancelledHoldDoesNotAttack(GameTestHelper h) {
        ServerPlayer p = player(h, "mixed");
        LivingEntity target = target(h, 4, 6);
        MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.PRESS);
        MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.CANCEL);
        h.runAfterDelay(15, () -> {
            h.assertTrue(target.getHealth() == 20, "Cancelling a hold never fires the tap or charge");
            done(h, p);
        });
    }

    @GameTest(template = "melee_empty")
    public static void staggerAndFire(GameTestHelper h) {
        ServerPlayer p = player(h, "stagger");
        LivingEntity target = target(h, 4, 6);
        h.runAfterDelay(20, () -> {
            MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.ATTACK);
            h.assertTrue(target.isOnFire(), "Fire is inherent to a successful hit");
            h.assertTrue(MeleeWeaponServerEvents.isStaggered(target), "Stagger delays mob melee attacks");
            done(h, p);
        });
    }

    @GameTest(template = "melee_empty")
    public static void flurryAndDuplicatePackets(GameTestHelper h) {
        ServerPlayer p = player(h, "thrust");
        LivingEntity target = target(h, 4, 6);
        h.runAfterDelay(20, () -> {
            MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.ATTACK);
            h.assertTrue(p.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED) > 1.6, "Successful flurry speeds up recovery");
            float health = target.getHealth();
            target.invulnerableTime = 0;
            MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.ATTACK);
            h.assertTrue(target.getHealth() == health, "Duplicate packets in one tick cannot attack twice");
            h.runAfterDelay(15, () -> {
                p.setYRot(180);
                MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.ATTACK);
                h.assertTrue(p.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED) < 1.6, "A whiff slows flurry recovery");
                p.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ITEMS.get("simple")));
                h.runAfterDelay(2, () -> {
                    h.assertTrue(Math.abs(p.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED) - 1.6) < 0.01, "Flurry must not leak to another weapon");
                    done(h, p);
                });
            });
        });
    }

    @GameTest(template = "melee_empty")
    public static void whipAttachesPullsAndDetaches(GameTestHelper h) {
        ServerPlayer p = player(h, "whip");
        LivingEntity target = target(h, 4, 6);
        MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.PRESS);
        MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.RELEASE);
        h.runAfterDelay(12, () -> {
            var hooks = h.getLevel().getEntitiesOfClass(MeleeProjectileEntity.class, h.getBounds(), e -> e.getOwner() == p);
            h.assertTrue(hooks.size() == 1 && hooks.getFirst().attachedTarget() == target, "Whip must attach to the target");
            MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.PRESS);
            h.runAfterDelay(6, () -> {
                h.assertTrue(target.getDeltaMovement().z < 0, "Holding the latched whip pulls toward the user");
                MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.RELEASE);
                h.assertTrue(!hooks.getFirst().isRemoved(), "Releasing a hold preserves the latch");
                MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.PRESS);
                MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.RELEASE);
                h.assertTrue(hooks.getFirst().isRemoved(), "A quick second tap detaches the whip");
                done(h, p);
            });
        });
    }

    @GameTest(template = "melee_empty")
    public static void formSwitchPersists(GameTestHelper h) {
        ServerPlayer p = player(h, "switch");
        ItemStack stack = p.getMainHandItem();
        MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.PRESS);
        MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.RELEASE);
        ItemStack restored = ItemStack.parseOptional(h.getLevel().registryAccess(), (net.minecraft.nbt.CompoundTag) stack.save(h.getLevel().registryAccess()));
        h.assertTrue(((MeleeWeaponItem) restored.getItem()).getForm(restored).damage() == 8, "Alternate form must survive save/load");
        h.runAfterDelay(12, () -> {
            MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.PRESS);
            h.assertTrue(((MeleeWeaponItem) stack.getItem()).getForm(stack).damage() == 3, "Switch toggles back to base form");
            done(h, p);
        });
    }

    @GameTest(template = "melee_empty")
    public static void landingFiresOnlyOnce(GameTestHelper h) {
        ServerPlayer p = player(h, "fall");
        LivingEntity target = target(h, 4, 6);
        p.setPos(h.absoluteVec(new Vec3(4, 4, 4)));
        p.setOnGround(false);
        p.setDeltaMovement(0, -0.1, 0);
        MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.PRESS);
        h.runAfterDelay(6, () -> {
            h.assertTrue(target.getHealth() == 20, "Fall slam must wait for landing");
            p.setPos(h.absoluteVec(new Vec3(4, 2, 4)));
            p.setOnGround(true);
            p.setDeltaMovement(Vec3.ZERO);
            MeleeWeaponServerEvents.tick(new net.neoforged.neoforge.event.tick.PlayerTickEvent.Post(p));
            h.assertTrue(target.getHealth() < 20 && target.getDeltaMovement().y > 0, "Landing triggers the slam and lift");
            float health = target.getHealth();
            target.invulnerableTime = 0;
            MeleeWeaponServerEvents.tick(new net.neoforged.neoforge.event.tick.PlayerTickEvent.Post(p));
            h.assertTrue(target.getHealth() == health, "Holding after landing cannot repeat the slam");
            done(h, p);
        });
    }

    @GameTest(template = "melee_empty")
    public static void levitateTracksAndCancels(GameTestHelper h) {
        ServerPlayer p = player(h, "levitate");
        // Unlike stationary collision fixtures, keep normal mob movement enabled here.
        LivingEntity target = h.spawn(EntityType.HUSK, 4.0F, 2.0F, 6.0F);
        target.setNoGravity(true);
        h.assertTrue(net.uhhitscam.knightfall.util.MeleeTargeting.ray(p, 12) == target, "Levitation fixture starts under the crosshair");
        double initialY = target.getY();
        MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.PRESS);
        h.runAfterDelay(12, () -> {
            h.assertTrue(target.getY() > initialY, "Levitate moves a target toward the aimed hold position; target="
                    + target.position() + " initialY=" + initialY + " player=" + p.position() + " look=" + p.getLookAngle()
                    + " action=" + p.getMainHandItem().getOrDefault(net.uhhitscam.knightfall.component.ModDataComponentTypes.MELEE_ACTION.get(), 0));
            p.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ITEMS.get("simple")));
            h.runAfterDelay(2, () -> {
                h.assertTrue(target.getHealth() == 20, "Levitation itself does not deal damage");
                done(h, p);
            });
        });
    }

    @GameTest(template = "melee_empty")
    public static void steadyAndSpinContact(GameTestHelper h) {
        ServerPlayer p = player(h, "steady"), spinner = player(h, "spin");
        spinner.setPos(h.absoluteVec(new Vec3(8, 2, 4)));
        LivingEntity spearTarget = target(h, 4, 6), spinTarget = target(h, 8, 6), behind = target(h, 8, 2);
        MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.PRESS);
        MeleeWeaponServerEvents.handleInput(spinner, SSMeleeInputPacket.PRESS);
        h.runAfterDelay(6, () -> {
            h.assertTrue(spearTarget.getHealth() < 20 && spinTarget.getHealth() < 20, "Stationary contact and spinning damage front targets");
            h.assertTrue(behind.getHealth() == 20, "Spin must not turn into a 360-degree sweep");
            done(h, p, spinner);
        });
    }

    @GameTest(template = "melee_empty")
    public static void slashAndShove(GameTestHelper h) {
        ServerPlayer p = player(h, "slash"), shield = player(h, "knockback");
        shield.setPos(h.absoluteVec(new Vec3(8, 2, 4)));
        LivingEntity front = target(h, 4, 6), back = target(h, 4, 2), shove = target(h, 8, 6);
        h.runAfterDelay(20, () -> {
            MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.ATTACK);
            MeleeWeaponServerEvents.handleInput(shield, SSMeleeInputPacket.PRESS);
            h.assertTrue(front.getHealth() < 20 && back.getHealth() == 20, "Slash stays in front");
            h.assertTrue(shove.getHealth() == 20 && shove.getDeltaMovement().z > 0, "Default knockback pushes without damage");
            done(h, p, shield);
        });
    }

    @GameTest(template = "melee_empty")
    public static void chargedBlasterFiresOnce(GameTestHelper h) {
        ServerPlayer p = player(h, "blaster");
        MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.PRESS);
        h.runAfterDelay(11, () -> {
            var bolts = h.getLevel().getEntitiesOfClass(net.uhhitscam.knightfall.entity.custom.BlasterBoltEntity.class,
                    p.getBoundingBox().inflate(64), e -> e.getOwner() == p);
            h.assertTrue(bolts.size() == 1, "A charged melee shot spawns one existing blaster bolt");
            MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.RELEASE);
            done(h, p);
        });
    }

    @GameTest(template = "melee_empty")
    public static void dashChecksTravelPath(GameTestHelper h) {
        ServerPlayer p = player(h, "dash");
        LivingEntity target = target(h, 4, 6);
        h.runAfterDelay(20, () -> {
            MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.PRESS);
            h.assertTrue(p.getDeltaMovement().z > 0, "Dash gives forward movement");
            h.runAfterDelay(5, () -> {
                h.assertTrue(target.getHealth() < 20, "Dash damages a target along the traveled path");
                done(h, p);
            });
        });
    }

    @GameTest(template = "melee_empty")
    public static void chargeRequiresMovement(GameTestHelper h) {
        ServerPlayer p = player(h, "charge");
        LivingEntity target = target(h, 4, 4.9F);
        MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.PRESS);
        h.runAfterDelay(6, () -> {
            h.assertTrue(target.getDeltaMovement().horizontalDistanceSqr() < 0.01, "Stationary hold cannot charge");
            p.setSprinting(true);
            p.setOnGround(true);
            p.setPos(p.position().add(0, 0, 0.15));
            MeleeWeaponServerEvents.tick(new net.neoforged.neoforge.event.tick.PlayerTickEvent.Post(p));
            h.assertTrue(target.getDeltaMovement().z > 0, "Sprint movement activates charge contact");
            done(h, p);
        });
    }

    @GameTest(template = "melee_empty")
    public static void electricHooksAreCallable(GameTestHelper h) {
        ServerPlayer p = player(h, "electric"), whip = player(h, "shockwhip");
        whip.setPos(h.absoluteVec(new Vec3(8, 2, 4)));
        LivingEntity target = target(h, 4, 6), whipped = target(h, 8, 6);
        MeleeWeaponServerEvents.handleInput(whip, SSMeleeInputPacket.PRESS);
        h.runAfterDelay(20, () -> {
            MeleeWeaponServerEvents.handleInput(p, SSMeleeInputPacket.ATTACK);
            h.assertTrue(target.getPersistentData().getBoolean("test_shocked"), "Electric inherent property calls the future effect hook");
            h.assertTrue(whipped.getPersistentData().getBoolean("test_shocked"), "Held whip shock calls the same effect hook");
            done(h, p, whip);
        });
    }

    @GameTest(template = "melee_empty")
    public static void materialBladeContact(GameTestHelper h) {
        ServerPlayer plain = player(h, "block"), resistant = player(h, "resistant"), cortosis = player(h, "cortosis"), attacker = player(h, "saber");
        plain.setPos(h.absoluteVec(new Vec3(2, 2, 4)));
        resistant.setPos(h.absoluteVec(new Vec3(5, 2, 4)));
        cortosis.setPos(h.absoluteVec(new Vec3(8, 2, 4)));
        h.runAfterDelay(70, () -> {
            for (ServerPlayer defender : new ServerPlayer[]{plain, resistant, cortosis}) {
                MeleeWeaponServerEvents.handleInput(defender, SSMeleeInputPacket.PRESS);
                attacker.setPos(defender.position().add(0, 0, 2));
                defender.hurt(attacker.damageSources().playerAttack(attacker), 4);
            }
            h.assertTrue(plain.getHealth() < 20, "Ordinary melee blocking does not resist an active lightsaber");
            h.assertTrue(resistant.getHealth() == 20 && cortosis.getHealth() == 20, "Resistant materials permit blocking");
            var data = attacker.getMainHandItem().get(net.minecraft.core.component.DataComponents.CUSTOM_DATA);
            h.assertTrue(data != null && data.copyTag().getInt("disabled_ticks") == 60, "Cortosis calls the lightsaber shutdown contract");
            done(h, plain, resistant, cortosis, attacker);
        });
    }

    @GameTest(template = "melee_empty")
    public static void expiredParryAndPlayerStagger(GameTestHelper h) {
        ServerPlayer defender = player(h, "parry"), stagger = player(h, "stagger");
        defender.setPos(h.absoluteVec(new Vec3(4, 2, 6)));
        defender.setYRot(180);
        h.runAfterDelay(70, () -> {
            MeleeWeaponServerEvents.handleInput(defender, SSMeleeInputPacket.PRESS);
            h.runAfterDelay(2, () -> {
                MeleeWeaponServerEvents.handleInput(stagger, SSMeleeInputPacket.ATTACK);
                h.assertTrue(defender.getHealth() < 20, "Holding parry cannot renew its expired window");
                h.assertTrue(defender.getAttackStrengthScale(0) == 0, "Stagger resets a player's attack meter");
                done(h, defender, stagger);
            });
        });
    }

    private static final class TestSaber extends Item implements MeleeLightsaber {
        TestSaber() { super(new Item.Properties().stacksTo(1)); }
        public boolean isBladeActive(ItemStack stack, LivingEntity holder) {
            return !stack.has(net.minecraft.core.component.DataComponents.CUSTOM_DATA);
        }
        public void disableBlade(ItemStack stack, LivingEntity holder, int ticks) {
            var tag = new net.minecraft.nbt.CompoundTag();
            tag.putInt("disabled_ticks", ticks);
            stack.set(net.minecraft.core.component.DataComponents.CUSTOM_DATA, net.minecraft.world.item.component.CustomData.of(tag));
        }
    }
}
