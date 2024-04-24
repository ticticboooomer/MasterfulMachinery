package io.ticticboom.mods.mm;

import com.google.gson.Gson;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ref {
    public static final String ID = "mm";
    public static final Logger LOG = LogManager.getLogger();
    public static final Gson GSON = new Gson();

    public static ResourceLocation id(String path) {
        return new ResourceLocation(ID, path);
    }

    public static class Ports {
        public static final ResourceLocation ITEM = id("item");
    }

    public static class Controller {
        public static final ResourceLocation MACHINE = id("machine");
    }

    public static class Textures {
        public static final ResourceLocation BASE_BLOCK = id("block/base_block");
        public static final ResourceLocation CONTROLLER_OVERLAY = id("block/controller_cutout");
        public static final ResourceLocation INPUT_ITEM_PORT_OVERLAY = id("block/base_ports/item_input_cutout");
        public static final ResourceLocation OUTPUT_ITEM_PORT_OVERLAY = id("block/base_ports/item_output_cutout");
        public static final ResourceLocation GUI_LARGE = id("textures/gui/gui_large.png");
        public static final ResourceLocation PORT_GUI = id("textures/gui/port_gui.png");
        public static final ResourceLocation SLOT_PARTS = id("textures/gui/slot_parts.png");
    }
}
