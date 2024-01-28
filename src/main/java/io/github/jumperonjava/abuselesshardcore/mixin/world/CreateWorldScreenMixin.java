package io.github.jumperonjava.abuselesshardcore.mixin.world;

import com.mojang.serialization.Lifecycle;
import io.github.jumperonjava.abuselesshardcore.interfaceinjection.LevelInfoDeleteWorld;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.world.GeneratorOptionsHolder;
import net.minecraft.registry.CombinedDynamicRegistries;
import net.minecraft.registry.ServerDynamicRegistryType;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin implements LevelInfoDeleteWorld {
    @Unique
    private boolean ahc_deleteWorld;
    public void setAhc_deleteWorld(boolean ahc_deleteWorld){
        this.ahc_deleteWorld = ahc_deleteWorld;
    }
    public boolean getAhc_deleteWorld(){return ahc_deleteWorld;}

    @Inject(method = "startServer",locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelProperties;<init>(Lnet/minecraft/world/level/LevelInfo;Lnet/minecraft/world/gen/GeneratorOptions;Lnet/minecraft/world/level/LevelProperties$SpecialProperty;Lcom/mojang/serialization/Lifecycle;)V",shift = At.Shift.BEFORE))
    void addDel(LevelProperties.SpecialProperty specialProperty, CombinedDynamicRegistries<ServerDynamicRegistryType> combinedDynamicRegistries, Lifecycle lifecycle, CallbackInfo ci, Optional optional, boolean bl, GeneratorOptionsHolder generatorOptionsHolder, LevelInfo levelInfo){
        ((LevelInfoDeleteWorld)(Object)levelInfo).setAhc_deleteWorld(ahc_deleteWorld);
    }
}
