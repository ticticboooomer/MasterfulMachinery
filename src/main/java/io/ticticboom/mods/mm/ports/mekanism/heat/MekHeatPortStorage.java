package io.ticticboom.mods.mm.ports.mekanism.heat;

import io.ticticboom.mods.mm.ports.base.PortStorage;
import mekanism.api.heat.IHeatHandler;
import mekanism.common.capabilities.Capabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class MekHeatPortStorage extends PortStorage {

    public final MekHeatConfiguredPort config;
    public final MekHeatHandler handler;
    public final LazyOptional<IHeatHandler> handlerLO;


    public MekHeatPortStorage(MekHeatConfiguredPort config) {
        this.config = config;
        handler = new MekHeatHandler(config);
        handlerLO = LazyOptional.of(() -> handler);
    }

    @Override
    public void read(CompoundTag tag) {
        handler.capacitor.setHeatCapacityFromPacket(tag.getDouble("Heat"));
    }

    @Override
    public CompoundTag write() {
        var tag = new CompoundTag();
        tag.putDouble("Heat", handler.capacitor.getHeat());
        return tag;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        if (cap == Capabilities.HEAT_HANDLER) {
            return handlerLO.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public void onDestroy(Level level, BlockPos pos) {
    }

    @Override
    public PortStorage deepClone() {
        var res = new MekHeatPortStorage(config);
        res.handler.capacitor.setHeat(handler.capacitor.getHeat());
        return res;
    }

    @Override
    public void tick() {
        handler.capacitor.update();
    }
}
