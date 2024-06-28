package io.ticticboom.mods.mm.port.mekanism.infuse.register;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.datagen.provider.MMBlockstateProvider;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortBlock;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.PortUtils;

public class MekanismInfusePortBlock extends MekanismChemicalPortBlock {

    private final RegistryGroupHolder groupHolder;
    private final boolean isInput;

    public MekanismInfusePortBlock(PortModel model, RegistryGroupHolder groupHolder, boolean isInput) {
        super(model, groupHolder, isInput);
        this.groupHolder = groupHolder;
        this.isInput = isInput;
    }

    @Override
    public void generateModel(MMBlockstateProvider provider) {
        PortUtils.commonGenerateModel(provider, groupHolder, isInput, Ref.Textures.INPUT_INFUSE_PORT_OVERLAY,
                Ref.Textures.OUTPUT_INFUSE_PORT_OVERLAY);
    }
}
