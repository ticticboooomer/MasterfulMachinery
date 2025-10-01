package io.ticticboom.mods.mm.controller.machine.register;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.controller.IControllerBlockEntity;
import io.ticticboom.mods.mm.controller.IControllerPart;
import io.ticticboom.mods.mm.model.ControllerModel;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static io.ticticboom.mods.mm.config.MMConfigSetup.COMMON;

public class MachineControllerBlockEntity extends BlockEntity implements IControllerBlockEntity, IControllerPart {
    private static final Executor STRUCTURE_VALIDATION_EXECUTOR = Executors.newFixedThreadPool(
        Math.max(2, Runtime.getRuntime().availableProcessors() / 2), r -> {
        Thread thread = new Thread(r, "MM-Structures");
        thread.setDaemon(true);
        thread.setPriority(Thread.MAX_PRIORITY - 1);
        return thread;
    });
    
    private static final AtomicInteger ACTIVE_VALIDATIONS = new AtomicInteger(0);
    private static final int MAX_CONCURRENT_VALIDATIONS = Math.max(4, Runtime.getRuntime().availableProcessors());

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
    private boolean isFormed = false;
    private CompletableFuture<Void> structureValidationFuture = null;
    private long lastTick = 0;

    public void tick() {
        if (level.isClientSide() || isRemoved()) {
            return;
        }
        runMachineTick();

        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    private void runMachineTick() {
        //lastTick != level.getGameTime() if controller boosted by torcherino, we not making validation more than once per tick
        if((!isFormed || level.getGameTime() % COMMON.structureValidationRate.get() == 0) && lastTick != level.getGameTime()) {
            if (COMMON.asyncStructureValidation.get()) {
                validateStructureAsync(getLevel());
            } else {
                validateStructure(getLevel());
            }
        }
        lastTick = level.getGameTime();
        if (isFormed) {
            runRecipe();
        }
    }

    private void validateStructure(Level level) {
        if (structure == null) {
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

            invalidateProgress();
            isFormed = false;
        } else {
            isFormed = structure.formed(level, getBlockPos());
        }
    }

    private void validateStructureAsync(Level level) {
        // If there's already a validation running, don't start another one
        if (structureValidationFuture != null && !structureValidationFuture.isDone()) {
            return;
        }

        // Rate limiting: don't start if too many validations are already running
        if (ACTIVE_VALIDATIONS.get() >= MAX_CONCURRENT_VALIDATIONS) {
            return;
        }

        // Capture current state for the async operation
        final Level capturedLevel = level;
        final BlockPos capturedPos = getBlockPos().immutable();
        final ResourceLocation capturedControllerId = controllerId;
        final StructureModel capturedStructure = structure;

        structureValidationFuture = CompletableFuture.runAsync(() -> {
            ACTIVE_VALIDATIONS.incrementAndGet();
            try {
                validateStructureInBackground(capturedLevel, capturedPos, capturedControllerId, capturedStructure);
            } finally {
                ACTIVE_VALIDATIONS.decrementAndGet();
            }
        }, STRUCTURE_VALIDATION_EXECUTOR).exceptionally(throwable -> {
            ACTIVE_VALIDATIONS.decrementAndGet();
            // Log the exception but don't crash the game
            System.err.println("Error during structure validation: " + throwable.getMessage());
            return null;
        });
    }

    private void validateStructureInBackground(Level level, BlockPos pos, ResourceLocation controllerId, StructureModel currentStructure) {
        boolean newIsFormed = false;
        StructureModel newStructure = currentStructure;

        if (currentStructure == null) {
            // Search for a matching structure
            for (StructureModel structureModel : StructureManager.STRUCTURES.values()) {
                if (!structureModel.controllerIds().contains(controllerId)) {
                    continue;
                }

                if (structureModel.formed(level, pos)) {
                    newStructure = structureModel;
                    newIsFormed = true;
                    break;
                }
            }
        } else {
            // Check if current structure is still formed
            newIsFormed = currentStructure.formed(level, pos);
        }
        if(!newIsFormed){
            newStructure = null;
        }
        // Update the main thread with results
        final boolean finalIsFormed = newIsFormed;
        final StructureModel finalStructure = newStructure;
        
        // Schedule the update on the main thread
        level.getServer().execute(() -> {
            updateStructureValidationResults(finalIsFormed, finalStructure);
        });
    }

    private void updateStructureValidationResults(boolean newIsFormed, StructureModel newStructure) {
        boolean structureChanged = false;

        if (structure != newStructure) {
            structure = newStructure;
            structureChanged = true;
        }

        if (isFormed != newIsFormed) {
            isFormed = newIsFormed;
            structureChanged = true;
        }

        if (structureChanged) {
            setChanged();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
            if (structure == null) {
                invalidateProgress();
            } else if (newIsFormed && structureChanged) {
                // Structure just formed, trigger recipe processing
                runRecipe();
            }
        }
    }

    private boolean canContinueRecipe() {
        if (currentRecipe == null) {
            return false;
        }
        // Recipe can continue if it has inputs (already consumed) and is not finished yet
        return !recipeState.isCanFinish();
    }

    private void runRecipe() {
        if (portStorages == null) {
            portStorages = structure.getStorages(level, getBlockPos());
        }

        if (recipeState == null) {
            recipeState = new RecipeStateModel();
        }

        // If recipe is finished and waiting for output space
        if (recipeIsStuck()) {
            // Try to output again
            if (currentRecipe.outputs().canProcess(level, portStorages, recipeState)) {
                currentRecipe.outputs().process(level, portStorages, recipeState);
                invalidateRecipe(true);
            }
            // Otherwise, stay stuck and wait
            return;
        }

        // If no current recipe or current recipe can't continue, find a new one
        if (!canContinueRecipe()) {
            for (RecipeModel recipe : MachineRecipeManager.getRecipesByStrucutreId(structure.id())) {
                if (recipe.inputs().canProcess(level, portStorages, recipeState)) {
                    setChanged();
                    currentRecipe = recipe;
                    // Consume inputs immediately when starting the recipe
                    currentRecipe.inputs().process(level, portStorages, recipeState);
                    recipeState.setCanProcess(true);
                    performRecipeTick();
                    return;
                }
            }
            invalidateRecipe(false);
            return;
        }
        
        // Continue processing current recipe
        performRecipeTick();
    }

    private boolean recipeIsStuck() {
        if (currentRecipe == null) {
            return false;
        }
        // Recipe is stuck if it's finished but can't output
        return recipeState.isCanFinish() && !currentRecipe.outputs().canProcess(level, portStorages, recipeState);
    }

    private void performRecipeTick() {
        // Only tick the recipe (don't consume inputs again, they were consumed at start)
        currentRecipe.outputs().processTick(level, portStorages, recipeState);
        
        recipeState.proceedTick();
        recipeState.setTickPercentage(((double) recipeState.getTickProgress() / currentRecipe.ticks()) * 100);
        
        if (recipeState.getTickProgress() >= currentRecipe.ticks()) {
            recipeState.setCanFinish(true);
            // Check if we can output immediately
            if (currentRecipe.outputs().canProcess(level, portStorages, recipeState)) {
                currentRecipe.outputs().process(level, portStorages, recipeState);
                invalidateRecipe(true);
            }
            // Otherwise, recipe stays finished and will be stuck until output space is available
        }
    }

    public void invalidateProgress() {
        setChanged();
        structure = null;
        isFormed = false;
        
        // Cancel any ongoing structure validation
        if (structureValidationFuture != null && !structureValidationFuture.isDone()) {
            structureValidationFuture.cancel(true);
        }
        
        invalidateRecipe(false);
    }

    public void invalidateRecipe(boolean typical) {
        if (currentRecipe != null && !typical && portStorages != null) {
            currentRecipe.ditchRecipe(this.level, recipeState, portStorages);
        }
        portStorages = null;
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
            ResourceLocation structureId = ResourceLocation.tryParse(tag.get("structureId").getAsString());
            structure = StructureManager.STRUCTURES.get(structureId);
        } else {
            structure = null;
        }
        if (tag.contains("recipeId")) {
            ResourceLocation recipeId = ResourceLocation.tryParse(tag.get("recipeId").getAsString());
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

    @Override
    public void setRemoved() {
        super.setRemoved();
        // Cancel any ongoing structure validation when the block entity is removed
        if (structureValidationFuture != null && !structureValidationFuture.isDone()) {
            structureValidationFuture.cancel(true);
        }
    }
}
