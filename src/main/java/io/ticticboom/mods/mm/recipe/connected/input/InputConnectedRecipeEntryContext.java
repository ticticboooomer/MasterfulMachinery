package io.ticticboom.mods.mm.recipe.connected.input;

import io.ticticboom.mods.mm.recipe.IRecipeEntryContext;

public class InputConnectedRecipeEntryContext implements IRecipeEntryContext {
    private final String id;
    private boolean flag;
    public InputConnectedRecipeEntryContext(String id, boolean flag) {
        this.id = id;
        this.flag = flag;
    }

    public String id() {
        return id;
    }

    public boolean flag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
