package io.ticticboom.mods.mm.controller.machine.register;

import io.ticticboom.mods.mm.model.config.ControllerModel;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class MachineControllerBlock extends Block {
    private final ControllerModel model;
    private final RegistryGroupHolder groupHolder;

    public MachineControllerBlock(ControllerModel model, RegistryGroupHolder groupHolder) {
        super(Properties.of().requiresCorrectToolForDrops().destroyTime(5f).explosionResistance(6f).sound(SoundType.METAL));
        this.model = model;
        this.groupHolder = groupHolder;
    }
}
