package io.ticticboom.mods.mm.ports.mekanism.heat;

import mekanism.api.heat.HeatAPI;
import mekanism.api.heat.IHeatHandler;
import mekanism.common.capabilities.heat.BasicHeatCapacitor;

public class MekHeatHandler implements IHeatHandler {

    public final BasicHeatCapacitor capacitor;

    public MekHeatHandler(MekHeatConfiguredPort config) {
        capacitor = BasicHeatCapacitor.create(config.capacity(), () -> HeatAPI.AMBIENT_TEMP, null);
    }

    @Override
    public int getHeatCapacitorCount() {
        return 1;
    }

    @Override
    public double getTemperature(int i) {
        return capacitor.getTemperature();
    }

    @Override
    public double getInverseConduction(int i) {
        return capacitor.getInverseConduction();
    }

    @Override
    public double getHeatCapacity(int i) {
        return capacitor.getHeatCapacity();
    }

    @Override
    public void handleHeat(int i, double v) {
        capacitor.handleHeat(v);
    }
}
