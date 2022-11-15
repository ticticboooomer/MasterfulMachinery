package io.ticticboom.mods.mm.ports.base;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.block.entity.PortBlockEntity;
import io.ticticboom.mods.mm.client.container.PortContainer;
import io.ticticboom.mods.mm.client.screen.PortScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class PortStorage {
    public abstract void read(CompoundTag tag);
    public abstract CompoundTag write();
    public void setupContainer(PortContainer container, Inventory pinv, PortBlockEntity be) {
        for (var i = 0; i < 9; i++) {
            container.addSlot(new Slot(pinv, i, i * 18 + 8, 199));
        }

        for (var x = 0; x < 9; x++) {
            for (var y = 0; y < 3; y++) {
                container.addSlot(new Slot(pinv, x + y * 9 + 9, x * 18 + 8, y * 18 + 141));
            }
        }
    };

    public abstract void renderScreen(PortScreen screen, PoseStack ms);

    public abstract void onDestroy(Level level, BlockPos pos);
}
