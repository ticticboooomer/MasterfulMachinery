package io.ticticboom.mods.mm.ports;

import com.google.gson.JsonObject;

public interface IPortParser {
    IPortStorageFactory parseStorage(JsonObject json);
}
