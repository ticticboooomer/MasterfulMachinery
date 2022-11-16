package io.ticticboom.mods.mm.util;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.ports.base.ConfiguredIngredient;
import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.setup.MMRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class RecipeHelper {
    public static ConfiguredIngredient parseIngredient(JsonObject json) {
        var type = ResourceLocation.tryParse(json.get("type").getAsString());
        MMPortTypeEntry port = MMRegistries.PORTS.get(type);
        return new ConfiguredIngredient(type, port.parseIngredient(json));
    }
}
