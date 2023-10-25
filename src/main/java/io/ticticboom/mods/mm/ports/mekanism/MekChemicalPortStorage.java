package io.ticticboom.mods.mm.ports.mekanism;

import io.ticticboom.mods.mm.ports.base.PortStorage;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public abstract class MekChemicalPortStorage<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>> extends PortStorage {

    public IChemicalTank<CHEMICAL, STACK> tank;
    public MekChemicalConfiguredPort config;
    private final LazyOptional<IChemicalTank<CHEMICAL, STACK>> handlerLO;
    protected abstract IChemicalTank<CHEMICAL, STACK> createTank(long capacity);
    protected abstract boolean isCapability(Capability<?> cap);
    protected abstract STACK createStack(ResourceLocation loc, long amount);
    protected abstract MekChemicalPortStorage<CHEMICAL, STACK> createSelf();

    public MekChemicalPortStorage(MekChemicalConfiguredPort config) {
        tank = createTank(config.capacity());
        this.config = config;
        handlerLO = LazyOptional.of(() -> tank);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        if (isCapability(cap)) {
            return handlerLO.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public void read(CompoundTag tag) {
        var gas = tag.getString("Chemical");
        var amount = tag.getLong("Amount");
        tank.setStack(createStack(ResourceLocation.tryParse(gas), amount));
    }

    @Override
    public CompoundTag write() {
        var result = new CompoundTag();
        STACK stack = tank.getStack();
        result.putString("Chemical", stack.getRaw().getRegistryName().toString());
        result.putLong("Amount", stack.getAmount());
        return result;
    }

    @Override
    public void onDestroy(Level level, BlockPos pos) {

    }

    @Override
    public PortStorage deepClone() {
        var res = createSelf();
        res.tank = createTank(config.capacity());
        res.tank.setStack((STACK)this.tank.getStack().copy());
        return res;
    }
}
