package io.ticticboom.mods.mm.port.energy.register;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortBlockEntity;
import io.ticticboom.mods.mm.port.IPortPart;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.energy.EnergyPortStorage;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class EnergyPortBlockEntity extends BlockEntity implements IPortBlockEntity, IPortPart {
    private final PortModel model;
    private final RegistryGroupHolder groupHolder;
    private final boolean isInput;

    private final EnergyPortStorage storage;

    public EnergyPortBlockEntity(PortModel model, RegistryGroupHolder groupHolder, boolean isInput, BlockPos pos, BlockState state) {
        super(groupHolder.getBe().get(), pos, state);
        this.model = model;
        this.groupHolder = groupHolder;
        this.isInput = isInput;

        storage = (EnergyPortStorage) model.config().createPortStorage(this::setChanged);
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
}
