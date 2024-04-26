package io.ticticboom.mods.mm.port.common;

import io.ticticboom.mods.mm.port.IPortStorageModel;

public interface ISlottedPortStorageModel extends IPortStorageModel {
    int rows();
    int columns();
}
