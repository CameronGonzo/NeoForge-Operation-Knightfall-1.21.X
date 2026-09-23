package net.uhhitscam.knightfall.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.core.Direction;
import net.minecraft.client.model.Model;
import net.minecraft.util.Unit;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.entity.custom.GrenadeEntity;
import net.uhhitscam.knightfall.item.ModItems;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeDefinition;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeFuseSoundMode;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeVisualState;

public class GrenadeRenderer extends EntityRenderer<GrenadeEntity, GrenadeRenderer.State> {
    private static final float DEFAULT_MODEL_SCALE = 0.8F;
    private static final float THERMAL_IMPLODER_MODEL_SCALE = 0.85F;
    private static final float FIREBOMB_MODEL_SCALE = 0.85F;
    private static final float SONIC_IMPLODER_MODEL_SCALE = 0.75F;
    private static final float CRYOBAN_GRENADE_MODEL_SCALE = 0.55F;
    private static final float BACTA_BOMB_MODEL_SCALE = 0.75F;
    private static final float DIOXIS_GRENADE_MODEL_SCALE = 0.75F;
    private static final GrenadeTextures THERMAL_DETONATOR_TEXTURES = textures("thermal_detonator");
    private static final GrenadeTextures IMPACT_BOMB_TEXTURES = new GrenadeTextures(
            texture("impact_bomb"),
            texture("impact_bomb"),
            texture("impact_bomb_1")
    );
    private static final GrenadeTextures MAGNETIC_THERMAL_DETONATOR_TEXTURES = new GrenadeTextures(
            texture("magnetic_thermal_detonator"),
            texture("magnetic_thermal_detonator"),
            texture("magnetic_thermal_detonator_1")
    );
    private static final GrenadeTextures GRAV_CHARGE_TEXTURES = new GrenadeTextures(
            texture("grav_charge"),
            texture("grav_charge"),
            texture("grav_charge_1")
    );
    private static final GrenadeTextures DETONITE_CHARGE_TEXTURES = new GrenadeTextures(
            texture("detonite_charge"),
            texture("detonite_charge"),
            texture("detonite_charge_1")
    );
    private static final GrenadeTextures BARADIUM_BOMB_TEXTURES = singleTexture("baradium_bomb");
    private static final GrenadeTextures PYRO_DENTON_EXPLOSIVE_TEXTURES = new GrenadeTextures(
            texture("pyro_denton_explosive"),
            texture("pyro_denton_explosive_1"),
            texture("pyro_denton_explosive")
    );
    private static final GrenadeTextures STUNNER_TEXTURES = textures("stunner");
    private static final GrenadeTextures THERMAL_IMPLODER_TEXTURES = singleTexture("thermal_imploder");
    private static final GrenadeTextures SONIC_IMPLODER_TEXTURES = singleTexture("sonic_imploder");
    private static final GrenadeTextures FIREBOMB_TEXTURES = singleTexture("firebomb");
    private static final GrenadeTextures CRYOBAN_GRENADE_TEXTURES = singleTexture("cryoban_grenade");
    private static final GrenadeTextures BACTA_BOMB_TEXTURES = new GrenadeTextures(
            texture("bacta_bomb"),
            texture("bacta_bomb"),
            texture("bacta_bomb_1")
    );
    private static final GrenadeTextures DIOXIS_GRENADE_TEXTURES = new GrenadeTextures(
            texture("dioxis_grenade"),
            texture("dioxis_grenade"),
            texture("dioxis_grenade_1")
    );

    private final ThermalDetonatorModel thermalDetonatorModel;
    private final ImpactBombModel impactBombModel;
    private final MagneticThermalDetonatorModel magneticThermalDetonatorModel;
    private final GravChargeModel gravChargeModel;
    private final DetoniteChargeModel detoniteChargeModel;
    private final BaradiumBombModel baradiumBombModel;
    private final PyroDentonExplosiveModel pyroDentonExplosiveModel;
    private final StunnerModel stunnerModel;
    private final ThermalImploderModel thermalImploderModel;
    private final SonicImploderModel sonicImploderModel;
    private final FirebombModel firebombModel;
    private final CryobanGrenadeModel cryobanGrenadeModel;
    private final BactaBombModel bactaBombModel;
    private final DioxisGrenadeModel dioxisGrenadeModel;
    private final ItemModelResolver itemModelResolver;

