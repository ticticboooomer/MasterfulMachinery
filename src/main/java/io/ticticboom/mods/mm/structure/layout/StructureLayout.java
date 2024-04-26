package io.ticticboom.mods.mm.structure.layout;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortBlockEntity;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import io.ticticboom.mods.mm.structure.StructureModel;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructureLayout {
    @Getter
    private StructureCharacterGrid charGrid;

    @Getter
    private List<PositionedLayoutPiece> positionedPieces = new ArrayList<>();

    @Getter
    Map<Rotation, List<PositionedLayoutPiece>> rotatedPositionedPieces = new HashMap<>();

    @Getter
    private Map<StructureKeyChar, StructureLayoutPiece> pieces;

    public StructureLayout(StructureCharacterGrid rawLayout, Map<StructureKeyChar, StructureLayoutPiece> pieces) {
        this.charGrid = rawLayout;
        this.pieces = pieces;
        setupVariants();
    }

    public void setupVariants() {
        charGrid.runFor((pos, cPos, chr) -> {
            StructureLayoutPiece piece = pieces.get(chr);
            positionedPieces.add(new PositionedLayoutPiece(pos.subtract(cPos), piece));
        });

        rotatedPositionedPieces.put(Rotation.NONE, positionedPieces);

        for (Rotation rotation : Rotation.values()) {
            var rotatedPieces = new ArrayList<PositionedLayoutPiece>();
            for (PositionedLayoutPiece piece : positionedPieces) {
                rotatedPieces.add(piece.rotate(rotation));
            }
            rotatedPositionedPieces.put(rotation, rotatedPieces);
        }
    }

    public boolean formed(Level level, BlockPos worldControllerPos, StructureModel model) {
        for (List<PositionedLayoutPiece> asRotation : rotatedPositionedPieces.values()) {
            if (innerFormed(level, worldControllerPos, model, asRotation)) {
                return true;
            }
        }
        return false;
    }

    private boolean innerFormed(Level level, BlockPos worldControllerPos, StructureModel model, List<PositionedLayoutPiece> positionedPieces) {
        for (PositionedLayoutPiece piece : positionedPieces) {
            if (!piece.formed(level, worldControllerPos, model)) {
                return false;
            }
        }
        return true;
    }

    public RecipeStorages getRecipeStorages(Level level, BlockPos worldControllerPos, StructureModel model) {
        for (List<PositionedLayoutPiece> asRotation : rotatedPositionedPieces.values()) {
            if (innerFormed(level, worldControllerPos, model, asRotation)) {
                return innerGetRecipeStorages(level, worldControllerPos, asRotation);
            }
        }
        return null;
    }

    private RecipeStorages innerGetRecipeStorages(Level level, BlockPos worldControllerPos, List<PositionedLayoutPiece> positionedPieces) {
        var inputStorages = new ArrayList<IPortStorage>();
        var outputStorages = new ArrayList<IPortStorage>();
        for (PositionedLayoutPiece positionedPiece : positionedPieces) {
            BlockPos absolutePos = positionedPiece.findAbsolutePos(worldControllerPos);
            var be = level.getBlockEntity(absolutePos);
            if (be instanceof IPortBlockEntity pbe) {
                if (pbe.isInput()) {
                    inputStorages.add(pbe.getStorage());
                } else {
                    outputStorages.add(pbe.getStorage());
                }
            }
        }
        return new RecipeStorages(inputStorages, outputStorages);
    }

    public static StructureLayout parse(JsonObject json, ResourceLocation structureId) {
        var raw = getCharGrid(json);
        var pieces = getPieces(json, structureId);
        return new StructureLayout(raw, pieces);
    }

    private static Map<StructureKeyChar, StructureLayoutPiece> getPieces(JsonObject json, ResourceLocation structureId) {
        Map<StructureKeyChar, StructureLayoutPiece> pieces = new HashMap<>();
        for (var key : json.getAsJsonObject("key").asMap().entrySet()) {
            JsonObject jsonKey = key.getValue().getAsJsonObject();
            pieces.put(new StructureKeyChar(key.getKey().charAt(0)), StructureLayoutPiece.parse(jsonKey, structureId));
        }
        return pieces;
    }

    private static StructureCharacterGrid getCharGrid(JsonObject json) {
        ArrayList<List<String>> rawLayout = new ArrayList<>();
        JsonArray layers = json.get("layout").getAsJsonArray();
        for (var layer : layers) {
            var resultRows = new ArrayList<String>();
            JsonArray rows = layer.getAsJsonArray();
            for (var row : rows) {
                resultRows.add(row.getAsString());
            }
            rawLayout.add(resultRows);
        }
        return new StructureCharacterGrid(rawLayout);
    }
}
