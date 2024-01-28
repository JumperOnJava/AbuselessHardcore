package io.github.jumperonjava.abuselesshardcore.interfaceinjection;

public interface LevelInfoDeleteWorld {
    default void setAhc_deleteWorld(boolean ahc_deleteWorld){}
    default boolean getAhc_deleteWorld(){return false;}
}
