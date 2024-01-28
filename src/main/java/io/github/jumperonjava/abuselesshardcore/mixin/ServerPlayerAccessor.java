package io.github.jumperonjava.abuselesshardcore.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerAccessor {
    @Shadow private int joinInvulnerabilityTicks;

    @Inject(method = "<init>",at = @At("RETURN"))
    void cancel(MinecraftServer server, ServerWorld world, GameProfile profile, CallbackInfo ci){
        if(world.getLevelProperties().isHardcore())
            joinInvulnerabilityTicks=0;
    }
}
