package io.ticticboom.mods.mm.ports.fluid;

import io.ticticboom.mods.mm.ports.base.IConfiguredPort;

public record FluidConfiguredPort(
        int capacity
) implements IConfiguredPort {
}
