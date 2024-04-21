package io.ticticboom.mods.mm.setup.loader;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.model.config.PortModel;
import io.ticticboom.mods.mm.ports.MMPortRegistry;
import io.ticticboom.mods.mm.ports.PortType;

import javax.sound.sampled.Port;
import java.util.List;

public class PortLoader extends AbstractConfigLoader<PortModel> {

    public static void loadAll() {
        new PortLoader().load();
    }

    @Override
    protected String getConfigPath() {
        return "ports";
    }

    @Override
    protected List<PortModel> parseModels(List<JsonObject> jsons) {
        return jsons.stream().map(PortModel::parse).toList();
    }

    @Override
    protected void registerControllers(List<PortModel> portModels) {
        for (PortModel portModel : portModels) {
            PortType portType = MMPortRegistry.get(portModel.type());
            portType.register(portModel, true);
            portType.register(portModel, false);
        }
    }
}
