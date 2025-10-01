package io.ticticboom.mods.mm.port.energy.register;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortBlockEntity;
import io.ticticboom.mods.mm.port.IPortPart;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.common.AbstractPortBlockEntity;
import io.ticticboom.mods.mm.port.common.ISlottedPortStorageModel;
import io.ticticboom.mods.mm.port.energy.EnergyPortStorage;
import io.ticticboom.mods.mm.port.energy.EnergyPortStorageModel;
import io.ticticboom.mods.mm.port.energy.feature.EnergyPortAutoPushFeature;
import io.ticticboom.mods.mm.port.item.ItemPortStorageModel;
import io.ticticboom.mods.mm.port.item.feature.ItemPortAutoPushAddon;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class  EnergyPortBlockEntity extends AbstractPortBlockEntity {
    private final PortModel model;
    private final RegistryGroupHolder groupHolder;
    private final boolean isInput;

    private final EnergyPortStorage storage;
    private final Optional<EnergyPortAutoPushFeature> autoPushAddon;

    public EnergyPortBlockEntity(PortModel model, RegistryGroupHolder groupHolder, boolean isInput, BlockPos pos, BlockState state) {
        super(groupHolder.getBe().get(), pos, state);
        this.model = model;
        this.groupHolder = groupHolder;
        this.isInput = isInput;
        storage = (EnergyPortStorage) model.config().createPortStorage(this::setChanged);
        var shouldAutoPush = !isInput && ((EnergyPortStorageModel) storage.getStorageModel()).autoPush().get();
        if (shouldAutoPush) {
            autoPushAddon = Optional.of(new EnergyPortAutoPushFeature(this, this.model));
        } else {
            autoPushAddon = Optional.empty();
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return storage.getCapability(cap);
    }

    @Override
    public IPortStorage getStorage() {
        return storage;
    }

    public EnergyPortStorageModel getStorageModel() {
        return (EnergyPortStorageModel) storage.getStorageModel();
    }

    @Override
    public boolean isInput() {
        return isInput;
    }

    @Override
    public PortModel getModel() {
        return model;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Energy Port");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
        return new EnergyPortMenu(model, groupHolder, isInput, windowId, inv, this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put(Ref.NBT_STORAGE_KEY, storage.save(new CompoundTag()));
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        storage.load(tag.getCompound(Ref.NBT_STORAGE_KEY));
        super.load(tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        var tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void setChanged() {
        if (level.isClientSide()){
            return;
        }
        super.setChanged();
        level.sendBlockUpdated(getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
    }

    public void tick() {
        if(lastTick == level.getGameTime()) return;
        lastTick = level.getGameTime();
        autoPushAddon.ifPresent(EnergyPortAutoPushFeature::tick);
    }

    @Override
    public void onLoad() {
        autoPushAddon.ifPresent(EnergyPortAutoPushFeature::onLoad);
    }

    public void neighborsChanged() {
        autoPushAddon.ifPresent(EnergyPortAutoPushFeature::tryAddNeighboringHandlers);
    }
}
