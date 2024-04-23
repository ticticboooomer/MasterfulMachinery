package io.ticticboom.mods.mm.datagen.provider;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.controller.IControllerBlock;
import io.ticticboom.mods.mm.controller.IControllerPart;
import io.ticticboom.mods.mm.ports.IPortPart;
import io.ticticboom.mods.mm.setup.MMRegisters;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

public class MMLangProvider extends LanguageProvider {
    public MMLangProvider(DataGenerator generator, String locale) {
        super(generator.getPackOutput(), Ref.ID, locale);
    }

    @Override
    protected void addTranslations() {
        for (RegistryObject<Block> entry : MMRegisters.BLOCKS.getEntries()) {
            if (entry.get() instanceof IControllerPart controllerPart) {
                this.add(entry.get(), controllerPart.getModel().name());
            }
            if (entry.get() instanceof IPortPart part) {
                this.add(entry.get(), part.getModel().name());
            }
        }
    }
}