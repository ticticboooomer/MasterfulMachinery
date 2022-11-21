package io.ticticboom.mods.mm.ports.mekanism;

import io.ticticboom.mods.mm.ports.base.IConfiguredPort;

public record MekChemicalConfiguredPort(
        long capacity
) implements IConfiguredPort {
}
