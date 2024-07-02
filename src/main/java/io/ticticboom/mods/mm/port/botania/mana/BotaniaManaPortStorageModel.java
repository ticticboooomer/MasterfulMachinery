package io.ticticboom.mods.mm.port.botania.mana;

import io.ticticboom.mods.mm.port.IPortStorageModel;

public record BotaniaManaPortStorageModel(
        int capacity
) implements IPortStorageModel {
}
