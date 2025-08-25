//package com.p1nero.maid_sword_soaring.events;
//
//import com.github.tartaricacid.touhoulittlemaid.api.event.client.DefaultGeckoAnimationEvent;
//import com.github.tartaricacid.touhoulittlemaid.geckolib3.file.AnimationFile;
//import com.p1nero.maid_sword_soaring.MaidSwordSoaringMod;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
//@Mod.EventBusSubscriber(modid = MaidSwordSoaringMod.MOD_ID, value = Dist.CLIENT)
//public class ClientForgeEvents {
//
//    @SubscribeEvent
//    public static void onAdd(DefaultGeckoAnimationEvent event){
//        AnimationFile maidAnimationFile = event.getMaidAnimationFile();
//        event.addAnimation(maidAnimationFile, ResourceLocation.fromNamespaceAndPath(MaidSwordSoaringMod.MOD_ID, "animations/sword_soaring.animation.json"));
//    }
//}
