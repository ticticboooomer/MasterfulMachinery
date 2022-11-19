package io.ticticboom.mods.mm.ports.energy;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import io.ticticboom.mods.mm.ports.base.IConfiguredPort;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.ports.energy.jei.EnergyIngredientHelper;
import io.ticticboom.mods.mm.ports.energy.jei.EnergyIngredientRenderer;
import io.ticticboom.mods.mm.ports.energy.jei.EnergyIngredientType;
import io.ticticboom.mods.mm.ports.energy.jei.EnergyStack;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import io.ticticboom.mods.mm.util.Deferred;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.registration.IModIngredientRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Supplier;

public class EnergyPortTypeEntry extends MMPortTypeEntry {
    @Override
    public ResourceLocation id() {
        return Ref.Ports.ENERGY;
    }

    @Override
    public Class<? extends PortStorage> storageClass() {
        return EnergyPortStorage.class;
    }

    @Override
    public IConfiguredPort parse(JsonObject element) {
        var capacity = element.get("capacity").getAsInt();
        return new EnergyConfiguredPort(capacity);
    }

    @Override
    public IConfiguredIngredient parseIngredient(JsonObject json) {
        var amount = json.get("amount").getAsInt();
        return new EnergyConfiguredIngredient(amount);
    }

    @Override
    public ResourceLocation overlay(boolean input) {
        return input ? Ref.res("block/base_ports/energy_input_cutout") : Ref.res("block/base_ports/energy_output_cutout");

    }

    @Override
    public PortStorage createStorage(IConfiguredPort config) {
        return new EnergyPortStorage((EnergyConfiguredPort) config);
    }

    @Override
    public boolean processInputs(IConfiguredIngredient ingredient, List<PortStorage> storage) {
        var conf = (EnergyConfiguredIngredient) ingredient;
        var itemCounter = 0;
        for (PortStorage portStorage : storage) {
            if (portStorage instanceof EnergyPortStorage eps) {
                itemCounter += eps.handler.extractEnergy(conf.amount(), false);
                if (itemCounter >= conf.amount()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean processOutputs(IConfiguredIngredient ingredient, List<PortStorage> storage) {
        var conf = (EnergyConfiguredIngredient) ingredient;
        var itemCounter = 0;
        for (PortStorage portStorage : storage) {
            if (portStorage instanceof EnergyPortStorage eps) {
                itemCounter += eps.handler.receiveEnergy(conf.amount(), false);
                if (itemCounter >= conf.amount()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static EnergyIngredientType ENERGY_STACK = new EnergyIngredientType();
    @Override
    public void registerJeiIngredient(IModIngredientRegistration reg, Deferred<IJeiHelpers> helpers) {
        reg.register(ENERGY_STACK, ImmutableList.of(), new EnergyIngredientHelper(), new EnergyIngredientRenderer(helpers));
    }

    @Override
    public void renderJei(RecipeModel recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY, IConfiguredIngredient ing, IJeiHelpers helpers, boolean input, int x, int y) {
        helpers.getGuiHelper().getSlotDrawable().draw(stack, x - 1, y - 1);
    }

    @Override
    public void setupRecipeJei(IConfiguredIngredient ingredient, IRecipeLayoutBuilder builder, RecipeModel recipe, IFocusGroup focuses, IRecipeSlotBuilder slot, boolean input, int x, int y) {
        var conf = (EnergyConfiguredIngredient) ingredient;
        slot.addIngredient(ENERGY_STACK, new EnergyStack(conf.amount()));
    }
}
