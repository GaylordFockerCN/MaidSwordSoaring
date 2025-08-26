package com.p1nero.maid_sword_soaring.events;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.p1nero.maid_sword_soaring.MaidSwordSoaringMod;
import com.p1nero.maid_sword_soaring.compat.task.SwordSoaringTask;
import com.p1nero.maid_sword_soaring.entity.fly_sword.FlySwordEntity;
import com.p1nero.maid_sword_soaring.utils.MathUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MaidSwordSoaringMod.MOD_ID, value = Dist.CLIENT)
public class ForgeEvents {

    @SubscribeEvent
    public static void onLivingHurt(LivingAttackEvent event) {
        if(event.getEntity() instanceof EntityMaid maid) {
            if(maid.getTask().getUid() == SwordSoaringTask.UID && MaidSwordSoaringMod.isValidSword(maid.getMainHandItem()) && !maid.level().isClientSide) {
                if(event.getSource().is(DamageTypeTags.IS_FALL)) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        //受伤发六脉神剑
        if(event.getEntity() instanceof EntityMaid maid) {
            if(maid.getTask().getUid() == SwordSoaringTask.UID && MaidSwordSoaringMod.isValidSword(maid.getMainHandItem()) && !maid.level().isClientSide){
                for (int i = 0; i < 6; i++) {
                    FlySwordEntity flySwordEntity = new FlySwordEntity(maid, null);
                    flySwordEntity.setPos(maid.getX(), maid.getY() + maid.getEyeHeight(), maid.getZ());

                    float angle = (float) (i * Math.PI / 3);
                    float speed = 0.7F;

                    flySwordEntity.setDeltaMovement(Math.sin(angle) * speed, 0, Math.cos(angle) * speed);
                    flySwordEntity.setYRot((float) MathUtils.getYRotOfVector(flySwordEntity.getDeltaMovement()));
                    flySwordEntity.setDelay(0);
                    flySwordEntity.setLifeTime(60);
                    flySwordEntity.setSpeed(speed);
                    maid.level().addFreshEntity(flySwordEntity);
                }
                if(!event.getSource().is(DamageTypeTags.IS_FALL)){
                    event.setAmount(event.getAmount() * 0.5F);
                }
                maid.level().playSound(null, maid.getX(), maid.getY(), maid.getZ(), SoundEvents.TOTEM_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
    }
}
