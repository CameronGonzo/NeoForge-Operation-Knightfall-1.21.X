package net.uhhitscam.knightfall.util;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.knightfall.sound.ModSounds;

public final class BlasterImpactSoundUtil {
    private BlasterImpactSoundUtil() {
    }

    public static void playBlockImpactSound(Level level, BlockHitResult blockHitResult) {
        BlockState blockState = level.getBlockState(blockHitResult.getBlockPos());
        Vec3 location = blockHitResult.getLocation();

        SoundEvent impactSound = getImpactSound(blockState);

        level.playSound(
                null,
                location.x,
                location.y,
                location.z,
                impactSound,
                SoundSource.NEUTRAL,
                0.45F,
                0.9F + level.random.nextFloat() * 0.2F
        );
    }

    private static SoundEvent getImpactSound(BlockState blockState) {
        if (isDirt(blockState)) {
            return ModSounds.BLASTER_IMPACT_DIRT.get();
        }

        if (isFoliage(blockState)) {
            return ModSounds.BLASTER_IMPACT_FOLIAGE.get();
        }

        if (isGlass(blockState)) {
            return ModSounds.BLASTER_IMPACT_GLASS.get();
        }

        if (isMetal(blockState)) {
            return ModSounds.BLASTER_IMPACT_METAL.get();
        }

        if (isMoist(blockState)) {
            return ModSounds.BLASTER_IMPACT_MOIST.get();
        }

        if (isSand(blockState)) {
            return ModSounds.BLASTER_IMPACT_SAND.get();
        }

        if (isStone(blockState)) {
            return ModSounds.BLASTER_IMPACT_STONE.get();
        }

        if (isWood(blockState)) {
            return ModSounds.BLASTER_IMPACT_WOOD.get();
        }

        return ModSounds.BLASTER_IMPACT_DEFAULT.get();
    }

    private static boolean isDirt(BlockState blockState) {
        return blockState.is(ModTags.Blocks.BLASTER_IMPACT_DIRT);
    }

    private static boolean isFoliage(BlockState blockState) {
        return blockState.is(BlockTags.LEAVES)
                || blockState.is(BlockTags.FLOWERS)
                || blockState.is(ModTags.Blocks.BLASTER_IMPACT_FOLIAGE);
    }

    private static boolean isGlass(BlockState blockState) {
        return blockState.is(ModTags.Blocks.BLASTER_IMPACT_GLASS)
                || blockState.is(BlockTags.ICE);
    }

    public static boolean isBreakableGlass(BlockState blockState) {
        return blockState.is(ModTags.Blocks.BLASTER_BREAKABLE_GLASS);
    }

    private static boolean isMetal(BlockState blockState) {
        return blockState.is(ModTags.Blocks.BLASTER_IMPACT_METAL);
    }

    private static boolean isMoist(BlockState blockState) {
        return blockState.is(ModTags.Blocks.BLASTER_IMPACT_MOIST);
    }

    private static boolean isSand(BlockState blockState) {
        return blockState.is(ModTags.Blocks.BLASTER_IMPACT_SAND);
    }

    private static boolean isStone(BlockState blockState) {
        return blockState.is(BlockTags.BASE_STONE_OVERWORLD)
                || blockState.is(BlockTags.BASE_STONE_NETHER)
                || blockState.is(ModTags.Blocks.BLASTER_IMPACT_STONE);
    }

    private static boolean isWood(BlockState blockState) {
        return blockState.is(BlockTags.LOGS)
                || blockState.is(BlockTags.PLANKS)
                || blockState.is(BlockTags.WOODEN_DOORS)
                || blockState.is(BlockTags.WOODEN_TRAPDOORS)
                || blockState.is(BlockTags.WOODEN_FENCES)
                || blockState.is(ModTags.Blocks.BLASTER_IMPACT_WOOD);
    }
}