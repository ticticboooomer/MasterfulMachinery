package io.ticticboom.mods.mm.ports.base;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.block.PortBlock;
import io.ticticboom.mods.mm.datagen.gen.MMBlockStateProvider;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import io.ticticboom.mods.mm.util.Deferred;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.registration.IModIngredientRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;
import java.util.function.Supplier;

public abstract class MMPortTypeEntry {
    public abstract ResourceLocation id();
    public abstract Class<? extends PortStorage> storageClass();
    public abstract IConfiguredPort parse(JsonObject element);
    public abstract IConfiguredIngredient parseIngredient(JsonObject json);
    public abstract ResourceLocation overlay(boolean input);
    public abstract PortStorage createStorage(IConfiguredPort config);

    public void generateBlockStates(MMBlockStateProvider provider, PortBlock port) {
        var input = port.model().input();
        if (input) {
            provider.dynamicBlock(port.getRegistryName(), MMBlockStateProvider.BASE_TEXTURE, overlay(input));
            provider.simpleBlock(port, new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.ID, "block/" + port.getRegistryName().getPath())));
        } else {
            provider.dynamicBlock(port.getRegistryName(), MMBlockStateProvider.BASE_TEXTURE, overlay(input));
            provider.simpleBlock(port, new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.ID, "block/" + port.getRegistryName().getPath())));
        }
    }

    public abstract boolean processInputs(IConfiguredIngredient ingredient, List<PortStorage> storage);
    public abstract boolean processOutputs(IConfiguredIngredient ingredient, List<PortStorage> storage);
    public abstract void registerJeiIngredient(IModIngredientRegistration registration, Deferred<IJeiHelpers> helpers);

    public abstract void renderJei(RecipeModel recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY, IConfiguredIngredient ing, IJeiHelpers helpers, boolean input, int x, int y);
    public abstract void setupRecipeJei(IConfiguredIngredient ingredient, IRecipeLayoutBuilder builder, RecipeModel recipe, IFocusGroup focuses, IRecipeSlotBuilder slot, boolean input, int x, int y);

}
