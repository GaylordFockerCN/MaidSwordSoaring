package com.p1nero.maid_sword_soaring.entity.fly_sword;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.p1nero.maid_sword_soaring.entity.MaidSwordSoaringEntities;
import com.p1nero.maid_sword_soaring.utils.MathUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
    private int lifeTime = 100;
    private float damageRate = 0.5F;
    private float fixedYRot = 0;
    private Vec3 fixedDir = Vec3.ZERO;
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
        this.setPos(owner.getEyePosition().add(this.getRandom().nextFloat() * 2 - 1, 1, this.getRandom().nextFloat() * 2 - 1));
        this.setItemStack(owner.getMainHandItem());
        this.target = target;
        syncDirection();
    }

    @Override
    public void setYRot(float pYRot) {
        super.setYRot(pYRot);
        this.fixedYRot = pYRot;
    }

    @Override
    public void setDeltaMovement(@NotNull Vec3 pDeltaMovement) {
        super.setDeltaMovement(pDeltaMovement);
        this.fixedDir = pDeltaMovement;
    }

    public void setFixedDir(Vec3 fixedDir) {
        this.fixedDir = fixedDir;
    }

    public void setFixedYRot(float fixedYRot) {
        this.fixedYRot = fixedYRot;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public void setDamageRate(float damageRate) {
        this.damageRate = damageRate;
    }

    @Override
    public void tick() {
        super.tick();
        if(delay > 0) {
            delay--;
            if(delay == 0) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
        if(tickCount > lifeTime || this.onGround()) {
            this.discard();
        }

        syncDirection();
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
        target.hurt(this.damageSources().mobAttack(this.getOwner()), (float) this.getOwner().getAttributeValue(Attributes.ATTACK_DAMAGE) * damageRate);
        this.discard();
    }

    public void syncDirection() {
        if(level().isClientSide) {
            return;
        }
        if(this.target == null) {
            this.setYRot(fixedYRot);
            this.setFixedDir(fixedDir);
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
