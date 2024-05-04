package io.ticticboom.mods.mm.structure.layout;

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

    public StructureLayoutPiece(final StructurePiece piece, List<StructurePieceModifier> modifiers, GuiStructureLayoutPiece guiPiece, String valueId) {
        this.piece = piece;
        this.modifiers = modifiers;
        this.guiPiece = guiPiece;
        this.valueId = valueId;
    }

    public boolean formed(Level level, BlockPos pos, StructureModel model, Rotation rot) {
        if (!piece.isSetup()) {
            StructurePieceSetupMetadata meta = new StructurePieceSetupMetadata(model.id());
            piece.validateSetup(meta);
            piece.setSetup(true);
            List<Block> requiredBlocks = piece.createBlocksSupplier().get();
            for (StructurePieceModifier modifier : modifiers) {
                if (!modifier.isSetup()) {
                    modifier.validateSetup(meta, requiredBlocks);
                    modifier.setSetup(true);
                }
            }
        }
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
        return new StructureLayoutPiece(piece, modifiers, guiPiece, keyChar);
    }

    public void setup(ResourceLocation structureId) {
        var meta = new StructurePieceSetupMetadata(structureId);
        piece.validateSetup(meta);
        var blocks = piece.createBlocksSupplier().get();
        for (StructurePieceModifier modifier : modifiers) {
            modifier.validateSetup(meta, blocks);
        }
    }
}
