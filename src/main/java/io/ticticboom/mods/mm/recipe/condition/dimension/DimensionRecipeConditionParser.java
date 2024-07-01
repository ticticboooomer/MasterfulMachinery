package io.ticticboom.mods.mm.recipe.condition.dimension;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.recipe.condition.IRecipeCondition;
import io.ticticboom.mods.mm.recipe.condition.IRecipeConditionParser;
import io.ticticboom.mods.mm.util.ParserUtils;

public class DimensionRecipeConditionParser implements IRecipeConditionParser {
    @Override
    public IRecipeCondition parse(JsonObject json) {
        var id = ParserUtils.parseId(json, "dimension");
        return new DimensionRecipeCondition(id);
    }
}
