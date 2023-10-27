package io.ticticboom.mods.mm.recipedisplay.image;

import io.ticticboom.mods.mm.recipedisplay.IConfiguredRecipeDisplayElement;
import io.ticticboom.mods.mm.recipedisplay.RecipeDisplayContext;
import io.ticticboom.mods.mm.util.RenderHelper;
import net.minecraft.resources.ResourceLocation;

public record ImageConfiguredRecipeDisplayElement(
        ResourceLocation texture,
        int u,
        int v,
        int width,
        int height,
        int x,
        int y
) implements IConfiguredRecipeDisplayElement {

    @Override
    public void render(IConfiguredRecipeDisplayElement config, RecipeDisplayContext ctx) {
        var conf = (ImageConfiguredRecipeDisplayElement) config;
        RenderHelper.useTexture(conf.texture());
        ctx.helpers().getGuiHelper().createDrawable(conf.texture(), conf.u(), conf.v(), conf.width(), conf.height()).draw(ctx.stack(), conf.x(), conf.y());
    }
}
