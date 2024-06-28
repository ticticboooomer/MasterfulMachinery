package io.ticticboom.mods.mm.port.fluid.register;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.common.AbstractPortBlockEntity;
import io.ticticboom.mods.mm.port.fluid.FluidPortStorage;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FluidPortBlockEntity extends AbstractPortBlockEntity {

    private final PortModel model;
    private final RegistryGroupHolder groupHolder;
    private final boolean isInput;

    private final FluidPortStorage storage;

    public FluidPortBlockEntity(PortModel model, RegistryGroupHolder groupHolder, boolean isInput, BlockPos pos,
                                BlockState state) {
        super(groupHolder.getBe().get(), pos, state);
        this.model = model;
        this.groupHolder = groupHolder;
        this.isInput = isInput;
        storage = (FluidPortStorage) model.config().createPortStorage(this::setChanged);
    }

    @Override
    public IPortStorage getStorage() {
        return storage;
    }


    @Override
    public boolean isInput() {
        return isInput;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Fluid Port");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
        return new FluidPortMenu(model, groupHolder, isInput, windowId, inv, this);
    }

    @Override
    public PortModel getModel() {
        return model;
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return storage.getCapability(cap);
    }
}
