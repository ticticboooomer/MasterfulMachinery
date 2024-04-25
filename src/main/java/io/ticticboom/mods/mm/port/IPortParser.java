package io.ticticboom.mods.mm.port;

import com.google.gson.JsonObject;

public interface IPortParser {
    IPortStorageFactory parseStorage(JsonObject json);
    IPortIngredient parseRecipeIngredient(JsonObject json);
}
