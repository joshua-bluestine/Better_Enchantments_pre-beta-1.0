package net.josh.coolenchants.explosion;
import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TargetExplosion extends Explosion {
float power = 0;
World world;
double x, y, z;
Entity entity;
    public TargetExplosion(World world, Entity entity, double x, double y, double z, float power, @Nullable ExplosionBehavior behavior) {
        super(world, entity, x, y, z, power, false, DestructionType.KEEP); // Set knockback power to 0// Disable fire
        this.power = power;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.entity = entity;
        this.affectWorld(false);
    }

    @Override
    public void collectBlocksAndDamageEntities() {
        int l;
        int k;
        Objects.requireNonNull(this.getEntity()).getWorld().emitGameEvent(this.getEntity(), GameEvent.EXPLODE, new Vec3d(this.getEntity().getX(), this.getEntity().getY(), this.getEntity().getZ()));
        HashSet<BlockPos> set = Sets.newHashSet();
        int i = 16;
        for (int j = 0; j < 16; ++j) {
            for (k = 0; k < 16; ++k) {
                block2: for (l = 0; l < 16; ++l) {
                    if (j != 0 && j != 15 && k != 0 && k != 15 && l != 0 && l != 15) continue;
                    double d = (float)j / 15.0f * 2.0f - 1.0f;
                    double e = (float)k / 15.0f * 2.0f - 1.0f;
                    double f = (float)l / 15.0f * 2.0f - 1.0f;
                    double g = Math.sqrt(d * d + e * e + f * f);
                    d /= g;
                    e /= g;
                    f /= g;
                    double m = this.getEntity().getX();
                    double n = this.getEntity().getY();
                    double o = this.getEntity().getZ();
                    float p = 0.3f;
                    for (float h = this.power * (0.7f + this.getEntity().getWorld().random.nextFloat() * 0.6f); h > 0.0f; h -= 0.22500001f) {
                        BlockPos blockPos = BlockPos.ofFloored(m, n, o);
                        BlockState blockState = this.getEntity().getWorld().getBlockState(blockPos);
                        FluidState fluidState = this.getEntity().getWorld().getFluidState(blockPos);
                        if (!this.getEntity().getWorld().isInBuildLimit(blockPos)) continue block2;
                        m += d * (double)0.3f;
                        n += e * (double)0.3f;
                        o += f * (double)0.3f;
                    }
                }
            }
        }
        this.getAffectedBlocks().addAll((Collection<BlockPos>)set);
        float q = this.power * 2.0f;
        k = MathHelper.floor(this.x - (double)q - 1.0);
        l = MathHelper.floor(this.x + (double)q + 1.0);
        int r = MathHelper.floor(this.y - (double)q - 1.0);
        int s = MathHelper.floor(this.y + (double)q + 1.0);
        int t = MathHelper.floor(this.z - (double)q - 1.0);
        int u = MathHelper.floor(this.z + (double)q + 1.0);
        List<Entity> list = this.world.getOtherEntities(this.entity, new Box(k, r, t, l, s, u));
        Vec3d vec3d = new Vec3d(this.x, this.y, this.z);
        for (int v = 0; v < list.size(); ++v) {
            PlayerEntity playerEntity;
            double ad;
            double z;
            double y;
            double x;
            double aa;
            double w;
            Entity entity = list.get(v);
            if (entity.isImmuneToExplosion() || !((w = Math.sqrt(entity.squaredDistanceTo(vec3d)) / (double)q) <= 1.0) || (aa = Math.sqrt((x = entity.getX() - this.x) * x + (y = (entity instanceof TntEntity ? entity.getY() : entity.getEyeY()) - this.y) * y + (z = entity.getZ() - this.z) * z)) == 0.0) continue;
            x /= aa;
            y /= aa;
            z /= aa;
            double ab = Explosion.getExposure(vec3d, entity);
            double ac = (1.0 - w) * ab;
            entity.damage(this.getDamageSource(), 0);
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                //ad = ProtectionEnchantment.transformExplosionKnockback(livingEntity, ac);
            } else {
                ad = ac;
            }
            //Vec3d vec3d2 = new Vec3d(x *= ad, y *= ad, z *= ad);
            //entity.setVelocity(entity.getVelocity().add(vec3d2));
            //if (!(entity instanceof PlayerEntity) || (playerEntity = (PlayerEntity)entity).isSpectator() || playerEntity.isCreative() && playerEntity.getAbilities().flying) continue;
            //this.getAffectedPlayers().put(playerEntity, vec3d2);
        }
    }
    }

