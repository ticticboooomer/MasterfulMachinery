package io.ticticboom.mods.mm.piece.type.block;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.piece.type.StructurePiece;
import io.ticticboom.mods.mm.piece.type.MMStructurePieceType;
import io.ticticboom.mods.mm.util.ParserUtils;

public class BlockStructurePieceType extends MMStructurePieceType {
    @Override
    public boolean identify(JsonObject json) {
        return json.has("block");
    }

    @Override
    public StructurePiece parse(JsonObject json) {
        var blockId = ParserUtils.parseId(json, "block");
        return new BlockStructurePiece(blockId);
    }
}