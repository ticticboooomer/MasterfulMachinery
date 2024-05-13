package io.ticticboom.mods.mm.compat.kjs.builder;

import dev.latvian.mods.rhino.util.HideFromJS;
import io.ticticboom.mods.mm.compat.kjs.builder.port.PortConfigBuilderJS;
import io.ticticboom.mods.mm.model.IdList;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageFactory;
import io.ticticboom.mods.mm.port.MMPortRegistry;
import io.ticticboom.mods.mm.port.PortType;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.util.IConsumer;

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

    @HideFromJS
    public List<PortModel> build() {
        var portType = MMPortRegistry.get(type);
        var storageFactory = portType.createStorageFactory(builder);
        IdList controllerIds = new IdList(controllers);
        var inputPort = new PortModel(id, name, controllerIds, type, storageFactory,
                        PortModel.paramsToJson(id, name, controllerIds, type, storageFactory, true), true);

        var outputPort = new PortModel(id, name, controllerIds, type, storageFactory,
                        PortModel.paramsToJson(id, name, controllerIds, type, storageFactory, false), false);
        return List.of(inputPort, outputPort);
    }
}