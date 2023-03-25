package io.ticticboom.mods.mm.ports.js;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import io.ticticboom.mods.mm.ports.base.IConfiguredPort;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.ports.item.ItemConfiguredIngredient;
import io.ticticboom.mods.mm.ports.item.ItemPortStorage;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class PortTypeEntryJS extends MMPortTypeEntry {

    private OverlayCallback overlay;
    private ProcessIOCallback processInputs;
    private ProcessIOCallback processOutputs;
    private Function<ConfiguredPortJS, PortStorageJS>  storageFactory;

    public PortTypeEntryJS(OverlayCallback overlay,
                           ProcessIOCallback processInputs,
                           ProcessIOCallback processOutputs,
                           Function<ConfiguredPortJS, PortStorageJS> storageFactory) {

        this.overlay = overlay;
        this.processInputs = processInputs;
        this.processOutputs = processOutputs;
        this.storageFactory = storageFactory;
    }
    @Override
    public Class<? extends PortStorage> storageClass() {
        return PortStorageJS.class;
    }

    @Override
    public IConfiguredPort parse(JsonObject element) {
        return new ConfiguredPortJS(element);
    }

    @Override
    public IConfiguredIngredient parseIngredient(JsonObject json) {
        return new ConfiguredIngredientJS(json);
    }

    @Override
    public ResourceLocation overlay(boolean input) {
        if (overlay == null) {
            return input ? Ref.res("block/base_ports/item_input_cutout") : Ref.res("block/base_ports/item_output_cutout");
        }
        return ResourceLocation.tryParse(overlay.run(input));
    }

    @Override
    public PortStorage createStorage(IConfiguredPort config) {
        return storageFactory.apply(((ConfiguredPortJS) config));
    }

    @Override
    public boolean processInputs(IConfiguredIngredient ingredient, List<PortStorage> storage) {
        return processIO(ingredient, storage, processInputs);

    }

    @Override
    public boolean processOutputs(IConfiguredIngredient ingredient, List<PortStorage> storage) {
        return processIO(ingredient, storage, processOutputs);
    }

    private boolean processIO(IConfiguredIngredient ingredient, List<PortStorage> storage, ProcessIOCallback processor) {
        var conf = (ConfiguredIngredientJS) ingredient;
        var state = new JsonObject();
        for (PortStorage portStorage : storage) {
            if (portStorage instanceof PortStorageJS psjs) {
                var result = processor.run(conf.config(), psjs.data, state);
                if (result) {
                    return true;
                }
            }
        }
        return false;
    }


    @FunctionalInterface
    public interface OverlayCallback {
        String run(boolean input);
    }

    @FunctionalInterface
    public interface ProcessIOCallback {
        boolean run(JsonObject ingredient, JsonObject portStorage, JsonObject state);
    }
}
