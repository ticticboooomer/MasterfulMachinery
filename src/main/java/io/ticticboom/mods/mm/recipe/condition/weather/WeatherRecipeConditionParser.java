package io.ticticboom.mods.mm.recipe.condition.weather;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.recipe.condition.IRecipeCondition;
import io.ticticboom.mods.mm.recipe.condition.IRecipeConditionParser;
import io.ticticboom.mods.mm.util.ParserUtils;

public class WeatherRecipeConditionParser implements IRecipeConditionParser {
    @Override
    public IRecipeCondition parse(JsonObject json) {
        var type = ParserUtils.parseEnum(json, "weather", RecipeWeatherType.class);
        return new WeatherRecipeCondition(type);
    }
}
