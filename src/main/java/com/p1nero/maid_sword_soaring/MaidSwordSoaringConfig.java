package com.p1nero.maid_sword_soaring;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = MaidSwordSoaringMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MaidSwordSoaringConfig
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue SWORD_GLOWING;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEMS_SWORD;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEMS_NO_SWORD;

    static final ForgeConfigSpec SPEC;

    static {
        ITEMS_SWORD = BUILDER
                .comment("A list of items considered as sword.")
                .defineListAllowEmpty("items_sword", List.of("minecraft:trident"), MaidSwordSoaringConfig::validateItemName);
        ITEMS_NO_SWORD = BUILDER
                .comment("A list of items NOT considered as sword.")
                .defineListAllowEmpty("items_not_sword", List.of("minecraft:iron_ingot"), MaidSwordSoaringConfig::validateItemName);
        SWORD_GLOWING = BUILDER
                .comment("Should sword glowing")
                .define("sword_glowing", false);
        SPEC = BUILDER.build();
    }

    public static Set<Item> swordItems = new HashSet<>();
    public static Set<Item> notSwordItems = new HashSet<>();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(ResourceLocation.parse(itemName));
    }

}