package net.josh.coolenchants.entity.custom;

import net.josh.coolenchants.IEntityDataSaver;
import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.data.NecromancerOwnerData;
import net.josh.coolenchants.data.ThirstData;
import net.josh.coolenchants.entity.ai.goal.FollowPlayerGoal;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UndeadDragonEntity extends IronGolemEntity {



    public int latestSegment = -1;
    public final double[][] segmentCircularBuffer = new double[64][3];
    public int ticksSinceDeath;
    public float prevWingPosition;
    public float wingPosition;

    protected static final TrackedData<Byte> NECROMANCER_FLAGS = DataTracker.registerData(NecromancerEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static int counter = 0;
    public UndeadDragonEntity(EntityType<? extends IronGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    public boolean isFlappingWings() {
        float f = MathHelper.cos(this.wingPosition * ((float)Math.PI * 2));
        float g = MathHelper.cos(this.prevWingPosition * ((float)Math.PI * 2));
        return g <= -0.3f && f >= -0.3f;
    }

    public double[] getSegmentProperties(int segmentNumber, float tickDelta) {
        if (this.isDead()) {
            tickDelta = 0.0f;
        }
        tickDelta = 1.0f - tickDelta;
        int i = this.latestSegment - segmentNumber & 0x3F;
        int j = this.latestSegment - segmentNumber - 1 & 0x3F;
        double[] ds = new double[3];
        double d = this.segmentCircularBuffer[i][0];
        double e = MathHelper.wrapDegrees(this.segmentCircularBuffer[j][0] - d);
        ds[0] = d + e * (double)tickDelta;
        d = this.segmentCircularBuffer[i][1];
        e = this.segmentCircularBuffer[j][1] - d;
        ds[1] = d + e * (double)tickDelta;
        ds[2] = MathHelper.lerp((double)tickDelta, this.segmentCircularBuffer[i][2], this.segmentCircularBuffer[j][2]);
        return ds;
    }
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new FollowPlayerGoal(this, 1.0, 10.0f, 400.0f));
        this.goalSelector.add(0, new MeleeAttackGoal(this, 1.0, true));
        //this.targetSelector.add(0, new ActiveTargetGoal<MobEntity>(this, MobEntity.class, 5, false, false, entity -> entity instanceof Monster && !(entity instanceof CreeperEntity)));

        //this.goalSelector.add(1, new IronGolemWanderAroundGoal(this, 0.6));
        //this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        //this.goalSelector.add(2, new LookAroundGoal(this));
        //this.targetSelector.add(3, new ActiveTargetGoal<PlayerEntity>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.targetSelector.add(3, new ActiveTargetGoal<MobEntity>(this, MobEntity.class, 5, false, false, entity -> entity instanceof Monster));
        //this.targetSelector.add(4, new UniversalAngerGoal<IronGolemEntity>(this, false));
    }


    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        Random random = world.getRandom();
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        float f = difficulty.getClampedLocalDifficulty();
        // NecromancerOwnerData.setOwener((IEntityDataSaver) this, world.getClosestPlayer(this, 5));
        createIronGolemAttributes();
        return entityData;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        if (!this.getWorld().isClient()) {
            //ModUtils.print(this.getWorld().getPlayerByUuid(NecromancerOwnerData.getOwner((IEntityDataSaver) this)), "yooo");
                if (this.getWorld().getPlayerByUuid(NecromancerOwnerData.getOwner((IEntityDataSaver) this)) != null) {
                    ThirstData.removeThirst((IEntityDataSaver) this.getWorld().getPlayerByUuid(
                            NecromancerOwnerData.getOwner((IEntityDataSaver) this)), 1);
                }

        }
        super.onDeath(damageSource);
    }



    @Override
    public boolean damage(DamageSource source, float amount) {


        if (!super.damage(source, amount)) {
            return false;
        }
        if (!(this.getWorld() instanceof ServerWorld)) {
            return false;
        }
        ServerWorld serverWorld = (ServerWorld)this.getWorld();
        LivingEntity livingEntity = this.getTarget();

        if (livingEntity == null && source.getAttacker() instanceof LivingEntity) {
            livingEntity = (LivingEntity)source.getAttacker();
        }

        return true;
    }

    public static DefaultAttributeContainer.Builder createIronGolemAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 200)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0)
                .add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(NECROMANCER_FLAGS, (byte)0);
    }
    private void damageLivingEntities(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity && !(entity instanceof PlayerEntity)) {
                entity.damage(this.getDamageSources().mobAttack(this), 10.0f);
                this.applyDamageEffects(this, entity);
            }
        }
    }

    private void launchLivingEntities(List<Entity> entities) {
        double d = (this.getBoundingBox().minX + this.getBoundingBox().maxX) / 2.0;
        double e = (this.getBoundingBox().minZ + this.getBoundingBox().maxZ) / 2.0;
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity && !(entity instanceof PlayerEntity)) {
                double f = entity.getX() - d;
                double g = entity.getZ() - e;
                double h = Math.max(f * f + g * g, 0.1);
                entity.addVelocity(f / h * 4.0, 0.2f, g / h * 4.0);
                entity.damage(this.getDamageSources().mobAttack(this), 5.0f);
                this.applyDamageEffects(this, entity);
            }
        }
    }
    @Override
    public void tickMovement() {
        super.tickMovement();

        this.prevWingPosition = this.wingPosition;
        this.bodyYaw = this.getYaw();
        float t = (float)(this.getSegmentProperties(5, 1.0f)[1] - this.getSegmentProperties(10, 1.0f)[1]) * 10.0f * ((float)Math.PI / 180);
        float u = MathHelper.cos(t);
        float v = MathHelper.sin(t);
        float w = this.getYaw() * ((float)Math.PI / 180);
        float x = MathHelper.sin(w);
        float y = MathHelper.cos(w);
        if (!this.getWorld().isClient && this.hurtTime == 0) {
            this.launchLivingEntities(this.getWorld().getOtherEntities(this, this.getBoundingBox().expand(4.0, 2.0, 4.0).offset(0.0, -2.0, 0.0), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR));
            this.damageLivingEntities(this.getWorld().getOtherEntities(this, this.getBoundingBox().expand(1.0), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR));
        }
        if (counter < 10){
            counter++;
        } else {
            counter = 0;
            if (NecromancerOwnerData.getOwner(((IEntityDataSaver) this)) != null){
                if (this.getWorld().getPlayerByUuid(
                        NecromancerOwnerData.getOwner(((IEntityDataSaver) this))) != null) {
                    if (ThirstData.getDragonAmount(((IEntityDataSaver)
                            this.getWorld().getPlayerByUuid(
                                    NecromancerOwnerData.getOwner(((IEntityDataSaver) this)))))
                            == 0) {
                        this.kill();
                    }
                } else {
                    this.kill();
                }
            } else {
                this.kill();
            }

        }
    }

}
