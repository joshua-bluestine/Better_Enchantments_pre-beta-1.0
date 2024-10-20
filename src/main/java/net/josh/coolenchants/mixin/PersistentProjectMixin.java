package net.josh.coolenchants.mixin;

import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.block.SaplingBlock.STAGE;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectMixin {
/*
code for "bowhit" which all of my bow enchants use
tnt rain
tree
 */
    int countee = 0;

//..................USED TO MAKE SURE THAT BOW ENCHANTMENTS ONLY ACTIVATE ON ARROW HITS AND NOT PUNCHING SHIT WITH A BOW.......


    @Inject(method = "onEntityHit", at = @At("HEAD"))
    private void onEntityHit(EntityHitResult entityHitResult, CallbackInfo ci) {
        Entity projectileEntity = (Entity) (Object) this;
        Entity targetEntity = entityHitResult.getEntity();

        if (projectileEntity instanceof ArrowEntity && targetEntity instanceof LivingEntity) {
            LivingEntity targetLivingEntity = (LivingEntity) targetEntity;

            if (((PersistentProjectileEntity) projectileEntity).getOwner() instanceof PlayerEntity) {
                PlayerEntity shooter = (PlayerEntity) ((PersistentProjectileEntity) projectileEntity).getOwner();
                //shooter.setStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST,1000,1),null);
                CoolEnchants.bowHit = 1;
            }
        }
    }


    

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo info) {



        Entity projectileEntity = (Entity) (Object) this;
        if (ModUtils.chronoPause2) {

            if (!(((ProjectileEntity) projectileEntity).getOwner() instanceof PlayerEntity)) {

                PlayerEntity closestPlayer = projectileEntity.getWorld().getClosestPlayer(projectileEntity, 20);
                if (closestPlayer != null && ModUtils.isWearingEnchantedHelmet(closestPlayer, ModEnchantments.CHRONO_PAUSE)) {
                    //info.cancel();
                    return;
                }
            }
        }
        PlayerEntity player = null;

        if (((PersistentProjectileEntity) projectileEntity).getOwner() instanceof PlayerEntity) {
            player = (PlayerEntity) ((PersistentProjectileEntity) projectileEntity).getOwner();
        }

//...........................TNT RAIN (might be scrapped lol that shit LAGS)........................
        if (player != null){
            if (ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.TNT_RAIN) && projectileEntity instanceof ArrowEntity){
                if (player.getWorld().getBlockState(projectileEntity.getBlockPos().down()).isAir()) {
                    for (int i = 0; i < EnchantmentHelper.getLevel(ModEnchantments.TNT_RAIN, player.getMainHandStack()); i++) {
                        TntEntity tnt = new TntEntity(EntityType.TNT, player.getWorld());
                        tnt.setPos(projectileEntity.getX(), projectileEntity.getY(), projectileEntity.getZ());
                        if (!player.getWorld().isClient()) {
                            player.getWorld().spawnEntity(tnt);
                        }
                    }
                }
            }
        }
//..................................TREE (fingies inspired (dont tell anyone)).........................
        if (player != null){
            if (ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.TREE) && projectileEntity instanceof ArrowEntity){


                if ((player.getWorld().getBlockState(projectileEntity.getBlockPos().down())
                        == Blocks.GRASS_BLOCK.getDefaultState())
                        && countee == 0) {

                    BlockPos pos = projectileEntity.getBlockPos();
                    player.getWorld().setBlockState(pos, Blocks.OAK_SAPLING.getDefaultState().with(STAGE, 1));

                    Random random = player.getWorld().getRandom();
                            for (int i = 0; i < 20; i++) {
                                double d = random.nextGaussian() * 0.16D;
                                double e = random.nextGaussian() * 0.16D;
                                double f = random.nextGaussian() * 0.16D;
                                player.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, projectileEntity.getParticleX(1.0D), 0.5 + pos.getY()+random.nextGaussian() * 0.1D, projectileEntity.getParticleZ(1.0D), d, e, f);
                            }
                    projectileEntity.remove(Entity.RemovalReason.DISCARDED);

                    countee = 1;
                }
            }
        }
    }
}
