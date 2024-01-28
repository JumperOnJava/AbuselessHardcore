package io.github.jumperonjava.abuselesshardcore.mixin;

import io.github.jumperonjava.abuselesshardcore.AbuselessHardcore;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.OpenToLanScreen;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(OpenToLanScreen.class)
public class OpenToLanScreenMixin {
    @Redirect(method = "init",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/widget/CyclingButtonWidget$Builder;values([Ljava/lang/Object;)Lnet/minecraft/client/gui/widget/CyclingButtonWidget$Builder;"))
    private CyclingButtonWidget.Builder<GameMode> removeCreative(CyclingButtonWidget.Builder instance, Object[] values){
        if(values[0] instanceof GameMode)
            if(AbuselessHardcore.clientIsHardcore()){
                return instance.values(new GameMode[]{GameMode.SURVIVAL, GameMode.SPECTATOR, GameMode.ADVENTURE});
            }
        return instance.values(values);
    }
    @Redirect(method = "init", at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/screen/OpenToLanScreen;addDrawableChild(Lnet/minecraft/client/gui/Element;)Lnet/minecraft/client/gui/Element;",ordinal = 1))
    private Element removeButtonIfHardcode(OpenToLanScreen instance, Element element) {
        if (AbuselessHardcore.clientIsHardcore())
            return null;
        return element;
    }
    @ModifyConstant(method = "init",constant = @Constant(intValue = 155,ordinal = 0))
    private int moveButton(int i){
        if(AbuselessHardcore.clientIsHardcore())
           return 75;
        return i;
    }
}
