package io.ticticboom.mods.mm.compat.kube.port;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.setup.PortManager;
import io.ticticboom.mods.mm.setup.model.PortModel;
import net.minecraft.resources.ResourceLocation;

public class PortBuilderJS {
    private String id;
    private String port;
    private boolean input;
    private String name;
    private JsonObject config;

    public void build() {
        var nameJson = new JsonObject();
        nameJson.addProperty("text", name);
        JsonObject port = new JsonObject();
        port.addProperty("id", id);
        port.addProperty("port", this.port);
        port.addProperty("input", input);
        port.add("name", nameJson);
        port.add("config", config);
        PortManager.REGISTRY.put(Ref.res(id), PortModel.parse(port));
    }

    public PortBuilderJS id(String id) {
        this.id = id;
        return this;
    }

    public PortBuilderJS port(String port) {
        this.port = port;
        return this;
    }

    public PortBuilderJS input(boolean input) {
        this.input = input;
        return this;
    }

    public PortBuilderJS name(String name) {
        this.name = name;
        return this;
    }

    public PortBuilderJS config(JsonObject config) {
        this.config = config;
        return this;
    }
}
