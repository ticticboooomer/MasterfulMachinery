package io.ticticboom.mods.mm.setup.reload;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.recipe.ConfiguredRecipeEntry;
import io.ticticboom.mods.mm.setup.MMRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PresetManager extends SimpleJsonResourceReloadListener {

    public PresetManager() {
        super(MMRegistries.GSON, "mm/presets");
    }

    @SubscribeEvent
    public static void register(AddReloadListenerEvent event) {
        event.addListener(new PresetManager());
    }

    public static final Map<ResourceLocation, ConfiguredRecipeEntry> REGISTRY = new HashMap<>();

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> entries, ResourceManager resourceManager, ProfilerFiller profile) {
        REGISTRY.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : entries.entrySet()) {
            ResourceLocation type = ResourceLocation.tryParse(entry.getValue().getAsJsonObject().get("type").getAsString());
            var entryType = MMRegistries.RECIPE_ENTRIES.get().getValue(type);
            REGISTRY.put(entry.getKey(), new ConfiguredRecipeEntry(type, entryType.parse(entry.getValue().getAsJsonObject())));
        }
    }
}
