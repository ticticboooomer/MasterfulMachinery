package io.ticticboom.mods.mm.compat.interop;

import java.util.Optional;

public class MMInteropManager {
    public static Optional<IKubeJSInterop> KUBEJS = Optional.empty();

    public static void setKubeJS(IKubeJSInterop interop) {
        KUBEJS = Optional.ofNullable(interop);
    }
}
