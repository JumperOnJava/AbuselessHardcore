package io.github.jumperonjava.abuselesshardcore;

import io.github.jumperonjava.abuselesshardcore.interfaceinjection.LevelInfoDeleteWorld;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;

import java.io.Console;

public class AbuselessHardcore implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerTickEvents.END_SERVER_TICK.register((minecraftServer)->{
            if(MinecraftClient.getInstance().player!=null)
                    ((LevelInfoDeleteWorld)MinecraftClient.getInstance().player).setAhc_deleteWorld(((LevelInfoDeleteWorld)minecraftServer.getWorld(World.OVERWORLD).getLevelProperties()).getAhc_deleteWorld());
        });
    }

    public static boolean clientIsHardcore(){
        return MinecraftClient.getInstance().player.getWorld().getLevelProperties().isHardcore();
    }
}
