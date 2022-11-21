package io.ticticboom.mods.mm.ports.mekanism.gas;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.client.screen.PortScreen;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.util.GuiHelper;
import io.ticticboom.mods.mm.util.RenderHelper;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.common.Mekanism;
import mekanism.common.capabilities.Capabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class MekGasPortStorage extends PortStorage {
    public MekGasConfiguredPort config;
    public IGasTank tank;
    public LazyOptional<IGasTank> handlerLO;

    public MekGasPortStorage(MekGasConfiguredPort config) {
        this.config = config;
        tank = ChemicalTankBuilder.GAS.create(config.capacity(), null);
        handlerLO = LazyOptional.of(() -> tank);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        if (cap == Capabilities.GAS_HANDLER_CAPABILITY) {
            return handlerLO.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public void read(CompoundTag tag) {
        var gas = tag.getString("Gas");
        var amount = tag.getLong("Amount");
        var gasType= MekanismAPI.gasRegistry().getValue(ResourceLocation.tryParse(gas));
        tank.setStack(new GasStack(gasType, amount));
    }

    @Override
    public CompoundTag write() {
        var result = new CompoundTag();
        GasStack stack = tank.getStack();
        result.putString("Gas", stack.getRaw().getRegistryName().toString());
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
        var res = new MekGasPortStorage(config);
        res.tank.setStack(tank.getStack());
        return res;
    }
}
