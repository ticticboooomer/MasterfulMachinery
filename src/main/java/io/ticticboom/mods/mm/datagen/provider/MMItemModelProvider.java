package io.ticticboom.mods.mm.datagen.provider;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.controller.IControllerPart;
import io.ticticboom.mods.mm.ports.IPortPart;
import io.ticticboom.mods.mm.setup.MMRegisters;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
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
        for (RegistryObject<Item> entry : MMRegisters.ITEM.getEntries()) {
            if (entry.get() instanceof IControllerPart controllerPart) {
                String id = controllerPart.getModel().id();
                this.getBuilder(Ref.id(id).toString()).parent(new ModelFile.UncheckedModelFile(Ref.id("block/" + id)));
            }
            if (entry.get() instanceof IPortPart portPart) {
                String id = portPart.getModel().id();
                this.getBuilder(Ref.id(id).toString()).parent(new ModelFile.UncheckedModelFile(Ref.id("block/" + id)));

            }
        }
    }
}
