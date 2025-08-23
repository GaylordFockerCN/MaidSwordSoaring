package com.p1nero.maid_sword_soaring.utils;

import net.minecraft.world.phys.Vec3;

public class MathUtils {
    public static double getXRotOfVector(Vec3 vec) {
        Vec3 normalized = vec.normalize();
        return -(org.joml.Math.atan2(normalized.y, (double) ((float) org.joml.Math.sqrt(normalized.x * normalized.x + normalized.z * normalized.z))) * (180D / Math.PI));
    }

    public static double getYRotOfVector(Vec3 vec) {
        Vec3 normalized = vec.normalize();
        return org.joml.Math.atan2(normalized.z, normalized.x) * (180D / Math.PI) - (double) 90.0F;
    }
}
