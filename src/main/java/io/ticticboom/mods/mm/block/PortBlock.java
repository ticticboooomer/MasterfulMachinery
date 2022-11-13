package io.ticticboom.mods.mm.block;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.setup.model.PortModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class PortBlock extends Block {

    private final PortModel model;

    public PortBlock(PortModel model) {
        super(Properties.of(Material.METAL));
        this.model = model;
    }

    public PortModel model() {
        return model;
    }
}
