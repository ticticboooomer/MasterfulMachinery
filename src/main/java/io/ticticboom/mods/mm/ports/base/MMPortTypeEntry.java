package io.ticticboom.mods.mm.ports.base;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.block.PortBlock;
import io.ticticboom.mods.mm.block.entity.PortBlockEntity;
import io.ticticboom.mods.mm.datagen.gen.MMBlockStateProvider;
import io.ticticboom.mods.mm.setup.model.PortModel;
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
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public abstract class MMPortTypeEntry {
    public abstract Class<? extends PortStorage> storageClass();
    public abstract IConfiguredPort parse(JsonObject element);
    public abstract IConfiguredIngredient parseIngredient(JsonObject json);
    public abstract ResourceLocation overlay(boolean input);
    public abstract PortStorage createStorage(IConfiguredPort config);
    public Supplier<Block> blockSupplier(boolean input, PortModel model, Deferred<RegistryObject<MenuType<?>>> menu, Deferred<RegistryObject<BlockEntityType<?>>> beType) {
        return () -> new PortBlock(model, menu.data, beType.data);
    }
    public BlockEntityType.BlockEntitySupplier<?> beSupplier(boolean input, PortModel model, RegistryObject<BlockEntityType<?>> beType) {
        return (a, b) -> new PortBlockEntity(beType.get(), a, b, model);
    }

    public void generateBlockStates(MMBlockStateProvider provider, Block block) {
        var port = ((IPortBlock) block);
        var input = port.model().input();
        if (input) {
            provider.dynamicBlock(block.getRegistryName(), MMBlockStateProvider.BASE_TEXTURE, overlay(input));
            provider.simpleBlock(block, new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.ID, "block/" + block.getRegistryName().getPath())));
        } else {
            provider.dynamicBlock(block.getRegistryName(), MMBlockStateProvider.BASE_TEXTURE, overlay(input));
            provider.simpleBlock(block, new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.ID, "block/" + block.getRegistryName().getPath())));
        }
    }

    public abstract boolean processInputs(IConfiguredIngredient ingredient, List<PortStorage> storage);
    public abstract boolean processOutputs(IConfiguredIngredient ingredient, List<PortStorage> storage);

}
