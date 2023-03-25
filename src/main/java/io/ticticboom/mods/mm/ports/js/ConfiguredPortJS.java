package io.ticticboom.mods.mm.ports.js;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.ports.base.IConfiguredPort;

public record ConfiguredPortJS(
        JsonObject config
) implements IConfiguredPort {
}
