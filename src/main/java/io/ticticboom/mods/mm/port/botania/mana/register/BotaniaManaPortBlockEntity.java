package io.ticticboom.mods.mm.port.botania.mana.register;

import com.mojang.blaze3d.systems.RenderSystem;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.cap.BotaniaCapabilities;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortBlockEntity;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.botania.mana.BotaniaManaPortStorage;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.block.WandHUD;
import vazkii.botania.api.mana.ManaBlockType;
import vazkii.botania.api.mana.ManaNetworkAction;
import vazkii.botania.api.mana.ManaPool;
import vazkii.botania.api.mana.ManaReceiver;
import vazkii.botania.client.core.helper.RenderHelper;
import vazkii.botania.client.gui.HUDHandler;
import vazkii.botania.common.handler.ManaNetworkHandler;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.ManaTabletItem;

import java.util.Optional;

public class BotaniaManaPortBlockEntity extends BlockEntity implements ManaPool, IPortBlockEntity {

    private final PortModel model;
    private final RegistryGroupHolder groupHolder;
    private final BotaniaManaPortStorage storage;
    private final WandHud wandHud = new WandHud(this);
    private final LazyOptional<WandHUD> wandHudCap = LazyOptional.of(() -> wandHud);
    private final LazyOptional<ManaReceiver> manaReceiverCap = LazyOptional.of(() -> this);
    private long lastTick = 0;

    public BotaniaManaPortBlockEntity(PortModel model, RegistryGroupHolder groupHolder, BlockPos pos, BlockState state) {
        super(groupHolder.getBe().get(), pos, state);
        this.model = model;
        this.groupHolder = groupHolder;
        this.storage = (BotaniaManaPortStorage) model.config().createPortStorage(this::setChanged);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == BotaniaCapabilities.WAND_HUD) {
            return wandHudCap.cast();
        }
        if (cap == BotaniaCapabilities.MANA_RECEIVER) {
            return manaReceiverCap.cast();
        }
        return super.getCapability(cap, side);
    }

    public void tick() {
        if(lastTick == level.getGameTime()) return;
        lastTick = level.getGameTime();
        boolean inNetwork = ManaNetworkHandler.instance.isPoolIn(level, this);
        if (!inNetwork && !this.isRemoved()) {
            BotaniaAPI.instance().getManaNetworkInstance().fireManaNetworkEvent(this, ManaBlockType.POOL, ManaNetworkAction.ADD);
        }
    }

    public void setRemoved() {
        super.setRemoved();
        BotaniaAPI.instance().getManaNetworkInstance().fireManaNetworkEvent(this, ManaBlockType.POOL, ManaNetworkAction.REMOVE);
        this.wandHudCap.invalidate();
        this.manaReceiverCap.invalidate();
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
        return Component.literal("Botania Mana Port");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return null;
    }

    @Override
    public boolean isOutputtingPower() {
        return !this.model.input();
    }


    @Override
    public int getMaxMana() {
        return storage.getCapacity();
    }

    @Override
    public Optional<DyeColor> getColor() {
        return Optional.of(DyeColor.CYAN);
    }

    @Override
    public void setColor(Optional<DyeColor> optional) {

    }

    @Override
    public Level getManaReceiverLevel() {
        return level;
    }

    @Override
    public BlockPos getManaReceiverPos() {
        return this.getBlockPos();
    }

    @Override
    public int getCurrentMana() {
        return storage.getStored();
    }

    @Override
    public boolean isFull() {
        return storage.getStored() >= storage.getCapacity();
    }

    @Override
    public void receiveMana(int i) {
        if (i < 0) {
            storage.extractMana(i, false);
        } else {
            storage.receiveMana(i, false);
        }
    }

    @Override
    public boolean canReceiveManaFromBursts() {
        return this.model.input();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(Ref.NBT_STORAGE_KEY, storage.save(new CompoundTag()));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        storage.load(tag.getCompound(Ref.NBT_STORAGE_KEY));
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level.isClientSide()){
            return;
        }
        level.sendBlockUpdated(getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
    }

    @Override
    public CompoundTag getUpdateTag() {
        var tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public static class WandHud implements WandHUD {
        private final BotaniaManaPortBlockEntity pool;

        public WandHud(BotaniaManaPortBlockEntity pool) {
            this.pool = pool;
        }

        public void renderHUD(GuiGraphics gui, Minecraft mc) {
            ItemStack poolStack = new ItemStack(this.pool.getBlockState().getBlock());
            String name = poolStack.getHoverName().getString();
            int centerX = mc.getWindow().getGuiScaledWidth() / 2;
            int centerY = mc.getWindow().getGuiScaledHeight() / 2;
            int width = Math.max(102, mc.font.width(name)) + 4;
            RenderHelper.renderHUDBox(gui, centerX - width / 2, centerY + 8, centerX + width / 2, centerY + 48);
            BotaniaAPIClient.instance().drawSimpleManaHUD(gui, 38399, this.pool.getCurrentMana(), this.pool.getMaxMana(), name);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(770, 771);
            int arrowU = this.pool.isOutputtingPower() ? 22 : 0;
            int arrowV = 38;
            RenderHelper.drawTexturedModalRect(gui, HUDHandler.manaBar, centerX - 11, centerY + 30, arrowU, arrowV, 22, 15);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            ItemStack tablet = new ItemStack(BotaniaItems.manaTablet);
            ManaTabletItem.setStackCreative(tablet);
            gui.renderItem(tablet, centerX - 31, centerY + 30);
            gui.renderItem(poolStack, centerX + 15, centerY + 30);
            RenderSystem.disableBlend();
        }
    }
}
