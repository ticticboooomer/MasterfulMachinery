package io.ticticboom.mods.mm.port.fluid.register;

import io.ticticboom.mods.mm.client.FluidRenderer;
import io.ticticboom.mods.mm.port.IPortBlockEntity;
import io.ticticboom.mods.mm.port.common.SlottedContainerScreen;
import io.ticticboom.mods.mm.port.fluid.FluidPortStorage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.Vec2;

public class FluidPortScreen extends SlottedContainerScreen<FluidPortMenu> {
    public FluidPortScreen(FluidPortMenu menu, Inventory p_97742_, Component p_97743_) {
        super(menu, p_97742_, p_97743_);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        super.render(gfx, mouseX, mouseY, partialTicks);
        IPortBlockEntity pbe = menu.getBlockEntity();
        FluidPortStorage storage = (FluidPortStorage) pbe.getStorage();
        int i = 0;
        for (Vec2 slot : slots) {
            var stack = storage.getStackInSlot(i);
            FluidRenderer.INSTANCE.render(gfx, this.leftPos + (int) slot.x + 1, this.topPos + (int) slot.y + 1, stack, 16);
            i++;
        }
    }
}