    public GrenadeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.thermalDetonatorModel = new ThermalDetonatorModel(
                context.bakeLayer(ModModelLayers.THERMAL_DETONATOR)
        );
        this.impactBombModel = new ImpactBombModel(
                context.bakeLayer(ModModelLayers.IMPACT_BOMB)
        );
        this.magneticThermalDetonatorModel = new MagneticThermalDetonatorModel(
                context.bakeLayer(ModModelLayers.MAGNETIC_THERMAL_DETONATOR)
        );
        this.gravChargeModel = new GravChargeModel(context.bakeLayer(ModModelLayers.GRAV_CHARGE));
        this.detoniteChargeModel = new DetoniteChargeModel(context.bakeLayer(ModModelLayers.DETONITE_CHARGE));
        this.baradiumBombModel = new BaradiumBombModel(context.bakeLayer(ModModelLayers.BARADIUM_BOMB));
        this.pyroDentonExplosiveModel = new PyroDentonExplosiveModel(
                context.bakeLayer(ModModelLayers.PYRO_DENTON_EXPLOSIVE)
        );
        this.stunnerModel = new StunnerModel(context.bakeLayer(ModModelLayers.STUNNER));
        this.thermalImploderModel = new ThermalImploderModel(
                context.bakeLayer(ModModelLayers.THERMAL_IMPLODER)
        );
        this.sonicImploderModel = new SonicImploderModel(
                context.bakeLayer(ModModelLayers.SONIC_IMPLODER)
        );
        this.firebombModel = new FirebombModel(
                context.bakeLayer(ModModelLayers.FIREBOMB)
        );
        this.cryobanGrenadeModel = new CryobanGrenadeModel(
                context.bakeLayer(ModModelLayers.CRYOBAN_GRENADE)
        );
        this.bactaBombModel = new BactaBombModel(context.bakeLayer(ModModelLayers.BACTA_BOMB));
        this.dioxisGrenadeModel = new DioxisGrenadeModel(context.bakeLayer(ModModelLayers.DIOXIS_GRENADE));
        this.itemModelResolver = context.getItemModelResolver();
        this.shadowRadius = 0.15F;
    }

    public static class State extends ProjectileRenderState {
        public Model<Unit> model;
        public float height;
        public float scale;
        public Direction stuckFace;
        public boolean resting;
        public final ItemStackRenderState item = new ItemStackRenderState();
    }

    @Override
    public State createRenderState() { return new State(); }

    @Override
    public void extractRenderState(GrenadeEntity entity, State state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.model = getModel(entity);
        state.height = (float) entity.getBoundingBox().getYsize();
        state.scale = DEFAULT_MODEL_SCALE * getModelScale(entity);
        state.stuckFace = entity.getStuckFace();
        state.resting = entity.isResting();
        state.yaw = Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot());
        state.pitch = Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
        state.texture = getTextureLocation(entity);
        itemModelResolver.updateForNonLiving(state.item, entity.getItem(), ItemDisplayContext.GROUND, entity);
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();
        if (state.model == null) {
            poseStack.mulPose(camera.orientation);
            state.item.submit(poseStack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        } else {
            poseStack.translate(0.0F, state.height * 0.5F, 0.0F);
            poseStack.scale(state.scale, -state.scale, state.scale);
            if (state.stuckFace != null) {
                applyStuckRotation(poseStack, state.stuckFace, state.yaw);
            } else {
                poseStack.mulPose(Axis.YP.rotationDegrees(state.yaw));
                poseStack.mulPose(Axis.XP.rotationDegrees(state.resting ? 0.0F : -state.pitch));
            }
            collector.submitModelPart(state.model.root(), poseStack, RenderTypes.entityCutout(state.texture),
                    state.lightCoords, OverlayTexture.NO_OVERLAY, null);
        }
        poseStack.popPose();
        super.submit(state, poseStack, collector, camera);
    }

    public Identifier getTextureLocation(GrenadeEntity entity) {
        GrenadeTextures textures = getTextures(entity);
        if (textures == null) {
            return THERMAL_DETONATOR_TEXTURES.base();
        }

        GrenadeDefinition definition = entity.getGrenadeDefinition();
        if (definition == null) {
            return textures.base();
        }

        if (definition.remoteProfile() != null) {
            if (entity.isRemoteDetonationActivated()) {
                return textures.active();
            }
            return entity.isBeepFlashActive() ? textures.beep() : textures.base();
        }

        if (definition.fuseSoundMode() == GrenadeFuseSoundMode.REPEATED_AFTER_THROW) {
            return entity.isBeepFlashActive() ? textures.beep() : textures.active();
        }

        return switch (GrenadeVisualState.forThrownGrenade(
                definition,
                entity.getFuseTicks(),
                entity.isFuseRunning()
        )) {
            case INACTIVE -> textures.base();
            case ACTIVE -> textures.active();
            case BEEP -> textures.beep();
        };
    }

    private Model<Unit> getModel(GrenadeEntity entity) {
        if (entity.getItem().is(ModItems.THERMAL_DETONATOR.get())) {
            return thermalDetonatorModel;
        }
        if (entity.getItem().is(ModItems.IMPACT_BOMB.get())) {
            return impactBombModel;
        }
        if (entity.getItem().is(ModItems.MAGNETIC_THERMAL_DETONATOR.get())) {
            return magneticThermalDetonatorModel;
        }
        if (entity.getItem().is(ModItems.GRAV_CHARGE.get())) {
            return gravChargeModel;
        }
        if (entity.getItem().is(ModItems.DETONITE_CHARGE.get())) {
            return detoniteChargeModel;
        }
        if (entity.getItem().is(ModItems.BARADIUM_BOMB.get())) {
            return baradiumBombModel;
        }
        if (entity.getItem().is(ModItems.PYRO_DENTON_EXPLOSIVE.get())) {
            return pyroDentonExplosiveModel;
        }
        if (entity.getItem().is(ModItems.STUNNER.get())) {
            return stunnerModel;
        }
        if (entity.getItem().is(ModItems.THERMAL_IMPLODER.get())) {
            return thermalImploderModel;
        }
        if (entity.getItem().is(ModItems.SONIC_IMPLODER.get())) {
            return sonicImploderModel;
        }
        if (entity.getItem().is(ModItems.FIREBOMB.get())) {
            return firebombModel;
        }
        if (entity.getItem().is(ModItems.CRYOBAN_GRENADE.get())) {
            return cryobanGrenadeModel;
        }
        if (entity.getItem().is(ModItems.BACTA_BOMB.get())) {
            return bactaBombModel;
        }
        if (entity.getItem().is(ModItems.DIOXIS_GRENADE.get())) {
            return dioxisGrenadeModel;
        }
        return null;
    }

    private static GrenadeTextures getTextures(GrenadeEntity entity) {
        if (entity.getItem().is(ModItems.THERMAL_DETONATOR.get())) {
            return THERMAL_DETONATOR_TEXTURES;
        }
        if (entity.getItem().is(ModItems.IMPACT_BOMB.get())) {
            return IMPACT_BOMB_TEXTURES;
        }
        if (entity.getItem().is(ModItems.MAGNETIC_THERMAL_DETONATOR.get())) {
            return MAGNETIC_THERMAL_DETONATOR_TEXTURES;
        }
        if (entity.getItem().is(ModItems.GRAV_CHARGE.get())) {
            return GRAV_CHARGE_TEXTURES;
        }
        if (entity.getItem().is(ModItems.DETONITE_CHARGE.get())) {
            return DETONITE_CHARGE_TEXTURES;
        }
        if (entity.getItem().is(ModItems.BARADIUM_BOMB.get())) {
            return BARADIUM_BOMB_TEXTURES;
        }
        if (entity.getItem().is(ModItems.PYRO_DENTON_EXPLOSIVE.get())) {
            return PYRO_DENTON_EXPLOSIVE_TEXTURES;
        }
        if (entity.getItem().is(ModItems.STUNNER.get())) {
            return STUNNER_TEXTURES;
        }
        if (entity.getItem().is(ModItems.THERMAL_IMPLODER.get())) {
            return THERMAL_IMPLODER_TEXTURES;
        }
        if (entity.getItem().is(ModItems.SONIC_IMPLODER.get())) {
            return SONIC_IMPLODER_TEXTURES;
        }
        if (entity.getItem().is(ModItems.FIREBOMB.get())) {
            return FIREBOMB_TEXTURES;
        }
        if (entity.getItem().is(ModItems.CRYOBAN_GRENADE.get())) {
            return CRYOBAN_GRENADE_TEXTURES;
        }
        if (entity.getItem().is(ModItems.BACTA_BOMB.get())) {
            return BACTA_BOMB_TEXTURES;
        }
        if (entity.getItem().is(ModItems.DIOXIS_GRENADE.get())) {
            return DIOXIS_GRENADE_TEXTURES;
        }
        return null;
    }

    private static void applyStuckRotation(PoseStack poseStack, Direction direction, float yaw) {
        switch (direction) {
            case UP -> {
            }
            case DOWN -> poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
            case NORTH -> poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            case SOUTH -> poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            case EAST -> poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
            case WEST -> poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
        }
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
    }

    private static float getModelScale(GrenadeEntity entity) {
        if (entity.getItem().is(ModItems.THERMAL_IMPLODER.get())) {
            return THERMAL_IMPLODER_MODEL_SCALE;
        }
        if (entity.getItem().is(ModItems.FIREBOMB.get())) {
            return FIREBOMB_MODEL_SCALE;
        }
        if (entity.getItem().is(ModItems.SONIC_IMPLODER.get())) {
            return SONIC_IMPLODER_MODEL_SCALE;
        }
        if (entity.getItem().is(ModItems.CRYOBAN_GRENADE.get())) {
            return CRYOBAN_GRENADE_MODEL_SCALE;
        }
        if (entity.getItem().is(ModItems.BACTA_BOMB.get())) {
            return BACTA_BOMB_MODEL_SCALE;
        }
        if (entity.getItem().is(ModItems.DIOXIS_GRENADE.get())) {
            return DIOXIS_GRENADE_MODEL_SCALE;
        }
        return 1.0F;
    }

    private static GrenadeTextures textures(String name) {
        return new GrenadeTextures(
                texture(name),
                texture(name + "_1"),
                texture(name + "_2")
        );
    }

    private static GrenadeTextures singleTexture(String name) {
        Identifier base = texture(name);
        return new GrenadeTextures(base, base, base);
    }

    private static Identifier texture(String name) {
        return Identifier.fromNamespaceAndPath(
                OperationKnightfall.MODID,
                "textures/entity/" + name + ".png"
        );
    }

    private record GrenadeTextures(
            Identifier base,
            Identifier active,
            Identifier beep
    ) {
    }
}
