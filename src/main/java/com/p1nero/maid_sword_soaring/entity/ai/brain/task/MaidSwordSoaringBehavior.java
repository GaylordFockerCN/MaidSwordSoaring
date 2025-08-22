package com.p1nero.maid_sword_soaring.entity.ai.brain.task;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.google.common.collect.ImmutableMap;
import com.p1nero.maid_sword_soaring.MaidSwordSoaringMod;
import com.p1nero.maid_sword_soaring.entity.fly_sword.RideableSwordEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import org.jetbrains.annotations.NotNull;

public class MaidSwordSoaringBehavior extends Behavior<EntityMaid> {
    public MaidSwordSoaringBehavior() {
        super(ImmutableMap.of());
    }

    @Override
    protected boolean checkExtraStartConditions(@NotNull ServerLevel pLevel, @NotNull EntityMaid maid) {
        return MaidSwordSoaringMod.isValidSword(maid.getMainHandItem());
    }

    @Override
    protected boolean timedOut(long pGameTime) {
        return false;
    }

    @Override
    protected boolean canStillUse(@NotNull ServerLevel pLevel, @NotNull EntityMaid pEntity, long pGameTime) {
        return checkExtraStartConditions(pLevel, pEntity) && pEntity.getVehicle() != null;
    }

    @Override
    protected void start(@NotNull ServerLevel pLevel, @NotNull EntityMaid maid, long pGameTime) {
        RideableSwordEntity rideableSwordEntity = new RideableSwordEntity(maid);
        if(pLevel.addFreshEntity(rideableSwordEntity)){
            maid.startRiding(rideableSwordEntity, true);
        }
    }

    @Override
    protected void stop(@NotNull ServerLevel pLevel, @NotNull EntityMaid pEntity, long pGameTime) {
        Entity vehicle = pEntity.getVehicle();
        pEntity.stopRiding();
        if(vehicle != null) {
            vehicle.discard();
        }
    }

    @Override
    protected void tick(@NotNull ServerLevel pLevel, @NotNull EntityMaid maid, long pGameTime) {
        LivingEntity owner = maid.getOwner();
        if(owner != null) {
            BehaviorUtils.lookAtEntity(maid, owner);
        }
    }
}
