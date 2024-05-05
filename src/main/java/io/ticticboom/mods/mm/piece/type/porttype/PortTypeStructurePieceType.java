package io.ticticboom.mods.mm.piece.type.porttype;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.piece.type.MMStructurePieceType;
import io.ticticboom.mods.mm.piece.type.StructurePiece;
import io.ticticboom.mods.mm.util.ParserUtils;

import java.util.Optional;

public class PortTypeStructurePieceType extends MMStructurePieceType {

    @Override
    public boolean identify(JsonObject json) {
        return json.has("portType");
    }

    @Override
    public StructurePiece parse(JsonObject json) {
        var portType = ParserUtils.parseId(json, "portType");
        Optional<Boolean> input = Optional.empty();
        if (json.has("input")) {
            input = Optional.of(json.get("input").getAsBoolean());
        }
        return new PortTypeStructurePiece(portType, input);
    }
}
