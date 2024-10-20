package net.josh.coolenchants;

import net.josh.coolenchants.structures.SkyStructures;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.StructureType;

public class CEStructures {

    public static StructureType<SkyStructures> SKY_STRUCTURES;

    /**
     * Registers the structure itself and sets what its path is. In this case, the
     * structure will have the Identifier of structure_tutorial:sky_structures.
     *
     * It is always a good idea to register your Structures so that other mods and datapacks can
     * use them too directly from the registries. It's great for mod/datapacks compatibility.
     */
    public static void registerStructureFeatures() {
        SKY_STRUCTURES = Registry.register(Registries.STRUCTURE_TYPE,
                new Identifier(CoolEnchants.MOD_ID, "sky_structures"), () -> SkyStructures.CODEC);
    }
}
