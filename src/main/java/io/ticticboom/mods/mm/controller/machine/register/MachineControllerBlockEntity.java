package io.ticticboom.mods.mm.controller.machine.register;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.controller.IControllerBlockEntity;
import io.ticticboom.mods.mm.controller.IControllerPart;
import io.ticticboom.mods.mm.model.ControllerModel;
import io.ticticboom.mods.mm.port.fluid.register.FluidPortBlock;
import io.ticticboom.mods.mm.recipe.MachineRecipeManager;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import io.ticticboom.mods.mm.recipe.RecipeStateModel;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.structure.StructureManager;
import io.ticticboom.mods.mm.structure.StructureModel;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MachineControllerBlockEntity extends BlockEntity implements IControllerBlockEntity, IControllerPart {
    private final ControllerModel model;
    private final RegistryGroupHolder groupHolder;
    private final ResourceLocation controllerId;

    public MachineControllerBlockEntity(ControllerModel model, RegistryGroupHolder groupHolder, BlockPos pos, BlockState state) {
        super(groupHolder.getBe().get(), pos, state);
        this.model = model;
        this.groupHolder = groupHolder;
        controllerId = Ref.id(model.id());
    }

    @Getter
    private StructureModel structure = null;
    @Getter
    private RecipeStateModel recipeState = null;
    private RecipeModel currentRecipe = null;
    private RecipeStorages portStorages = null;

    public void tick() {
        if (level.isClientSide()) {
            return;
        }
        runMachineTick();

        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    private void runMachineTick() {
        // check if cached structure is still formed
        if (canRemainFormed()) {
            runRecipe();
            return;
        }

        // if cached structure is not formed anymore, find a new one
        for (StructureModel structureModel : StructureManager.STRUCTURES.values()) {
            if (!structureModel.controllerIds().contains(controllerId)) {
                continue;
            }

            if (structureModel.formed(level, getBlockPos())) {
                setChanged();
                structure = structureModel;
                runRecipe();
                return;
            }
        }

        // if gets here, the structure is not formed
        invalidateProgress();
    }

    private boolean canRemainFormed() {
        if (structure == null) {
            return false;
        }
        return structure.formed(level, getBlockPos());
    }

    private boolean canContinueRecipe() {
        if (currentRecipe == null) {
            return false;
        }
        return currentRecipe.canProcess(level, recipeState, portStorages);
    }

    private void runRecipe() {
        if (portStorages == null) {
            portStorages = structure.getStorages(level, getBlockPos());
        }

        if (recipeState == null) {
            recipeState = new RecipeStateModel();
        }

        if (!canContinueRecipe()) {
            for (RecipeModel recipe : MachineRecipeManager.RECIPES.values()) {
                if (!recipe.structureId().equals(structure.id())) {
                    continue;
                }

                if (recipe.canProcess(level, recipeState, portStorages)) {
                    setChanged();
                    currentRecipe = recipe;
                    performRecipeTick();
                    return;
                }
            }
            invalidateRecipe();
            return;
        }
        performRecipeTick();
    }

    private void performRecipeTick() {
        currentRecipe.runTick(level, recipeState, portStorages);
        if (recipeState.isCanFinish()) {
            currentRecipe.process(level, recipeState, portStorages);
            invalidateRecipe();
        }
    }

    public void invalidateProgress() {
        setChanged();
        structure = null;
        invalidateRecipe();
    }

    public void invalidateRecipe() {
        recipeState = null;
        currentRecipe = null;
    }

    @Override
    public ControllerModel getModel() {
        return model;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal(model.name());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
        return new MachineControllerMenu(model, groupHolder, windowId, inv, this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        if (recipeState != null) {
            tag.put("recipeState", recipeState.save(new CompoundTag()));
        }
        if (structure != null) {
            tag.putString("structureId", structure.id().toString());
        }
        if (currentRecipe != null) {
            tag.putString("recipeId", currentRecipe.id().toString());
        }
        tag.putBoolean("filler", true);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("recipeState")) {
            recipeState = RecipeStateModel.load(tag.getCompound("recipeState"));
        } else {
            recipeState = null;
        }
        if (tag.contains("structureId")) {
            ResourceLocation structureId = new ResourceLocation(tag.get("structureId").getAsString());
            structure = StructureManager.STRUCTURES.get(structureId);
        } else {
            structure = null;
        }
        if (tag.contains("recipeId")) {
            ResourceLocation recipeId = new ResourceLocation(tag.get("recipeId").getAsString());
            currentRecipe = MachineRecipeManager.RECIPES.get(recipeId);
        } else {
            currentRecipe = null;
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        var tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
