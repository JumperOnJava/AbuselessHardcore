package io.github.jumperonjava.abuselesshardcore.mixin;

import io.github.jumperonjava.abuselesshardcore.LastSessionWorld;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;

@Mixin(LevelStorage.Session.class)
public class SessionMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    void saveLastLoadedWorldId(LevelStorage levelStorage, String directoryName, Path path, CallbackInfo ci) {
        LastSessionWorld.directoryName = directoryName;
    }
}
