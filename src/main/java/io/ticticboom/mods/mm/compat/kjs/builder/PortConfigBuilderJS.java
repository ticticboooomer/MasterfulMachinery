package io.ticticboom.mods.mm.compat.kjs.builder;

import dev.latvian.mods.rhino.util.HideFromJS;
import io.ticticboom.mods.mm.port.IPortStorageModel;

public abstract class PortConfigBuilderJS {
    @HideFromJS
    public abstract IPortStorageModel build();
}
