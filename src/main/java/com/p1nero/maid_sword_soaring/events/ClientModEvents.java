package com.p1nero.maid_sword_soaring.events;

import com.p1nero.maid_sword_soaring.MaidSwordSoaringMod;
import com.p1nero.maid_sword_soaring.entity.MaidSwordSoaringEntities;
import com.p1nero.maid_sword_soaring.entity.fly_sword.client.SwordEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = MaidSwordSoaringMod.MOD_ID, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
        EntityRenderers.register(MaidSwordSoaringEntities.RIDEABLE_SWORD.get(), SwordEntityRenderer::new);
        EntityRenderers.register(MaidSwordSoaringEntities.FLY_SWORD.get(), SwordEntityRenderer::new);
    }
}
