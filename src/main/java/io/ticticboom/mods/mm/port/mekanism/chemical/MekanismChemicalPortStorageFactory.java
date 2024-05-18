package io.ticticboom.mods.mm.port.mekanism.chemical;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortStorageFactory;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;

public abstract class MekanismChemicalPortStorageFactory<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>> implements IPortStorageFactory {


    protected final MekanismChemicalPortStorageModel model;

    public MekanismChemicalPortStorageFactory(MekanismChemicalPortStorageModel model) {
        this.model = model;
    }

    @Override
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("amount", model.amount());
        return json;
    }

    @Override
    public IPortStorageModel getModel() {
        return model;
    }
}
