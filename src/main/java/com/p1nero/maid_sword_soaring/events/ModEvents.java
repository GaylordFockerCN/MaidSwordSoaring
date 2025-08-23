package com.p1nero.maid_sword_soaring.events;

import com.p1nero.maid_sword_soaring.MaidSwordSoaringMod;
import com.p1nero.maid_sword_soaring.entity.MaidSwordSoaringEntities;
import com.p1nero.maid_sword_soaring.entity.fly_sword.SwordEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MaidSwordSoaringMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(MaidSwordSoaringEntities.RIDEABLE_SWORD.get(), SwordEntity.getDefaultAttribute());
    }
}
