package io.ticticboom.mods.mm.compat.kjs.builder;

import dev.latvian.mods.rhino.util.HideFromJS;
import io.ticticboom.mods.mm.model.IdList;
import io.ticticboom.mods.mm.structure.StructureModel;
import io.ticticboom.mods.mm.structure.layout.StructureLayout;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class StructureBuilderJS {

    private final List<ResourceLocation> controllers=  new ArrayList<>();
    private final ResourceLocation id;
    private String name;
    private Consumer<StructureLayoutBuilderJS> layoutConsumer;

    public StructureBuilderJS(String id) {
        this.id = new ResourceLocation(id);
    }

    public StructureBuilderJS name(String name) {
        this.name = name;
        return this;
    }

    public StructureBuilderJS controllerId(String id) {
        controllers.add(new ResourceLocation(id));
        return this;
    }

    public StructureBuilderJS layout(Consumer<StructureLayoutBuilderJS> consumer) {
        this.layoutConsumer = consumer;
        return this;
    }

    @HideFromJS
    public StructureModel build() {
        var event = new StructureLayoutBuilderJS(this);
        layoutConsumer.accept(event);
        IdList controllerIds = new IdList(controllers);
        StructureLayout layout = event.build();
        return new StructureModel(id, name, controllerIds, layout, StructureModel.paramsToJson(id, name, controllerIds, layout));
    }
}
