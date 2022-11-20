package io.ticticboom.mods.mm.block.entity;

import io.ticticboom.mods.mm.ports.base.IPortBE;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.setup.model.PortModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PortBlockEntity extends BlockEntity implements IPortBE {
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

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return this.getCapability(cap);
    }

    public void forceUpdate() {
        if (this.level.isClientSide) return;
        this.setChanged();
        ((ServerLevel) level).getChunkSource().blockChanged(getBlockPos());
    }
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("Port", storage.write());
        return tag;
    }
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override
    public void handleUpdateTag(CompoundTag tag) {
        storage.read(tag.getCompound("Port"));
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        storage.read(pkt.getTag().getCompound("Port"));
    }
    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("Port", storage.write());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        storage.read(tag.getCompound("Port"));
        super.load(tag);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        if (level.isClientSide) {
            return;
        }
        var be = ((PortBlockEntity) t);
        be.forceUpdate();
    }

    @Override
    public PortModel model() {
        return model;
    }

    @Override
    public PortStorage storage() {
        return storage;
    }
}
