package io.ticticboom.mods.mm.controller;

public interface IControllerBlockEntity {
    default <T> T getBlockEntity() {
        return (T) this;
    }
}
