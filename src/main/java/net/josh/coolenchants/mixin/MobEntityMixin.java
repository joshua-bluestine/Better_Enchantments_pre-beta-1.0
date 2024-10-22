package net.josh.coolenchants.mixin;

import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static net.minecraft.entity.mob.MobEntity.getEquipmentForSlot;

@Mixin(MobEntity.class)
public class MobEntityMixin {


//.............................CHALLENGE................................
    @Overwrite
    public void enchantMainHandItem(Random random, float power) {
        MobEntity jones = (MobEntity) (Object) this;
        float modifier = 0.25F;
        if (ModUtils.challenge){
            modifier *= (ModUtils.challengeLevel + 1);
        }
        if (!jones.getMainHandStack().isEmpty() && random.nextFloat() < modifier * power) {
            jones.equipStack(EquipmentSlot.MAINHAND, EnchantmentHelper.enchant(
                    random,
                    jones.getMainHandStack(),
                    (int)(5.0F + power * (float)random.nextInt(18)),
                    false));
        }

    }
    @Overwrite
    public void initEquipment(Random random, LocalDifficulty localDifficulty) {
        MobEntity jones = (MobEntity) (Object) this;
        float modifier = 0.15F;
        int modifier2 = 2;
        float modifier3 = 0.095F;
        int modifier4 = 0;
        if (ModUtils.challenge){
            modifier *= (ModUtils.challengeLevel + 3);
            modifier2 *= (ModUtils.challengeLevel + 1);
            modifier3 *= (ModUtils.challengeLevel + 1);
            modifier4 = (ModUtils.challengeLevel + 1);
        }
        if (random.nextFloat() < modifier * localDifficulty.getClampedLocalDifficulty()) {
            int i = random.nextInt(modifier2);
            float f = jones.getWorld().getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
            if (random.nextFloat() < modifier3) {
                ++i;
            }

            if (random.nextFloat() < modifier3) {
                ++i;
            }

            if (random.nextFloat() < modifier3) {
                ++i;
            }

            boolean bl = true;
            EquipmentSlot[] var6 = EquipmentSlot.values();
            int var7 = var6.length;
            if (jones instanceof ZombieEntity && modifier4 > 0) {
                int lootLevel = random.nextInt(modifier4*4);
                int enchantType = random.nextInt(modifier4*3);
                int enchantLevel = 1;
                ItemStack stack = switch (lootLevel) {
                    case 15 -> new ItemStack(Items.DIAMOND_AXE);
                    case 14 -> new ItemStack(Items.DIAMOND_SWORD);
                    case 13 -> new ItemStack(Items.DIAMOND_SHOVEL);
                    case 11, 12 -> new ItemStack(Items.IRON_AXE);
                    case 7, 8, 9, 10 -> new ItemStack(Items.IRON_SWORD);
                    case 3, 4, 5, 6 -> new ItemStack(Items.IRON_SHOVEL);
                    default -> new ItemStack(Items.WOODEN_SWORD);
                };
                switch (enchantType){
                    case 11:
                        enchantLevel = 1 + random.nextInt(ModEnchantments.LIGHTNING_STRIKER.getMaxLevel());
                        stack.addEnchantment(ModEnchantments.LIGHTNING_STRIKER, enchantLevel);
                        break;
                    case 10:
                    case 9:
                        enchantLevel = 1 + random.nextInt(ModEnchantments.POISON_ENCHANT.getMaxLevel());
                        stack.addEnchantment(ModEnchantments.POISON_ENCHANT, enchantLevel);
                        break;
                    case 8:
                        enchantLevel = 1 + random.nextInt(ModEnchantments.WITHER.getMaxLevel());
                        stack.addEnchantment(ModEnchantments.WITHER, enchantLevel);
                        break;
                    case 7:
                        enchantLevel = 1 + random.nextInt(Enchantments.FIRE_ASPECT.getMaxLevel());
                        stack.addEnchantment(Enchantments.FIRE_ASPECT, enchantLevel);
                        break;
                    case 6:
                    case 5:
                    case 4:
                        enchantLevel = 1 + random.nextInt(Enchantments.SHARPNESS.getMaxLevel());
                        stack.addEnchantment(Enchantments.SHARPNESS, enchantLevel);
                        break;
                    case 3:
                    case 2:
                    case 1:
                        enchantLevel = 1 + random.nextInt(Enchantments.UNBREAKING.getMaxLevel());
                        stack.addEnchantment(Enchantments.UNBREAKING, enchantLevel);
                        break;
                    default:
                        break;
                }
                jones.equipStack(EquipmentSlot.MAINHAND, stack);
            }
            for(int var8 = 0; var8 < var7; ++var8) {
                EquipmentSlot equipmentSlot = var6[var8];
                if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
                    ItemStack itemStack = jones.getEquippedStack(equipmentSlot);
                    if (!bl && random.nextFloat() < f) {
                        break;
                    }

                    bl = false;
                    if (itemStack.isEmpty()) {
                        Item item = getEquipmentForSlot(equipmentSlot, i);
                        if (item != null) {
                            jones.equipStack(equipmentSlot, new ItemStack(item));
                        }
                    }
                }
            }
        }
    }
}
