package io.ticticboom.mods.mm.ports.energy;

import io.ticticboom.mods.mm.ports.base.IConfiguredPort;

public record EnergyConfiguredPort(
        int capacity
) implements IConfiguredPort {
}
