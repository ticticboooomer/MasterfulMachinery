package io.ticticboom.mods.mm.ports.js;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import io.ticticboom.mods.mm.ports.base.IConfiguredPort;

public record ConfiguredIngredientJS(
        JsonObject config
) implements IConfiguredIngredient {
}
