package com.p1nero.maid_sword_soaring.mixin;

import com.github.tartaricacid.touhoulittlemaid.api.entity.IMaid;
import com.github.tartaricacid.touhoulittlemaid.client.animation.inner.IAnimation;
import com.github.tartaricacid.touhoulittlemaid.client.animation.inner.MaidBaseAnimation;
import com.github.tartaricacid.touhoulittlemaid.client.animation.script.GlWrapper;
import com.github.tartaricacid.touhoulittlemaid.client.animation.script.ModelRendererWrapper;
import com.p1nero.maid_sword_soaring.entity.fly_sword.RideableSwordEntity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;

import static com.github.tartaricacid.touhoulittlemaid.client.animation.inner.MaidBaseAnimation.isPassengerMarisaBroom;

/**
 * 御剑飞行时取消坐姿干扰
 */
@Mixin(MaidBaseAnimation.class)
public class MaidBaseAnimationMixin {

    @Inject(method = "getSitSkirtRotation", at = @At("HEAD"), cancellable = true, remap = false)
    private static void mss$getSitSkirtRotation(CallbackInfoReturnable<IAnimation<Mob>> cir) {
        cir.setReturnValue(new IAnimation<>() {
            @Override
            public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Mob mob, HashMap<String, ModelRendererWrapper> modelMap) {
                IMaid maid = IMaid.convert(mob);
                if (maid == null || (mob.getVehicle() instanceof RideableSwordEntity)) return;

                ModelRendererWrapper sittingRotationSkirt = modelMap.get("sittingRotationSkirt");
                if (sittingRotationSkirt != null) {
                    if (isPassengerMarisaBroom(mob) || mob.isPassenger() || maid.isMaidInSittingPose()) {
                        sittingRotationSkirt.setRotateAngleX(-0.567f);
                    } else {
                        sittingRotationSkirt.setRotateAngleX(sittingRotationSkirt.getInitRotateAngleX());
                    }
                }
            }
        });
    }

    @Inject(method = "getSitSkirtRotationSwing", at = @At("HEAD"), cancellable = true, remap = false)
    private static void mss$getSitSkirtRotationSwing(CallbackInfoReturnable<IAnimation<Mob>> cir) {
        cir.setReturnValue(new IAnimation<>() {
            @Override
            public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Mob mob, HashMap<String, ModelRendererWrapper> modelMap) {
                IMaid maid = IMaid.convert(mob);
                if (maid == null || (mob.getVehicle() instanceof RideableSwordEntity)) return;

                ModelRendererWrapper sittingRotationSwingSkirt = modelMap.get("sittingRotationSwingSkirt");
                if (sittingRotationSwingSkirt != null && !(mob.getVehicle() instanceof RideableSwordEntity)) {
                    if (isPassengerMarisaBroom(mob) || mob.isPassenger() || maid.isMaidInSittingPose()) {
                        sittingRotationSwingSkirt.setRotateAngleX(-0.567f);
                        sittingRotationSwingSkirt.setRotateAngleZ(sittingRotationSwingSkirt.getInitRotateAngleZ());
                    } else {
                        sittingRotationSwingSkirt.setRotateAngleX(sittingRotationSwingSkirt.getInitRotateAngleX());
                        float rotationZ = (float) Math.sin(ageInTicks * 0.05) * 0.03f;
                        sittingRotationSwingSkirt.setRotateAngleZ(sittingRotationSwingSkirt.getInitRotateAngleZ() + rotationZ);
                    }
                }
            }
        });
    }

    @Inject(method = "getSitNoLeg", at = @At("HEAD"), cancellable = true, remap = false)
    private static void mss$getSitNoLeg(CallbackInfoReturnable<IAnimation<Mob>> cir) {
        cir.setReturnValue(new IAnimation<>() {
            @Override
            public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Mob mob, HashMap<String, ModelRendererWrapper> modelMap) {
                IMaid maid = IMaid.convert(mob);
                if (maid == null || (mob.getVehicle() instanceof RideableSwordEntity)) return;

                ModelRendererWrapper head = modelMap.get("head");
                ModelRendererWrapper legLeft = modelMap.get("legLeft");
                ModelRendererWrapper legRight = modelMap.get("legRight");
                ModelRendererWrapper armLeft = modelMap.get("armLeft");
                ModelRendererWrapper armRight = modelMap.get("armRight");

                // 头部复位
                if (head != null) {
                    head.setOffsetY(0);
                }

                if (isPassengerMarisaBroom(mob)) {
                    // 坐在扫帚上时，应用待命的动作
                    ridingBroomPosture(head, armLeft, armRight, legLeft, legRight);
                } else if (mob.isPassenger()) {
                    ridingPosture(legLeft, legRight);
                } else if (maid.isMaidInSittingPose()) {
                    sittingNoLegPosture(armLeft, armRight);
                }
            }
        });
    }

    @Inject(method = "getSitDefault", at = @At("HEAD"), cancellable = true, remap = false)
    private static void mss$getSitAnim(CallbackInfoReturnable<IAnimation<Mob>> cir) {
        cir.setReturnValue(new IAnimation<>() {
            @Override
            public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Mob mob, HashMap<String, ModelRendererWrapper> modelMap) {
                IMaid maid = IMaid.convert(mob);
                if (maid == null || (mob.getVehicle() instanceof RideableSwordEntity)) return;

                ModelRendererWrapper head = modelMap.get("head");
                ModelRendererWrapper legLeft = modelMap.get("legLeft");
                ModelRendererWrapper legRight = modelMap.get("legRight");
                ModelRendererWrapper armLeft = modelMap.get("armLeft");
                ModelRendererWrapper armRight = modelMap.get("armRight");

                // 头部复位
                if (head != null) {
                    head.setOffsetY(0);
                }

                if (isPassengerMarisaBroom(mob)) {
                    // 坐在扫帚上时，应用待命的动作
                    ridingBroomPosture(head, armLeft, armRight, legLeft, legRight);
                } else if (mob.isPassenger()) {
                    ridingPosture(legLeft, legRight);
                } else if (maid.isMaidInSittingPose() && !(mob.getVehicle() instanceof RideableSwordEntity)) {
                    sittingPosture(armLeft, armRight, legLeft, legRight);
                }
            }
        });
    }

    private static void ridingPosture(ModelRendererWrapper legLeft, ModelRendererWrapper legRight) {
        if (legLeft != null) {
            legLeft.setRotateAngleX(-1.134f);
            legLeft.setRotateAngleZ(-0.262f);
        }
        if (legRight != null) {
            legRight.setRotateAngleX(-1.134f);
            legRight.setRotateAngleZ(0.262f);
        }
        GlWrapper.translate(0, 0.3, 0);
    }

    private static void ridingBroomPosture(ModelRendererWrapper head, ModelRendererWrapper armLeft, ModelRendererWrapper armRight, ModelRendererWrapper legLeft, ModelRendererWrapper legRight) {
        sittingPosture(armLeft, armRight, legLeft, legRight);
        if (head != null) {
            head.setRotateAngleX((float) (head.getRotateAngleX() - 30 * Math.PI / 180));
            head.setOffsetY(0.0625f);
        }
        GlWrapper.rotate(30, 1, 0, 0);
        GlWrapper.translate(0, -0.4, -0.3);
    }

    private static void sittingPosture(ModelRendererWrapper armLeft, ModelRendererWrapper armRight, ModelRendererWrapper legLeft, ModelRendererWrapper legRight) {
        if (armLeft != null) {
            armLeft.setRotateAngleX(-0.798f);
            armLeft.setRotateAngleZ(0.274f);
        }
        if (armRight != null) {
            armRight.setRotateAngleX(-0.798f);
            armRight.setRotateAngleZ(-0.274f);
        }
        ridingPosture(legLeft, legRight);
    }

    private static void sittingNoLegPosture(ModelRendererWrapper armLeft, ModelRendererWrapper armRight) {
        if (armLeft != null) {
            armLeft.setRotateAngleX(-0.798f);
            armLeft.setRotateAngleZ(0.274f);
        }
        if (armRight != null) {
            armRight.setRotateAngleX(-0.798f);
            armRight.setRotateAngleZ(-0.274f);
        }
    }
}
