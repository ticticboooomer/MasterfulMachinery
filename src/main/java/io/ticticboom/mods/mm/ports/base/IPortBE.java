package io.ticticboom.mods.mm.ports.base;

import io.ticticboom.mods.mm.setup.model.PortModel;

public interface IPortBE {
    PortModel model();
    PortStorage storage();
}