package com.p1nero.maid_sword_soaring.compat;

import com.p1nero.maid_sword_soaring.compat.task.SwordSoaringTask;
import com.github.tartaricacid.touhoulittlemaid.api.ILittleMaid;
import com.github.tartaricacid.touhoulittlemaid.api.LittleMaidExtension;
import com.github.tartaricacid.touhoulittlemaid.entity.task.TaskManager;
@LittleMaidExtension
public class LittleMaidCompat implements ILittleMaid {

    @Override
    public void addMaidTask(TaskManager manager) {
        manager.add(new SwordSoaringTask());
    }

}
