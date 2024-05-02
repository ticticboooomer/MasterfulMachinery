package io.ticticboom.mods.mm.foundation.scanner;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.cap.IScannerSelection;
import io.ticticboom.mods.mm.cap.MMCapabilities;
import io.ticticboom.mods.mm.setup.MMRegisters;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class StructureScannerBlockEntity extends BlockEntity implements MenuProvider {

    @Getter
    private final StructureScannerItemHandler handler = new StructureScannerItemHandler(1, this::setChanged);

    @Getter
    private StructureScannerStatus status = StructureScannerStatus.NEEDS_DEVICE;

    public StructureScannerBlockEntity(BlockPos pos, BlockState state) {
        super(MMRegisters.SCANNER_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Structure Scanner");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
        return new StructureScannerMenu(windowId, inv, this);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        handler.deserializeNBT(tag.getCompound(Ref.NBT_STORAGE_KEY));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put(Ref.NBT_STORAGE_KEY, handler.serializeNBT());
        tag.putInt("status", status.ordinal());
        super.saveAdditional(tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        var tag = new CompoundTag();
        var statusOrdinal = tag.getInt("status");
        status = StructureScannerStatus.values()[statusOrdinal];
        saveAdditional(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        checkToolSlot();
    }

    public void checkToolSlot() {
        ItemStack stack = handler.getStackInSlot(0);
        if (!stack.is(MMRegisters.SCANNER_TOOL.get())) {
            status = StructureScannerStatus.NEEDS_DEVICE;
            return;
        }
        IScannerSelection selection = stack.getCapability(MMCapabilities.SCANNER_SELECTION).orElseThrow(() -> new RuntimeException("Failed to get selection capability"));
        if (selection.getEndSelection() != null && selection.getEndSelection() != null) {
            status = StructureScannerStatus.READY;
        } else {
            status = StructureScannerStatus.INVALID_SELECTION;
        }
    }
}
