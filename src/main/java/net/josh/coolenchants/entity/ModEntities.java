package net.josh.coolenchants.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.entity.custom.NecromancerEntity;
import net.josh.coolenchants.entity.custom.UndeadDragonEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.josh.coolenchants.CoolEnchants.LOGGER;

public class ModEntities {


    public static EntityType<NecromancerEntity> NECROMANCER;
    public static EntityType<UndeadDragonEntity> UNDEAD_DRAGON;
    public static void registerEntities() {
        NECROMANCER = Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier(CoolEnchants.MOD_ID, "necromancer"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, NecromancerEntity::new)
                        .dimensions(EntityDimensions.fixed(0.6F, 1.95F)) // Zombie dimensions
                        .build()
        );
        UNDEAD_DRAGON = Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier(CoolEnchants.MOD_ID, "undead_dragon"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, UndeadDragonEntity::new)
                        .dimensions(EntityDimensions.fixed(16f, 8f)) // Zombie dimensions
                        .build()
        );

        LOGGER.info("Necromancer entity registered successfully");
    }
}
