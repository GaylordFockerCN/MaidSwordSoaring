package com.p1nero.maid_sword_soaring;

import com.mojang.logging.LogUtils;
import com.p1nero.maid_sword_soaring.entity.MaidSwordSoaringEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mod(MaidSwordSoaringMod.MOD_ID)
public class MaidSwordSoaringMod {
    public static final String MOD_ID = "maid_sword_soaring";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MaidSwordSoaringMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        MaidSwordSoaringEntities.ENTITIES.register(modEventBus);
        context.registerConfig(ModConfig.Type.COMMON, MaidSwordSoaringConfig.SPEC);
    }

    public static boolean isValidSword(ItemStack sword){
        if(MaidSwordSoaringConfig.swordItems.isEmpty()){
            MaidSwordSoaringConfig.swordItems = MaidSwordSoaringConfig.ITEMS_SWORD.get().stream()
                    .map(itemName -> ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(itemName)))
                    .collect(Collectors.toSet());
            MaidSwordSoaringConfig.notSwordItems = MaidSwordSoaringConfig.ITEMS_NO_SWORD.get().stream()
                    .map(itemName -> ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(itemName)))
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
