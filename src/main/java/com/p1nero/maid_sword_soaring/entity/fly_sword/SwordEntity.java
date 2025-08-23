package com.p1nero.maid_sword_soaring.entity.fly_sword;

import com.mojang.blaze3d.vertex.PoseStack;
import com.p1nero.maid_sword_soaring.MaidSwordSoaringMod;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import java.util.Optional;
import java.util.UUID;

public abstract class SwordEntity extends PathfinderMob {
    private static final EntityDataAccessor<Optional<UUID>> RIDER_UUID = SynchedEntityData.defineId(SwordEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<ItemStack> ITEM_STACK = SynchedEntityData.defineId(SwordEntity.class, EntityDataSerializers.ITEM_STACK);

    protected SwordEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier getDefaultAttribute() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 19.9F)
                .add(Attributes.ATTACK_DAMAGE, 6.0f)
                .build();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(RIDER_UUID, Optional.empty());
        this.getEntityData().define(ITEM_STACK, ItemStack.EMPTY);
    }

    public @Nullable UUID getOwnerUUID() {
        return this.entityData.get(RIDER_UUID).orElse(null);
    }

    @Nullable
    public LivingEntity getOwner() {
        try {
            UUID uuid = this.getOwnerUUID();
            if(uuid != null){
                Player player = this.level().getPlayerByUUID(uuid);
                if(player == null){
                    if(this.level() instanceof ServerLevel serverLevel){
                        return serverLevel.getEntity(uuid) instanceof LivingEntity livingEntity ? livingEntity : null;
                    }
                } else {
                    return player;
                }
            }
            return null;
        } catch (Exception e) {
            MaidSwordSoaringMod.LOGGER.error("Error in get sword's owner", e);
            return null;
        }
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

    @OnlyIn(Dist.CLIENT)
    public void setRenderPose(PoseStack poseStack, float yRot, float partialTick){
        Vec3 view = this.calculateViewVector(0, yRot);
        poseStack.mulPose(new Quaternionf().rotateTo((float) 0, 1F, -0.1F, (float) view.x, (float) view.y, (float) view.z));
    }
}
