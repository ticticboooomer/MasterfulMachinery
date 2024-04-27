package io.ticticboom.mods.mm.port.fluid.register;

import io.ticticboom.mods.mm.port.common.SlottedContainerScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class FluidPortScreen extends SlottedContainerScreen<FluidPortMenu> {
    public FluidPortScreen(FluidPortMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }
}
