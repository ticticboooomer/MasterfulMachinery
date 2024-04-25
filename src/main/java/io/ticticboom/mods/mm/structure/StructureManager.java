package io.ticticboom.mods.mm.structure;

import com.google.gson.JsonElement;
import io.ticticboom.mods.mm.Ref;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class StructureManager extends SimpleJsonResourceReloadListener {

    public StructureManager() {
        super(Ref.GSON, "mm/structures");
    }

    public static final Map<ResourceLocation, StructureModel> STRUCTURES = new HashMap<>();

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        for (Map.Entry<ResourceLocation, JsonElement> entry : jsons.entrySet()) {
            var model = StructureModel.parse(entry.getValue().getAsJsonObject(), entry.getKey());
            STRUCTURES.put(entry.getKey(), model);
        }
    }
}
