package com.p1nero.maid_sword_soaring;

import com.mojang.logging.LogUtils;
import com.p1nero.maid_sword_soaring.client.MaidSwordSoaringSounds;
import com.p1nero.maid_sword_soaring.entity.MaidSwordSoaringEntities;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mod(MaidSwordSoaringMod.MOD_ID)
public class MaidSwordSoaringMod {
    public static final String MOD_ID = "maid_sword_soaring";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MaidSwordSoaringMod(IEventBus bus,  ModContainer modContainer) {
        MaidSwordSoaringEntities.ENTITIES.register(bus);
        MaidSwordSoaringSounds.SOUND_EVENTS.register(bus);
        modContainer.registerConfig(ModConfig.Type.COMMON, MaidSwordSoaringConfig.SPEC);
    }

    public static boolean isValidSword(ItemStack sword){
        if(MaidSwordSoaringConfig.swordItems.isEmpty()){
            MaidSwordSoaringConfig.swordItems = MaidSwordSoaringConfig.ITEMS_SWORD.get().stream()
                    .map(itemName -> BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemName)))
                    .collect(Collectors.toSet());
            MaidSwordSoaringConfig.notSwordItems = MaidSwordSoaringConfig.ITEMS_NO_SWORD.get().stream()
                    .map(itemName -> BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemName)))
                    .collect(Collectors.toSet());
        }
        if (MaidSwordSoaringConfig.notSwordItems.contains(sword.getItem())) {
            return false;
        }
        return sword.getItem() instanceof SwordItem || MaidSwordSoaringConfig.swordItems.contains(sword.getItem());
    }

    public static void runInArmourersWorkshopLoaded(Supplier<Runnable> handler) {
        if (ModList.get().isLoaded("armourers_workshop")) {
            handler.get().run();
        }
    }
}
