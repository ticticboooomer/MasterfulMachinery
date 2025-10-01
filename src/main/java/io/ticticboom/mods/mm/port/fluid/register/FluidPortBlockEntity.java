package io.ticticboom.mods.mm.port.fluid.register;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortBlockEntity;
import io.ticticboom.mods.mm.port.IPortPart;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.common.AbstractPortBlockEntity;
import io.ticticboom.mods.mm.port.fluid.FluidPortStorage;
import io.ticticboom.mods.mm.port.fluid.FluidPortStorageModel;
import io.ticticboom.mods.mm.port.fluid.feature.FluidPortAutoPushFeature;
import io.ticticboom.mods.mm.port.item.ItemPortStorageModel;
import io.ticticboom.mods.mm.port.item.feature.ItemPortAutoPushAddon;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketEncoder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;
import java.util.Optional;

public class FluidPortBlockEntity extends AbstractPortBlockEntity {

    private final PortModel model;
    private final RegistryGroupHolder groupHolder;
    private final boolean isInput;

    private final FluidPortStorage storage;
    private final Optional<FluidPortAutoPushFeature> autoPushAddon;

    public FluidPortBlockEntity(PortModel model, RegistryGroupHolder groupHolder, boolean isInput, BlockPos pos,
                                BlockState state) {
        super(groupHolder.getBe().get(), pos, state);
        this.model = model;
        this.groupHolder = groupHolder;
        this.isInput = isInput;
        storage = (FluidPortStorage) model.config().createPortStorage(this::setChanged);
        var shouldAutoPush = !isInput && ((FluidPortStorageModel) storage.getStorageModel()).autoPush().get();
        if (shouldAutoPush) {
            autoPushAddon = Optional.of(new FluidPortAutoPushFeature(this, this.model));
        } else {
            autoPushAddon = Optional.empty();
        }
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

    public void tick() {
        if(lastTick == level.getGameTime()) return;
        lastTick = level.getGameTime();
        autoPushAddon.ifPresent(FluidPortAutoPushFeature::tick);
    }

    @Override
    public void onLoad() {
        autoPushAddon.ifPresent(FluidPortAutoPushFeature::onLoad);
    }

    public void neighborsChanged() {
        autoPushAddon.ifPresent(FluidPortAutoPushFeature::tryAddNeighboringHandlers);
    }
}
