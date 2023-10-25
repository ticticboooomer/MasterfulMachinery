package io.ticticboom.mods.mm.setup.reload;

import com.google.gson.JsonElement;
import io.ticticboom.mods.mm.setup.MMRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class RecipeDisplayManager extends SimpleJsonResourceReloadListener {
    public RecipeDisplayManager() {
        super(MMRegistries.GSON, "recipe_display");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> p_10793_, ResourceManager p_10794_, ProfilerFiller p_10795_) {

    }
}
