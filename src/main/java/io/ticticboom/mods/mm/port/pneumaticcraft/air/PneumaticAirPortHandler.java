package io.ticticboom.mods.mm.port.pneumaticcraft.air;

import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;
import it.unimi.dsi.fastutil.floats.FloatPredicate;
import me.desht.pneumaticcraft.api.pressure.PressureTier;
import me.desht.pneumaticcraft.common.capabilities.MachineAirHandler;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;
import java.util.List;

public class PneumaticAirPortHandler extends MachineAirHandler {
    private final INotifyChangeFunction changed;

    public PneumaticAirPortHandler(PressureTier tier, int volume, INotifyChangeFunction changed) {
        super(tier, volume);
        this.changed = changed;
    }

    @Override
    public void setPressure(float pressure) {
        changed.call();
        super.setPressure(pressure);
    }

    @Override
    public void setVolumeUpgrades(int newVolumeUpgrades) {
        changed.call();
        super.setVolumeUpgrades(newVolumeUpgrades);
    }

    @Override
    public void disableSafetyVenting() {
        changed.call();
        super.disableSafetyVenting();
    }

    @Override
    public void setConnectedFaces(List<Direction> sides) {
        changed.call();
        super.setConnectedFaces(sides);
    }

    @Override
    public void tick(BlockEntity ownerTE) {
        changed.call();
        super.tick(ownerTE);
    }

    @Override
    public void setSideLeaking(@Nullable Direction dir) {
        changed.call();
        super.setSideLeaking(dir);
    }
}