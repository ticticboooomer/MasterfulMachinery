package io.ticticboom.mods.mm.structure;

import io.ticticboom.mods.mm.model.config.IdList;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

@Getter
public abstract class StructureModel {
    private final String name;
    private final IdList controllerIds;
    private final ResourceLocation type;

    public StructureModel(String name, IdList controllerIds, ResourceLocation type) {

        this.name = name;
        this.controllerIds = controllerIds;
        this.type = type;
    }
}
