package io.ticticboom.mods.mm.datagen.gen;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.block.ControllerBlock;
import io.ticticboom.mods.mm.block.PortBlock;
import io.ticticboom.mods.mm.ports.base.IPortBlock;
import io.ticticboom.mods.mm.setup.MMRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.Collectors;

public class MMItemModelProvider extends ItemModelProvider {
    public MMItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Ref.ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        var controllers = MMRegistries.BLOCKS.getEntries().stream().filter(x -> x.get() instanceof ControllerBlock).collect(Collectors.toList());
        var ports = MMRegistries.BLOCKS.getEntries().stream().filter(x -> x.get() instanceof IPortBlock).collect(Collectors.toList());

        for (RegistryObject<Block> controller : controllers) {
            if (!controller.isPresent()) {
                return;
            }
            this.getBuilder(controller.getId().toString()).parent(new ModelFile.UncheckedModelFile(new ResourceLocation(controller.getId().getNamespace(), "block/" + controller.getId().getPath())));
        }

        for (RegistryObject<Block> port : ports) {
            this.getBuilder(port.getId().toString()).parent(new ModelFile.UncheckedModelFile(new ResourceLocation(port.getId().getNamespace(), "block/" + port.getId().getPath())));
        }
    }
}
