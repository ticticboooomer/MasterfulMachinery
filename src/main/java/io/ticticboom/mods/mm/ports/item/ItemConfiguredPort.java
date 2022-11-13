package io.ticticboom.mods.mm.ports.item;

import io.ticticboom.mods.mm.ports.base.IConfiguredPort;

public record ItemConfiguredPort(
        int slotRows,
        int slotCols
) implements IConfiguredPort {

}
