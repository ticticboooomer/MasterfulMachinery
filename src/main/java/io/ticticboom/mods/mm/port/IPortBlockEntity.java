package io.ticticboom.mods.mm.port;

import io.ticticboom.mods.mm.model.PortModel;
import net.minecraft.world.MenuProvider;

public interface IPortBlockEntity extends MenuProvider {
    PortModel getModel();

    IPortStorage getStorage();

    boolean isInput();

    default <T> T getBlockEntity() {
        return (T) this;
    }

    default boolean hasMenu() {
        return true;
    }

}
