package io.ticticboom.mods.mm.setup.reload;

import com.google.gson.JsonElement;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.setup.model.StructureModel;
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
public class StructureManager extends SimpleJsonResourceReloadListener {
    public StructureManager() {
        super(MMRegistries.GSON, "mm/structures");
    }

    @SubscribeEvent
    public static void register(AddReloadListenerEvent event) {
        event.addListener(new StructureManager());
    }

    public static final Map<ResourceLocation, StructureModel> REGISTRY = new HashMap<>();

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> records, ResourceManager resManager, ProfilerFiller profiler) {
        REGISTRY.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : records.entrySet()) {
            REGISTRY.put(entry.getKey(), StructureModel.parse(entry.getKey(), entry.getValue().getAsJsonObject()));
        }
    }
}
