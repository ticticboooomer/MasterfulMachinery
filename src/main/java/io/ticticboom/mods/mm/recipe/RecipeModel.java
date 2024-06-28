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
        List<SlotGridEntry> outputSlots,
        JsonObject config
) {
    public static RecipeModel parse(JsonObject json, ResourceLocation id) {
        var structrueId = ParserUtils.parseId(json, "structureId");
        var ticks = json.get("ticks").getAsInt();
        var inputs = RecipeInputs.parse(json.getAsJsonArray("inputs"));
        var outputs = RecipeOutputs.parse(json.getAsJsonArray("outputs"));
        return new RecipeModel(id, structrueId, ticks, inputs, outputs, new ArrayList<>(), new ArrayList<>(), json);
    }

    public String debugPath() {
        return id.getNamespace() + "/" + id.getPath() + ".json";
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

    public void ditchRecipe(Level level, RecipeStateModel model, RecipeStorages storages) {
        inputs.ditch(level, storages, model);
        outputs.ditch(level, storages, model);
    }

    public JsonObject debugRun(Level level, RecipeStateModel model, RecipeStorages storages) {
        var json = new JsonObject();
        json.addProperty("id", id.toString());
        json.addProperty("structureId", structureId.toString());
        json.addProperty("ticks", ticks);
        json.add("inputs", inputs.debugRun(level, storages, model));
        json.add("outputs", outputs.debugRun(level, storages, model));
        json.add("portStorages", storages.debug());
        return json;
    }
}
