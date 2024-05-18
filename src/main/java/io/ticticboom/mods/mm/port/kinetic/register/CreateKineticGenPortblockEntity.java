package io.ticticboom.mods.mm.port.kinetic.register;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortBlockEntity;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.kinetic.CreateKineticPortStorage;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CreateKineticGenPortblockEntity extends GeneratingKineticBlockEntity implements IPortBlockEntity {

    private final PortModel model;
    private final RegistryGroupHolder groupHolder;
    private final CreateKineticPortStorage storage;

    public CreateKineticGenPortblockEntity(PortModel model, RegistryGroupHolder groupHolder, BlockPos pos, BlockState state) {
        super(groupHolder.getBe().get(), pos, state);
        this.model = model;
        this.groupHolder = groupHolder;
        storage = (CreateKineticPortStorage) model.config().createPortStorage(this::setChanged);
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
    public boolean isSource() {
        return true;
    }
}
