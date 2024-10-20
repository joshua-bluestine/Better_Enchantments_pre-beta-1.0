package net.josh.coolenchants.entity.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class FollowPlayerGoal extends Goal {
    private final MobEntity mob;
    private final double speed;
    private PlayerEntity targetPlayer;
    private final float minDistance;
    private final float maxDistance;

    public FollowPlayerGoal(MobEntity mob, double speed, float minDistance, float maxDistance) {
        this.mob = mob;
        this.speed = speed;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        Vec3d mobCenter = mob.getPos().add(0, 0, 0);
        targetPlayer = mob.getWorld().getClosestPlayer(mob, maxDistance);
        return targetPlayer != null && targetPlayer.squaredDistanceTo(mobCenter) >= minDistance * minDistance;
    }

    @Override
    public void start() {
        if (targetPlayer != null) {
            mob.getNavigation().startMovingTo(targetPlayer, speed);
        }
    }

    @Override
    public boolean shouldContinue() {
        if (targetPlayer == null) {
            return false;
        }

        Vec3d mobCenter = mob.getPos().add(0, mob.getHeight() / 2, 0);
        double distanceToPlayer = targetPlayer.squaredDistanceTo(mobCenter);
        return distanceToPlayer >= minDistance * minDistance && distanceToPlayer <= maxDistance * maxDistance;
    }

    @Override
    public void stop() {
        targetPlayer = null;
        mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (targetPlayer != null) {
            mob.getNavigation().startMovingTo(targetPlayer, speed);
        }
    }
}
