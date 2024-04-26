package io.ticticboom.mods.mm.recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.resources.ResourceLocation;

@Getter
@Setter
@NoArgsConstructor
public class RecipeStateModel {
    private ResourceLocation recipeId;
    private boolean canProcess = false;
    private int tickProgress = 0;
    private double tickPercentage = 0;
    private boolean canFinish = false;

    public void proceedTick() {
        tickProgress++;
    }
}
