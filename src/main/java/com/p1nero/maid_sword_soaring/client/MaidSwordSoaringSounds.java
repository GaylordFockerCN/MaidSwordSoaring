package com.p1nero.maid_sword_soaring.client;

import com.p1nero.maid_sword_soaring.MaidSwordSoaringMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MaidSwordSoaringSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MaidSwordSoaringMod.MOD_ID);
    public static RegistryObject<SoundEvent> HIT = registerSoundEvent("hit");
    public static RegistryObject<SoundEvent> SHOOT = registerSoundEvent("shoot");
    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MaidSwordSoaringMod.MOD_ID, name)));
    }
}
