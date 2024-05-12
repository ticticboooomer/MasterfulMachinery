package io.ticticboom.mods.mm.client.structure;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.piece.modifier.StructurePieceModifier;
import net.minecraft.client.Minecraft;
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

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class GuiBlockRenderer {
    private final Block block;
    private BlockEntityRenderer<BlockEntity> ber;
    private BlockEntity be;
    private BlockState state;
    private final List<StructurePieceModifier> modifiers;
    private static Minecraft mc = Minecraft.getInstance();
    private BlockPos pos;

    public GuiBlockRenderer(Block block, List<StructurePieceModifier> modifiers) {
        this.block = block;
        this.modifiers = modifiers;
    }

    public void setupAt(BlockPos pos) {
        this.pos = pos;
        state = block.defaultBlockState();
        if (block instanceof EntityBlock eb) {
            be = eb.newBlockEntity(pos, state);
            ber = Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(be);
        }
        for (StructurePieceModifier mod : modifiers) {
            state = mod.modifyBlockState(state, be, pos);
            if (be != null) {
                be = mod.modifyBlockEntity(state, be, pos);
            }
        }
    }

    public void render(PoseStack pose, int mouseX, int mouseY, AutoTransform mouseTransform) {
        pose.pushPose();
        mouseTransform.transform(pose, pos);
        BlockRenderDispatcher brd = mc.getBlockRenderer();
        MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();
        var modeldata = be != null ? be.getModelData() : ModelData.EMPTY;
        var color = mc.getBlockColors().getColor(state, null, pos, 0);
        color = color == -1 ? 0xFFFFFF : color;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        RenderType renderType = ItemBlockRenderTypes.getRenderType(state, true);
        brd.renderSingleBlock(state, pose, bufferSource, 0xF000F0, OverlayTexture.NO_OVERLAY, modeldata, renderType);
        if (ber != null) {
            try {
                ber.render(be, 1.f, pose, bufferSource, 0xF000F0, OverlayTexture.NO_OVERLAY);
            } catch (Exception ignored) {

            }
        }
        bufferSource.endBatch();
        pose.popPose();
    }
}
