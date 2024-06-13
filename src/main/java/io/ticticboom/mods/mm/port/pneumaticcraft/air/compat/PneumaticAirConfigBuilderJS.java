package io.ticticboom.mods.mm.port.pneumaticcraft.air.compat;

import io.ticticboom.mods.mm.compat.kjs.builder.PortConfigBuilderJS;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.pneumaticcraft.air.PneumaticAirPortStorageModel;

public class PneumaticAirConfigBuilderJS extends PortConfigBuilderJS {
    private int volume;

    public PneumaticAirConfigBuilderJS() {

    }


    public PneumaticAirConfigBuilderJS volume(int volume) {
        this.volume = volume;
        return this;
    }

    @Override
    public IPortStorageModel build() {
        return new PneumaticAirPortStorageModel(volume);
    }
}
