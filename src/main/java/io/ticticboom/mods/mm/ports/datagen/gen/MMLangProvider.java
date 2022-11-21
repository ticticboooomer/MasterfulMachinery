package io.ticticboom.mods.mm.ports.datagen.gen;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.block.ControllerBlock;
import io.ticticboom.mods.mm.block.PortBlock;
import io.ticticboom.mods.mm.ports.base.IPortBlock;
import io.ticticboom.mods.mm.setup.MMRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.Collectors;

public class MMLangProvider extends LanguageProvider {
    public MMLangProvider(DataGenerator gen) {
        super(gen, Ref.ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        var controllers = MMRegistries.BLOCKS.getEntries().stream().filter(x -> x.get() instanceof ControllerBlock).collect(Collectors.toList());
        var ports = MMRegistries.BLOCKS.getEntries().stream().filter(x -> x.get() instanceof IPortBlock).collect(Collectors.toList());

        for (RegistryObject<Block> controller : controllers) {
            var controllerBlock = ((ControllerBlock) controller.get());
            this.add(controller.get(), controllerBlock.model().name().getString() + " Controller");
            this.add("container.masterfulmachinery." + controllerBlock.model().blockId() + "_controller.name", controllerBlock.model().name().getString() + " Controller");
        }

        for (RegistryObject<Block> port : ports) {
            var portBlock = ((IPortBlock) port.get());
            this.add(port.get(), portBlock.model().name().getString() + " " + (portBlock.model().input() ? "Input" : "Output") + " Port");
        }
    }
}
