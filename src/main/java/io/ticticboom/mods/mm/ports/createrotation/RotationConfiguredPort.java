package io.ticticboom.mods.mm.ports.createrotation;

import io.ticticboom.mods.mm.ports.base.IConfiguredPort;

public record RotationConfiguredPort(
        int stress
) implements IConfiguredPort {

}
