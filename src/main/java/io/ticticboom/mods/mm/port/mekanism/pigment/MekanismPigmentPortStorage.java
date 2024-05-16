package io.ticticboom.mods.mm.port.mekanism.pigment;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.cap.MekCapabilities;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;
import io.ticticboom.mods.mm.port.mekanism.NotifyChangeContentsListener;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorage;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorageModel;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentStack;
import net.minecraftforge.common.capabilities.Capability;

public class MekanismPigmentPortStorage extends MekanismChemicalPortStorage<Pigment, PigmentStack> {

    public MekanismPigmentPortStorage(MekanismChemicalPortStorageModel model, INotifyChangeFunction changed) {
        super(model, changed);
    }

    @Override
    protected IChemicalTank<Pigment, PigmentStack> createTank(long capacity, INotifyChangeFunction changed) {
        return ChemicalTankBuilder.PIGMENT.createAllValid(capacity, new NotifyChangeContentsListener(changed));
    }

    @Override
    protected JsonObject debugStack(PigmentStack stack) {
        var json = new JsonObject();
        json.addProperty("pigment", stack.getType().getRegistryName().toString());
        json.addProperty("amount", stack.getAmount());
        return json;
    }

    @Override
    public <T> boolean hasCapability(Capability<T> capability) {
        return MekCapabilities.PIGMENT == capability;
    }
}
