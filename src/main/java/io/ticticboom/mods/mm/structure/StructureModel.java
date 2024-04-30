package io.ticticboom.mods.mm.structure;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.client.structure.GuiStructureRenderer;
import io.ticticboom.mods.mm.model.IdList;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import io.ticticboom.mods.mm.structure.layout.PositionedLayoutPiece;
import io.ticticboom.mods.mm.structure.layout.StructureLayout;
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
import java.util.List;

public class StructureModel {

    private final ResourceLocation id;
    private final String name;
    private final IdList controllerIds;
    private final StructureLayout layout;

    @OnlyIn(Dist.CLIENT)
    private GuiStructureRenderer renderer;

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
        }
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
            List<Block> blocks = positionedPiece.piece().getGuiPiece().getBlocks();
            for (Block block : blocks) {
                result.add(block.asItem().getDefaultInstance());
            }
        }
        return result;
    }
}