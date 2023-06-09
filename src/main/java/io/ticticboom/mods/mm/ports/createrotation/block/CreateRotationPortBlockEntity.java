package io.ticticboom.mods.mm.ports.createrotation.block;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import io.ticticboom.mods.mm.ports.base.IPortBE;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.ports.createrotation.RotationPortStorage;
import io.ticticboom.mods.mm.ports.item.ItemPortStorage;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.setup.model.PortModel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CreateRotationPortBlockEntity extends KineticBlockEntity implements IPortBE {
    private final PortStorage storage;
    private PortModel model;
    private float appliedStress = 0;

    public CreateRotationPortBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, PortModel model) {
        super(type, pos, state);
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
    public float calculateStressApplied() {
        return appliedStress;
    }

    @Override
    public void onSpeedChanged(float previousSpeed) {
        super.onSpeedChanged(previousSpeed);
    }

    @Override
    public void updateFromNetwork(float maxStress, float currentStress, int networkSize) {
        super.updateFromNetwork(maxStress, currentStress, networkSize);
    }

    @Override
    public boolean isSource() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        var storg = (RotationPortStorage) storage;
        storg.speed = speed;
        storg.isOverStressed = overStressed;
        appliedStress = storg.speed > 0 ? storg.stress : 0;
        forceUpdate();
    }

    @Override
    public float getGeneratedSpeed() {
        var storg = (RotationPortStorage) storage;
        return storg.speed;
    }
}
