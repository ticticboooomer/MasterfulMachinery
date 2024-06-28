package io.ticticboom.mods.mm.compat.kjs.event;

import dev.latvian.mods.kubejs.event.StartupEventJS;
import io.ticticboom.mods.mm.compat.kjs.builder.ControllerBuilderJS;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ControllerEventJS extends StartupEventJS {
    private final List<ControllerBuilderJS> controllers = new ArrayList<>();

    public ControllerEventJS() {

    }

    public ControllerBuilderJS create(String id) {
        var val = new ControllerBuilderJS(id);
        controllers.add(val);
        return val;
    }
}
