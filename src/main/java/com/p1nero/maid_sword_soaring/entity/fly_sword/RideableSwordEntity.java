package com.p1nero.maid_sword_soaring.entity.fly_sword;

import com.p1nero.maid_sword_soaring.entity.MaidSwordSoaringEntities;
import com.p1nero.maid_sword_soaring.entity.ai.goal.SwordFollowOwnerGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class RideableSwordEntity extends SwordEntity {
    public RideableSwordEntity(EntityType<? extends RideableSwordEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public RideableSwordEntity(LivingEntity owner) {
        this(MaidSwordSoaringEntities.RIDEABLE_SWORD.get(), owner.level());
        this.setPos(owner.position());
        this.setOwner(owner);
        this.setItemStack(owner.getMainHandItem());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwordFollowOwnerGoal(this));
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

}
