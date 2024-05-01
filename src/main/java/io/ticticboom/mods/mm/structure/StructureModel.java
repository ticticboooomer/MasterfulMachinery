package io.ticticboom.mods.mm.structure;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.client.structure.GuiBlockRenderer;
import io.ticticboom.mods.mm.client.structure.GuiCountedItemStack;
import io.ticticboom.mods.mm.client.structure.GuiStructureRenderer;
import io.ticticboom.mods.mm.client.structure.PositionedCyclingBlockRenderer;
import io.ticticboom.mods.mm.compat.jei.SlotGrid;
import io.ticticboom.mods.mm.controller.MMControllerRegistry;
import io.ticticboom.mods.mm.model.IdList;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import io.ticticboom.mods.mm.structure.layout.PositionedLayoutPiece;
import io.ticticboom.mods.mm.structure.layout.StructureCharacterGrid;
import io.ticticboom.mods.mm.structure.layout.StructureLayout;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.util.thread.EffectiveSide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructureModel {

    private final ResourceLocation id;
    private final String name;
    private final IdList controllerIds;
    private final StructureLayout layout;

    @OnlyIn(Dist.CLIENT)
    private GuiStructureRenderer renderer;

    @OnlyIn(Dist.CLIENT)
    private List<GuiCountedItemStack> countedPartItems;

    @Getter
    @Setter
    @OnlyIn(Dist.CLIENT)
    private SlotGrid grid;

    public StructureModel(
            ResourceLocation id,
            String name,
            IdList controllerIds,
            StructureLayout layout
    ) {
        this.id = id;
        this.name = name;
        this.controllerIds = controllerIds;
        this.layout = layout;
        if (EffectiveSide.get() == LogicalSide.CLIENT) {
            renderer = new GuiStructureRenderer(this);
            countedPartItems = getCountedItemStacks();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public PositionedCyclingBlockRenderer controllerUiRenderer() {
        BlockPos pos = new BlockPos(0, 0, 0);
        List<ResourceLocation> ids = controllerIds.getIds();
        var res = new ArrayList<GuiBlockRenderer>();
        for (ResourceLocation id : ids) {
            Block controllerBlock = MMControllerRegistry.getControllerBlock(id);
            GuiBlockRenderer gbr = new GuiBlockRenderer(controllerBlock);
            gbr.setupAt(pos);
            res.add(gbr);
        }
        return new PositionedCyclingBlockRenderer(res, pos);
    }

    public static StructureModel parse(JsonObject json, ResourceLocation structureId) {
        var name = json.get("name").getAsString();
        var layout = StructureLayout.parse(json, structureId);
        var ids = IdList.parse(json.get("controllerIds"));
        return new StructureModel(structureId, name, ids, layout);
    }

    public boolean formed(Level level, BlockPos controllerPos) {
        return layout.formed(level, controllerPos, this);
    }

    public RecipeStorages getStorages(Level level, BlockPos controllerPos) {
        return layout.getRecipeStorages(level, controllerPos, this);
    }

    @OnlyIn(Dist.CLIENT)
    public GuiStructureRenderer getGuiRenderer() {
        return renderer;
    }

    public ResourceLocation id() {
        return id;
    }

    public String name() {
        return name;
    }

    public IdList controllerIds() {
        return controllerIds;
    }

    public StructureLayout layout() {
        return layout;
    }

    public List<ItemStack> getRelatedBlocksAsItemStacks() {
        var result = new ArrayList<ItemStack>();
        for (PositionedLayoutPiece positionedPiece : layout.getPositionedPieces()) {
            var items = positionedPiece.piece().getGuiPiece().getBlocks().stream().map(x -> x.asItem().getDefaultInstance()).toList();
            result.addAll(items);
        }
        return result;
    }

    public List<GuiCountedItemStack> getCountedItemStacks() {
        var result = new HashMap<String, GuiCountedItemStack>();
        for (PositionedLayoutPiece positionedPiece : layout.getPositionedPieces()) {
            var guiPiece = positionedPiece.piece().getGuiPiece();
            var blocks = guiPiece.getBlocks().stream().map(x -> x.asItem().getDefaultInstance()).toList();
            String valueId = positionedPiece.piece().getValueId();
            if (!result.containsKey(valueId)) {
                result.put(valueId, new GuiCountedItemStack(1, blocks, guiPiece.getDisplay(),
                        valueId));
            } else {
                result.get(valueId).addCount(1);
            }
        }
        return new ArrayList<>(result.values());
    }
}