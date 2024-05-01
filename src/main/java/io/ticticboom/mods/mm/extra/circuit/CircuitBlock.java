package io.ticticboom.mods.mm.extra.circuit;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.extra.ExtraBlockModel;
import io.ticticboom.mods.mm.extra.IExtraBlock;
import io.ticticboom.mods.mm.datagen.provider.MMBlockstateProvider;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.BlockUtils;
import net.minecraft.world.level.block.Block;

public class CircuitBlock extends Block implements IExtraBlock {
    private final ExtraBlockModel model;
    private final RegistryGroupHolder groupHolder;

    public CircuitBlock(ExtraBlockModel model, RegistryGroupHolder groupHolder) {
        super(BlockUtils.createBlockProperties());
        this.model = model;
        this.groupHolder = groupHolder;
    }

    @Override
    public ExtraBlockModel getModel() {
        return model;
    }

    @Override
    public void generateModel(MMBlockstateProvider provider) {
        var mdl = provider.dynamicBlock(groupHolder.getBlock().getId(), Ref.Textures.BASE_BLOCK, Ref.Textures.CIRCUIT_OVERLAY);
        provider.simpleBlock(groupHolder.getBlock().get(), mdl);
    }
}
