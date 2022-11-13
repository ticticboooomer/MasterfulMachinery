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

    public static final class Ports {
        public static final ResourceLocation ITEM = res("item");
    }

    public static final class StructureParts {
        public static final ResourceLocation BLOCK = res("block");
        public static final ResourceLocation TAG = res("tag");
    }
}
