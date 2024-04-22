package io.ticticboom.mods.mm.ports.item.register;

import io.ticticboom.mods.mm.model.config.PortModel;
import io.ticticboom.mods.mm.ports.IPortBlockEntity;
import io.ticticboom.mods.mm.ports.IPortPart;
import io.ticticboom.mods.mm.ports.IPortStorage;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ItemPortBlockEntity extends BlockEntity implements IPortBlockEntity, IPortPart {
    private final RegistryGroupHolder groupHolder;
    private final PortModel model;

    @Getter
    private final IPortStorage storage;

    @Getter
    private final boolean input;

    public ItemPortBlockEntity(RegistryGroupHolder groupHolder, PortModel model, boolean input, BlockPos pos, BlockState state) {
        super(groupHolder.getBe().get(), pos, state);
        this.groupHolder = groupHolder;
        this.model = model;
        storage = model.config().createPortStorage();
        this.input = input;
    }

    @Override
    public PortModel getPortModel() {
        return model;
    }

    @Override
    public IPortStorage getStorage() {
        return null;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Item Port");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return groupHolder.getMenu().get().create(i, inventory);
    }

    @Override
    public PortModel getModel() {
        return model;
    }
}
