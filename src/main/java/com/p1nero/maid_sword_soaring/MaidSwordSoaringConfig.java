package com.p1nero.maid_sword_soaring;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MaidSwordSoaringConfig
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEMS_SWORD;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEMS_NO_SWORD;
    public static final ModConfigSpec.BooleanValue SWORD_GLOWING;


    static final ModConfigSpec SPEC;

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
        return obj instanceof final String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

}