package com.p1nero.maid_sword_soaring.entity.ai.brain.behavior;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.p1nero.maid_sword_soaring.entity.fly_sword.FlySwordEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class MaidShootFlySwordBehavior {
    public static OneShot<EntityMaid> create(int pCooldownBetweenAttacks) {
        return BehaviorBuilder.create((instance) -> instance.group(instance.registered(MemoryModuleType.LOOK_TARGET), instance.present(MemoryModuleType.ATTACK_TARGET), instance.absent(MemoryModuleType.ATTACK_COOLING_DOWN), instance.present(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)).apply(instance, (accessor, targetAccessor, cooldownAccessor, nearestVisibleLivingEntities) -> (serverLevel, maid, gameTime) -> {
            LivingEntity target = instance.get(targetAccessor);
            if (instance.get(nearestVisibleLivingEntities).contains(target)) {
                maid.swing(InteractionHand.MAIN_HAND);
                FlySwordEntity flySwordEntity = new FlySwordEntity(maid, target);
                flySwordEntity.setDelay(15);
                flySwordEntity.setSpeed(0.7F);
                flySwordEntity.setDamage((float) maid.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
                serverLevel.addFreshEntity(flySwordEntity);
                flySwordEntity.setDeltaMovement(maid.getViewVector(1.0F));
                FlySwordEntity flySwordEntity2 = new FlySwordEntity(maid, target);
                flySwordEntity2.setDelay(20);
                flySwordEntity2.setSpeed(0.7F);
                flySwordEntity2.setDamage((float) maid.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
                serverLevel.addFreshEntity(flySwordEntity2);
                flySwordEntity2.setDeltaMovement(maid.getViewVector(1.0F));
                FlySwordEntity flySwordEntity3 = new FlySwordEntity(maid, target);
                flySwordEntity3.setDelay(25);
                flySwordEntity3.setSpeed(0.7F);
                flySwordEntity3.setDamage((float) maid.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
                serverLevel.addFreshEntity(flySwordEntity3);
                flySwordEntity3.setDeltaMovement(maid.getViewVector(1.0F));
                cooldownAccessor.setWithExpiry(true, pCooldownBetweenAttacks);
                return true;
            } else {
                return false;
            }
        }));
    }
}
