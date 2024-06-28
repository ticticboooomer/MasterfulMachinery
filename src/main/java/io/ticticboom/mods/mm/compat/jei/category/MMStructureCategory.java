package io.ticticboom.mods.mm.compat.jei.category;

import com.mojang.blaze3d.platform.GlStateManager;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.client.structure.GuiCountedItemStack;
import io.ticticboom.mods.mm.client.structure.GuiStructureRenderer;
import io.ticticboom.mods.mm.compat.jei.SlotGrid;
import io.ticticboom.mods.mm.compat.jei.SlotGridEntry;
import io.ticticboom.mods.mm.controller.MMControllerRegistry;
import io.ticticboom.mods.mm.setup.MMRegisters;
import io.ticticboom.mods.mm.structure.StructureModel;
import io.ticticboom.mods.mm.util.GLScissor;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class MMStructureCategory implements IRecipeCategory<StructureModel> {

    public static final RecipeType<StructureModel> RECIPE_TYPE = RecipeType.create("mm", "structure", StructureModel.class);

    private final IGuiHelper helper;

    private Vector3f pan = new Vector3f(0, 0, 0);

    public MMStructureCategory(final IGuiHelper helper) {
        this.helper = helper;
    }

    @Override
    public RecipeType<StructureModel> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("MM Structure");
    }

    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(Ref.Textures.GUI_LARGE_JEI, 0, 0, 162, 170);
    }

    @Override
    public IDrawable getIcon() {
        return helper.createDrawableItemStack(MMRegisters.BLUEPRINT.get().getDefaultInstance());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, StructureModel recipe, IFocusGroup focuses) {
        var catalysts = builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST);
        for (ResourceLocation id : recipe.controllerIds().getIds()) {
            Item controller = MMControllerRegistry.getControllerItem(id);
            if (controller != null) {
                catalysts.addItemStack(controller.getDefaultInstance());
            }
        }
        GuiStructureRenderer guiRenderer = recipe.getGuiRenderer();
        guiRenderer.resetTransforms();
        guiRenderer.init();
        var countedItemStacks = recipe.getCountedItemStacks();
        var grid = new SlotGrid(20, 20, 8, 3, 1, 130);
        recipe.setGrid(grid);
        for (GuiCountedItemStack countedItemStack : countedItemStacks) {
            SlotGridEntry next = grid.next();
            next.setUsed();
            var slot = builder.addSlot(RecipeIngredientRole.INPUT, next.x, next.y);
            slot.addItemStacks(countedItemStack.getStacks());
            slot.addTooltipCallback((a, b) -> {
                b.add(b.size() - 2, countedItemStack.getDetail());
            });
        }
    }

    @Override
    public void draw(StructureModel recipe, IRecipeSlotsView recipeSlotsView, PoseStack pose, double mouseX, double mouseY) {
        Vector4f zero = new Vector4f(0, 0, 0, 1);
        zero.transform(pose.last().pose());
        GLScissor.enable((int) zero.x(), (int) zero.y(), 160, 120);
        var renderer = recipe.getGuiRenderer();
        renderer.render(pose, (int) mouseX, (int) mouseY);
        GLScissor.disable();
        for (SlotGridEntry slot : recipe.getGrid().getSlots()) {
            if (!slot.used()) {
                continue;
            }
            RenderSystem.setShaderTexture(0, Ref.Textures.SLOT_PARTS);
            GuiComponent.blit(pose,  slot.x -1, slot.y - 1, 0, 26, 18, 18, 256, 256);
        }
        var fText = FormattedText.of(recipe.name());
        pose.pushPose();
        pose.translate(0, 0, 1000);
        WidgetUtils.drawWordWrap(pose, Minecraft.getInstance().font, fText, 5, 5, 160, 0xFFFFFF);
        pose.popPose();
    }
}
