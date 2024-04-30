package io.ticticboom.mods.mm.recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class RecipeStateModel {
    private boolean canProcess = false;
    private int tickProgress = 0;
    private double tickPercentage = 0;
    private boolean canFinish = false;

    public void proceedTick() {
        tickProgress++;
    }

    public CompoundTag save(CompoundTag tag) {
        tag.putBoolean("canProcess", canProcess);
        tag.putInt("tickProgress", tickProgress);
        tag.putDouble("tickPercentage", tickPercentage);
        tag.putBoolean("canFinish", canFinish);
        return tag;
    }

    public static RecipeStateModel load(CompoundTag tag) {
        RecipeStateModel model = new RecipeStateModel();
        model.setCanProcess(tag.getBoolean("canProcess"));
        model.setTickProgress(tag.getInt("tickProgress"));
        model.setTickPercentage(tag.getDouble("tickPercentage"));
        model.setCanFinish(tag.getBoolean("canFinish"));
        return model;
    }

}
