package io.github.jumperonjava.abuselesshardcore.mixin.world;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Dynamic;
import io.github.jumperonjava.abuselesshardcore.interfaceinjection.LevelInfoDeleteWorld;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.resource.DataConfiguration;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.level.LevelInfo;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelInfo.class)
public abstract class LevelInfoMixin implements LevelInfoDeleteWorld {
    @Shadow @Final private DataConfiguration dataConfiguration;
    private static Dynamic<?> dynamic_;

    @Shadow public abstract GameRules getGameRules();

    @Shadow @Final private boolean allowCommands;
    @Shadow @Final private Difficulty difficulty;
    @Shadow @Final private boolean hardcore;
    @Shadow @Final private GameMode gameMode;
    @Shadow @Final private String name;
    @Unique
    private boolean ahc_deleteWorld;
    public void setAhc_deleteWorld(boolean ahc_deleteWorld){
        this.ahc_deleteWorld = ahc_deleteWorld;
    }
    public boolean getAhc_deleteWorld(){return ahc_deleteWorld;}
    @Inject(method = "fromDynamic",at=@At("HEAD"))
    private static void getDynamic(Dynamic<?> dynamic, DataConfiguration dataConfiguration, CallbackInfoReturnable<LevelInfo> cir){
        dynamic_ = dynamic;
    }
    @Redirect(method = "withCopiedGameRules",at = @At(value = "NEW",target = "(Ljava/lang/String;Lnet/minecraft/world/GameMode;ZLnet/minecraft/world/Difficulty;ZLnet/minecraft/world/GameRules;Lnet/minecraft/resource/DataConfiguration;)Lnet/minecraft/world/level/LevelInfo;"))
    private LevelInfo level_(
            String name,
            GameMode gameMode,
            boolean hardcore,
            Difficulty difficulty,
            boolean allowCommands,
            GameRules gameRules,
            DataConfiguration dataConfiguration
    )
    {
        var info = new LevelInfo(name, gameMode, hardcore, difficulty, allowCommands, gameRules, dataConfiguration);
        ((LevelInfoDeleteWorld)(Object)info).setAhc_deleteWorld(this.ahc_deleteWorld);
        return info;
    }

    @WrapOperation(method = "fromDynamic",at = @At(value = "NEW",target = "(Ljava/lang/String;Lnet/minecraft/world/GameMode;ZLnet/minecraft/world/Difficulty;ZLnet/minecraft/world/GameRules;Lnet/minecraft/resource/DataConfiguration;)Lnet/minecraft/world/level/LevelInfo;"))
    private static LevelInfo level(
            String name,
            GameMode gameMode,
            boolean hardcore,
            Difficulty difficulty,
            boolean allowCommands,
            GameRules gameRules,
            DataConfiguration dataConfiguration,
            Operation<LevelInfo> original
    ){
        var info = original.call(name, gameMode, hardcore, difficulty, allowCommands, gameRules, dataConfiguration);
        ((LevelInfoDeleteWorld)(Object)info).setAhc_deleteWorld(dynamic_.get("deleteWorld").asBoolean(false));
        return info;
    }

}
