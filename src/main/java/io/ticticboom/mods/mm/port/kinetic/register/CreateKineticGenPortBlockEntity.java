package io.ticticboom.mods.mm.port.kinetic.register;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortBlockEntity;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.kinetic.CreateKineticPortStorage;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CreateKineticGenPortBlockEntity extends GeneratingKineticBlockEntity implements IPortBlockEntity {

    private final PortModel model;
    private final RegistryGroupHolder groupHolder;
    private final CreateKineticPortStorage storage;

    public CreateKineticGenPortBlockEntity(PortModel model, RegistryGroupHolder groupHolder, BlockPos pos, BlockState state) {
        super(groupHolder.getBe().get(), pos, state);
        this.model = model;
        this.groupHolder = groupHolder;
        storage = (CreateKineticPortStorage) model.config().createPortStorage(this::internalChanged);
        setLazyTickRate(20);
    }

    @Override
    public void initialize() {
        super.initialize();
        updateGeneratedRotation();
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

    public void internalChanged() {
        this.reActivateSource = true;
    }

    @Override
    public float getGeneratedSpeed() {
        return storage.getSpeed();
    }

    @Override
    protected Block getStressConfigKey() {
        return groupHolder.getBlock().get();
    }

    @Override
    public float calculateAddedStressCapacity() {
        this.lastCapacityProvided = storage.getStress();
        return storage.getStress();
    }

    @Override
    public void tick() {
        super.tick();
        if (storage.isShouldStop()) {
            storage.updateSpeed(0);
        }
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.put(Ref.NBT_STORAGE_KEY, storage.save(new CompoundTag()));
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        storage.load(compound.getCompound(Ref.NBT_STORAGE_KEY));
    }
    // TODO make the port rotate after break by player and replace by player
}
