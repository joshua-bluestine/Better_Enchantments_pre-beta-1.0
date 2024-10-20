package net.josh.coolenchants.world.dimension;

import net.josh.coolenchants.CoolEnchants;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;

import java.util.OptionalLong;

public class ModDimensions {
    public static final RegistryKey<DimensionOptions> ASTRAL_PLANE_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
            new Identifier(CoolEnchants.MOD_ID, "astral_plane"));
    public static final RegistryKey<World> ASTRAL_PLANE_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD,
            new Identifier(CoolEnchants.MOD_ID, "astral_plane"));
    public static final RegistryKey<DimensionType> ASTRAL_PLANE_DIM_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            new Identifier(CoolEnchants.MOD_ID, "astral_plane_type"));
    public static void bootstrapType(Registerable<DimensionType> context) {

        context.register(ASTRAL_PLANE_DIM_TYPE, new DimensionType(
                OptionalLong.of(20000), // fixedTime
                false, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                true, // natural
                16.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                -2, // minY
                384, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                DimensionTypes.OVERWORLD_ID, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, UniformIntProvider.create(0, 0), 0)));

    }
}