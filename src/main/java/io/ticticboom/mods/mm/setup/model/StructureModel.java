package io.ticticboom.mods.mm.setup.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.structure.IConfiguredStructurePart;
import io.ticticboom.mods.mm.structure.transformers.MMStructureTransform;
import io.ticticboom.mods.mm.util.Deferred;
import io.ticticboom.mods.mm.util.ParseHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.*;
import java.util.function.Consumer;

public record StructureModel(
        ResourceLocation id,
        Component name,
        ResourceLocation controllerId,
        List<List<String>> layout,
        Map<String, IdentifiedStructurePart> key,
        List<PlacedStructurePart> flattened,
        List<List<PlacedStructurePart>> transformed
) {
    public static StructureModel parse(ResourceLocation id, JsonObject json) {
        var controllerId = ResourceLocation.tryParse(json.get("controllerId").getAsString());
        var name = ParseHelper.parseName(json.get("name").getAsJsonObject(), "");
        var layout = parseLayout(json.get("layout"));
        var key = parseKey(json.get("key").getAsJsonObject());
        var flattened = parseFlattened(key, layout);
        var transformed = applyTransforms(flattened);
        return new StructureModel(id, name, controllerId, layout, key, flattened, transformed);
    }

    private static List<List<String>> parseLayout(JsonElement elem) {
        var result = new ArrayList<List<String>>();
        for (JsonElement layer : elem.getAsJsonArray()) {
            var inner = new ArrayList<String>();
            for (JsonElement row : layer.getAsJsonArray()) {
                inner.add(row.getAsString());
            }
            result.add(inner);
        }
        return result;
    }

    private static Map<String, IdentifiedStructurePart> parseKey(JsonObject json) {
        var result = new HashMap<String, IdentifiedStructurePart>();
        for (String s : json.keySet()) {
            if (s.equals("C")) {
                continue;
            }
            var obj = json.get(s).getAsJsonObject();
            var partId = ResourceLocation.tryParse(obj.get("type").getAsString());
            var structurePart = MMRegistries.STRUCTURE_PARTS.get().getValue(partId);
            if (structurePart == null) {
                Ref.LOG.error("structure Part id: {} does not exist in registries", partId);
            }
            var config = structurePart.parse(obj);
            result.put(s, new IdentifiedStructurePart(partId, config));
        }
        return result;
    }

    private static List<PlacedStructurePart> parseFlattened(Map<String, IdentifiedStructurePart> key, List<List<String>> layout) {
        var controllerPos = findControllerPos(layout);
        final var result = new ArrayList<PlacedStructurePart>();
        runWithCoords(layout, x -> {
            if (x.character == 'C') {
                return;
            }
            result.add(placeStructurePart(key, x, controllerPos));
        });
        return result;
    }

    private static BlockPos findControllerPos(List<List<String>> layout) {
        final Deferred<BlockPos> controllerPos = new Deferred<>();
        runWithCoords(layout, x -> {
            if (x.character == 'C') {
                controllerPos.set(x.pos);
            }
        });
        return controllerPos.data;
    }

    private static PlacedStructurePart placeStructurePart(Map<String, IdentifiedStructurePart> key, AnnotatedPos anPos, BlockPos controllerPos) {
        var relativePos = anPos.pos.subtract(controllerPos);
        var config = key.get(anPos.character.toString());
        return new PlacedStructurePart(relativePos, config.id, config.part);
    }

    private static void runWithCoords(List<List<String>> layout, Consumer<AnnotatedPos> consumer) {
        int y = 0;
        int x = 0;
        int z = 0;
        Collections.reverse(layout);
        for (List<String> layer : layout) {
            for (String row : layer) {
                for (char c : row.toCharArray()) {
                    consumer.accept(new AnnotatedPos(new BlockPos(x, y, z), c));
                    z++;
                }
                x++;
            }
            y++;
        }
    }

    private static List<List<PlacedStructurePart>> applyTransforms(List<PlacedStructurePart> flattened) {
        var result = new ArrayList<List<PlacedStructurePart>>();
        result.add(flattened);
        for (Map.Entry<ResourceKey<MMStructureTransform>, MMStructureTransform> entry : MMRegistries.STRUCTURE_TRANSFORMS.get().getEntries()) {
            var transformed = entry.getValue().transform(flattened);
            result.add(transformed);
        }
        return result;
    }

    private record AnnotatedPos(
            BlockPos pos,
            Character character
    ) {
    }

    public record PlacedStructurePart(
            BlockPos pos,
            ResourceLocation partId,
            IConfiguredStructurePart part
    ) {
    }

    public record IdentifiedStructurePart(
            ResourceLocation id,
            IConfiguredStructurePart part
    ) {
    }

}
