package io.ticticboom.mods.mm.util;

public class PortUtils {

    public static String id(String id, boolean input) {
        var res = id + "_" + (input ? "input" : "output");
        return res;
    }

    public static String name(String name, boolean input) {
        var res = name + " " + (input ? "Input" : "Output");
        return res;
    }
}
