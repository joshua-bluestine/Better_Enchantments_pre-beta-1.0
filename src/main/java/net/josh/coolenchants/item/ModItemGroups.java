package net.josh.coolenchants.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup ENCHANT_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(CoolEnchants.MOD_ID, "enchants"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.enchants"))
                    .icon(() -> new ItemStack(ModItems.GAYSHIT)).entries((displayContext, entries) -> {
                    entries.add(ModItems.GAYSHIT);
    }).build());
    public static final ItemGroup SECOND_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(CoolEnchants.MOD_ID, "second"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.second"))
                    .icon(() -> new ItemStack(ModItems.PLASTIC)).entries((displayContext, entries) -> {
                    entries.add(ModItems.PLASTIC);

    }).build());

    public static void registerItemGroups(){
        CoolEnchants.LOGGER.info("Registering Item Groups for " + CoolEnchants.MOD_ID);
    }
}
