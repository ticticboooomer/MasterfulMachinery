package io.ticticboom.mods.mm.client.port;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.client.screen.PortScreen;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class ClientPortTypeEntry extends ForgeRegistryEntry<ClientPortTypeEntry> {
    public abstract void renderScreen(PortStorage storage, PortScreen screen, PoseStack ms, int x, int y);
}
