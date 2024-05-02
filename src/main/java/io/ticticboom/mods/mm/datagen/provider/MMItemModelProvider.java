package io.ticticboom.mods.mm.datagen.provider;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.extra.IExtraBlockPart;
import io.ticticboom.mods.mm.controller.IControllerPart;
import io.ticticboom.mods.mm.port.IPortPart;
import io.ticticboom.mods.mm.setup.MMRegisters;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class MMItemModelProvider extends ItemModelProvider {
    private final DataGenerator generator;

    public MMItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), Ref.ID, existingFileHelper);
        this.generator = generator;
    }

    @Override
    protected void registerModels() {
        for (RegistryObject<Item> entry : MMRegisters.ITEMS.getEntries()) {
            if (entry.get() instanceof IControllerPart controllerPart) {
                String id = controllerPart.getModel().id();
                this.getBuilder(Ref.id(id).toString()).parent(new ModelFile.UncheckedModelFile(Ref.id("block/" + id)));

            }
            if (entry.get() instanceof IPortPart portPart) {
                String id = portPart.getModel().id();
                this.getBuilder(Ref.id(id).toString()).parent(new ModelFile.UncheckedModelFile(Ref.id("block/" + id)));
            }
            if (entry.get() instanceof IExtraBlockPart ebp) {
                String id = ebp.getModel().id();
                this.getBuilder(Ref.id(id).toString()).parent(new ModelFile.UncheckedModelFile(Ref.id("block/" + id)));
            }
        }
        this.getBuilder(MMRegisters.SCANNER_BLOCK_ITEM.getId().getPath()).parent(new ModelFile.UncheckedModelFile(Ref.id("block/structure_scanner")));
    }
}