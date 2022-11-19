package io.ticticboom.mods.mm.ports.energy;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.client.screen.PortScreen;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.setup.MMCapabilities;
import io.ticticboom.mods.mm.util.GuiHelper;
import io.ticticboom.mods.mm.util.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyPortStorage extends PortStorage {
    public EnergyConfiguredPort config;
    public EnergyHandler handler;
    public final LazyOptional<EnergyHandler> handlerLO;

    public EnergyPortStorage(EnergyConfiguredPort config) {
        this.config = config;
        handler = new EnergyHandler(config.capacity());
        handlerLO = LazyOptional.of(() -> handler);
    }

    @Override
    public void read(CompoundTag tag) {
        var stored = tag.getInt("Amount");
        handler.setStored(stored);
    }

    @Override
    public CompoundTag write() {
        var result = new CompoundTag();
        result.putInt("Amount", handler.getEnergyStored());
        return result;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        if (cap == MMCapabilities.ENERGY) {
            return handlerLO.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public void renderScreen(PortScreen screen, PoseStack ms, int x, int y) {
        RenderHelper.useTexture(Ref.SLOT_PARTS);
        var startX = 175 / 2 - (18 / 2);
        var startY = 252 / 4 - (18 / 2) - 10;
        var height = 50;
        screen.blit(ms, screen.getGuiLeft() + startX, screen.getGuiTop() + startY -10, 50, 14, 18, height);
        var percentage = (float)handler.getEnergyStored() / handler.getMaxEnergyStored();
        GuiHelper.renderVerticallyFilledBar(ms, screen, screen.getGuiLeft() + startX, screen.getGuiTop() + startY -10, 72, 14, 18, 50, percentage);
        Gui.drawCenteredString(ms, Minecraft.getInstance().font, percentage * 100 + "%", screen.getGuiLeft() + startX + 9, screen.getGuiTop() + startY + 60, 0xfefefe);
    }

    @Override
    public void onDestroy(Level level, BlockPos pos) {

    }

    @Override
    public PortStorage deepClone() {
        var result = new EnergyPortStorage(config);
        result.handler = new EnergyHandler(config.capacity());
        result.handler.setStored(handler.getEnergyStored());
        return result;
    }
}
