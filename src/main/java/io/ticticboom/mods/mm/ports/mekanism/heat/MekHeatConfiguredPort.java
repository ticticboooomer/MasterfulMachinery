package io.ticticboom.mods.mm.ports.mekanism.heat;

import io.ticticboom.mods.mm.ports.base.IConfiguredPort;

public record MekHeatConfiguredPort(
        double capacity
) implements IConfiguredPort {
}
