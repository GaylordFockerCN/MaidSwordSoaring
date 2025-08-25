package com.p1nero.maid_sword_soaring.client;

import com.p1nero.maid_sword_soaring.MaidSwordSoaringMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

public class MaidSwordSoaringSounds {

    public static final net.neoforged.neoforge.registries.DeferredRegister<SoundEvent> SOUND_EVENTS =
            net.neoforged.neoforge.registries.DeferredRegister.create(Registries.SOUND_EVENT, MaidSwordSoaringMod.MOD_ID);
    public static DeferredHolder<SoundEvent, SoundEvent> HIT = registerSoundEvent("hit");
    public static DeferredHolder<SoundEvent, SoundEvent> SHOOT = registerSoundEvent("shoot");
    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MaidSwordSoaringMod.MOD_ID, name)));
    }
}
