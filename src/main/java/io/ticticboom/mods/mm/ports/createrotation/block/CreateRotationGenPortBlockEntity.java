package io.ticticboom.mods.mm.ports.createrotation.block;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import io.ticticboom.mods.mm.ports.base.IPortBE;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.ports.createrotation.RotationConfiguredPort;
import io.ticticboom.mods.mm.ports.createrotation.RotationPortStorage;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.setup.model.PortModel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CreateRotationGenPortBlockEntity extends GeneratingKineticBlockEntity implements IPortBE {
    private final PortStorage storage;
    private PortModel model;

    public CreateRotationGenPortBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state, PortModel model) {
        super(typeIn, pos, state);
        this.model = model;
        var port = MMRegistries.PORTS.get(model.port());
        storage = port.createStorage(model.configuredPort());
    }

    public void forceUpdate() {
        if (this.level.isClientSide) return;
        this.setChanged();
        ((ServerLevel) level).getChunkSource().blockChanged(getBlockPos());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag res = new CompoundTag();
        saveAdditional(res);
        res.put("Port", storage.write());
        return res;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        storage.read(tag.getCompound("Port"));
        read(tag, true);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        storage.read(pkt.getTag().getCompound("Port"));
        read(pkt.getTag(), true);
    }


    @Override
    public PortModel model() {
        return model;
    }

    @Override
    public PortStorage storage() {
        return storage;
    }

    @Override
    public boolean isSource() {
        RotationPortStorage storg = (RotationPortStorage) storage;
        return true;
    }


    @Override
    public void tick() {
        this.reActivateSource = true;
        super.tick();
        RotationPortStorage storg = (RotationPortStorage) storage;
        storg.isOverStressed = overStressed;
        storg.stress = ((RotationConfiguredPort) model.configuredPort()).stress();
        forceUpdate();
    }

    @Override
    public float calculateStressApplied() {
        return 0;
    }

    @Override
    public float getGeneratedSpeed() {
        RotationPortStorage storg = (RotationPortStorage) storage;
        return storg.speed;
    }

    @Override
    public float calculateAddedStressCapacity() {
        RotationPortStorage storg = (RotationPortStorage) storage;
        return storg.stress;
    }


}
