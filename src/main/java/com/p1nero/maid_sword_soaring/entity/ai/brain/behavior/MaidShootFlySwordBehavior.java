package com.p1nero.maid_sword_soaring.entity.ai.brain.behavior;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.p1nero.maid_sword_soaring.entity.fly_sword.FlySwordEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class MaidShootFlySwordBehavior {
    public static OneShot<EntityMaid> create(int pCooldownBetweenAttacks) {
        return BehaviorBuilder.create((instance) -> instance.group(instance.registered(MemoryModuleType.LOOK_TARGET), instance.present(MemoryModuleType.ATTACK_TARGET), instance.absent(MemoryModuleType.ATTACK_COOLING_DOWN), instance.present(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)).apply(instance, (accessor, targetAccessor, cooldownAccessor, nearestVisibleLivingEntities) -> (serverLevel, maid, gameTime) -> {
            LivingEntity target = instance.get(targetAccessor);
            if (instance.get(nearestVisibleLivingEntities).contains(target)) {
                //TODO 播放攻击动画
                FlySwordEntity flySwordEntity = new FlySwordEntity(maid, target);
                flySwordEntity.setDelay(15);
                flySwordEntity.setSpeed(0.7F);
                serverLevel.addFreshEntity(flySwordEntity);
                flySwordEntity.setDeltaMovement(maid.getViewVector(1.0F));
                cooldownAccessor.setWithExpiry(true, pCooldownBetweenAttacks);
                return true;
            } else {
                return false;
            }
        }));
    }
}
