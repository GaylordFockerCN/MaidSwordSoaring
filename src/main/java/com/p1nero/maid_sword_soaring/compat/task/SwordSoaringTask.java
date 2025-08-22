package com.p1nero.maid_sword_soaring.compat.task;

import com.p1nero.maid_sword_soaring.MaidSwordSoaringMod;
import com.github.tartaricacid.touhoulittlemaid.api.task.IMaidTask;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mojang.datafixers.util.Pair;
import com.p1nero.maid_sword_soaring.entity.ai.brain.task.MaidSwordSoaringBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class SwordSoaringTask implements IMaidTask {

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(MaidSwordSoaringMod.MOD_ID, "sword_soaring");

    private static final ItemStack ICON = Items.IRON_SWORD.getDefaultInstance();

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
    public boolean enablePanic(@NotNull EntityMaid maid) {
        return false;
    }

    @Override
    public @NotNull List<Pair<String, Predicate<EntityMaid>>> getConditionDescription(@NotNull EntityMaid maid) {
        return List.of(Pair.of("is_valid", (maid1 -> MaidSwordSoaringMod.isValidSword(maid1.getMainHandItem()))));
    }

    @Override
    public @NotNull List<Pair<Integer, BehaviorControl<? super EntityMaid>>> createBrainTasks(@NotNull EntityMaid maid) {
        Pair<Integer, BehaviorControl<? super EntityMaid>> swordSoaringTask = Pair.of(5, new MaidSwordSoaringBehavior());
        return List.of(swordSoaringTask);
    }
}
