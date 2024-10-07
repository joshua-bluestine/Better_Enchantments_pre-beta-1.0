package net.josh.coolenchants.mixin;

import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.apache.logging.log4j.core.jmx.Server;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;

import java.util.Objects;
import java.util.UUID;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    //public double counter = 0;
//...................................EXTRA HEARTS.............................
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo info) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        ModUtils.health = getMaxEnchantmentLevel(player) * 2;
        if (player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getValue() >
                ModUtils.health + player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue()) {

            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).clearModifiers();

        } else if (player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getValue() <
                ModUtils.health + player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue()) {

            ModUtils.health += (player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getValue() -
                    player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue());
            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).clearModifiers();
            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).addPersistentModifier(new EntityAttributeModifier
                    ("Health Boost", ModUtils.health,
                            EntityAttributeModifier.Operation.ADDITION));
        }
    }
    private int getMaxEnchantmentLevel(PlayerEntity player) {
        int maxLevel = 0;
        for (ItemStack stack : player.getArmorItems()) {
            int level = EnchantmentHelper.getLevel(ModEnchantments.EXTRA_HEALTH, stack);
            maxLevel += level;
        }
        return maxLevel;
    }
}
