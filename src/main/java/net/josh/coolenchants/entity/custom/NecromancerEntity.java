package net.josh.coolenchants.entity.custom;

import net.josh.coolenchants.IEntityDataSaver;
import net.josh.coolenchants.data.NecromancerOwnerData;
import net.josh.coolenchants.data.ThirstData;
import net.josh.coolenchants.entity.ai.goal.FollowPlayerGoal;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

public class NecromancerEntity extends IronGolemEntity {

    protected static final TrackedData<Byte> NECROMANCER_FLAGS = DataTracker.registerData(NecromancerEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static int counter = 0;
    public NecromancerEntity(EntityType<? extends IronGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new FollowPlayerGoal(this, 1.0, 10.0f, 20.0f));
        this.goalSelector.add(0, new MeleeAttackGoal(this, 1.0, true));
        //this.goalSelector.add(1, new IronGolemWanderAroundGoal(this, 0.6));
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(2, new LookAroundGoal(this));
        //this.targetSelector.add(3, new ActiveTargetGoal<PlayerEntity>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.targetSelector.add(3, new ActiveTargetGoal<MobEntity>(this, MobEntity.class, 5, false, false, entity -> entity instanceof Monster && !(entity instanceof CreeperEntity)));
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
            if (this.getHealth() <= 0) {
                if (this.getWorld().getPlayerByUuid(NecromancerOwnerData.getOwner((IEntityDataSaver) this)) != null) {
                    ThirstData.removeThirst((IEntityDataSaver) this.getWorld().getPlayerByUuid(
                            NecromancerOwnerData.getOwner((IEntityDataSaver) this)), 1);
                }
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
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 10)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0)
                .add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(NECROMANCER_FLAGS, (byte)0);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (counter < 10){
            counter++;
        } else {
            counter = 0;
            if (NecromancerOwnerData.getOwner(((IEntityDataSaver) this)) != null){
                if (this.getWorld().getPlayerByUuid(
                        NecromancerOwnerData.getOwner(((IEntityDataSaver) this))) != null) {
                    if (ThirstData.getAmount(((IEntityDataSaver)
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
