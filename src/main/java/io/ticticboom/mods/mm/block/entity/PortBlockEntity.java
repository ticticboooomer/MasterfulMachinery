package io.ticticboom.mods.mm.block.entity;

import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.setup.model.PortModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class PortBlockEntity extends BlockEntity {
    public final PortStorage storage;
    private final PortModel model;

    public PortBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_, PortModel model) {
        super(p_155228_, p_155229_, p_155230_);
        this.model = model;
        var port = MMRegistries.PORTS.get(model.port());
        storage = port.createStorage(model.configuredPort());
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return storage.getCapability(cap);
    }
}
