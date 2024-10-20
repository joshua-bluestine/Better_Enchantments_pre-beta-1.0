package net.josh.coolenchants.mixin;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.josh.coolenchants.BlockPosNBTUtil;
import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.IEntityDataSaver;
import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.block.ModBlocks;
import net.josh.coolenchants.data.AstralPositionData;
import net.josh.coolenchants.enchantment.sword.FireBallShooterEnchantment;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.josh.coolenchants.entity.ModEntities;
import net.josh.coolenchants.entity.custom.NecromancerEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.TradeOffer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static net.josh.coolenchants.ModUtils.isWearingEnchantedArmor;
import static net.josh.coolenchants.ModUtils.print;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerRightClickMixin {
/*
Necromancer
heal
snowball
fireball
witherskull (in fireball)
green thumb (more code in hoeitemmixin and farmlandblockmixin)
 */

    @Shadow public abstract boolean shouldDisplaySoulSpeedEffects();

    @Inject(at = @At("TAIL"), method = "tickMovement")
    private void tickMovement(CallbackInfo info) {
        ClientPlayerEntity user = (ClientPlayerEntity) (Object) this;
//...........................NECROMANCER.........................


//..........................HEAL...............................
        AttackEntityCallback.EVENT.register((player, world, hand, entity, entityHitResult) -> {
            ItemStack heldItem = player.getStackInHand(hand);
            if (entity instanceof LivingEntity){
                if (ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.HEAL)){
                    LivingEntity l = (LivingEntity) entity;
                    if (l.getHealth() < l.getMaxHealth()) {
                        int particleCount = (int) 5 * (EnchantmentHelper.getLevel(ModEnchantments.HEAL, player.getStackInHand(hand)));
                        for (int i = 0; i < particleCount; i++) {
                            double e = world.random.nextGaussian() * 0.02D;
                            world.addParticle(ParticleTypes.HEART, entity.getParticleX(1.0D), entity.getY(), entity.getParticleZ(1.0D), 0, e, 0);
                        }
                        if (EnchantmentHelper.getLevel(ModEnchantments.HEAL, player.getStackInHand(hand)) == 1) {
                            ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 1));
                            return ActionResult.SUCCESS;
                        }
                        if (EnchantmentHelper.getLevel(ModEnchantments.HEAL, player.getStackInHand(hand)) == 2) {
                            ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 2));
                            return ActionResult.SUCCESS;
                        }
                        if (EnchantmentHelper.getLevel(ModEnchantments.HEAL, player.getStackInHand(hand)) == 3) {
                            ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 4));
                            return ActionResult.SUCCESS;
                        }
                    }
                    return ActionResult.CONSUME;

                }
                if (ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.ASTRAL_REMOVE)){
                    ModUtils.removedEntity = ((LivingEntity)entity).getUuidAsString();
                    System.out.println(ModUtils.removedEntity);
                    System.out.println(((LivingEntity) entity).getUuidAsString());
                    return ActionResult.SUCCESS;
                }
            }

            return ActionResult.PASS;
        });

        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack itemStack = player.getStackInHand(hand);


            if (!world.isClient()) {
                if (ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.FILL)) {
                    HitResult hitResult = player.raycast(4.5, 0.0F, false);

                    if (hitResult.getType() == HitResult.Type.BLOCK) {
                        BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                        BlockPos blockPos = blockHitResult.getBlockPos();

                        if (fillHole(world, player, blockPos)) {
                            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                                    SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.NEUTRAL, 0.5f,
                                    0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
                            return TypedActionResult.success(itemStack);
                        }
                        return TypedActionResult.consume(itemStack);
                    }
                }


            }


//............................SOME OF GREEN THUMB, BUT MOST OF ITS IN HOEITEMMIXIN...............................


            if (!player.getWorld().isClient() && ModUtils.isHoldingEnchantedWeapon(user, ModEnchantments.GREEN_THUMB)) {
                HitResult hitResult = player.raycast(4.5, 0.0F, false);

                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                    BlockPos blockPos = blockHitResult.getBlockPos();
                    BlockState s = world.getBlockState(blockPos);
                    if (s.equals(Blocks.FARMLAND.getDefaultState()) ||
                            s.equals(Blocks.DIRT.getDefaultState()) ||
                            s.equals(Blocks.GRASS_BLOCK.getDefaultState()) ||
                            s.equals(Blocks.COARSE_DIRT.getDefaultState())) {



                        //BlockPosNBTUtil.writeBlockPosToList(blockPos, CoolEnchants.some);

                        //BlockPos posToCheck = blockPos;
                        boolean exists = BlockPosNBTUtil.isBlockPosInList(blockPos, CoolEnchants.some);
                       // if (exists) {
                        //    print(player,"BlockPos exists in the list");
                       // } else {
                       //     print(player,"BlockPos does not exist in the list");
                       // }

                        world.setBlockState(blockPos, Blocks.FARMLAND.getDefaultState().with(FarmlandBlock.MOISTURE, 7));
                        return TypedActionResult.success(itemStack);
                    }
                }
            }

            return TypedActionResult.pass(itemStack);
        });
    }
    private static boolean fillHole(World world, PlayerEntity player, BlockPos startPos) {
        int dirtSlot = findDirtInInventory(player);
        if (dirtSlot == -1) {
            return false;
        }
        int full = player.getInventory().getStack(dirtSlot).getCount();
        List<BlockPos> positionsToFill = getHolePositions(world, startPos, full);
        boolean filled = false;

        for (BlockPos pos : positionsToFill) {
            BlockState state = world.getBlockState(pos);
            if (state.isAir() || state.isOf(Blocks.WATER) || state.isOf(Blocks.LAVA)) {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState());
                filled = true;
            }
        }
        decreaseDirtInInventory(player, dirtSlot, positionsToFill.size());
        return filled;
    }

    private static List<BlockPos> getHolePositions(World world, BlockPos startPos, int full) {
        List<BlockPos> positions = new ArrayList<>();
        int radius = 4;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius-1; y <= 0; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = startPos.add(x, y, z);
                    BlockState state = world.getBlockState(pos);
                    if (state.isAir() || state.isOf(Blocks.WATER) || state.isOf(Blocks.LAVA) &&
                            positions.size() < full) {
                        positions.add(pos);
                    }
                }
            }
        }
        return positions;
    }

    private static int findDirtInInventory(PlayerEntity player) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.getItem() == Items.DIRT) {
                return i;
            }
        }
        return -1;
    }

    private static void decreaseDirtInInventory(PlayerEntity player, int slot, int full) {
        ItemStack stack = player.getInventory().getStack(slot);
            if (!stack.isEmpty() && stack.getItem() == Items.DIRT) {
                stack.decrement(full);
                if (stack.isEmpty()) {
                    player.getInventory().removeStack(slot);
                }
            }

    }

}
