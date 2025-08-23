package com.p1nero.maid_sword_soaring.entity.fly_sword;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.p1nero.maid_sword_soaring.MaidSwordSoaringMod;
import com.p1nero.maid_sword_soaring.compat.ArmourersWorkshopCompat;
import com.p1nero.maid_sword_soaring.entity.MaidSwordSoaringEntities;
import com.p1nero.maid_sword_soaring.utils.MathUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

public class FlySwordEntity extends SwordEntity {
    private Entity target;
    private int delay;
    private float speed = 0.3F;
    @Override
    public boolean isCurrentlyGlowing() {
        return true;
    }

    public FlySwordEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public FlySwordEntity(LivingEntity owner, Entity target) {
        this(MaidSwordSoaringEntities.FLY_SWORD.get(), owner.level());
        this.setOwner(owner);
        this.setPos(owner.getEyePosition().add(0, 1, 0));
        this.setItemStack(owner.getMainHandItem());
        this.target = target;
        syncDirection();
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void tick() {
        super.tick();
        if(delay > 0) {
            delay--;
        }
        if(tickCount > 100 || this.onGround()) {
            this.discard();
        }
        if(!level().isClientSide) {
            if(this.target != null) {
                syncDirection();
            }
        }
    }

    @Override
    public void push(@NotNull Entity target) {
        super.push(target);
        if(this.getOwner() == null || target == this.getOwner()) {
            return;
        }
        if(target instanceof LivingEntity livingEntity && !this.getOwner().canAttack(livingEntity)) {
            return;
        }
        target.hurt(this.damageSources().mobAttack(this.getOwner()), (float) this.getOwner().getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5f);
        this.discard();
    }

    public void syncDirection() {
        if(level().isClientSide) {
            return;
        }
        Vec3 dir = this.target.getEyePosition().subtract(this.position()).normalize();
        if(delay > 0) {
            this.setDeltaMovement(Vec3.ZERO);
        } else {
            this.setDeltaMovement(dir.scale(speed));
        }
        this.setYRot((float) MathUtils.getYRotOfVector(dir));
        this.setXRot((float) MathUtils.getXRotOfVector(dir));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void setRenderPose(PoseStack poseStack, float yRot, float partialTick) {
        Vec3 view = this.calculateViewVector(this.getXRot(), yRot);
        poseStack.mulPose(new Quaternionf().rotateTo((float) 0, 0, 1, (float) view.x, (float) view.y, (float) view.z));
        poseStack.mulPose(Axis.XP.rotationDegrees(90f));
    }

}
