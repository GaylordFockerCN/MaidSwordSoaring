package com.p1nero.maid_sword_soaring.compat;

import com.github.tartaricacid.touhoulittlemaid.client.animation.HardcodedAnimationManger;
import com.p1nero.maid_sword_soaring.client.animation.SwordSoaringAnimation;
import com.p1nero.maid_sword_soaring.compat.task.SwordSoaringTask;
import com.github.tartaricacid.touhoulittlemaid.api.ILittleMaid;
import com.github.tartaricacid.touhoulittlemaid.api.LittleMaidExtension;
import com.github.tartaricacid.touhoulittlemaid.entity.task.TaskManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@LittleMaidExtension
public class LittleMaidSwordSoaringCompat implements ILittleMaid {

    @Override
    public void addMaidTask(TaskManager manager) {
        manager.add(new SwordSoaringTask());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addHardcodeAnimation(HardcodedAnimationManger manger) {
        manger.addMaidAnimation(new SwordSoaringAnimation());
    }

}
