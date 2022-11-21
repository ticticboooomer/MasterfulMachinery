package io.ticticboom.mods.mm.ports.mekanism;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.client.screen.PortScreen;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.util.GuiHelper;
import io.ticticboom.mods.mm.util.RenderHelper;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public abstract class MekChemicalPortStorage<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>> extends PortStorage {

    public IChemicalTank<CHEMICAL, STACK> tank;
    protected MekChemicalConfiguredPort config;
    private final LazyOptional<IChemicalTank<CHEMICAL, STACK>> handlerLO;
    protected abstract IChemicalTank<CHEMICAL, STACK> createTank(long capacity);
    protected abstract boolean isCapability(Capability<?> cap);
    protected abstract STACK createStack(ResourceLocation loc, long amount);
    protected abstract MekChemicalPortStorage<CHEMICAL, STACK> createSelf();

    public MekChemicalPortStorage(MekChemicalConfiguredPort config) {
        tank = createTank(config.capacity());
        this.config = config;
        handlerLO = LazyOptional.of(() -> tank);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        if (isCapability(cap)) {
            return handlerLO.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public void read(CompoundTag tag) {
        var gas = tag.getString("Chemical");
        var amount = tag.getLong("Amount");
        tank.setStack(createStack(ResourceLocation.tryParse(gas), amount));
    }

    @Override
    public CompoundTag write() {
        var result = new CompoundTag();
        STACK stack = tank.getStack();
        result.putString("Chemical", stack.getRaw().getRegistryName().toString());
        result.putLong("Amount", stack.getAmount());
        return result;
    }

    @Override
    public void renderScreen(PortScreen screen, PoseStack ms, int x, int y) {
        RenderHelper.useTexture(Ref.SLOT_PARTS);
        var startX = 175 / 2 - (18 / 2);
        var startY = 252 / 4 - (18 / 2) - 10;
        var height = 50;
        screen.blit(ms, screen.getGuiLeft() + startX, screen.getGuiTop() + startY -10, 50, 14, 18, height);
        var percentage = (float)tank.getStack().getAmount() / config.capacity();
        GuiHelper.renderVerticallyFilledBar(ms, screen, screen.getGuiLeft() + startX, screen.getGuiTop() + startY -10, 72, 14, 18, 50, percentage);
        Gui.drawCenteredString(ms, Minecraft.getInstance().font, percentage * 100 + "%", screen.getGuiLeft() + startX + 9, screen.getGuiTop() + startY + 60, 0xfefefe);
        Gui.drawCenteredString(ms, Minecraft.getInstance().font, tank.getStack().getAmount() + " mb", screen.getGuiLeft() + startX + 9, screen.getGuiTop() + 17, 0xfefefe);
    }

    @Override
    public void onDestroy(Level level, BlockPos pos) {

    }

    @Override
    public PortStorage deepClone() {
        var res = createSelf();
        res.tank = createTank(config.capacity());
        res.tank.setStack((STACK)this.tank.getStack().copy());
        return res;
    }
}
