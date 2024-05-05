package io.ticticboom.mods.mm.piece.type.tag;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.piece.type.MMStructurePieceType;
import io.ticticboom.mods.mm.piece.type.StructurePiece;
import io.ticticboom.mods.mm.util.ParserUtils;

public class TagStructurePieceType extends MMStructurePieceType {
    @Override
    public boolean identify(JsonObject json) {
        return json.has("tag");
    }

    @Override
    public StructurePiece parse(JsonObject json) {
        var tag = ParserUtils.parseId(json, "tag");
        return new TagStructurePiece(tag);
    }
}
