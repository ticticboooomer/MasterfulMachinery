package io.ticticboom.mods.mm.compat.kjs.builder;

import dev.latvian.mods.rhino.util.HideFromJS;
import io.ticticboom.mods.mm.compat.kjs.builder.port.PortConfigBuilderJS;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class PortBuilderJS {

    private final String id;
    private ResourceLocation type;
    private String name;
    private final List<ResourceLocation> controllers = new ArrayList<>();
    private Consumer<PortConfigBuilderJS> builder;

    @HideFromJS
    public PortBuilderJS(String id) {
        this.id = id;
    }

    public PortBuilderJS type(String type) {
        this.type = new ResourceLocation(type);
        return this;
    }

    public PortBuilderJS name(String name) {
        this.name = name;
        return this;
    }

    public PortBuilderJS controllerId(String controllerId) {
        controllers.add(new ResourceLocation(controllerId));
        return this;
    }

    public PortBuilderJS config(Consumer<PortConfigBuilderJS> builder) {
        this.builder = builder;
        return this;
    }
}
