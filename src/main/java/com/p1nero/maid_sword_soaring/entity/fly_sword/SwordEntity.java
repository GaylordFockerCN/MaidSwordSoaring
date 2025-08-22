package com.p1nero.maid_sword_soaring.entity.fly_sword;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public abstract class SwordEntity extends PathfinderMob implements OwnableEntity {
    private static final EntityDataAccessor<Optional<UUID>> RIDER_UUID = SynchedEntityData.defineId(SwordEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<ItemStack> ITEM_STACK = SynchedEntityData.defineId(SwordEntity.class, EntityDataSerializers.ITEM_STACK);

    protected SwordEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(RIDER_UUID, Optional.empty());
        this.getEntityData().define(ITEM_STACK, ItemStack.EMPTY);
    }

    @Override
    public @Nullable UUID getOwnerUUID() {
        return this.entityData.get(RIDER_UUID).orElse(null);
    }

    public ItemStack getItemStack() {
        return this.getEntityData().get(ITEM_STACK);
    }

    public void setItemStack(ItemStack itemStack) {
        this.getEntityData().set(ITEM_STACK, itemStack);
    }

    public void setOwner(LivingEntity rider) {
        this.getEntityData().set(RIDER_UUID, Optional.of(rider.getUUID()));
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }
}
