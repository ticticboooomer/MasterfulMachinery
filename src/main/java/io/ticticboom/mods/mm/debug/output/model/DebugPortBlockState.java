package io.ticticboom.mods.mm.debug.output.model;

import net.minecraft.resources.ResourceLocation;

public class DebugPortBlockState {
    private final ResourceLocation portId;
    private final ResourceLocation portType;

    public DebugPortBlockState(ResourceLocation portId, ResourceLocation portType /*, state & contents data*/) {

        this.portId = portId;
        this.portType = portType;
    }
}
