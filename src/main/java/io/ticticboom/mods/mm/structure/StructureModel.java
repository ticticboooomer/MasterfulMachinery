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
import io.ticticboom.mods.mm.structure.layout.StructureLayout;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StructureModel {

    private final ResourceLocation id;
    private final String name;
    private final IdList controllerIds;
    private final StructureLayout layout;
    @Getter
    private final JsonObject config;

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
            StructureLayout layout,
            JsonObject config
    ) {
        this.id = id;
        this.name = name;
        this.controllerIds = controllerIds;
        this.layout = layout;
        this.config = config;
        if (FMLEnvironment.dist == Dist.CLIENT) {
            renderer = new GuiStructureRenderer(this);
//            countedPartItems = getCountedItemStacks();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public PositionedCyclingBlockRenderer controllerUiRenderer() {
        BlockPos pos = new BlockPos(0, 0, 0);
        List<ResourceLocation> ids = controllerIds.getIds();
        var res = new ArrayList<GuiBlockRenderer>();
        for (ResourceLocation id : ids) {
            Block controllerBlock = MMControllerRegistry.getControllerBlock(id);
            GuiBlockRenderer gbr = new GuiBlockRenderer(controllerBlock, List.of());
            gbr.setupAt(pos);
            res.add(gbr);
        }
        return new PositionedCyclingBlockRenderer(res, pos);
    }

    public static StructureModel parse(JsonObject json, ResourceLocation structureId) {
        var name = json.get("name").getAsString();
        var layout = StructureLayout.parse(json, structureId);
        var ids = IdList.parse(json.get("controllerIds"));
        return new StructureModel(structureId, name, ids, layout, json);
    }

    public static JsonObject paramsToJson(ResourceLocation id, String name, IdList controllerIds, StructureLayout layout) {
        var json = new JsonObject();
        json.addProperty("id", id.toString());
        json.addProperty("name", name);
        json.add("controllerIds", controllerIds.serialize());
        layout.serialize(json);
        return json;
    }

    public boolean formed(Level level, BlockPos controllerPos) {
        return layout.formed(level, controllerPos, this);
    }

    public RecipeStorages getStorages(Level level, BlockPos controllerPos) {
        return layout.getRecipeStorages(level, controllerPos, this);
    }

    public JsonObject debugFormed(Level level, BlockPos controllerPos) {
        var json = new JsonObject();
        var debugLayout = layout.debugFormed(level, controllerPos, this);
        json.addProperty("structureId", id.toString());
        json.addProperty("name", name);
        json.add("controllerIds", controllerIds.serialize());
        json.add("layout", debugLayout);
        return json;
    }

    public JsonObject debugSerialize() {
        var json = new JsonObject();
        json.addProperty("structureId", id.toString());
        json.addProperty("name", name);
        json.add("controllerIds", controllerIds.serialize());

        return json;
    }

    @OnlyIn(Dist.CLIENT)
    public GuiStructureRenderer getGuiRenderer() {
        return renderer;
    }

    public ResourceLocation id() {
        return id;
    }

    public String debugPath() {
        return id.getNamespace() + "/" + id.getPath() + ".json";
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

    public List<GuiCountedItemStack> getCountedItemStacks() {
        if (countedPartItems != null) {
            return countedPartItems;
        }
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
        countedPartItems = new ArrayList<>(result.values());
        var controllerList = new ArrayList<ItemStack>();
        for (ResourceLocation controller : controllerIds.getIds()) {
            Block controllerBlock = MMControllerRegistry.getControllerBlock(controller);
            controllerList.add(controllerBlock.asItem().getDefaultInstance());
        }
        countedPartItems.add(new GuiCountedItemStack(1, controllerList, Component.literal("Controller").withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD), "C"));
        return countedPartItems;
    }
}