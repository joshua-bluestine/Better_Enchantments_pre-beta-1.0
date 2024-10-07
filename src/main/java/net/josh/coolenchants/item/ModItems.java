package net.josh.coolenchants.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.josh.coolenchants.CoolEnchants;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item GAYSHIT = registerItem("gayshit", new Item(new FabricItemSettings()));
    public static final Item PLASTIC = registerItem("plastic", new Item(new FabricItemSettings()));

    private static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(GAYSHIT);
    }
    private static void addItemsToBuildingBlockTabItemGroup(FabricItemGroupEntries entries) {
       entries.add(PLASTIC);
    }

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(CoolEnchants.MOD_ID, name), item);
    }

    public static void registerItems(){
        CoolEnchants.LOGGER.info("Registering Mod Items for " + CoolEnchants.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientTabItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(ModItems::addItemsToBuildingBlockTabItemGroup);
    }
}
