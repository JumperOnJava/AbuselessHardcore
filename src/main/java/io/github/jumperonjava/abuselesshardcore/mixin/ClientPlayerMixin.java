package io.github.jumperonjava.abuselesshardcore.mixin;

import io.github.jumperonjava.abuselesshardcore.interfaceinjection.LevelInfoDeleteWorld;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.level.LevelInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerMixin implements LevelInfoDeleteWorld {
    private boolean ahc_deleteWorld;
    public void setAhc_deleteWorld(boolean ahc_deleteWorld){
        this.ahc_deleteWorld = ahc_deleteWorld;
    }
    public boolean getAhc_deleteWorld(){return ahc_deleteWorld;}
}
