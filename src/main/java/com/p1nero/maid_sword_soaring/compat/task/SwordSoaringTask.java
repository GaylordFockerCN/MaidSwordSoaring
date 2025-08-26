package com.p1nero.maid_sword_soaring.compat.task;

import com.github.tartaricacid.touhoulittlemaid.api.task.IAttackTask;
import com.google.common.collect.Lists;
import com.p1nero.maid_sword_soaring.MaidSwordSoaringMod;
import com.github.tartaricacid.touhoulittlemaid.api.task.IMaidTask;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mojang.datafixers.util.Pair;
import com.p1nero.maid_sword_soaring.entity.ai.brain.behavior.MaidShootFlySwordBehavior;
import com.p1nero.maid_sword_soaring.entity.ai.brain.behavior.MaidSwordSoaringBehavior;
import com.p1nero.maid_sword_soaring.entity.fly_sword.SwordEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class SwordSoaringTask implements IAttackTask {

    private static final int MAX_STOP_ATTACK_DISTANCE = 16;

    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(MaidSwordSoaringMod.MOD_ID, "sword_soaring");

    public static final ItemStack ICON = Items.IRON_SWORD.getDefaultInstance();

    @Override
    public @NotNull ResourceLocation getUid() {
        return UID;
    }

    @Override
    public @NotNull ItemStack getIcon() {
        return ICON;
    }

    @Override
    @Nullable
    public SoundEvent getAmbientSound(@NotNull EntityMaid maid) {
        return null;
    }

    @Override
    public @NotNull List<Pair<String, Predicate<EntityMaid>>> getConditionDescription(@NotNull EntityMaid maid) {
        return List.of(Pair.of("is_valid", (maid1 -> MaidSwordSoaringMod.isValidSword(maid1.getMainHandItem()))));
    }

    @Override
    public @NotNull List<Pair<Integer, BehaviorControl<? super EntityMaid>>> createBrainTasks(@NotNull EntityMaid maid) {
        Pair<Integer, BehaviorControl<? super EntityMaid>> swordSoaringTask = Pair.of(5, new MaidSwordSoaringBehavior());
        return Lists.newArrayList(swordSoaringTask);
    }

    @Override
    public @NotNull List<Pair<Integer, BehaviorControl<? super EntityMaid>>> createRideBrainTasks(@NotNull EntityMaid maid) {
        BehaviorControl<EntityMaid> supplementedTask = StartAttacking.create(this::hasAssaultWeapon, IAttackTask::findFirstValidAttackTarget);
        BehaviorControl<EntityMaid> findTargetTask = StopAttackingIfTargetInvalid.create(target -> !hasAssaultWeapon(maid) || farAway(target, maid));
        BehaviorControl<EntityMaid> shootFlySword = MaidShootFlySwordBehavior.create(40);
        return Lists.newArrayList(Pair.of(5, supplementedTask), Pair.of(5, findTargetTask), Pair.of(5, shootFlySword));
    }

    @Override
    public boolean isEnable(@NotNull EntityMaid maid) {
        return hasAssaultWeapon(maid);
    }

    @Override
    public boolean canAttack(@NotNull EntityMaid maid, @NotNull LivingEntity target) {
        if(target instanceof SwordEntity) {
            return false;
        }
        return IAttackTask.super.canAttack(maid, target);
    }

    @Override
    public boolean isWeapon(@NotNull EntityMaid maid, @NotNull ItemStack stack) {
        return MaidSwordSoaringMod.isValidSword(stack);
    }

    private boolean hasAssaultWeapon(EntityMaid maid) {
        return MaidSwordSoaringMod.isValidSword(maid.getMainHandItem());
    }

    private boolean farAway(LivingEntity target, EntityMaid maid) {
        return maid.distanceTo(target) > MAX_STOP_ATTACK_DISTANCE;
    }
}
