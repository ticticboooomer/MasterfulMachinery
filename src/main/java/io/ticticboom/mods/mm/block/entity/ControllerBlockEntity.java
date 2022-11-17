package io.ticticboom.mods.mm.block.entity;

import io.ticticboom.mods.mm.block.ControllerBlock;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.recipe.RecipeContext;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import io.ticticboom.mods.mm.setup.model.StructureModel;
import io.ticticboom.mods.mm.setup.reload.RecipeManager;
import io.ticticboom.mods.mm.setup.reload.StructureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ControllerBlockEntity extends BlockEntity {

    private int ticks = 0;
    public final DisplayInfo displayInfo = new DisplayInfo();

    private final DecimalFormat format = new DecimalFormat("###.00");

    public ControllerBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }


    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("DisplayInfo", this.displayInfo.serialize());
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.displayInfo.deserialize(tag.getCompound("DisplayInfo"));
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.displayInfo.deserialize(pkt.getTag().getCompound("DisplayInfo"));
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        if (level.isClientSide) {
            return;
        }
        var be = ((ControllerBlockEntity) t);
        var block = (ControllerBlock) blockState.getBlock();
        var foundAny = false;
        for (Map.Entry<ResourceLocation, StructureModel> entry : StructureManager.REGISTRY.entrySet()) {
            var inputPorts = new ArrayList<PortStorage>();
            var outputPorts = new ArrayList<PortStorage>();
            var model = entry.getValue();
            if (!model.controllerId().equals(block.model().id())) {
                continue;
            }
            for (List<StructureModel.PlacedStructurePart> flattened : model.transformed()) {
                boolean found = true;
                for (StructureModel.PlacedStructurePart placed : flattened) {
                    var part = MMRegistries.STRUCTURE_PARTS.get().getValue(placed.partId());
                    assert part != null;
                    BlockPos expectedPos = blockPos.offset(placed.pos());
                    if (!part.validatePlacement(level, expectedPos, placed.part())) {
                        found = false;
                        break;
                    }
                    var port = part.getPortIfPresent(level, expectedPos, placed.part());
                    if (port.isPresent()) {
                        if (port.get().input()) {
                            inputPorts.add(port.get().port());
                        } else {
                            outputPorts.add(port.get().port());
                        }
                    }
                }
                if (found) {
                    be.displayInfo.structureName = model.name().getContents();
                    be.forceUpdate();
                    foundAny = true;
                    be.chooseRecipe(model, new RecipeContext(model, inputPorts, outputPorts));
                    break;
                }
            }
        }
        if (!foundAny) {
            be.displayInfo.structureName = "";
            be.forceUpdate();
        }
    }

    protected RecipeContext clonePorts(RecipeContext original) {
        var inputs = new ArrayList<PortStorage>();
        for (PortStorage port : original.inputPorts()) {
            inputs.add(port.deepClone());
        }
        var outputs = new ArrayList<PortStorage>();
        for (PortStorage port : original.outputPorts()) {
            outputs.add(port.deepClone());
        }
        return new RecipeContext(original.structure(), inputs, outputs);
    }

    protected void resetRecipe() {
        ticks = 0;
        displayInfo.processStatus = "Idle";
    }

    protected void chooseRecipe(StructureModel model, RecipeContext ctx) {
        for (Map.Entry<ResourceLocation, RecipeModel> recipe : RecipeManager.REGISTRY.entrySet()) {
            var found = true;
            var cloned = clonePorts(ctx);
            for (RecipeModel.RecipeEntry input : recipe.getValue().inputs()) {
                var entry = MMRegistries.RECIPE_ENTRIES.get().getValue(input.type());
                if (!entry.processInputs(input.config(), cloned)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                ticks++;
                var percentage = (float) ticks / recipe.getValue().duration();
                this.displayInfo.processStatus = format.format(100f * percentage) + "% Processing";
                if (ticks >= recipe.getValue().duration()) {
                    var canOutput = true;
                    for (RecipeModel.RecipeEntry input : recipe.getValue().outputs()) {
                        var entry = MMRegistries.RECIPE_ENTRIES.get().getValue(input.type());
                        if (!entry.processOutputs(input.config(), cloned)) {
                            canOutput = false;
                            break;
                        }
                    }
                    if (canOutput) {
                        for (RecipeModel.RecipeEntry input : recipe.getValue().inputs()) {
                            var entry = MMRegistries.RECIPE_ENTRIES.get().getValue(input.type());
                            entry.processInputs(input.config(), ctx);
                        }
                        for (RecipeModel.RecipeEntry output : recipe.getValue().outputs()) {
                            var entry = MMRegistries.RECIPE_ENTRIES.get().getValue(output.type());
                            entry.processOutputs(output.config(), ctx);
                        }
                        resetRecipe();
                    } else {
                        this.displayInfo.processStatus = "Cannot Output";
                    }
                }
            } else {
                resetRecipe();
            }
        }
        this.forceUpdate();
    }

    public static class DisplayInfo {

        public String structureName;
        public String processStatus;

        public CompoundTag serialize() {
            CompoundTag tag = new CompoundTag();
            if (this.structureName != null) {
                tag.putString("Structure", this.structureName);
            }
            if (this.processStatus != null) {
                tag.putString("status", this.processStatus);
            }

            return tag;
        }

        public void deserialize(CompoundTag tag) {
            if (tag.contains("Structure")) {
                this.structureName = tag.getString("Structure");
            }
            if (tag.contains("status")) {
                this.processStatus = tag.getString("status");
            }
        }
    }
}