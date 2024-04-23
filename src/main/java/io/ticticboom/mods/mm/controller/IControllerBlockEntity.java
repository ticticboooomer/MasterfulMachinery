package io.ticticboom.mods.mm.controller;

import net.minecraft.world.MenuProvider;

public interface IControllerBlockEntity extends MenuProvider {
    default <T> T getBlockEntity() {
        return (T) this;
    }
}
