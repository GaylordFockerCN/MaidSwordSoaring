package com.p1nero.maid_sword_soaring.entity.ai.goal;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.p1nero.maid_sword_soaring.entity.fly_sword.SwordEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class SwordFollowOwnerGoal extends Goal {
    private final SwordEntity sword;
    public SwordFollowOwnerGoal(SwordEntity sword) {
        this.sword = sword;
    }

    @Override
    public boolean canUse() {
        return sword.getOwner() != null && !sword.getPassengers().isEmpty();
    }

    @Override
    public void tick() {
        if(sword.getFirstPassenger() instanceof EntityMaid maid) {
            Optional<LivingEntity> targetOptional = maid.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
            LivingEntity target = targetOptional.orElseGet(maid::getOwner);
            if(target != null) {
                Vec3 swordPos = sword.position();
                Vec3 targetPos = target.position();

                double distanceToTarget = swordPos.distanceTo(targetPos);

                double teleportThreshold = 50.0;
                double baseSpeed = 0.1;

                if (distanceToTarget > teleportThreshold) {
                    sword.teleportTo(targetPos.x, targetPos.y + 1, targetPos.z);
                    return;
                }

                if(distanceToTarget < 3) {
                    return;
                }

                Vec3 direction = targetPos.subtract(swordPos).add(0, 1, 0).normalize();
                float yRot = getYRotOfVector(direction);
                double speedMultiplier = Math.min(1.0 + (distanceToTarget / 5.0), 3.0);
                double speed = baseSpeed * speedMultiplier;

                Vec3 motion = direction.scale(speed);
                sword.setDeltaMovement(motion);
                sword.setYRot(yRot);
                sword.setYHeadRot(yRot);
                sword.setYBodyRot(yRot);
                maid.setYRot(yRot);
                maid.setYBodyRot(yRot);
            }

        }
    }

    public static float getYRotOfVector(Vec3 vec) {
        Vec3 normalized = vec.normalize();
        return (float) (org.joml.Math.atan2(normalized.z, normalized.x) * (180D / Math.PI) - (double)90.0F);
    }
}
