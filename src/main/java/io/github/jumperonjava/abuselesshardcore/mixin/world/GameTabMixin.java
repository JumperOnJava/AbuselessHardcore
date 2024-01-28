package io.github.jumperonjava.abuselesshardcore.mixin.world;

import io.github.jumperonjava.abuselesshardcore.interfaceinjection.LevelInfoDeleteWorld;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.Positioner;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(targets = {"net.minecraft.client.gui.screen.world.CreateWorldScreen$GameTab"})
public class GameTabMixin {
    @Unique
    private boolean superhardcore=false;

    @Inject(method = "<init>", at=@At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    void f(CreateWorldScreen createWorldScreen, CallbackInfo ci, GridWidget.Adder adder, Positioner positioner, GridWidget.Adder adder2, CyclingButtonWidget cyclingButtonWidget, CyclingButtonWidget cyclingButtonWidget2){
        var superHardWidget = (CyclingButtonWidget.onOffBuilder(false).build(-10000, 156, 210, 20, Text.translatable("worldCreation.delete"), (button, bool) -> {
           this.superhardcore = bool;
            System.out.println(bool);
            ((LevelInfoDeleteWorld)createWorldScreen).setAhc_deleteWorld(createWorldScreen.getWorldCreator().isHardcore() && bool);
        }));
        adder.add(superHardWidget);
        createWorldScreen.getWorldCreator().addListener((worldCreator)->{
            ((LevelInfoDeleteWorld)createWorldScreen).setAhc_deleteWorld(worldCreator.isHardcore() && superhardcore);
            superHardWidget.active=(worldCreator.isHardcore());
        });
    }
}
