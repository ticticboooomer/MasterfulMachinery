package io.ticticboom.mods.mm.setup.reload;

import com.google.gson.JsonElement;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
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
public class RecipeManager extends SimpleJsonResourceReloadListener {

    public RecipeManager() {
        super(MMRegistries.GSON, "mm/processes");
    }

    @SubscribeEvent
    public static void register(AddReloadListenerEvent event) {
        event.addListener(new RecipeManager());
    }

    public static final Map<ResourceLocation, RecipeModel> REGISTRY = new HashMap<>();

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> entries, ResourceManager manager, ProfilerFiller profiler) {
        REGISTRY.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : entries.entrySet()) {
            REGISTRY.put(entry.getKey(), RecipeModel.parse(entry.getKey(), entry.getValue().getAsJsonObject()));
        }
    }
}
