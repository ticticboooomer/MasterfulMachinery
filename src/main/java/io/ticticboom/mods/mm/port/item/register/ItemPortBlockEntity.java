package io.ticticboom.mods.mm.port.item.register;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortBlockEntity;
import io.ticticboom.mods.mm.port.IPortPart;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.common.AbstractPortBlockEntity;
import io.ticticboom.mods.mm.port.item.ItemPortHandler;
import io.ticticboom.mods.mm.port.item.ItemPortStorage;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemPortBlockEntity extends AbstractPortBlockEntity {
    private final RegistryGroupHolder groupHolder;
    private final PortModel model;

    private final ItemPortStorage storage;

    @Getter
    private final boolean input;

    public ItemPortBlockEntity(RegistryGroupHolder groupHolder, PortModel model, boolean input, BlockPos pos,
                               BlockState state) {
        super(groupHolder.getBe().get(), pos, state);
        this.groupHolder = groupHolder;
        this.model = model;
        storage = (ItemPortStorage) model.config().createPortStorage(this::setChanged);
        this.input = input;
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return storage.getCapability(cap);
    }

    @Override
    public IPortStorage getStorage() {
        return storage;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Item Port");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ItemPortMenu(model, groupHolder, input, i, inventory, this);
    }

    @Override
    public PortModel getModel() {
        return model;
    }

}
