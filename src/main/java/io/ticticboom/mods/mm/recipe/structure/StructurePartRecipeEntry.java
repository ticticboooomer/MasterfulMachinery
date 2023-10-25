package io.ticticboom.mods.mm.recipe.structure;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.MMRecipeEntry;
import io.ticticboom.mods.mm.recipe.RecipeContext;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.setup.model.StructureModel;
import io.ticticboom.mods.mm.structure.ConfiguredStructurePart;
import io.ticticboom.mods.mm.structure.IConfiguredStructurePart;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class StructurePartRecipeEntry extends MMRecipeEntry {
    @Override
    public IConfiguredRecipeEntry parse(JsonObject json) {
        JsonObject structurePart = json.getAsJsonObject("structurePart");
        var partType = ResourceLocation.tryParse(structurePart.get("type").getAsString());
        var part = MMRegistries.STRUCTURE_PARTS.get().getValue(partType);
        IConfiguredStructurePart config = part.parse(structurePart);
        var pos = json.getAsJsonObject("relativePos");
        var x = pos.get("x").getAsInt();
        var y = pos.get("y").getAsInt();
        var z = pos.get("z").getAsInt();
        return new StructurePartConfiguredRecipeEntry(new ConfiguredStructurePart(partType, config), new BlockPos(x, y, z));
    }

    @Override
    public boolean processInputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        var conf = (StructurePartConfiguredRecipeEntry) config;
        var part = MMRegistries.STRUCTURE_PARTS.get().getValue(conf.part().type());
        var transformer = MMRegistries.STRUCTURE_TRANSFORMS.get().getValue(ctx.appliedTransformId());
        var transformedPosList = transformer.transform(ImmutableList.of(new StructureModel.PlacedStructurePart(conf.relativePos(), MMRegistries.STRUCTURE_PARTS.get().getKey(part), conf.part().part())));
        var transformedPos = transformedPosList.get(0);
        return part.validatePlacement(ctx.level(), ctx.controllerPos().offset(transformedPos.pos()), conf.part().part());
    }

    @Override
    public boolean processOutputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        return true;
    }
}
