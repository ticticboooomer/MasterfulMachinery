package io.ticticboom.mods.mm.ports;

import io.ticticboom.mods.mm.model.config.PortModel;
import net.minecraft.world.MenuProvider;

public interface IPortBlockEntity extends MenuProvider {
    PortModel getPortModel();
    IPortStorage getStorage();
    boolean isInput();
    default <T> T getBlockEntity() {
        return (T) this;
    }
}

