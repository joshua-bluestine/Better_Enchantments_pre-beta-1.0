package net.josh.coolenchants.mixin;

import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EndermanEntity.class)
public class EndermanEntityMixin {

//.............................AVERT................................

    @Overwrite
    public boolean isPlayerStaring(PlayerEntity player) {
        EndermanEntity endy = (EndermanEntity) (Object) this;
        ItemStack itemStack = (ItemStack)player.getInventory().armor.get(3);
        if (itemStack.isOf(Blocks.CARVED_PUMPKIN.asItem())) {
            return false;
        } else if (ModUtils.isWearingEnchantedHelmet(player, ModEnchantments.AVERT)) {
            Vec3d vec3d = player.getRotationVec(1.0F).normalize();
            Vec3d vec3d2 = new Vec3d(endy.getX() - player.getX(), endy.getEyeY() - player.getEyeY(), endy.getZ() - player.getZ());
            double g = vec3d2.length();
            vec3d2 = vec3d2.normalize();
            double h = vec3d.dotProduct(vec3d2);

            if (h > 1.0 - 0.025 / g && player.canSee(endy)) {
                if (!endy.getWorld().isClient() && endy.isAlive()) {
                  //  endy.damage(endy.getDamageSources().magic(), 1);
                   // endy.setVelocity(0,0.5,0);

                    double d = endy.getX() + (endy.getWorld().random.nextDouble() - 0.5) * 64.0;
                    double e = endy.getY() + (double) (endy.getWorld().random.nextInt(64) - 32);
                    double f = endy.getZ() + (endy.getWorld().random.nextDouble() - 0.5) * 64.0;


                    BlockPos.Mutable mutable = new BlockPos.Mutable(d, e, f);

                    while (mutable.getY() > endy.getWorld().getBottomY() && !endy.getWorld().getBlockState(mutable).blocksMovement()) {
                        mutable.move(Direction.DOWN);
                    }

                    BlockState blockState = endy.getWorld().getBlockState(mutable);
                    boolean bl = blockState.blocksMovement();
                    boolean bl2 = blockState.getFluidState().isIn(FluidTags.WATER);
                    if (bl && !bl2) {
                        Vec3d vec3d3 = endy.getPos();
                        boolean bl3 = endy.teleport(d, e, f, true);
                        if (bl3) {
                            endy.getWorld().emitGameEvent(GameEvent.TELEPORT, vec3d3, GameEvent.Emitter.of(endy));
                            if (!endy.isSilent()) {
                                endy.getWorld().playSound((PlayerEntity) null, endy.prevX, endy.prevY, endy.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, endy.getSoundCategory(), 1.0F, 1.0F);
                                endy.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
                            }
                        }
                    }


                }
            }
                return false;
        } else {
            Vec3d vec3d = player.getRotationVec(1.0F).normalize();
            Vec3d vec3d2 = new Vec3d(endy.getX() - player.getX(), endy.getEyeY() - player.getEyeY(), endy.getZ() - player.getZ());
            double d = vec3d2.length();
            vec3d2 = vec3d2.normalize();
            double e = vec3d.dotProduct(vec3d2);
            return e > 1.0 - 0.025 / d ? player.canSee(endy) : false;
        }
    }
}
