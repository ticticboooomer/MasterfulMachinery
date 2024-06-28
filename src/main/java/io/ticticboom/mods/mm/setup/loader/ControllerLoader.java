package io.ticticboom.mods.mm.setup.loader;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.compat.interop.MMInteropManager;
import io.ticticboom.mods.mm.controller.ControllerType;
import io.ticticboom.mods.mm.controller.MMControllerRegistry;
import io.ticticboom.mods.mm.model.ControllerModel;

import java.util.List;

public class ControllerLoader extends AbstractConfigLoader<ControllerModel> {
    public static void loadAll() {
        new ControllerLoader().load();
    }

    @Override
    protected void registerModels(List<ControllerModel> models) {
        for (ControllerModel model : models) {
            ControllerType controllerType = MMControllerRegistry.get(model.type());
            controllerType.register(model);
        }

        if (MMInteropManager.KUBEJS.isPresent()) {
            var controllers = MMInteropManager.KUBEJS.get().postRegisterControllers();
            for (ControllerModel model : controllers) {
                ControllerType controllerType = MMControllerRegistry.get(model.type());
                controllerType.register(model);
            }
        }
    }

    @Override
    protected List<ControllerModel> parseModels(List<JsonObject> jsons) {
        return jsons.stream().map(ControllerModel::parse).toList();
    }

    @Override
    protected String getConfigPath() {
        return "controllers";
    }
}
