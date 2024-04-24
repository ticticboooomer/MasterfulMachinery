package io.ticticboom.mods.mm.structure.layout;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructureLayout {
    @Getter
    private List<List<String>> rawLayout;

    @Getter
    private Map<String, StructureLayoutPiece> pieces;

    public StructureLayout(List<List<String>> rawLayout, Map<String, StructureLayoutPiece> pieces) {

        this.rawLayout = rawLayout;
        this.pieces = pieces;
    }

    public static StructureLayout parse(JsonObject json, ResourceLocation structureId) {
        var raw = getRawLayout(json.getAsJsonObject("layout"));
        var pieces = getPieces(json.getAsJsonObject("key"), structureId);
        return new StructureLayout(raw, pieces);
    }

    private static Map<String, StructureLayoutPiece> getPieces(JsonObject json, ResourceLocation structureId) {
        Map<String, StructureLayoutPiece> pieces = new HashMap<>();
        for (JsonElement key : json.getAsJsonArray("key")) {
            JsonObject jsonKey = key.getAsJsonObject();
            StructureLayoutPiece.parse(jsonKey, structureId);
        }
        return pieces;
    }

    private static List<List<String>> getRawLayout(JsonObject json) {
        ArrayList<List<String>> rawLayout = new ArrayList<>();
        JsonArray layers = json.get("layout").getAsJsonArray();
        for (var layer : layers) {
            var resultRows = new ArrayList<String>();
            JsonArray rows = layer.getAsJsonArray();
            for (var row : rows) {
                resultRows.add(row.toString());
            }
            rawLayout.add(resultRows);
        }
        return rawLayout;
    }
}
