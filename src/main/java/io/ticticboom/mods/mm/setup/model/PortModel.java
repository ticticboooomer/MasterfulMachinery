package io.ticticboom.mods.mm.setup.model;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.ports.base.IConfiguredPort;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.util.ParseHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public record PortModel(
        ResourceLocation id,
        ResourceLocation port,
        IConfiguredPort configuredPort,
        boolean input,
        ResourceLocation blockId,
        Component name
) {

    public static PortModel parse(JsonObject json) {
        var id = new ResourceLocation(Ref.ID, json.get("id").getAsString());
        var port = ResourceLocation.tryParse(json.get("port").getAsString());
        MMPortTypeEntry value = MMRegistries.PORTS.get(port);
        if (value == null) {
            Ref.LOG.error("Port Type: {}, doesnt exist", port);
        }
        var configuredPort = value.parse(json.get("config").getAsJsonObject());
        var input = json.get("input").getAsBoolean();
        var blockId = ResourceLocation.tryParse(id + "_port");
        var name = ParseHelper.parseName(json.get("name").getAsJsonObject(), "Port");
        return new PortModel(id, port, configuredPort, input, blockId, name);
    }
}
