package net.josh.coolenchants.mixin;

import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.client.font.UnihexFont;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
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

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    int counter = 0;
    int counter2 = 0;
    private static int counter3 = 0;
    private static int arrows = 0;
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo info) {
        PlayerEntity player = (PlayerEntity) (Object) this;

//........................BARRAGE PART 2 (DELAYED FIRING MECHANISM)...........................
        if (ModUtils.bowshot && counter3 > 2){
            if (arrows < EnchantmentHelper.getLevel(ModEnchantments.BARRAGE, player.getMainHandStack())) {
                player.getWorld().spawnEntity(CoolEnchants.pers.get(arrows));
                arrows++;
            } else {
                arrows = 0;
                counter3 = 0;
                ModUtils.bowshot = false;
            }
        } else {
            counter3++;
        }

//.....................WITHER IMMUNE....................................
            if (ModUtils.isWearingEnchantedArmor(player,ModEnchantments.WITHER_IMMUNE)){
                if (player.hasStatusEffect(StatusEffects.WITHER)){
                    player.removeStatusEffect(StatusEffects.WITHER);
                }
            }
//.....................REGEN......................................
            if (ModUtils.isWearingEnchantedArmor(player, ModEnchantments.REGEN)) {
                //player.sendMessage(Text.literal(String.valueOf(counter)));
                if (counter >= (400/getMaxEnchantmentLevel(player))) {

                    if (player.getHealth() < player.getMaxHealth()) {
                        player.setHealth(player.getHealth()+1);
                    }
                    counter = 0;
                }
                counter++;
            }
//...........................PHOTO.................................
            if (ModUtils.isWearingEnchantedHelmet(player, ModEnchantments.PHOTO)) {

                if (EnchantmentHelper.getLevel(ModEnchantments.PHOTO, player.getEquippedStack(EquipmentSlot.HEAD)) == 1) {
                    if (player.getWorld().getRegistryKey().getValue().hashCode() == World.OVERWORLD.getValue().hashCode() &&
                            player.getWorld().getTime() < 12000) {
                        if (player.getHungerManager().getFoodLevel() < 20 && counter2 >= 200) {
                            // Increment the player's food level
                            player.getHungerManager().add(1, 0.3f);
                            counter2 = 0;
                        }
                        counter2++;
                    }
                    } else if (EnchantmentHelper.getLevel(ModEnchantments.PHOTO, player.getEquippedStack(EquipmentSlot.HEAD)) == 2) {
                        if (player.getWorld().getRegistryKey().getValue().hashCode() == World.OVERWORLD.getValue().hashCode()) {
                            if (player.getHungerManager().getFoodLevel() < 20 && counter2 >= 200) {
                               // Increment the player's food level
                              player.getHungerManager().add(1, 0.3f);
                              counter2 = 0;
                          }
                          counter2++;
                        }
                    } else {
                        if (player.getHungerManager().getFoodLevel() < 20 && counter2 >= 200) {
                            // Increment the player's food level
                            player.getHungerManager().add(1, 0.3f);
                            counter2 = 0;
                        }
                        counter2++;
                    }
                }

    }
    private int getMaxEnchantmentLevel(PlayerEntity player) {
        int maxLevel = 0;
        for (ItemStack stack : player.getArmorItems()) {
            int level = EnchantmentHelper.getLevel(ModEnchantments.REGEN, stack);
            maxLevel += level;
        }
        return maxLevel;
    }
}
