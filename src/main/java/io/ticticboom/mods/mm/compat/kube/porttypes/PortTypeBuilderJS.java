package io.ticticboom.mods.mm.compat.kube.porttypes;

import io.ticticboom.mods.mm.ports.js.ConfiguredPortJS;
import io.ticticboom.mods.mm.ports.js.PortStorageJS;
import io.ticticboom.mods.mm.ports.js.PortTypeEntryJS;
import io.ticticboom.mods.mm.setup.MMRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class PortTypeBuilderJS {
    private ResourceLocation id;
    private PortTypeEntryJS.OverlayCallback overlay;
    private PortTypeEntryJS.ProcessIOCallback processInputs;
    private PortTypeEntryJS.ProcessIOCallback processOutputs;
    private PortStorageJS.DataCallback readCallback;
    private PortStorageJS.DataCallback writeCallback;
    private PortStorageJS.DestroyCallback destroyCallback;

    public PortTypeBuilderJS(ResourceLocation id) {
        this.id = id;
    }

    public PortTypeBuilderJS overlay(PortTypeEntryJS.OverlayCallback overlay) {
        this.overlay = overlay;
        return this;
    }

    public PortTypeBuilderJS processInputs(PortTypeEntryJS.ProcessIOCallback processInputs) {
        this.processInputs = processInputs;
        return this;
    }

    public PortTypeBuilderJS processOutputs(PortTypeEntryJS.ProcessIOCallback processOutputs) {
        this.processOutputs = processOutputs;
        return this;
    }

    public PortTypeBuilderJS readCallback(PortStorageJS.DataCallback readCallback) {
        this.readCallback = readCallback;
        return this;
    }

    public PortTypeBuilderJS writeCallback(PortStorageJS.DataCallback writeCallback) {
        this.writeCallback = writeCallback;
        return this;
    }

    public PortTypeBuilderJS destroyCallback(PortStorageJS.DestroyCallback destroyCallback) {
        this.destroyCallback = destroyCallback;
        return this;
    }

    public void build() {
        Function<ConfiguredPortJS, PortStorageJS> storage = (ConfiguredPortJS config) -> new PortStorageJS(readCallback, writeCallback, destroyCallback, config);
        MMRegistries.PORTS.put(id, new PortTypeEntryJS(overlay, processInputs, processOutputs, storage));
    }
}
