package io.ticticboom.mods.mm;

import com.google.gson.Gson;
import io.ticticboom.mods.mm.log.LogContextStack;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ref {
    public static final String ID = "mm";
    public static final Logger LOG = LogManager.getLogger("MasterfulMachinery");
    public static final LogContextStack LCTX = new LogContextStack();
    public static final Gson GSON = new Gson();

    public static ResourceLocation id(String path) {
        return new ResourceLocation(ID, path);
    }

    public static final ResourceLocation SCANNER_CAP = id("scanner_selection");
    public static String NBT_STORAGE_KEY = "MMStorage";

    public static final class Registry {
        public static final ResourceLocation STRUCTURES = id("structures");
        public static final ResourceLocation PORCESSES = id("processes");
    }


    public static class Ports {
        public static final ResourceLocation ITEM = id("item");
        public static final ResourceLocation FLUID = id("fluid");
        public static final ResourceLocation ENERGY = id("energy");
        public static final ResourceLocation MEK_GAS = id("mekanism/gas");
        public static final ResourceLocation MEK_SLURRY = id("mekanism/slurry");
        public static final ResourceLocation MEK_PIGMENT = id("mekanism/pigment");
        public static final ResourceLocation MEK_INFUSE = id("mekanism/infuse");
        public static final ResourceLocation CREATE_KINETIC = id("create/kinetic");
        public static final ResourceLocation BOTANIA_MANA = id("botania/mana");

        public static final ResourceLocation PNEUMATIC_AIR = id("pneumaticcraft/air");
        public static final ResourceLocation PNEUMATIC_TEMPERATURE = id("pneumaticcraft/temperature");
    }

    public static class Controller {
        public static final ResourceLocation MACHINE = id("machine");
    }

    public static class RecipeEntries {
        public static final ResourceLocation CONSUME_INPUT = id("input/consume");
        public static final ResourceLocation SIMPLE_OUTPUT = id("output/simple");
    }

    public static class RecipeConditions {
        public static final ResourceLocation DIMENSION = id("dimension");
        public static final ResourceLocation WEATHER = id("weather");
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
        public static final ResourceLocation SCANNER_GUI = id("textures/gui/scanner_gui.png");
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

        public static final ResourceLocation INPUT_GAS_PORT_OVERLAY = id("block/compat_ports/mekanism_gas_input_cutout");
        public static final ResourceLocation OUTPUT_GAS_PORT_OVERLAY = id("block/compat_ports/mekanism_gas_output_cutout");

        public static final ResourceLocation INPUT_SLURRY_PORT_OVERLAY = id("block/compat_ports/mekanism_slurry_input_cutout");
        public static final ResourceLocation OUTPUT_SLURRY_PORT_OVERLAY = id("block/compat_ports/mekanism_slurry_output_cutout");

        public static final ResourceLocation INPUT_PIGMENT_PORT_OVERLAY = id("block/compat_ports/mekanism_pigment_input_cutout");
        public static final ResourceLocation OUTPUT_PIGMENT_PORT_OVERLAY = id("block/compat_ports/mekanism_pigment_output_cutout");

        public static final ResourceLocation INPUT_INFUSE_PORT_OVERLAY = id("block/compat_ports/mekanism_infusion_input_cutout");
        public static final ResourceLocation OUTPUT_INFUSE_PORT_OVERLAY = id("block/compat_ports/mekanism_infusion_output_cutout");

        public static final ResourceLocation INPUT_KINETIC_PORT_OVERLAY = id("block/compat_ports/create_rotation_input_cutout");
        public static final ResourceLocation OUTPUT_KINETIC_PORT_OVERLAY = id("block/compat_ports/create_rotation_output_cutout");

        public static final ResourceLocation INPUT_PNCR_AIR_PORT_OVERLAY = id("block/compat_ports/pncr_pressure_input_cutout");
        public static final ResourceLocation OUTPUT_PNCR_AIR_PORT_OVERLAY = id("block/compat_ports/pncr_pressure_output_cutout");

        public static final ResourceLocation INPUT_BOTANIA_MANA_PORT_OVERLAY = id("block/compat_ports/botania_mana_input_cutout");
        public static final ResourceLocation OUTPUT_BOTANIA_MANA_PORT_OVERLAY = id("block/compat_ports/botania_mana_output_cutout");
    }

}
