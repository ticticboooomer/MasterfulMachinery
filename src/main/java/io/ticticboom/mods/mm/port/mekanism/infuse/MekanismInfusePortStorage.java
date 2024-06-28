package io.ticticboom.mods.mm.port.mekanism.infuse;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.cap.MekCapabilities;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;
import io.ticticboom.mods.mm.port.mekanism.NotifyChangeContentsListener;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorage;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorageModel;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfusionStack;
import net.minecraftforge.common.capabilities.Capability;

public class MekanismInfusePortStorage extends MekanismChemicalPortStorage<InfuseType, InfusionStack> {

    protected MekanismInfusePortStorage(MekanismChemicalPortStorageModel model, INotifyChangeFunction changed) {
        super(model, changed);
    }

    @Override
    protected IChemicalTank<InfuseType, InfusionStack> createTank(long capacity, INotifyChangeFunction changed) {
        return ChemicalTankBuilder.INFUSION.createAllValid(capacity, new NotifyChangeContentsListener(changed));
    }

    @Override
    protected JsonObject debugStack(InfusionStack stack) {
        var json = new JsonObject();
        json.addProperty("infuse", stack.getType().getRegistryName().toString());
        json.addProperty("amount", stack.getAmount());
        return json;
    }

    @Override
    public <T> boolean hasCapability(Capability<T> capability) {
        return MekCapabilities.INFUSE == capability;
    }
}
