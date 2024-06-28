package io.ticticboom.mods.mm.port.pneumaticcraft.air.register;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.port.pneumaticcraft.air.PneumaticAirPortStorage;
import io.ticticboom.mods.mm.port.pneumaticcraft.air.PneumaticAirPortStorageModel;
import io.ticticboom.mods.mm.util.WidgetUtils;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.entity.player.Inventory;

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
    protected void renderBg(PoseStack gfx, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, Ref.Textures.PORT_GUI);
        this.blit(gfx, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(PoseStack gfx, int p_282681_, int p_283686_) {
        WidgetUtils.drawWordWrap(gfx, this.font, header, 8, 8, 150, 0x404040);
    }

    @Override
    public void render(PoseStack gfx, int mouseX, int mouseY, float partialTick) {
        renderBackground(gfx);
        super.render(gfx, mouseX, mouseY, partialTick);
        renderTooltip(gfx, mouseX, mouseY);
        PneumaticAirPortBlockEntity be = menu.getBE();
        PneumaticAirPortStorage storage = (PneumaticAirPortStorage) be.getStorage();
        PneumaticAirPortStorageModel model = be.getStorageModel();
        float air = storage.getAir();
        float pressure = storage.getPressure();
        float volume = storage.getVolume();
        var filledValue = (double)pressure / (double)storage.getAirhandler().getDangerPressure();
        var filledHeight = (int)(filledValue * 78);
        var start = 129 - filledHeight;
        WidgetUtils.drawWordWrap(gfx, this.font, FormattedText.of("Air: " + air + " mB"), this.leftPos + 8, this.topPos + 30, 150,0x404040);
        WidgetUtils.drawWordWrap(gfx, this.font, FormattedText.of("pressure: " + pressure + " Bar"), this.leftPos + 8, this.topPos + 40, 150, 0x404040);
        WidgetUtils.drawWordWrap(gfx, this.font, FormattedText.of("volume: " + volume + " mB"), this.leftPos + 8, this.topPos + 50, 150,0x404040);
    }
}
