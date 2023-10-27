package io.ticticboom.mods.mm.recipedisplay.image;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.recipedisplay.IConfiguredRecipeDisplayElement;
import io.ticticboom.mods.mm.recipedisplay.MMRecipeDisplayElement;
import io.ticticboom.mods.mm.recipedisplay.RecipeDisplayContext;
import io.ticticboom.mods.mm.util.RenderHelper;
import net.minecraft.resources.ResourceLocation;

public class ImageRecipeDisplayElement extends MMRecipeDisplayElement {
    @Override
    public IConfiguredRecipeDisplayElement parse(JsonObject json) {
        var texture = ResourceLocation.tryParse(json.get("texture").getAsString());
        var u = json.get("u").getAsInt();
        var v = json.get("v").getAsInt();
        var w = json.get("w").getAsInt();
        var h = json.get("h").getAsInt();
        var x = json.get("x").getAsInt();
        var y = json.get("y").getAsInt();
        return new ImageConfiguredRecipeDisplayElement(texture, u, v, w, h, x, y);
    }

}
