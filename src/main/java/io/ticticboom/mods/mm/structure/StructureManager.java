package io.ticticboom.mods.mm.structure;

import com.google.gson.JsonElement;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.kjs.MMKubeEvents;
import io.ticticboom.mods.mm.compat.kjs.builder.StructureBuilderJS;
import io.ticticboom.mods.mm.compat.kjs.event.StructureEventJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StructureManager extends SimpleJsonResourceReloadListener {

    public StructureManager() {
        super(Ref.GSON, "mm/structures");
    }

    public static final Map<ResourceLocation, StructureModel> STRUCTURES = new HashMap<>();

    public static List<StructureModel> getStructuresForController(ResourceLocation controllerId) {
        return STRUCTURES.values().stream()
                .filter(x -> x.controllerIds().contains(controllerId))
                .toList();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        profilerFiller.push("MM Structures");
        STRUCTURES.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : jsons.entrySet()) {
            var model = StructureModel.parse(entry.getValue().getAsJsonObject(), entry.getKey());
            STRUCTURES.put(entry.getKey(), model);
        }
        if (MMKubeEvents.isLoaded()) {
            var event = new StructureEventJS();
            MMKubeEvents.STRUCTURES.post(event);
            for (StructureBuilderJS builder : event.getBuilders()) {
                var model = builder.build();
                STRUCTURES.put(model.id(), model);
            }
        }
        profilerFiller.pop();
    }
}
