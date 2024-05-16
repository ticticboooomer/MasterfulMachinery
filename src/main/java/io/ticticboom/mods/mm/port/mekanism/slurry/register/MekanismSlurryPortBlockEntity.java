package io.ticticboom.mods.mm.port.mekanism.slurry.register;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortBlockEntity;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.chemical.slurry.SlurryStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

public class MekanismSlurryPortBlockEntity extends MekanismChemicalPortBlockEntity<Slurry, SlurryStack> {
    public MekanismSlurryPortBlockEntity(PortModel model, RegistryGroupHolder groupHolder, boolean isInput, BlockPos pos, BlockState state) {
        super(model, groupHolder, isInput, pos, state);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Slurry Port");
    }
}