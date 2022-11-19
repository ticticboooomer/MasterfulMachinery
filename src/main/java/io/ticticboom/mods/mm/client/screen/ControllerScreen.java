package io.ticticboom.mods.mm.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.block.entity.ControllerBlockEntity;
import io.ticticboom.mods.mm.client.container.ControllerContainer;
import io.ticticboom.mods.mm.util.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
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
    protected void init() {
        this.imageWidth = 174;
        this.imageHeight = 222;
        super.init();
    }

    @Override
    protected void renderBg(PoseStack ms, float pt, int x, int y) {
        this.renderBackground(ms);
        RenderHelper.useTexture(CGUI);
        blit(ms, leftPos, topPos, 0, 0, 189, 254);
    }

    @Override
    protected void renderLabels(PoseStack ms, int x, int y) {
        this.renderDisplayInfo(ms, x, y, this.be.getDisplayInfo());
    }

    private void renderDisplayInfo(PoseStack ms, int mouseX, int mouseY, ControllerBlockEntity.DisplayInfo info) {
        // Draw structure name
        if (!StringUtil.isNullOrEmpty(info.structureName)) {
            drawCenteredString(ms, this.font, info.structureName, imageWidth / 2, 10, 0xffffff);
        }
        if (!StringUtil.isNullOrEmpty(info.processStatus)) {
            drawCenteredString(ms, this.font, info.processStatus, imageWidth / 2, 30, 0xffffff);
        }
        if (!StringUtil.isNullOrEmpty(info.recipe)) {
            drawCenteredString(ms, this.font, info.recipe, imageWidth / 2, 60, 0xffffff);
        }
    }
}
