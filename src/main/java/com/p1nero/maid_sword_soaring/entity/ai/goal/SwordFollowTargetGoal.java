package com.p1nero.maid_sword_soaring.entity.ai.goal;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.p1nero.maid_sword_soaring.entity.fly_sword.SwordEntity;
import com.p1nero.maid_sword_soaring.utils.MathUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class SwordFollowTargetGoal extends Goal {
    private final SwordEntity sword;
    public SwordFollowTargetGoal(SwordEntity sword) {
        this.sword = sword;
    }

    @Override
    public boolean canUse() {
        return !sword.getPassengers().isEmpty();
    }

    @Override
    public void tick() {
        if(sword.getFirstPassenger() instanceof EntityMaid maid) {
            Optional<LivingEntity> targetOptional = maid.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
            LivingEntity followTarget = targetOptional.orElseGet(maid::getOwner);
            boolean isOwner = targetOptional.isEmpty();

            if(followTarget != null) {
                Vec3 swordPos = sword.position();
                Vec3 targetPos = followTarget.position();

                double distanceToTarget = swordPos.distanceTo(targetPos);
                double teleportThreshold = 50.0;
                double baseSpeed = 0.1;

                if (distanceToTarget > teleportThreshold) {
                    sword.teleportTo(targetPos.x, targetPos.y + 1, targetPos.z);
                    return;
                }

                Vec3 direction = targetPos.subtract(swordPos).add(0, 1.5, 0).normalize();
                float yRot = (float) MathUtils.getYRotOfVector(direction);

                double speedMultiplier = Math.min(1.0 + (distanceToTarget / 5.0), 3.0);
                double speed = baseSpeed * speedMultiplier;
                Vec3 motion = direction.scale(speed);
                if(distanceToTarget >= 5) {
                    sword.setDeltaMovement(motion);
                }

                //拉开距离
                if(!isOwner && distanceToTarget < 3) {
                    sword.setDeltaMovement(-motion.x * 2, motion.y * 2, -motion.z * 2);
                }

                sword.setYRot(yRot);
                sword.setYHeadRot(yRot);
                sword.setYBodyRot(yRot);
                maid.setYRot(yRot);
                maid.setYBodyRot(yRot);
            }

        }
    }

}
