package com.p1nero.maid_sword_soaring.entity.fly_sword;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.p1nero.maid_sword_soaring.MaidSwordSoaringMod;
import com.p1nero.maid_sword_soaring.compat.ArmourersWorkshopCompat;
import com.p1nero.maid_sword_soaring.entity.MaidSwordSoaringEntities;
import com.p1nero.maid_sword_soaring.entity.ai.goal.SwordFollowTargetGoal;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;

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
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if(!this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof EntityMaid maid) {
            return maid.mobInteract(pPlayer, pHand);
        }
        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0.15;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwordFollowTargetGoal(this));
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.tickCount > 60 && this.getPassengers().isEmpty()) {
            this.discard();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void setRenderPose(PoseStack poseStack, float yRot, float partialTick) {
        Vec3 view = this.calculateViewVector(0, yRot);
        poseStack.translate(0, 0.30, 0);
        poseStack.mulPose(new Quaternionf().rotateTo((float) 0, 0, 1, (float) view.x, (float) view.y, (float) view.z));
        poseStack.mulPose(Axis.XP.rotationDegrees(90f));
        poseStack.mulPose(Axis.YP.rotationDegrees(90));
    }
}
