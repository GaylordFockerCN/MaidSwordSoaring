package com.p1nero.maid_sword_soaring.mixin;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.client.resource.GeckoModelLoader;
import com.p1nero.maid_sword_soaring.MaidSwordSoaringMod;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.io.InputStream;

import static com.github.tartaricacid.touhoulittlemaid.client.resource.GeckoModelLoader.mergeAnimationFile;

@Mixin(GeckoModelLoader.class)
public class GeckoLoaderMixin {
    @Inject(method = "loadDefaultAnimation", at = @At("TAIL"), remap = false)
    private static void loadAnim(CallbackInfo ci) {
        ResourceLocation file = ResourceLocation.fromNamespaceAndPath(MaidSwordSoaringMod.MOD_ID, "animations/sword_soaring.animation.json");
        try (InputStream stream = Minecraft.getInstance().getResourceManager().open(file)) {
            mergeAnimationFile(stream, GeckoModelLoader.DEFAULT_MAID_ANIMATION_FILE);
        } catch (IOException e) {
            TouhouLittleMaid.LOGGER.error("Failed to load animation file", e);
        }
    }
}
