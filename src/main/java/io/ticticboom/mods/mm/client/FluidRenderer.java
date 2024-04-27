package io.ticticboom.mods.mm.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import org.joml.Matrix4f;

public class FluidRenderer {

    public static final FluidRenderer INSTANCE = new FluidRenderer(16);
    private static final int TEX_WIDTH = 16;
    private static final int TEX_HEIGHT = 16;

    private final int width;

    public FluidRenderer(int width) {
        this.width = width;
    }

    public void render(GuiGraphics gfx, final int xPosition, final int yPosition, FluidStack stack, final int height) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        drawFluid(gfx, xPosition, yPosition, stack, height);

        RenderSystem.setShaderColor(1, 1, 1, 1);

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    private void drawFluid(GuiGraphics gfx, int xPosition, int yPosition, FluidStack stack, final int height) {
        if (stack == null || stack.isEmpty()) {
            return;
        }
        Fluid fluid = stack.getFluid();
        if (fluid == null) {
            return;
        }

        TextureAtlasSprite fluidStillSprite = getStillFluidSprite(stack);

        var prop = IClientFluidTypeExtensions.of(fluid);
        int color = prop.getTintColor(stack);

        drawTiledSprite(gfx, xPosition, yPosition, width, height, color, (stack.getAmount() / 1000000f), fluidStillSprite);
    }

    private void drawTiledSprite(GuiGraphics gfx, int xPosition, int yPosition, int width, int height, int color, float scaledAmount, TextureAtlasSprite sprite) {
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        setGLColorFromInt(color);

        gfx.blit(xPosition, yPosition,  0, width, height, sprite);
    }

    private static void setGLColorFromInt(int color) {
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;
        float alpha = ((color >> 24) & 0xFF) / 255F;

        RenderSystem.setShaderColor(red, green, blue, alpha);
    }

    private TextureAtlasSprite getStillFluidSprite(FluidStack stack) {
        Minecraft minecraft = Minecraft.getInstance();
        Fluid fluid = stack.getFluid();
        var prop = IClientFluidTypeExtensions.of(fluid);
        ResourceLocation fluidStill = prop.getStillTexture();
        return minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidStill);
    }
}
