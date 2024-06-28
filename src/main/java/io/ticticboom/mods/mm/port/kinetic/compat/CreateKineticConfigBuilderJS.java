package io.ticticboom.mods.mm.port.kinetic.compat;

import io.ticticboom.mods.mm.compat.kjs.builder.PortConfigBuilderJS;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.kinetic.CreateKineticPortStorageModel;

public class CreateKineticConfigBuilderJS extends PortConfigBuilderJS {
    private float stress;

    public CreateKineticConfigBuilderJS stress(float stress) {
        this.stress = stress;
        return this;
    }

    @Override
    public IPortStorageModel build() {
        return new CreateKineticPortStorageModel(stress);
    }
}
