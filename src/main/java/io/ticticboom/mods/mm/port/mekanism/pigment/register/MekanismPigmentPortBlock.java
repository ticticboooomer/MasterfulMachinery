package io.ticticboom.mods.mm.port.mekanism.pigment.register;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.datagen.provider.MMBlockstateProvider;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortBlock;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.PortUtils;

public class MekanismPigmentPortBlock extends MekanismChemicalPortBlock {

    private final PortModel model;
    private final RegistryGroupHolder groupHolder;
    private final boolean isInput;

    public MekanismPigmentPortBlock(PortModel model, RegistryGroupHolder groupHolder, boolean isInput) {
        super(model, groupHolder, isInput);
        this.model = model;
        this.groupHolder = groupHolder;
        this.isInput = isInput;
    }

    @Override
    public void generateModel(MMBlockstateProvider provider) {
        PortUtils.commonGenerateModel(provider, groupHolder, isInput, Ref.Textures.INPUT_PIGMENT_PORT_OVERLAY,
                Ref.Textures.OUTPUT_PIGMENT_PORT_OVERLAY);
    }
}