package io.ticticboom.mods.mm.ports.fluid;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.client.screen.PortScreen;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.util.FluidRenderer;
import io.ticticboom.mods.mm.util.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidPortStorage extends PortStorage {

    public FluidConfiguredPort config;
    public final FluidHandler handler;
    public final LazyOptional<FluidHandler> handlerLO;

    @Override
    public InteractionResult playerInteractWithItem(Player player, Level level, BlockPos pos, InteractionHand hand) {
        if (FluidUtil.interactWithFluidHandler(player, hand, handler)) {
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    public FluidPortStorage(FluidConfiguredPort config) {
        this.config = config;
        handler = new FluidHandler(config.capacity());
        handlerLO = LazyOptional.of(() -> handler);
    }

    @Override
    public void read(CompoundTag tag) {
        if (tag.contains("Amount") && tag.contains("Fluid")) {
            var amount = tag.getInt("Amount");
            String fluidId = tag.getString("Fluid");
            var fluid = ForgeRegistries.FLUIDS.getValue(ResourceLocation.tryParse(fluidId));
            handler.fluid = new FluidStack(fluid, amount);
        }
    }

    @Override
    public CompoundTag write() {
        var result = new CompoundTag();
        result.putString("Fluid", handler.fluid.getFluid().getRegistryName().toString());
        result.putInt("Amount", handler.fluid.getAmount());
        return result;
    }

    @Override
    public void renderScreen(PortScreen screen, PoseStack ms, int x, int y) {
        RenderHelper.useTexture(Ref.SLOT_PARTS);
        var startX = 175 / 2 - (18 / 2);
        var startY = 252 / 4 - (18 / 2);
        screen.blit(ms, screen.getGuiLeft() + startX, screen.getGuiTop() + startY, 0, 26, 18, 18);
        if (!handler.fluid.isEmpty()) {
            FluidRenderer.INSTANCE.render(ms, screen.getGuiLeft() + startX + 1, screen.getGuiTop() + startY + 1, handler.fluid, 16);
            Gui.drawCenteredString(ms, Minecraft.getInstance().font, handler.fluid.getAmount() + " " + handler.fluid.getDisplayName().getString(), screen.getGuiLeft() + startX + 9, screen.getGuiTop() + startY + 30, 0xfefefe);
        }


    }

    @Override
    public void onDestroy(Level level, BlockPos pos) {

    }

    @Override
    public PortStorage deepClone() {
        var result = new FluidPortStorage(config);
        result.handler.fluid = handler.fluid.copy();
        return result;
    }
}
