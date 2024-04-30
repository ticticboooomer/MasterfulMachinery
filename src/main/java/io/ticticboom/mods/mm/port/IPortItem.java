package io.ticticboom.mods.mm.port;

import net.minecraft.network.chat.Component;

public interface IPortItem extends IPortPart {
    Component getTypeName();
}
