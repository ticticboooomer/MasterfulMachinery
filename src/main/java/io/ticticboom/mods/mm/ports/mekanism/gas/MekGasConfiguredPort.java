package io.ticticboom.mods.mm.ports.mekanism.gas;

import io.ticticboom.mods.mm.ports.base.IConfiguredPort;

public record MekGasConfiguredPort(
        long capacity
) implements IConfiguredPort {
}
