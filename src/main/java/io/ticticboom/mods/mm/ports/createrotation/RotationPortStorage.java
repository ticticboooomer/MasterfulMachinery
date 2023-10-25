package io.ticticboom.mods.mm.ports.createrotation;

import io.ticticboom.mods.mm.ports.base.PortStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public class RotationPortStorage extends PortStorage {

    public int stress;
    public float speed;
    public boolean isOverStressed;
    public boolean manualUpdate = false;

    public RotationPortStorage(int stress) {
        this.stress = stress;
    }


    @Override
    public void read(CompoundTag tag) {
        speed = tag.getFloat("Speed");
        stress = tag.getInt("Stress");
    }

    @Override
    public CompoundTag write() {
        var tag = new CompoundTag();
        tag.putFloat("Speed", speed);
        tag.putInt("Stress", stress);
        return tag;
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

    @Override
    public void reset() {
        this.speed = 0;
        this.stress = 0;
        manualUpdate = true;
    }
}
