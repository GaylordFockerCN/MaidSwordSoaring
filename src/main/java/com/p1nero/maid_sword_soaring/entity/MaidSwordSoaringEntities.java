package com.p1nero.maid_sword_soaring.entity;

import com.p1nero.maid_sword_soaring.MaidSwordSoaringMod;
import com.p1nero.maid_sword_soaring.entity.fly_sword.FlySwordEntity;
import com.p1nero.maid_sword_soaring.entity.fly_sword.RideableSwordEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MaidSwordSoaringEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MaidSwordSoaringMod.MOD_ID);

    public static final RegistryObject<EntityType<RideableSwordEntity>> RIDEABLE_SWORD = register("rideable_sword", EntityType.Builder.of(RideableSwordEntity::new, MobCategory.CREATURE), 1F, 2);
    public static final RegistryObject<EntityType<FlySwordEntity>> FLY_SWORD = register("fly_sword", EntityType.Builder.of(FlySwordEntity::new, MobCategory.CREATURE), 0.1F, 0.1F);

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryName, EntityType.Builder<T> entityTypeBuilder) {
        return ENTITIES.register(registryName, () -> entityTypeBuilder.build(MaidSwordSoaringMod.MOD_ID + ":" + registryName));
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryName, EntityType.Builder<T> entityTypeBuilder, float xz, float y) {
        return ENTITIES.register(registryName, () -> entityTypeBuilder.sized(xz,y).build(MaidSwordSoaringMod.MOD_ID + ":" + registryName));
    }

}
