package io.ticticboom.mods.mm.port.kinetic.register;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortBlockEntity;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.kinetic.CreateKineticPortStorage;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CreateKineticGenPortBlockEntity extends GeneratingKineticBlockEntity implements IPortBlockEntity {

    private final PortModel model;
    private final RegistryGroupHolder groupHolder;
    private final CreateKineticPortStorage storage;

    public CreateKineticGenPortBlockEntity(PortModel model, RegistryGroupHolder groupHolder, BlockPos pos, BlockState state) {
        super(groupHolder.getBe().get(), pos, state);
        this.model = model;
        this.groupHolder = groupHolder;
        storage = (CreateKineticPortStorage) model.config().createPortStorage(this::internalChanged);
    }

    @Override
    public PortModel getModel() {
        return model;
    }

    @Override
    public IPortStorage getStorage() {
        return storage;
    }

    @Override
    public boolean isInput() {
        return model.input();
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Kinetic Port");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
        return null;
    }

    @Override
    public boolean hasMenu() {
        return false;
    }

    @Override
    public boolean isSource() {
        return true;
    }

    public void internalChanged() {
        this.updateGeneratedRotation();
    }


    @Override
    public void setChanged() {
        if (level.isClientSide()) {
            return;
        }
        super.setChanged();
        level.sendBlockUpdated(getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public float calculateAddedStressCapacity() {
        return storage.getSpeed() * storage.getStress();
    }

    @Override
    public float getGeneratedSpeed() {
        return storage.getSpeed();
    }
}