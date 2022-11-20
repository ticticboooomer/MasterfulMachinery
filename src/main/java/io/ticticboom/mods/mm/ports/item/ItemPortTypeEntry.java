package io.ticticboom.mods.mm.ports.item;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.ports.base.*;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import io.ticticboom.mods.mm.util.Deferred;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.registration.IModIngredientRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Supplier;

public class ItemPortTypeEntry extends MMPortTypeEntry {

    @Override
    public Class<? extends PortStorage> storageClass() {
        return ItemPortStorage.class;
    }

    @Override
    public IConfiguredPort parse(JsonObject element) {
        var rows = element.get("slotRows").getAsInt();
        var cols = element.get("slotCols").getAsInt();
        return new ItemConfiguredPort(rows, cols);
    }

    @Override
    public IConfiguredIngredient parseIngredient(JsonObject json) {
        var item = ResourceLocation.tryParse(json.get("item").getAsString());
        var count = json.get("count").getAsInt();
        return new ItemConfiguredIngredient(item, count);
    }

    @Override
    public ResourceLocation overlay(boolean input) {
        return input ? Ref.res("block/base_ports/item_input_cutout") : Ref.res("block/base_ports/item_output_cutout");
    }

    @Override
    public PortStorage createStorage(IConfiguredPort config) {
        return new ItemPortStorage(((ItemConfiguredPort) config));
    }


    @Override
    public boolean processInputs(IConfiguredIngredient ingredient, List<PortStorage> storage) {
        var conf = (ItemConfiguredIngredient) ingredient;
        var itemCounter = 0;
        for (PortStorage portStorage : storage) {
            if (portStorage instanceof ItemPortStorage itemPortStorage) {
                for (int i = 0; i < itemPortStorage.items.getSlots(); i++) {
                    var slot = itemPortStorage.items.getStackInSlot(i);
                    var requiredAmount = conf.count() - itemCounter;
                    if (slot.getItem().getRegistryName().equals(conf.item())) {
                        if (slot.getCount() >= requiredAmount) {
                            var remains = slot.getCount() - requiredAmount;
                            itemCounter += requiredAmount;
                            slot.setCount(remains);
                        } else {
                            itemCounter += slot.getCount();
                            slot.setCount(0);
                        }
                    }
                }
                if (itemCounter >= conf.count()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean processOutputs(IConfiguredIngredient ingredient, List<PortStorage> storage) {
        var conf = (ItemConfiguredIngredient) ingredient;
        var itemCounter = 0;
        Item item = ForgeRegistries.ITEMS.getValue(conf.item());
        var maxStack = item.getMaxStackSize();
        for (PortStorage portStorage : storage) {
            if (portStorage instanceof ItemPortStorage itemPortStorage) {
                for (int i = 0; i < itemPortStorage.items.getSlots(); i++) {
                    var slot = itemPortStorage.items.getStackInSlot(i);
                    var requiredAmount = conf.count() - itemCounter;
                    if (slot.getItem().getRegistryName().toString().equals(conf.item().toString())) {
                        var availableSpace = maxStack - slot.getCount();
                        int remains = Math.min(availableSpace, requiredAmount);
                        itemCounter += remains;
                        slot.setCount(slot.getCount() + remains);
                    } else if (slot.isEmpty()) {
                        itemCounter += maxStack;
                        int remains = Math.min(maxStack, requiredAmount);
                        itemPortStorage.items.setStackInSlot(i, new ItemStack(item, remains));
                    }
                }
                if (itemCounter >= conf.count()) {
                    return true;
                }
            }
        }
        return false;
    }


}
