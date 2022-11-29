package io.ticticboom.mods.mm.ports.mekanism.laser.block;

import io.ticticboom.mods.mm.ports.base.IPortBE;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.ports.mekanism.laser.MekLaserConfiguredPort;
import io.ticticboom.mods.mm.ports.mekanism.laser.MekLaserPortStorage;
import io.ticticboom.mods.mm.setup.model.PortModel;
import mekanism.api.IContentsListener;
import mekanism.api.math.FloatingLong;
import mekanism.api.providers.IBlockProvider;
import mekanism.common.capabilities.energy.BasicEnergyContainer;
import mekanism.common.capabilities.energy.LaserEnergyContainer;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.tile.laser.TileEntityLaserReceptor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MekLaserInputPortBlockEntity extends TileEntityLaserReceptor implements IPortBE {
    private final PortModel model;
    public  final MekLaserPortStorage storage;

    public MekLaserInputPortBlockEntity(IBlockProvider blockProvider, BlockPos pos, BlockState state, PortModel model) {
        super(blockProvider, pos, state);
        this.model = model;
        storage = new MekLaserPortStorage(((MekLaserConfiguredPort) model.configuredPort()));
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
    protected void addInitialEnergyContainers(EnergyContainerHelper energyContainerHelper, IContentsListener iContentsListener) {
        energyContainerHelper.addContainer(this.energyContainer = LaserEnergyContainer.create(BasicEnergyContainer.notExternal, BasicEnergyContainer.alwaysTrue, this, iContentsListener));
    }

    @Override
    public void receiveLaserEnergy(@NotNull FloatingLong energy) {
        super.receiveLaserEnergy(energy);
        storage.laser = super.energyContainer.getEnergy();
    }
}
