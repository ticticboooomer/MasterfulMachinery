package io.ticticboom.mods.mm.recipedisplay.image;

import io.ticticboom.mods.mm.recipedisplay.IConfiguredRecipeDisplayElement;
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
}
