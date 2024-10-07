package net.josh.coolenchants.mixin;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.josh.coolenchants.BlockPosNBTUtil;
import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.block.ModBlocks;
import net.josh.coolenchants.enchantment.sword.FireBallShooterEnchantment;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;

import static net.josh.coolenchants.ModUtils.isWearingEnchantedArmor;
import static net.josh.coolenchants.ModUtils.print;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientplayerRightClickMixin {


    @Shadow public abstract boolean shouldDisplaySoulSpeedEffects();

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo info) {
        ClientPlayerEntity user = (ClientPlayerEntity) (Object) this;



        UseItemCallback.EVENT.register((player, world, hand) -> {
                    ItemStack itemStack = player.getStackInHand(hand);
                    // Check if the player is right-clicking with a specific item

//.........................SNOWBALL...................................

                    if (!player.getWorld().isClient() && ModUtils.isHoldingEnchantedWeapon(user, ModEnchantments.SNOWBALL)) {
                        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
                        SnowballEntity snowballEntity = new SnowballEntity(world, user);
                        //snowballEntity.setItem(itemStack);
                        snowballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 1.0f);
                        world.spawnEntity(snowballEntity);

                    // user.incrementStat(Stats.USED.getOrCreateStat(this));
                        return TypedActionResult.success(itemStack);
                }
//............................FIREBALL & WITHER SKULL................................
            if (!player.getWorld().isClient() &&
                    (ModUtils.isHoldingEnchantedWeapon(user, ModEnchantments.FIRE_SHOOTER) ||
                            ModUtils.isHoldingEnchantedWeapon(user, ModEnchantments.WITHER_SKULL))) {
                WitherSkullEntity skull = new WitherSkullEntity(EntityType.WITHER_SKULL, world);
                FireballEntity fireball = new FireballEntity(EntityType.FIREBALL, world);

                float pitch = user.getPitch();
                float yaw = user.getYaw();
                double xVelocity = -Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
                double yVelocity = -Math.sin(Math.toRadians(pitch));
                double zVelocity = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));

                if (ModUtils.isHoldingEnchantedWeapon(user, ModEnchantments.WITHER_SKULL)){
                    skull.setVelocity(user, user.getPitch(), user.getYaw(), 1.0f, 3f, 1.0f);
                    skull.setPos(user.getX(), user.getY() + 1, user.getZ());
                    world.spawnEntity(skull);
                } else {
                    fireball.setVelocity(user, user.getPitch(), user.getYaw(), 1.0f, 3f, 1.0f);
                    fireball.setPos(user.getX(), user.getY() + 1, user.getZ());
                    world.spawnEntity(fireball);

                }
                return TypedActionResult.success(itemStack); // Return SUCCESS to cancel further processing
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
                        if (exists) {
                            print(player,"BlockPos exists in the list");
                        } else {
                            print(player,"BlockPos does not exist in the list");
                        }

                        world.setBlockState(blockPos, Blocks.FARMLAND.getDefaultState().with(FarmlandBlock.MOISTURE, 7));
                        return TypedActionResult.success(itemStack);
                    }
                }
            }

            return TypedActionResult.pass(itemStack); // Return PASS to allow further processing
        });
    }

}
