package io.ticticboom.mods.mm.model.config;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.ports.IPortStorageFactory;
import io.ticticboom.mods.mm.ports.MMPortRegistry;
import io.ticticboom.mods.mm.util.ParserUtils;
import io.ticticboom.mods.mm.util.PortUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public record PortModel(
        String id,
        String name,
        IdList controllerIds,
        ResourceLocation type,
        IPortStorageFactory config,
        boolean input) {

    public static PortModel parse(JsonObject json, boolean input) {
        var id = PortUtils.id(json.get("id").getAsString(), input);
        var name = PortUtils.name(json.get("name").getAsString(), input);
        var controllerIds = IdList.parse(json.get("controllerIds"));
        var type = ParserUtils.parseId(json, "type");
        var portType = MMPortRegistry.get(type);
        var storageFactory = portType.getParser().parseStorage(json.get("config").getAsJsonObject());
        return new PortModel(id, name, controllerIds, type, storageFactory, input);
    }
}