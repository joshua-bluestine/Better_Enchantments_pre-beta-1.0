package net.josh.coolenchants;

import com.google.gson.JsonElement;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.josh.coolenchants.block.ModBlocks;
import net.josh.coolenchants.command.CommandGiveAstralHelmet;
import net.josh.coolenchants.command.CommandGiveAstralHelmet2;
import net.josh.coolenchants.command.CommandSummonMobs;
import net.josh.coolenchants.effect.ModEffects;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.josh.coolenchants.entity.ModEntities;
import net.josh.coolenchants.entity.custom.NecromancerEntity;
import net.josh.coolenchants.entity.custom.UndeadDragonEntity;
import net.josh.coolenchants.event.*;
//import net.josh.coolenchants.event.PlayerTickHandler;
import net.josh.coolenchants.item.ModItemGroups;
import net.josh.coolenchants.item.ModItems;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.data.client.BlockStateSupplier;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.function.Supplier;

import static net.josh.coolenchants.world.dimension.ModDimensions.ASTRAL_PLANE_LEVEL_KEY;

public class CoolEnchants implements ModInitializer {
	public static final String MOD_ID = "coolenchants";
	public static boolean jumping = false;
	public static final Logger LOGGER = LoggerFactory.getLogger("coolenchants");

	public static final Identifier C2S_DO_DOUBLEJUMP = new Identifier("doublejump", "request_effects");
	public static final Identifier S2C_PLAY_EFFECTS_PACKET_ID = new Identifier("doublejump", "play_effects");



	public static int bowHit = 0;
	public static final Identifier DM_EFFECT = new Identifier("deathmark", "request_effects");
	public static final Identifier DM_PLAY_EFFECTS_PACKET_ID = new Identifier("deathmark", "play_effects");
	public static final Identifier SB_EFFECT = new Identifier("slimeboots", "request_effects");
	public static final Identifier SB_PLAY_EFFECTS_PACKET_ID = new Identifier("slimeboots", "play_effects");
	public static BlockPos placeholder;
	public static NbtList some;
	public static ArrayList<PersistentProjectileEntity> pers = new ArrayList<>();
	private CustomBlockStateModelGenerator blockStateModelGenerator;


	@Override
	public void onInitialize() {
		CEStructures.registerStructureFeatures();
		CommandRegistrationCallback.EVENT.register(this::registerCommands);
		if (some == null || some.isEmpty()){
			some = new NbtList();
		}

		CustomPortalBuilder.beginPortal()
				.frameBlock(Blocks.DIAMOND_BLOCK)
				.lightWithItem(Items.ENDER_EYE)
				.destDimID(Identifier.of("coolenchants", "astral_plane"))
				.tintColor(45,65,101)
				.registerPortal();

		RegisterTrades.registerTrades();
		ModEnchantments.registerModEnchantments();
		//Registry.register(Registries.STATUS_EFFECT, new Identifier(CoolEnchants.MOD_ID, "creeper_null_effect"), CREEPER_NULL_EFFECT);
		ModEffects.registerModEffects();
		ModBlocks.registerBlocks();
		ModEntities.registerEntities();
		FabricDefaultAttributeRegistry.register(ModEntities.NECROMANCER, NecromancerEntity.createIronGolemAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.UNDEAD_DRAGON, UndeadDragonEntity.createIronGolemAttributes());

//......................DISCONTINUED SHIT...................
		ModItems.registerItems();
		ModItemGroups.registerItemGroups();
//............................................................
		//LOGGER.info("Hello Fabric world!");

		ModLootTableModifiers.modifyLootTablesWood2();
		ModLootTableModifiers.modifyLootTablesMining();
		ModLootTableModifiers.modifyLootTablesChests();
		blockStateModelGenerator = new CustomBlockStateModelGenerator(
				this::registerBlockStates, this::registerModels, this::exemptSimpleItemModels);


		blockStateModelGenerator.registerCooledLavaBlock();


		//PlayerBlockBreakEvents.BEFORE.register(new BlockBreakHandler());
		PlayerBlockBreakEvents.AFTER.register(new BlockBreakHandler2());
		ClimbingHandler.register();
		ServerPlayerEvents.COPY_FROM.register(new PlayerCopyHandler());


		ServerPlayNetworking.registerGlobalReceiver(C2S_DO_DOUBLEJUMP,
				(server, player, handler, buf, responseSender) -> {
					PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
					passedData.writeUuid(buf.readUuid());

					server.execute(() -> {
						PlayerLookup.tracking(player).forEach(p -> {
							if (p != player) {
								ServerPlayNetworking.send(p, S2C_PLAY_EFFECTS_PACKET_ID, passedData);
							}
						});
					});
				});

		ServerPlayNetworking.registerGlobalReceiver(DM_EFFECT,
				(server, player, handler, buf, responseSender) -> {
					PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
					passedData.writeUuid(buf.readUuid());

					server.execute(() -> {
						PlayerLookup.tracking(player).forEach(p -> {
							if (p != player) {
								ServerPlayNetworking.send(p, DM_PLAY_EFFECTS_PACKET_ID, passedData);
							}
						});
					});
				});
		ServerPlayNetworking.registerGlobalReceiver(SB_EFFECT,
				(server, player, handler, buf, responseSender) -> {
					PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
					passedData.writeUuid(buf.readUuid());

					server.execute(() -> {
						PlayerLookup.tracking(player).forEach(p -> {
							if (p != player) {
								ServerPlayNetworking.send(p, SB_PLAY_EFFECTS_PACKET_ID, passedData);
							}
						});
					});
				});

	}



	private void registerBlockStates(BlockStateSupplier blockStateSupplier) {
	}

	private void registerModels(Identifier identifier, Supplier<JsonElement> supplier) {
	}

	private void exemptSimpleItemModels(Item item) {
	}
	private void registerCommands(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
		serverCommandSourceCommandDispatcher.register(CommandManager.literal("create")
				.then(CommandManager.argument("type", StringArgumentType.string())
						.then(CommandManager.argument("number", IntegerArgumentType.integer())
						.executes(context -> CommandSummonMobs.summon(context, StringArgumentType.getString(context, "type"), IntegerArgumentType.getInteger(context, "number"))))));

		serverCommandSourceCommandDispatcher.register(CommandManager.literal("cool")
				.then(CommandManager.argument("name", StringArgumentType.string())
				.then(CommandManager.argument("level", IntegerArgumentType.integer(1, 5))
						.executes(context -> CommandGiveAstralHelmet.giveAstralHelmet(context, StringArgumentType.getString(context, "name"), IntegerArgumentType.getInteger(context, "level"))))));

		serverCommandSourceCommandDispatcher.register(CommandManager.literal("cool")
				.then(CommandManager.argument("name", StringArgumentType.string())
				.then(CommandManager.argument("toolname", StringArgumentType.string())
				.then(CommandManager.argument("level", IntegerArgumentType.integer(1, 5))
						.executes(context -> CommandGiveAstralHelmet2.giveAstralHelmet(context, StringArgumentType.getString(context, "toolname"), StringArgumentType.getString(context, "name"), IntegerArgumentType.getInteger(context, "level")))))));




	}
}
