package io.github.jumperonjava.abuselesshardcore.mixin;

import io.github.jumperonjava.abuselesshardcore.AbuselessHardcore;
import io.github.jumperonjava.abuselesshardcore.interfaceinjection.LevelInfoDeleteWorld;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Shadow public abstract ServerWorld getServerWorld();

    @Inject(method = "onDeath",at = @At("HEAD"))
    private void onDeath(DamageSource damageSource, CallbackInfo ci){
        if(AbuselessHardcore.clientIsHardcore()){

        }
    }
}
