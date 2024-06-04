package io.ticticboom.mods.mm.structure.layout;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.client.structure.GuiStructureLayoutPiece;
import io.ticticboom.mods.mm.piece.MMStructurePieceRegistry;
import io.ticticboom.mods.mm.piece.StructurePieceSetupMetadata;
import io.ticticboom.mods.mm.piece.modifier.StructurePieceModifier;
import io.ticticboom.mods.mm.piece.type.StructurePiece;
import io.ticticboom.mods.mm.structure.StructureModel;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;

import java.util.List;

public class StructureLayoutPiece {
    private StructurePiece piece;
    @Getter
    private final List<StructurePieceModifier> modifiers;
    @Getter
    private GuiStructureLayoutPiece guiPiece;
    @Getter
    private final String valueId;
    @Getter
    private final JsonObject json;

    public StructureLayoutPiece(final StructurePiece piece, List<StructurePieceModifier> modifiers, GuiStructureLayoutPiece guiPiece, String valueId, JsonObject json) {
        this.piece = piece;
        this.modifiers = modifiers;
        this.guiPiece = guiPiece;
        this.valueId = valueId;
        this.json = json;
    }

    public void validate(StructureModel model) {
        StructurePieceSetupMetadata meta = new StructurePieceSetupMetadata(model.id());
        piece.validateSetup(meta);
        List<Block> blocks = piece.createBlocksSupplier().get();
        for (StructurePieceModifier modifier : modifiers) {
            modifier.validateSetup(meta, blocks);
        }
    }

    public boolean formed(Level level, BlockPos pos, StructureModel model, Rotation rot) {
        var formed = piece.formed(level, pos, model);
        if (!formed) {
            return false;
        }
        for (StructurePieceModifier modifier : modifiers) {
            formed = modifier.formed(level, pos, model, rot);
            if (!formed) {
                return false;
            }
        }
        return true;
    }

    public static StructureLayoutPiece parse(JsonObject json, ResourceLocation structureId, String keyChar) {
        var piece = MMStructurePieceRegistry.findPieceType(json);
        var modifiers = MMStructurePieceRegistry.findModifierTypes(json);
        var guiPiece = new GuiStructureLayoutPiece(piece.createBlocksSupplier(), piece.createDisplayComponent(), modifiers);
        return new StructureLayoutPiece(piece, modifiers, guiPiece, keyChar, json);
    }

    public void setup(ResourceLocation structureId) {
        var meta = new StructurePieceSetupMetadata(structureId);
        piece.validateSetup(meta);
        var blocks = piece.createBlocksSupplier().get();
        for (StructurePieceModifier modifier : modifiers) {
            modifier.validateSetup(meta, blocks);
        }
    }

    public JsonObject debugFormed(Level level, BlockPos pos, StructureModel model, Rotation rot) {
        var json = new JsonObject();
        json.addProperty("formed", formed(level, pos, model, rot));
        var expected = piece.debugExpected(level, pos, model, new JsonObject());
        var found = piece.debugFound(level, pos, model, new JsonObject());
        json.add("expected", expected);
        json.add("found", found);

        // TODO audit modifiers
        var modifiersJson = new JsonArray();
        for (StructurePieceModifier modifier : modifiers) {
            var expectedModifier = modifier.debugExpected(level, pos, model, rot, new JsonObject());
            var foundModifier = modifier.debugFound(level, pos, model, rot, new JsonObject());
            var modifierInnerJson = new JsonObject();
            modifierInnerJson.addProperty("id", modifier.getId());
            modifierInnerJson.add("expected", expectedModifier);
            modifierInnerJson.add("found", foundModifier);
            modifiersJson.add(modifierInnerJson);
        }
        json.add("modifiers", modifiersJson);
        return json;
    }
}
