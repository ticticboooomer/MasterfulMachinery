package io.ticticboom.mods.mm.recipe;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.compat.jei.SlotGridEntry;
import io.ticticboom.mods.mm.util.ParserUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public record RecipeModel(
        ResourceLocation id,
        ResourceLocation structureId,
        int ticks,
        RecipeInputs inputs,
        RecipeOutputs outputs,
        List<SlotGridEntry> inputSlots,
        List<SlotGridEntry> outputSlots
) {
    public static RecipeModel parse(JsonObject json, ResourceLocation id) {
        var structrueId = ParserUtils.parseId(json, "structureId");
        var ticks = json.get("ticks").getAsInt();
        var inputs = RecipeInputs.parse(json.getAsJsonArray("inputs"));
        var outputs = RecipeOutputs.parse(json.getAsJsonArray("outputs"));
        return new RecipeModel(id, structrueId, ticks, inputs, outputs, new ArrayList<>(), new ArrayList<>());
    }

    public boolean canProcess(Level level, RecipeStateModel model, RecipeStorages storages) {
        var canInput = inputs.canProcess(level, storages, model);
        var canOutput = outputs.canProcess(level, storages, model);

        boolean canProcessResult = canInput && canOutput;
        if (!canProcessResult) {
            model.setTickProgress(0);
        }
        model.setCanProcess(canProcessResult);
        return canProcessResult;
    }

    public void runTick(Level level, RecipeStateModel model, RecipeStorages storages) {
        inputs.processTick(level, storages, model);
        outputs.processTick(level, storages, model);

        model.proceedTick();
        model.setTickPercentage(((double) model.getTickProgress() / ticks) * 100);
        if (model.getTickProgress() >= ticks) {
            model.setCanFinish(true);
        }
    }

    public void process(Level level, RecipeStateModel model, RecipeStorages storages) {
        inputs.process(level, storages, model);
        outputs.process(level, storages, model);

        model.setTickProgress(0);
        model.setCanFinish(false);
    }
}
