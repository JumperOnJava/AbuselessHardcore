package io.github.jumperonjava.abuselesshardcore.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.jumperonjava.abuselesshardcore.LastSessionWorld;
import io.github.jumperonjava.abuselesshardcore.interfaceinjection.LevelInfoDeleteWorld;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathScreen.class)
public class DeathScreenMixin {
    @Shadow @Final private boolean isHardcore;
    private boolean del;
    @WrapOperation(method = "init",at=@At(value = "INVOKE",target = "Lnet/minecraft/text/Text;translatable(Ljava/lang/String;)Lnet/minecraft/text/MutableText;"))
    MutableText changeText(String key, Operation<MutableText> original){
        update();
        if(isHardcore && del)
            return Text.translatable("deathScreen.deleteworld");
        else
            return original.call(key);
    }
    @WrapOperation(method = "init",at=@At(value = "INVOKE",target = "Lnet/minecraft/client/gui/screen/DeathScreen;addDrawableChild(Lnet/minecraft/client/gui/Element;)Lnet/minecraft/client/gui/Element;",ordinal = 0))
    Element removeSpectate(DeathScreen instance, Element element, Operation<Element> original){
        update();
        if(isHardcore && del)
            return new ButtonWidget.Builder(Text.empty(),(b)->{}).dimensions(0,0,0,0).build();
        else
             return original.call(instance,element);
    }
    @Inject(method = "quitLevel", at=@At("HEAD"))
    void saveDelete(CallbackInfo ci){
        update();
    }
    void update(){
        var p = MinecraftClient.getInstance().player;
        this.del = ((LevelInfoDeleteWorld)p).getAhc_deleteWorld();
    }

    @Inject(method = "quitLevel",at= @At(value = "RETURN"),cancellable = true)
    void quit(CallbackInfo ci){
        if(isHardcore && this.del) {
            this.del = false;
            try {
                MinecraftClient.getInstance().getLevelStorage().createSession(LastSessionWorld.directoryName).deleteSessionLock();
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ci.cancel();
        }
    }

}
