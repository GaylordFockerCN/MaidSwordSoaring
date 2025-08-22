package com.p1nero.maid_sword_soaring.events;

import com.p1nero.maid_sword_soaring.MaidSwordSoaringMod;
import com.p1nero.maid_sword_soaring.entity.MaidSwordSoaringEntities;
import com.p1nero.maid_sword_soaring.entity.fly_sword.client.SwordEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MaidSwordSoaringMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
        EntityRenderers.register(MaidSwordSoaringEntities.RIDEABLE_SWORD.get(), SwordEntityRenderer::new);
    }
}
