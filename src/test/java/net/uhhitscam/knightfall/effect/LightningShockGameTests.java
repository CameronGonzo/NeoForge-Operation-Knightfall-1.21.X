package net.uhhitscam.knightfall.effect;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.effect.custom.LightningShockEffect;
import net.uhhitscam.knightfall.entity.ModEntities;
import net.uhhitscam.knightfall.entity.custom.GrenadeEntity;
import net.uhhitscam.knightfall.item.ModItems;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeDetonationContext;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeItem;

import java.util.Map;
import java.util.function.Consumer;

@EventBusSubscriber(modid = OperationKnightfall.MODID)
public final class LightningShockGameTests {
    private static final Map<String, Consumer<GameTestHelper>> TESTS = Map.of(
            "shock_ion_radius", helper -> grenadeRadius(helper, ModItems.ION_GRENADE.get(), true),
            "shock_smart_ion_radius", helper -> grenadeRadius(helper, ModItems.SMART_ION_GRENADE.get(), true),
            "shock_droid_popper_radius", helper -> grenadeRadius(helper, ModItems.DROID_POPPER.get(), false),
            "shock_movement_expiry", LightningShockGameTests::movementAndExpiry,
            "shock_interactions", LightningShockGameTests::interactions);

    private static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(OperationKnightfall.MODID, name);
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        if (!Boolean.getBoolean("knightfall.gameTests")) return;
        var environment = event.registerEnvironment(id("lightning_shock"));
        TESTS.forEach((name, test) -> event.registerTest(id(name),
                new FunctionGameTestInstance(ResourceKey.create(Registries.TEST_FUNCTION, id(name)),
                        new TestData<>(environment, id("melee_empty"), 100, 0, true))));
    }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        if (!Boolean.getBoolean("knightfall.gameTests")) return;
        event.register(Registries.TEST_FUNCTION,
                registry -> TESTS.forEach((name, test) -> registry.register(id(name), test)));
    }

    private static void grenadeRadius(GameTestHelper helper, Item item, boolean damagesOrganics) {
        var inside = helper.spawn(EntityTypes.HUSK, 4, 2, 4);
        var outside = helper.spawn(EntityTypes.HUSK, 12, 2, 4);
        inside.setNoAi(true);
        outside.setNoAi(true);
        inside.setNoGravity(true);
        outside.setNoGravity(true);
        float health = inside.getHealth();
        // A shocked thrower must not cancel an independently detonating grenade's damage.
        LightningShockEffect.apply(outside, 1);
        var grenade = new GrenadeEntity(ModEntities.GRENADE.get(), helper.getLevel(), outside);
        grenade.setItem(new ItemStack(item));
        grenade.setPos(inside.position());
        var definition = ((GrenadeItem) item).getDefinition();
        definition.effect().detonate(new GrenadeDetonationContext(
                helper.getLevel(), grenade, outside, definition, grenade.position()));
        var effect = inside.getEffect(ModEffects.LIGHTNING_SHOCK_EFFECT);
        helper.assertTrue(effect != null && effect.getDuration() == 60, "Inside target receives configured shock duration");
        helper.assertTrue(outside.getEffect(ModEffects.LIGHTNING_SHOCK_EFFECT).getDuration() == 1,
                "Explosion does not refresh shock outside its radius");
        helper.assertTrue(damagesOrganics ? inside.getHealth() < health : inside.getHealth() == health,
                "Ion retains base damage; Droid Popper does not damage organic mobs");
        helper.succeed();
    }

    private static void movementAndExpiry(GameTestHelper helper) {
        var mob = helper.spawn(EntityTypes.HUSK, 4, 2, 4);
        mob.setNoAi(true);
        double speed = mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
        LightningShockEffect.apply(mob, 8);
        helper.assertTrue(Math.abs(mob.getAttributeValue(Attributes.MOVEMENT_SPEED) - speed * 0.5) < 0.00001,
                "Shock halves movement speed");
        mob.jumpFromGround();
        helper.assertTrue(mob.getDeltaMovement().y > 0, "Shock still permits jumping");
        helper.assertTrue(EffectActionRules.blocksActions(mob), "Shock blocks actions while active");
        helper.runAfterDelay(12, () -> {
            helper.assertTrue(!EffectActionRules.blocksActions(mob), "Actions recover when shock expires");
            helper.assertTrue(Math.abs(mob.getAttributeValue(Attributes.MOVEMENT_SPEED) - speed) < 0.00001,
                    "Movement speed recovers when shock expires");
            helper.succeed();
        });
    }

    private static void interactions(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var target = helper.spawn(EntityTypes.HUSK, 4, 2, 4);
        var pos = target.blockPosition();
        LightningShockEffect.apply(player, 20);
        helper.assertTrue(NeoForge.EVENT_BUS.post(new AttackEntityEvent(player, target)).isCanceled(), "Attacking is blocked");
        helper.assertTrue(NeoForge.EVENT_BUS.post(new PlayerInteractEvent.EntityInteract(
                player, InteractionHand.MAIN_HAND, target, Vec3.ZERO)).isCanceled(), "Mob interaction is blocked");
        helper.assertTrue(NeoForge.EVENT_BUS.post(new PlayerInteractEvent.RightClickBlock(
                player, InteractionHand.MAIN_HAND, pos,
                new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false))).isCanceled(), "Block interaction is blocked");
        helper.assertTrue(NeoForge.EVENT_BUS.post(new PlayerInteractEvent.RightClickItem(
                player, InteractionHand.MAIN_HAND)).isCanceled(), "Item interaction is blocked");
        helper.assertTrue(NeoForge.EVENT_BUS.post(new BreakBlockEvent(
                helper.getLevel(), pos, Blocks.STONE.defaultBlockState(), player)).isCanceled(), "Block breaking is blocked");
        helper.assertTrue(NeoForge.EVENT_BUS.post(new LivingEntityUseItemEvent.Start(
                player, new ItemStack(ModItems.THERMAL_DETONATOR.get()), InteractionHand.MAIN_HAND, 80)).isCanceled(),
                "Starting held item use is blocked");
        player.removeEffect(ModEffects.LIGHTNING_SHOCK_EFFECT);
        helper.assertTrue(!NeoForge.EVENT_BUS.post(new AttackEntityEvent(player, target)).isCanceled(), "Attacking recovers after shock");
        helper.succeed();
    }
}
