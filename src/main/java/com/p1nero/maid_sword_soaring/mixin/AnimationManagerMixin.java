package com.p1nero.maid_sword_soaring.mixin;

import com.github.tartaricacid.touhoulittlemaid.api.entity.IMaid;
import com.github.tartaricacid.touhoulittlemaid.client.animation.gecko.AnimationManager;
import com.github.tartaricacid.touhoulittlemaid.client.entity.GeckoMaidEntity;
import com.github.tartaricacid.touhoulittlemaid.geckolib3.core.PlayState;
import com.github.tartaricacid.touhoulittlemaid.geckolib3.core.builder.AnimationBuilder;
import com.github.tartaricacid.touhoulittlemaid.geckolib3.core.builder.ILoopType;
import com.github.tartaricacid.touhoulittlemaid.geckolib3.core.event.predicate.AnimationEvent;
import com.p1nero.maid_sword_soaring.entity.fly_sword.RideableSwordEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;

/**
 * 防止手部动画覆盖
 */
@Mixin(AnimationManager.class)
public class AnimationManagerMixin {

    @Inject(method = "predicateSwing", at = @At("HEAD"), cancellable = true, remap = false)
    private void mss$predicateSwing(AnimationEvent<GeckoMaidEntity<?>> event, CallbackInfoReturnable<PlayState> cir) {
        IMaid maid = event.getAnimatableEntity().getMaid();
        if (maid != null && maid.asEntity().swinging && maid.asEntity().swingTime != 0 && !maid.asEntity().isSleeping() && event.getAnimatableEntity().getEntity().getVehicle() instanceof RideableSwordEntity) {
            cir.setReturnValue(playAnimation(event, "shoot_fly_sword", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        }
    }

    @Inject(method = "predicateMainhandHold", at = @At("HEAD"), cancellable = true, remap = false)
    private void mss$predicateHand(AnimationEvent<GeckoMaidEntity<?>> event, CallbackInfoReturnable<PlayState> cir) {
        if(event.getAnimatableEntity().getEntity().getVehicle() instanceof RideableSwordEntity) {
            cir.setReturnValue(playAnimation(event, "empty", ILoopType.EDefaultLoopTypes.LOOP));
        }
    }


    @Inject(method = "predicateOffhandHold", at = @At("HEAD"), cancellable = true, remap = false)
    private void mss$OffhandHold(AnimationEvent<GeckoMaidEntity<?>> event, CallbackInfoReturnable<PlayState> cir) {
        if(event.getAnimatableEntity().getEntity().getVehicle() instanceof RideableSwordEntity) {
            cir.setReturnValue(playAnimation(event, "empty", ILoopType.EDefaultLoopTypes.LOOP));
        }
    }

    @Nonnull
    private static PlayState playAnimation(AnimationEvent<?> event, String animationName, ILoopType loopType) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation(animationName, loopType));
        return PlayState.CONTINUE;
    }
}
