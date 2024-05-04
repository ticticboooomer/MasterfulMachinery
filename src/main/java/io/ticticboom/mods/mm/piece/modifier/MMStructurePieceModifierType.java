package io.ticticboom.mods.mm.piece.modifier;

import com.google.gson.JsonObject;

public abstract class MMStructurePieceModifierType {
    public abstract boolean identify(JsonObject json);
    public abstract StructurePieceModifier parse(JsonObject json);
}
