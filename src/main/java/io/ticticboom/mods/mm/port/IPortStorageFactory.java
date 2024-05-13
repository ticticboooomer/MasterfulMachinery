package io.ticticboom.mods.mm.port;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;

public interface IPortStorageFactory {
    IPortStorage createPortStorage(INotifyChangeFunction changed);
    JsonObject serialize();
}
