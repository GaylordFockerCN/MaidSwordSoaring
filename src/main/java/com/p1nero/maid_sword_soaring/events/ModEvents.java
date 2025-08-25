package com.p1nero.maid_sword_soaring.events;

import com.p1nero.maid_sword_soaring.MaidSwordSoaringMod;
import com.p1nero.maid_sword_soaring.compat.ArmourersWorkshopCompat;
import com.p1nero.maid_sword_soaring.entity.MaidSwordSoaringEntities;
import com.p1nero.maid_sword_soaring.entity.fly_sword.SwordEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = MaidSwordSoaringMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(MaidSwordSoaringEntities.RIDEABLE_SWORD.get(), SwordEntity.getDefaultAttribute());
        event.put(MaidSwordSoaringEntities.FLY_SWORD.get(), SwordEntity.getDefaultAttribute());
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        MaidSwordSoaringMod.runInArmourersWorkshopLoaded(() -> ArmourersWorkshopCompat::registerSwordSoaringItemProvider);
    }

}
