package io.ticticboom.mods.mm.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.client.container.ControllerContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;

public class ControllerScreen extends AbstractContainerScreen<ControllerContainer> {

    private static final ResourceLocation CGUI = Ref.res("textures/gui/gui_large.png");
    private final ControllerContainer be;

    public ControllerScreen(ControllerContainer be, Inventory inv, Component title) {
        super(be, inv, title);
        this.be = be;
    }

    @Override
    protected void renderBg(PoseStack ms, float pt, int x, int y) {
        this.renderBackground(ms);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, CGUI);
        blit(ms, leftPos, topPos - 30, 0, 0, 189, 254);
    }

    @Override
    protected void renderLabels(PoseStack ms, int x, int y) {

    }
}
