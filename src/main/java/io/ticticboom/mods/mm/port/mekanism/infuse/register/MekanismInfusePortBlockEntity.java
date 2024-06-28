package io.ticticboom.mods.mm.port.mekanism.infuse.register;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortBlockEntity;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfusionStack;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MekanismInfusePortBlockEntity extends MekanismChemicalPortBlockEntity<InfuseType, InfusionStack> {

    private final PortModel model;
    private final RegistryGroupHolder groupHolder;
    private final boolean isInput;

    public MekanismInfusePortBlockEntity(PortModel model, RegistryGroupHolder groupHolder, boolean isInput, BlockPos pos, BlockState state) {
        super(model, groupHolder, isInput, pos, state);
        this.model = model;
        this.groupHolder = groupHolder;
        this.isInput = isInput;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Mekanism Infusion Port");

    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MekanismInfusePortMenu(model, groupHolder, i, inventory, this);
    }
}
