package io.ticticboom.mods.mm.port.mekanism.chemical.register;

import com.mojang.blaze3d.systems.RenderSystem;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorage;
import io.ticticboom.mods.mm.util.WidgetUtils;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.client.gui.GuiUtils;
import mekanism.client.render.MekanismRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

public class MekanismChemicalPortScreen<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>, T extends MekanismChemicalPortMenu<CHEMICAL, STACK>> extends AbstractContainerScreen<T> {

    private final FormattedText header;
    protected final MekanismChemicalPortBlockEntity<CHEMICAL, STACK> be;
    protected final MekanismChemicalPortStorage<CHEMICAL, STACK> storage;


    public MekanismChemicalPortScreen(T menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageHeight = 222;
        this.imageWidth = 174;
        String name = menu.getModel().name();
        int subStrLength = Math.min(55, name.length());
        header = FormattedText.of(name.substring(0, subStrLength) + (subStrLength < 55 ? "" : "..."));
        be = this.menu.getBlockEntity();
        storage = (MekanismChemicalPortStorage<CHEMICAL, STACK>) be.getStorage();

    }

    @Override
    protected void renderBg(GuiGraphics gfx, float v, int i, int i1) {
        gfx.blit(Ref.Textures.PORT_GUI, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        gfx.blit(Ref.Textures.SLOT_PARTS, this.leftPos + 80, this.topPos + 60, 0, 26, 18, 18);
    }

    @Override
    protected void renderLabels(GuiGraphics gfx, int mouseX, int mouseY) {
        gfx.drawWordWrap(this.font, header, 8, 8, 150, 0x404040);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        renderBackground(gfx);
        super.render(gfx, mouseX, mouseY, partialTick);
        renderTooltip(gfx, mouseX, mouseY);
        var type = storage.chemicalTank.getStack();
        if (!type.isEmpty()) {
            int color = type.getType().getTint();

            float red = (color >> 16 & 0xFF) / 255.0F;
            float green = (color >> 8 & 0xFF) / 255.0F;
            float blue = (color & 0xFF) / 255.0F;
            gfx.setColor(red, green, blue, 1.0f);
            GuiUtils.drawTiledSprite(gfx, this.leftPos + 81, this.topPos + 61, 16, 16, 16, MekanismRenderer.getSprite(type.getType().getIcon()), 16, 16, 100, GuiUtils.TilingDirection.UP_RIGHT);
            MekanismRenderer.resetColor(gfx);

            if (WidgetUtils.isPointerWithinSized(mouseX, mouseY, this.leftPos + 80, this.topPos + 60, 18, 18)) {
                var tooltip = List.of(type.getType().getTextComponent(), Component.literal(type.getAmount() + " amB"));
                gfx.renderComponentTooltip(this.font, tooltip, mouseX, mouseY);
            }
        }
    }
}