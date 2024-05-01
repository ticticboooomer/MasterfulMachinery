package io.ticticboom.mods.mm.client.structure;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

@OnlyIn(Dist.CLIENT)
public class GuiBlockRenderer {
    private final Block block;
    private BlockEntityRenderer<BlockEntity> ber;
    private BlockEntity be;
    private final BlockState state;
    private static Minecraft mc = Minecraft.getInstance();
    private BlockPos pos;

    public GuiBlockRenderer(Block block) {
        this.block = block;
        state = block.defaultBlockState();
    }

    public void setupAt(BlockPos pos) {
        this.pos = pos;
        if (block instanceof EntityBlock eb) {
            be = eb.newBlockEntity(pos, state);
            ber = Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(be);
        }
    }

    public void render(GuiGraphics gfx, int mouseX, int mouseY, AutoTransform mouseTransform) {
        PoseStack pose = gfx.pose();
        pose.pushPose();
        mouseTransform.transform(pose.last().pose(), pos);
        BlockRenderDispatcher brd = mc.getBlockRenderer();
        MultiBufferSource.BufferSource bufferSource = gfx.bufferSource();
        var modeldata = be != null ? be.getModelData() : ModelData.EMPTY;
        var model = brd.getBlockModel(state);
        var color = mc.getBlockColors().getColor(state, null, pos, 0);
        color = color == -1 ? 0xFFFFFF : color;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        RenderType renderType = ItemBlockRenderTypes.getRenderType(state, true);
        brd.renderSingleBlock(state, pose, bufferSource, 0xF000F0, OverlayTexture.NO_OVERLAY, modeldata, renderType);
        if (ber != null) {
            ber.render(be, 1.f, gfx.pose(), bufferSource, 0xF000F0, OverlayTexture.NO_OVERLAY);
        }
        bufferSource.endBatch();
        pose.popPose();
    }
}
