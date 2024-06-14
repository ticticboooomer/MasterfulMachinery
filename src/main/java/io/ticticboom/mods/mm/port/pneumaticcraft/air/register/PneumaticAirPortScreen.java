package io.ticticboom.mods.mm.port.pneumaticcraft.air.register;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.port.pneumaticcraft.air.PneumaticAirPortStorage;
import io.ticticboom.mods.mm.port.pneumaticcraft.air.PneumaticAirPortStorageModel;
import io.ticticboom.mods.mm.util.WidgetUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;

public class PneumaticAirPortScreen  extends AbstractContainerScreen<PneumaticAirPortMenu> {

    private final FormattedText header;

    public PneumaticAirPortScreen(PneumaticAirPortMenu menu, Inventory inv, Component displayName) {
        super(menu, inv, displayName);
        this.imageHeight = 222;
        this.imageWidth = 174;
        String name = menu.getModel().name();
        int subStrLength = Math.min(55, name.length());
        header = FormattedText.of(name.substring(0, subStrLength) + (subStrLength < 55 ? "" : "..."));
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTicks, int mouseX, int mouseY) {
        gfx.blit(Ref.Textures.PORT_GUI, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics gfx, int p_282681_, int p_283686_) {
        gfx.drawWordWrap(this.font, header, 8, 8, 150, 0x404040);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        renderBackground(gfx);
        super.render(gfx, mouseX, mouseY, partialTick);
        renderTooltip(gfx, mouseX, mouseY);
        gfx.blit(Ref.Textures.SLOT_PARTS, this.leftPos + 7, this.topPos + 50, 89, 78, 162, 80);
        PneumaticAirPortBlockEntity be = menu.getBE();
        PneumaticAirPortStorage storage = (PneumaticAirPortStorage) be.getStorage();
        PneumaticAirPortStorageModel model = be.getStorageModel();
        float air = storage.getAir();
        float pressure = storage.getPressure();
        float volume = model.volume();
        var filledValue = (double)pressure / (double)storage.getAirhandler().getDangerPressure();
        var filledHeight = (int)(filledValue * 78);
        var start = 129 - filledHeight;
        gfx.blit(Ref.Textures.SLOT_PARTS, this.leftPos + 8, this.topPos + start, 90, 0, 160, filledHeight);
        if (WidgetUtils.isPointerWithinSized(mouseX, mouseY, this.leftPos + 7, this.topPos + 50, 162, 80)) {
            var tooltip = new ArrayList<Component>();
            tooltip.add(Component.literal(String.format("Stored Energy: %sBar, %s, %s", volume, air, pressure)));
            gfx.renderComponentTooltip(this.font, tooltip, mouseX, mouseY);
        }
    }
}
