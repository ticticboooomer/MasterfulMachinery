package io.ticticboom.mods.mm;

import net.minecraft.resources.ResourceLocation;

public class Ref {
    public static final String ID = "mm";

    public static ResourceLocation id(String path) {
        return new ResourceLocation(ID, path);
    }

    public static class Ports {
        public static final ResourceLocation ITEM = id("item");
    }

    public static class Controller {
        public static final ResourceLocation MACHINE = id("machine");
    }
}
