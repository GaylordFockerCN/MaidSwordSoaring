package com.p1nero.maid_sword_soaring.entity.fly_sword;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.p1nero.maid_sword_soaring.entity.MaidSwordSoaringEntities;
import com.p1nero.maid_sword_soaring.entity.ai.goal.SwordFollowTargetGoal;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
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
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if(!this.getPassengers().isEmpty() && this.getPassengers().getFirst() instanceof EntityMaid maid){
            return maid.mobInteract(player, hand);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float partialTick) {
        return super.getPassengerAttachmentPoint(entity, EntityDimensions.fixed(0.5F, 0.2F), partialTick);
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
        poseStack.translate(0, 0.26, 0);
        poseStack.mulPose(new Quaternionf().rotateTo((float) 0, 0, 1, (float) view.x, (float) view.y, (float) view.z));
        poseStack.mulPose(Axis.XP.rotationDegrees(90f));
        poseStack.mulPose(Axis.YP.rotationDegrees(90));
    }

}
