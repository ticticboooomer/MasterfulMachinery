package io.ticticboom.mods.mm.port.mekanism.pigment.register;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortBlockEntity;
import io.ticticboom.mods.mm.port.mekanism.infuse.register.MekanismInfusePortMenu;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MekanismPigmentPortBlockEntity extends MekanismChemicalPortBlockEntity<Pigment, PigmentStack> {

    private final PortModel model;
    private final RegistryGroupHolder groupHolder;
    private final boolean isInput;

    public MekanismPigmentPortBlockEntity(PortModel model, RegistryGroupHolder groupHolder, boolean isInput, BlockPos pos, BlockState state) {
        super(model, groupHolder, isInput, pos, state);
        this.model = model;
        this.groupHolder = groupHolder;
        this.isInput = isInput;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Pigment Port");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MekanismPigmentPortMenu(model, groupHolder, i, inventory, this);
    }
}