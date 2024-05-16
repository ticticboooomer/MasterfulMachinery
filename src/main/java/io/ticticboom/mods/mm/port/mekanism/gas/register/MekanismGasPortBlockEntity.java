package io.ticticboom.mods.mm.port.mekanism.gas.register;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortBlockEntity;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

public class MekanismGasPortBlockEntity extends MekanismChemicalPortBlockEntity<Gas, GasStack> {

    public MekanismGasPortBlockEntity(PortModel model, RegistryGroupHolder groupHolder, boolean isInput, BlockPos pos, BlockState state) {
        super(model, groupHolder, isInput, pos, state);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Mekanism Gas Port");
    }
}
