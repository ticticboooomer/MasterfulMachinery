package io.ticticboom.mods.mm.port.pneumaticcraft.air.compat;

import io.ticticboom.mods.mm.compat.kjs.builder.PortConfigBuilderJS;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.pneumaticcraft.air.PneumaticAirPortStorageModel;
import me.desht.pneumaticcraft.api.pressure.PressureTier;

public class PneumaticAirConfigBuilderJS extends PortConfigBuilderJS {
    private float danger;
    private float critical;
    private int volume;

    public PneumaticAirConfigBuilderJS() {

    }


    public PneumaticAirConfigBuilderJS danger(float danger) {
        this.danger = danger;
        return this;
    }

    public PneumaticAirConfigBuilderJS critical(float critical) {
        this.critical = critical;
        return this;
    }

    public PneumaticAirConfigBuilderJS volume(int volume) {
        this.volume = volume;
        return this;
    }


    @Override
    public IPortStorageModel build() {
        PressureTier tier = new PressureTier() {
            public float getDangerPressure() {
                return danger;
            }

            public float getCriticalPressure() {
                return critical;
            }
        };
        return new PneumaticAirPortStorageModel(volume, tier);
    }
}
