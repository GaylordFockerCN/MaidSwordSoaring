package com.p1nero.maid_sword_soaring.mixin;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.geckolayer.GeckoLayerMaidHeld;
import com.github.tartaricacid.touhoulittlemaid.geckolib3.geo.animated.ILocationModel;
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

@Mixin(GeckoLayerMaidHeld.class)
public class GeckoLayerMaidHeldMixin <T extends Mob> {
    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true, remap = false)
    private void mss$renderArmWithItem(T livingEntity, ItemStack itemStack, ILocationModel geoModel, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource bufferSource, int light, CallbackInfo ci) {
        if(livingEntity.getVehicle() instanceof RideableSwordEntity) {
            ci.cancel();
        }
    }
}
