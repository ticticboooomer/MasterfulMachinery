package io.ticticboom.mods.mm.port.item;

import io.ticticboom.mods.mm.compat.jei.SlotGrid;
import io.ticticboom.mods.mm.compat.jei.ingredient.MMJeiIngredients;
import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class ItemPortIngredient implements IPortIngredient {

    private final ResourceLocation itemId;
    private final int count;
    private final Item item;
    private final ItemStack stack;

    public ItemPortIngredient(ResourceLocation itemId, int count) {

        this.itemId = itemId;
        this.count = count;
        item = ForgeRegistries.ITEMS.getValue(itemId);
        if (item == null){
            throw new RuntimeException(String.format("Could not find item [%s] which is required by an MM recipe", itemId));
        }
        stack = new ItemStack(item, count);
    }

    @Override
    public boolean canProcess(Level level, RecipeStorages storages) {
        List<ItemPortStorage> itemStorages = storages.getInputStorages(ItemPortStorage.class);
        int remaining = count;
        for (ItemPortStorage storage : itemStorages) {
            remaining = storage.canExtract(item, remaining);
        }
        return remaining <= 0;
    }

    @Override
    public void process(Level level, RecipeStorages storages) {
        List<ItemPortStorage> itemStorages = storages.getInputStorages(ItemPortStorage.class);
        int remaining = count;
        for (ItemPortStorage storage : itemStorages) {
            remaining = storage.extract(item, remaining);
        }
    }

    @Override
    public boolean canOutput(Level level, RecipeStorages storages) {
        List<ItemPortStorage> itemStorages = storages.getOutputStorages(ItemPortStorage.class);
        int remainingToInsert = count;
        for (ItemPortStorage itemStorage : itemStorages) {
            remainingToInsert = itemStorage.canInsert(item, remainingToInsert);
        }
        return remainingToInsert <= 0;
    }

    @Override
    public void output(Level level, RecipeStorages storages) {
        List<ItemPortStorage> itemStorages = storages.getOutputStorages(ItemPortStorage.class);
        int remainingToInsert = count;
        for (ItemPortStorage itemStorage : itemStorages) {
            remainingToInsert = itemStorage.insert(item, remainingToInsert);
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeModel model, IFocusGroup focus, IJeiHelpers helpers, SlotGrid grid, IRecipeSlotBuilder recipeSlot) {
        recipeSlot.addIngredient(MMJeiIngredients.ITEM, this.stack);
    }
}
