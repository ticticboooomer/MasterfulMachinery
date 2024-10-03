package io.ticticboom.mods.mm.structure;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.interop.MMInteropManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
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

    public static void validateAllPieces() {
        for (StructureModel value : STRUCTURES.values()) {
            value.layout().validate(value);
        }
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        profilerFiller.push("MM Structures");
        receiveStructures(jsons);
        profilerFiller.pop();
    }

    public static void receiveStructures(Map<ResourceLocation, JsonElement> jsons) {
        STRUCTURES.clear();
        try {
            Ref.LCTX.reset("Structure Loading");
            for (Map.Entry<ResourceLocation, JsonElement> entry : jsons.entrySet()) {
                Ref.LCTX.push(String.format("Loading Structure: %s", entry.getKey().toString()));
                var model = StructureModel.parse(entry.getValue().getAsJsonObject(), entry.getKey());
                STRUCTURES.put(entry.getKey(), model);
                Ref.LCTX.pop();
            }
            if (MMInteropManager.KUBEJS.isPresent()) {
                Ref.LCTX.push("Loading KubeJS Structures");
                for (StructureModel structureModel : MMInteropManager.KUBEJS.get().postCreateStructures()) {
                    Ref.LCTX.push(String.format("Loading KubeJS Structure: %s", structureModel.id()));
                    STRUCTURES.put(structureModel.id(), structureModel);
                    Ref.LCTX.pop();
                }
                Ref.LCTX.pop();
            }
        } catch (Exception e) {
            Ref.LCTX.doThrow(e);
        }
    }

}
