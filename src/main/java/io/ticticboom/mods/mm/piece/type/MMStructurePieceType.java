package io.ticticboom.mods.mm.piece.type;

import com.google.gson.JsonObject;

public abstract class MMStructurePieceType {
    public abstract boolean identify(JsonObject json);
    public abstract StructurePiece parse(JsonObject json);
}
