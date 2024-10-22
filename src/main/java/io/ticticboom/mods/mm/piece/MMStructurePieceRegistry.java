package io.ticticboom.mods.mm.piece;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.piece.modifier.StructurePieceModifier;
import io.ticticboom.mods.mm.piece.modifier.MMStructurePieceModifierType;
import io.ticticboom.mods.mm.piece.modifier.blockstate.BlockstateStructurePieceModifierType;
import io.ticticboom.mods.mm.piece.type.StructurePiece;
import io.ticticboom.mods.mm.piece.type.block.BlockStructurePieceType;
import io.ticticboom.mods.mm.piece.type.MMStructurePieceType;
import io.ticticboom.mods.mm.piece.type.port.PortStructurePieceType;
import io.ticticboom.mods.mm.piece.type.porttype.PortTypeStructurePieceType;
import io.ticticboom.mods.mm.piece.type.tag.TagStructurePieceType;

import java.util.ArrayList;
import java.util.List;

public class MMStructurePieceRegistry {
    private static List<MMStructurePieceType> PIECE_TYPES = new ArrayList<>();
    private static List<MMStructurePieceModifierType> MODIFIER_TYPES = new ArrayList<>();

    public static void registerPiece(final MMStructurePieceType piece) {
        PIECE_TYPES.add(piece);
    }

    public static void registerModifier(final MMStructurePieceModifierType modifier) {
        MODIFIER_TYPES.add(modifier);
    }

    public static StructurePiece findPieceType(JsonObject json) {
        for (final var pieceType : PIECE_TYPES) {
            if (pieceType.identify(json)) {
                return pieceType.parse(json);
            }
        }
        return null;
    }

    public static List<StructurePieceModifier> findModifierTypes(JsonObject json) {
        var result = new ArrayList<StructurePieceModifier>();
        for (final var modifierType : MODIFIER_TYPES) {
            if (modifierType.identify(json)) {
                result.add(modifierType.parse(json));
            }
        }
        return result;
    }

    public static void init() {
        registerPiece(new BlockStructurePieceType());
        registerPiece(new TagStructurePieceType());
        registerPiece(new PortStructurePieceType());
        registerPiece(new PortTypeStructurePieceType());

        registerModifier(new BlockstateStructurePieceModifierType());
    }
}
