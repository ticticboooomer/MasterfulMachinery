package io.ticticboom.mods.mm.compat.kjs.builder;

import io.ticticboom.mods.mm.extra.ExtraBlockModel;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

@Getter
public class ExtraBlockBuilderJS {
    private final String id;
    private ResourceLocation type;
    private String name;

    public ExtraBlockBuilderJS(String id) {
        this.id = id;
    }

    public ExtraBlockBuilderJS name(String name) {
        this.name = name;
        return this;
    }

    public ExtraBlockBuilderJS type(ResourceLocation type) {
        this.type = type;
        return this;
    }

    public ExtraBlockModel build() {
        return new ExtraBlockModel(id, name, type);
    }
}
