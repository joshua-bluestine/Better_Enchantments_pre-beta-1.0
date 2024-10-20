package net.josh.coolenchants.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.josh.coolenchants.BlockPosNBTUtil;
import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.IEntityDataSaver;
import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.data.EnchantmentData;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static net.josh.coolenchants.CoolEnchants.pers;

@Mixin(BowItem.class)
public class BowItemMixin {
    /*
    barrage (part one, part two is in playerentitymixin)

     */

    Item bow = (Item) (Object) this;

    //........................BARRAGE...........................
    /**
     * @author
     * @reason
     */
    @Overwrite
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        pers.clear();
        boolean bl2;
        int i;
        float f;

        if (!(user instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity playerEntity = (PlayerEntity)user;
        boolean bl = playerEntity.getAbilities().creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
        ItemStack itemStack = playerEntity.getProjectileType(stack);
        if (itemStack.isEmpty() && !bl) {
            return;
        }
        if (itemStack.isEmpty()) {
            itemStack = new ItemStack(Items.ARROW);
        }
        if ((double)(f = BowItem.getPullProgress(i = bow.getMaxUseTime(stack) - remainingUseTicks)) < 0.1) {
            return;
        }
        boolean bl3 = bl2 = bl && itemStack.isOf(Items.ARROW);
        if (!world.isClient) {
            int k;
            int j;
            Random random = playerEntity.getWorld().getRandom();


            ArrowItem arrowItem = (ArrowItem) (itemStack.getItem() instanceof ArrowItem ? itemStack.getItem() : Items.ARROW);
            for (int e = 0; e <= EnchantmentHelper.getLevel(ModEnchantments.BARRAGE, playerEntity.getMainHandStack()); e++) {
                pers.add(arrowItem.createArrow(world, itemStack, playerEntity));
                int poop = 1;
                if (random.nextFloat() > 0.5){
                    poop *= -1;
                }

                if (e> 0){
                    pers.get(e).setVelocity(playerEntity,
                            playerEntity.getPitch() + random.nextFloat() * 0.1F * poop,
                            playerEntity.getYaw() + random.nextFloat() * 15F * poop,
                            0.0f,
                            f * 3.0f,
                            1.0f);
                } else {
                    pers.get(e).setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, f * 3.0f, 1.0f);
                }
                float pee = EnchantmentHelper.getLevel(ModEnchantments.RANGE, stack);
                if (pee > 0) {

                    pers.get(e).setVelocity(
                            playerEntity,
                            playerEntity.getPitch(),
                            playerEntity.getYaw(),
                            0.0f,
                            f * 3.0f * (1+ (pee/5)),
                            1.0f);
                    pers.get(e).setDamage(pers.get(e).getDamage()/1.8);

                }

                if (f == 1.0f) {
                    pers.get(e).setCritical(true);
                }
                if ((j = EnchantmentHelper.getLevel(Enchantments.POWER, stack)) > 0) {
                    pers.get(e).setDamage(pers.get(e).getDamage() + (double) j * 0.5 + 0.5);
                }
                if ((k = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack)) > 0) {
                    pers.get(e).setPunch(k);
                }
                if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
                    pers.get(e).setOnFireFor(100);
                }
                stack.damage(1, playerEntity, p -> p.sendToolBreakStatus(playerEntity.getActiveHand()));
                if (bl2 || playerEntity.getAbilities().creativeMode && (itemStack.isOf(Items.SPECTRAL_ARROW) || itemStack.isOf(Items.TIPPED_ARROW))) {
                    pers.get(e).pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                }
                //if (e == 0){
                    world.spawnEntity(pers.get(e));
               // } else {
                    ModUtils.bowshot = true;
                //}

            }
        }
        for (int e = 0; e < EnchantmentHelper.getLevel(ModEnchantments.BARRAGE, playerEntity.getMainHandStack()); e++) {
            world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f / (world.getRandom().nextFloat() * 0.4f + 1.2f) + f * 0.5f);
        }
            if (!bl2 && !playerEntity.getAbilities().creativeMode) {
            itemStack.decrement(1);
            if (itemStack.isEmpty()) {
                playerEntity.getInventory().removeOne(itemStack);
            }
        }
        playerEntity.incrementStat(Stats.USED.getOrCreateStat(bow));
    }

}
