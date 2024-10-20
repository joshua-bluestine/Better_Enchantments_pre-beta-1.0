package net.josh.coolenchants.command;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class CommandSummonMobs {

    public static int summon(CommandContext<ServerCommandSource> context, String type, int number) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player == null) {
            return 1;
        }
        EntityType<?> entityType = Registries.ENTITY_TYPE.get(new Identifier(type));

        for (int i = 0; i < number; i++) {
            entityType.spawn(player.getServerWorld(), player.getBlockPos(), SpawnReason.COMMAND);
        }
        return 0;
    }
}
