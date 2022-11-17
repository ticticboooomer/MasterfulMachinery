package io.ticticboom.mods.mm.ports.fluid;

import io.ticticboom.mods.mm.ports.base.IConfiguredPort;
import net.minecraft.resources.ResourceLocation;

public record FluidConfiguredPort(
        int capacity
) implements IConfiguredPort {
}
