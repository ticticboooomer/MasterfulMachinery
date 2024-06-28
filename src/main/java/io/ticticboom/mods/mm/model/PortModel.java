package io.ticticboom.mods.mm.model;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortStorageFactory;
import io.ticticboom.mods.mm.port.MMPortRegistry;
import io.ticticboom.mods.mm.util.ParserUtils;
import io.ticticboom.mods.mm.util.PortUtils;
import net.minecraft.resources.ResourceLocation;

public record PortModel(
        String id,
        String name,
        IdList controllerIds,
        ResourceLocation type,
        IPortStorageFactory config,
        JsonObject jsonConfig,
        boolean input) {

    public static PortModel parse(JsonObject json, boolean input) {
        var id = PortUtils.id(json.get("id").getAsString(), input);
        var name = PortUtils.name(json.get("name").getAsString(), input);
        var controllerIds = IdList.parse(json.get("controllerIds"));
        var type = ParserUtils.parseId(json, "type");
        var portType = MMPortRegistry.get(type);
        var storageFactory = portType.getParser().parseStorage(json.get("config").getAsJsonObject());
        return new PortModel(id, name, controllerIds, type, storageFactory, json, input);
    }

    public static PortModel create(String id, String name, IdList controllerIds, ResourceLocation type, IPortStorageFactory config, boolean input) {
        var fid = PortUtils.id(id, input);
        var fname = PortUtils.name(name, input);
        var json = paramsToJson(fid, fname, controllerIds, type, config, input);
        return new PortModel(fid, fname, controllerIds, type, config, json, input);
    }

    public static JsonObject paramsToJson(String id, String name, IdList controllerIds, ResourceLocation type, IPortStorageFactory config, boolean input) {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("name", name);
        json.add("controllerIds", controllerIds.serialize());
        json.addProperty("type", type.toString());
        json.addProperty("config", config.toString());
        return json;
    }

}
