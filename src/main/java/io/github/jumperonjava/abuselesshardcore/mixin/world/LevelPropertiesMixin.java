package io.github.jumperonjava.abuselesshardcore.mixin.world;

import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import io.github.jumperonjava.abuselesshardcore.interfaceinjection.LevelInfoDeleteWorld;
import net.minecraft.nbt.NbtByte;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtFloat;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;
import net.minecraft.world.level.storage.SaveVersionInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelProperties.class)
public class LevelPropertiesMixin implements LevelInfoDeleteWorld {
    @Shadow private LevelInfo levelInfo;

    public void setAhc_deleteWorld(boolean ahc_deleteWorld){
        ((LevelInfoDeleteWorld)(Object)levelInfo).setAhc_deleteWorld(ahc_deleteWorld);
    }
    public boolean getAhc_deleteWorld(){return ((LevelInfoDeleteWorld)(Object)levelInfo).getAhc_deleteWorld();}
    @Inject(method = "updateProperties",at=@At("RETURN"))
    void addDel(DynamicRegistryManager registryManager, NbtCompound levelNbt, NbtCompound playerNbt, CallbackInfo ci){
        levelNbt.putBoolean("deleteWorld", getAhc_deleteWorld());
    }
}
