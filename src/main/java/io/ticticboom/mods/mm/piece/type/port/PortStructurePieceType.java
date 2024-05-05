package io.ticticboom.mods.mm.piece.type.port;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.piece.type.MMStructurePieceType;
import io.ticticboom.mods.mm.piece.type.StructurePiece;
import io.ticticboom.mods.mm.piece.type.porttype.PortTypeStructurePiece;
import io.ticticboom.mods.mm.util.ParserUtils;

import java.util.Optional;

public class PortStructurePieceType extends MMStructurePieceType {
    @Override
    public boolean identify(JsonObject json) {
        return json.has("port");
    }

    @Override
    public StructurePiece parse(JsonObject json) {
        var port = ParserUtils.parseId(json, "port");
        Optional<Boolean> input = Optional.empty();
        if (json.has("input")) {
            input = Optional.of(json.get("input").getAsBoolean());
        }
        return new PortStructurePiece(port, input);
    }
}
