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
    public static final ResourceLocation STRUCTURE_TRANSFORMS_REGISTRY = res("structure_transforms");
    public static final ResourceLocation PORT_GUI = res("textures/gui/port_gui.png");
    public static final ResourceLocation SLOT_PARTS = res("textures/gui/slot_parts.png");

    public static final class Ports {
        public static final ResourceLocation ITEM = res("item");
        public static final ResourceLocation FLUID = res("fluid");
        public static final ResourceLocation ENERGY = res("energy");
    }

    public static final class StructureParts {
        public static final ResourceLocation BLOCK = res("block");
        public static final ResourceLocation TAG = res("tag");
        public static final ResourceLocation PORT = res("port");
    }

    public static final class RecipeEntries {
        public static final ResourceLocation SIMPLE = res("simple");
    }
    public static final class StructureTransforms  {
        public static final ResourceLocation ROT_90 = res("rotated_90");
        public static final ResourceLocation ROT_180 = res("rotated_180");
        public static final ResourceLocation ROT_270 = res("rotated_270");
    }
}
