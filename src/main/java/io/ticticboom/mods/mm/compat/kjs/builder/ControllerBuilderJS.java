package io.ticticboom.mods.mm.compat.kjs.builder;

import dev.latvian.mods.rhino.util.HideFromJS;
import io.ticticboom.mods.mm.model.ControllerModel;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

@Getter
public class ControllerBuilderJS {
    private final String id;
    private String name;
    private ResourceLocation type;

    @HideFromJS
    public ControllerBuilderJS(String id) {
        this.id = id;
    }

    public ControllerBuilderJS type(String id) {
        this.type = new ResourceLocation(id);
        return this;
    }

    public ControllerBuilderJS name(String name) {
        this.name = name;
        return this;
    }

    @HideFromJS
    public ControllerModel build() {
        return new ControllerModel(id, type, name, ControllerModel.paramsToJson(id, type, name));
    }
}
