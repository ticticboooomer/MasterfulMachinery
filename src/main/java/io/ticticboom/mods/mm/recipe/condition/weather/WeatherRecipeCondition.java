package io.ticticboom.mods.mm.recipe.condition.weather;

import io.ticticboom.mods.mm.recipe.RecipeStateModel;
import io.ticticboom.mods.mm.recipe.condition.IRecipeCondition;
import net.minecraft.world.level.Level;

public class WeatherRecipeCondition implements IRecipeCondition {

    private final RecipeWeatherType type;

    public WeatherRecipeCondition(RecipeWeatherType type) {
        this.type = type;
    }

    @Override
    public boolean canRun(Level level, RecipeStateModel state) {
        if (type == RecipeWeatherType.RAIN) {
            return level.isRaining();
        } else if (type == RecipeWeatherType.THUNDER) {
            return level.isThundering();
        } else if (type == RecipeWeatherType.CLEAR) {
            return !level.isThundering() && !level.isRaining();
        }
        return false;
    }
}
