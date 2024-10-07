package net.josh.coolenchants;

import net.fabricmc.api.ModInitializer;
import net.josh.coolenchants.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoolEnchants implements ModInitializer {
	public static final String MOD_ID = "coolenchants";
	public static final Logger LOGGER = LoggerFactory.getLogger("coolenchants");

	@Override
	public void onInitialize() {
		ModItems.registerItems();
		LOGGER.info("Hello Fabric world!");
	}
}
