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

    public static final ResourceLocation SCANNER_CAP = id("scanner_selection");
    public static String NBT_STORAGE_KEY = "MMStorage";

    public static class Ports {
        public static final ResourceLocation ITEM = id("item");
        public static final ResourceLocation FLUID = id("fluid");
        public static final ResourceLocation ENERGY = id("energy");
    }

    public static class Controller {
        public static final ResourceLocation MACHINE = id("machine");
    }

    public static class RecipeEntries {
        public static final ResourceLocation CONSUME_INPUT = id("input/consume");
        public static final ResourceLocation SIMPLE_OUTPUT = id("output/simple");
    }

    public static class ExtraBlocks {
        public static final ResourceLocation CIRCUIT = id("circuit");
        public static final ResourceLocation GEARBOX = id("gearbox");
        public static final ResourceLocation VENT = id("vent");
    }

    public static class Textures {
        public static final ResourceLocation BASE_BLOCK = id("block/base_block");
        public static final ResourceLocation CONTROLLER_OVERLAY = id("block/controller_cutout");

        public static final ResourceLocation GUI_LARGE_JEI = id("textures/gui/gui_large_jei.png");
        public static final ResourceLocation GUI_LARGE = id("textures/gui/gui_large.png");
        public static final ResourceLocation PORT_GUI = id("textures/gui/port_gui.png");
        public static final ResourceLocation SLOT_PARTS = id("textures/gui/slot_parts.png");
        public static final ResourceLocation CREATIVE_TAB_BG = id("textures/gui/tab_item_search.png");

        public static final ResourceLocation INPUT_ITEM_PORT_OVERLAY = id("block/base_ports/item_input_cutout");
        public static final ResourceLocation OUTPUT_ITEM_PORT_OVERLAY = id("block/base_ports/item_output_cutout");

        public static final ResourceLocation INPUT_FLUID_PORT_OVERLAY = id("block/base_ports/fluid_input_cutout");
        public static final ResourceLocation OUTPUT_FLUID_PORT_OVERLAY = id("block/base_ports/fluid_output_cutout");

        public static final ResourceLocation INPUT_ENERGY_PORT_OVERLAY = id("block/base_ports/energy_input_cutout");
        public static final ResourceLocation OUTPUT_ENERGY_PORT_OVERLAY = id("block/base_ports/energy_output_cutout");

        public static final ResourceLocation CIRCUIT_OVERLAY = id("block/circuit_cutout");
        public static final ResourceLocation GEARBOX_OVERLAY = id("block/gearbox_cutout");
        public static final ResourceLocation VENT_OVERLAY = id("block/vent_cutout");

    }

}
