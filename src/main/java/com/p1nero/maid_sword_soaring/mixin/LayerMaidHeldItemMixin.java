package com.p1nero.maid_sword_soaring.mixin;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.layer.LayerMaidHeldItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.p1nero.maid_sword_soaring.entity.fly_sword.RideableSwordEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 御剑飞行时取消手上物品渲染（因为踩在脚下了）
 */
@Mixin(LayerMaidHeldItem.class)
public class LayerMaidHeldItemMixin {
    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true, remap = false)
    private void mss$renderArmWithItem(Mob maid, ItemStack itemStack, ItemDisplayContext transformTypeIn, HumanoidArm handSide, PoseStack poseStack, MultiBufferSource typeBuffer, int combinedLightIn, CallbackInfo ci) {
        if(maid.getVehicle() instanceof RideableSwordEntity) {
            ci.cancel();
        }
    }
}
