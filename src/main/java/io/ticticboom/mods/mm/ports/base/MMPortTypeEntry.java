package io.ticticboom.mods.mm.ports.base;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.block.PortBlock;
import io.ticticboom.mods.mm.datagen.gen.MMBlockStateProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelFile;

public abstract class MMPortTypeEntry {
    public abstract ResourceLocation id();
    public abstract IConfiguredPort parse(JsonObject element);
    public abstract ResourceLocation overlay(boolean input);
    public abstract PortStorage createStorage(IConfiguredPort config);

    public void generateBlockStates(MMBlockStateProvider provider, PortBlock port) {
        var input = port.model().input();
        if (input) {
            provider.dynamicBlock(port.getRegistryName(), MMBlockStateProvider.BASE_TEXTURE, overlay(input));
            provider.simpleBlock(port, new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.ID, "block/" + port.getRegistryName().getPath())));
        } else {
            provider.dynamicBlock(port.getRegistryName(), MMBlockStateProvider.BASE_TEXTURE, overlay(input));
            provider.simpleBlock(port, new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.ID, "block/" + port.getRegistryName().getPath())));
        }
    }

}
