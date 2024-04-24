package io.ticticboom.mods.mm.structure;

import com.google.gson.JsonObject;

public abstract class StructureType {
    public abstract StructureModel parse(JsonObject json);
    public abstract StructureFormedResult formed();
}
