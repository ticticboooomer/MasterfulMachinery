package io.ticticboom.mods.mm.ports.mekanism.laser;

import io.ticticboom.mods.mm.ports.base.PortStorage;
import mekanism.api.math.FloatingLong;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public class MekLaserPortStorage extends PortStorage {

    private MekLaserConfiguredPort config;
    public FloatingLong laser;

    public MekLaserPortStorage(MekLaserConfiguredPort config) {
        this.config = config;
    }

    @Override
    public void read(CompoundTag tag) {
        laser = FloatingLong.create(tag.getLong("Laser"));
    }

    @Override
    public CompoundTag write() {
        var res = new CompoundTag();
        res.putLong("Laser", laser.getValue());
        return res;
    }

    @Override
    public void onDestroy(Level level, BlockPos pos) {

    }

    @Override
    public PortStorage deepClone() {
        var res = new MekLaserPortStorage(config);
        res.laser = FloatingLong.create(laser.longValue());
        return res;
    }
}
