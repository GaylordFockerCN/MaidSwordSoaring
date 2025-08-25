package com.p1nero.maid_sword_soaring.client.animation;

import com.github.tartaricacid.simplebedrockmodel.client.bedrock.model.BedrockPart;
import com.github.tartaricacid.touhoulittlemaid.api.animation.ICustomAnimation;
import com.github.tartaricacid.touhoulittlemaid.api.animation.IModelRenderer;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.p1nero.maid_sword_soaring.entity.fly_sword.RideableSwordEntity;
import net.minecraft.world.entity.Mob;

import java.util.HashMap;

public class SwordSoaringAnimation implements ICustomAnimation<Mob> {
    @Override
    public void setupRotations(Mob entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        if(entity instanceof EntityMaid maid && maid.getVehicle() instanceof RideableSwordEntity) {
            poseStack.mulPose(Axis.YP.rotationDegrees(30));
        }
    }

    @Override
    public void setRotationAngles(Mob entity, HashMap<String, ? extends IModelRenderer> models, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if(entity instanceof EntityMaid maid && maid.getVehicle() instanceof RideableSwordEntity) {
            BedrockPart head = ICustomAnimation.getPartOrNull(models, "head");
            if(head != null) {
                head.yRot = (float) (head.getInitRotY() + Math.toRadians(30));
            }
        }
    }
}
