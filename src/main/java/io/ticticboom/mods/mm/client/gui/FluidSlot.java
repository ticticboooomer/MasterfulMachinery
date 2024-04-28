package io.ticticboom.mods.mm.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.function.Supplier;

public class FluidSlot extends BaseRenderable {

    private final int x;
    private final int y;
    private final Supplier<FluidStack> stackSupplier;

    private static final int WIDTH = 16;
    private static final int HEIGHT = 16;

    public FluidSlot(int x, int y, Supplier<FluidStack> stackSupplier) {

        this.x = x;
        this.y = y;
        this.stackSupplier = stackSupplier;

    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        FluidStack stack = stackSupplier.get();

    }
}
