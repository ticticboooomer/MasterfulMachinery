package io.ticticboom.mods.mm.setup.loader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.ticticboom.mods.mm.controller.ControllerType;
import io.ticticboom.mods.mm.controller.MMControllerRegistry;
import io.ticticboom.mods.mm.model.config.ControllerModel;
import io.ticticboom.mods.mm.ports.MMPortRegistry;
import io.ticticboom.mods.mm.util.ParserUtils;
import lombok.SneakyThrows;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ControllerLoader extends AbstractConfigLoader<ControllerModel> {
    public static void loadAll() {
        new ControllerLoader().load();
    }

    @Override
    protected void registerControllers(List<ControllerModel> models) {
        for (ControllerModel model : models) {
            ControllerType controllerType = MMControllerRegistry.get(model.type());
            controllerType.register(model);
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
