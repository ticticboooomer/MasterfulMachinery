package io.ticticboom.mods.mm.ports.createrotation;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.client.screen.PortScreen;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public class RotationPortStorage extends PortStorage {

    public int stress;
    public float speed;
    public boolean isOverStressed;

    public RotationPortStorage(int stress) {

        this.stress = stress;
    }


    @Override
    public void read(CompoundTag tag) {

    }

    @Override
    public CompoundTag write() {
        return new CompoundTag();
    }

    @Override
    public void renderScreen(PortScreen screen, PoseStack ms, int x, int y) {

    }

    @Override
    public void onDestroy(Level level, BlockPos pos) {

    }

    @Override
    public PortStorage deepClone() {
        var res = new RotationPortStorage(stress);
        res.speed = speed;
        res.isOverStressed =isOverStressed;
        return res;
    }
}
