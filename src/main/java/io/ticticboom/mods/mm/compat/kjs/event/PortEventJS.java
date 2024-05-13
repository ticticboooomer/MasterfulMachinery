package io.ticticboom.mods.mm.compat.kjs.event;

import dev.latvian.mods.kubejs.event.StartupEventJS;
import io.ticticboom.mods.mm.compat.kjs.builder.PortBuilderJS;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class PortEventJS extends StartupEventJS {

    @Getter
    private final List<PortBuilderJS> ports = new ArrayList<>();

    public PortEventJS() {
    }

    public PortBuilderJS create(String id) {
        var val = new PortBuilderJS(id);
        ports.add(val);
        return val;
    }
}
