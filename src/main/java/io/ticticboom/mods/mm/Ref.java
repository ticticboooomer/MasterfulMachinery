package io.ticticboom.mods.mm;

import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ref {
    public static final Logger LOG = LogManager.getLogger(Ref.ID);
    public static final String ID = "mm";

    public static ResourceLocation res(String path) {
        return new ResourceLocation(ID, path);
    }

    public static final ResourceLocation STRUCTURE_PART_REGISTRY = res("structure_parts");
    public static final ResourceLocation RECIPE_ENTRIES_REGISTRY = res("recipe_entries");
    public static final ResourceLocation CLIENT_PORTS_REGISTRY = res("client_ports");
    public static final ResourceLocation STRUCTURE_TRANSFORMS_REGISTRY = res("structure_transforms");
    public static final ResourceLocation PORT_GUI = res("textures/gui/port_gui.png");
    public static final ResourceLocation SLOT_PARTS = res("textures/gui/slot_parts.png");

    public static final class CompatRegistries {
        public static final ResourceLocation JEI_RECIPE_ENTRIES = res("jei/recipe_entries");
        public static final ResourceLocation JEI_PORT_TYPES = res("jei/port_types");
    }
    public static final class Ports {
        public static final ResourceLocation ITEM = res("item");
        public static final ResourceLocation FLUID = res("fluid");
        public static final ResourceLocation ENERGY = res("energy");
        public static final ResourceLocation CREATE_ROT = res("create_rotation");
        public static final ResourceLocation MEK_GAS = res("mekanism_gas");
        public static final ResourceLocation MEK_INFUSE = res("mekanism_infuse");
        public static final ResourceLocation MEK_PIGMENT = res("mekanism_pigment");
        public static final ResourceLocation MEK_SLURRY = res("mekanism_slurry");
    }

    public static final class StructureParts {
        public static final ResourceLocation BLOCK = res("block");
        public static final ResourceLocation TAG = res("tag");
        public static final ResourceLocation PORT = res("port");
    }

    public static final class RecipeEntries {
        public static final ResourceLocation SIMPLE = res("simple");
        public static final ResourceLocation PER_TICK = res("per_tick");
        public static final ResourceLocation INGREDIENT_TICK_MODIFIER = res("tick_modifier/ingredient");
        public static final ResourceLocation OR_GATE = res("gate/or");
        public static final ResourceLocation AND_GATE = res("gate/and");
        public static final ResourceLocation STRUCTURE_PART = res("structure_part");
        public static final ResourceLocation PRESET = res("preset");
    }

    public static final class StructureTransforms  {
        public static final ResourceLocation ROT_90 = res("rotated_90");
        public static final ResourceLocation ROT_180 = res("rotated_180");
        public static final ResourceLocation ROT_270 = res("rotated_270");
    }
}
