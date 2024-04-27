package io.ticticboom.mods.mm.port;

import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;

public interface IPortStorageFactory {
    IPortStorage createPortStorage(INotifyChangeFunction changed);
}
