package com.p1nero.maid_sword_soaring.entity.fly_sword.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.p1nero.maid_sword_soaring.entity.fly_sword.SwordEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class SwordEntityRenderer<T extends SwordEntity> extends EntityRenderer<T> {
    public SwordEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public boolean shouldRender(T pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public void render(@NotNull T swordEntity, float pEntityYaw, float pPartialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        poseStack.pushPose();
        swordEntity.setRenderPose(poseStack, pEntityYaw, pPartialTick);
        Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(swordEntity, swordEntity.getItemStack(), ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, false, poseStack, pBuffer, pPackedLight);
        poseStack.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T pEntity) {
        return ResourceLocation.parse("");
    }
}
