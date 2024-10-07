package net.josh.coolenchants.command;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class CommandGiveAstralHelmet {

    public static int giveAstralHelmet(CommandContext<ServerCommandSource> context, String name, int level) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player == null) {
            context.getSource().sendError(Text.literal("This command can only be executed by a player."));
            return 1; // Return a non-zero value to indicate failure
        }

        ItemStack item = new ItemStack(Items.
                DIAMOND_BOOTS);
        Enchantment astralProjectEnchantment =
                Registries.ENCHANTMENT.get(new Identifier("coolenchants",
                        name));

        if (astralProjectEnchantment == null) {
            context.getSource().sendError(Text.literal("The enchantment does not exist."));
            return 1; // Return a non-zero value to indicate failure
        }

        // Add the custom enchantment to the helmet
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(astralProjectEnchantment, level);
        EnchantmentHelper.set(enchantments, item);

        // Give the helmet to the player
        boolean added = player.getInventory().insertStack(item);

        if (!added) {
            context.getSource().sendError(Text.literal("Could not add the enchanted item to your inventory."));
            return 1; // Return a non-zero value to indicate failure
        }

        context.getSource().sendFeedback(() -> Text.literal("You have been given an enchanted item!"), false);
        return 0; // Return zero to indicate success
    }
}
