package io.ticticboom.mods.mm.datagen.provider;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.controller.IControllerBlock;
import io.ticticboom.mods.mm.extra.IExtraBlock;
import io.ticticboom.mods.mm.port.IPortBlock;
import io.ticticboom.mods.mm.setup.MMRegisters;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;


public class MMBlockTagsProvider extends TagsProvider<Block> {

    public MMBlockTagsProvider(DataGenerator generator, ExistingFileHelper efh) {
        super(generator, Registry.BLOCK, Ref.ID, efh);
    }

    @Override
    protected void addTags() {
        var pickaxeTool = this.tag(BlockTags.MINEABLE_WITH_PICKAXE);

        for (RegistryObject<Block> entry : MMRegisters.BLOCKS.getEntries()) {
            Block block = entry.get();
            if (block instanceof IControllerBlock || block instanceof IPortBlock || block instanceof IExtraBlock) {
                pickaxeTool.add(block);
            }
        }
    }
}
