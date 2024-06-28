package io.ticticboom.mods.mm.compat.kjs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import io.ticticboom.mods.mm.compat.interop.MMInteropManager;
import io.ticticboom.mods.mm.compat.interop.MMKubeJSInterop;

public class MMKubeJSPlugin extends KubeJSPlugin {

    public MMKubeJSPlugin() {
        MMInteropManager.setKubeJS(new MMKubeJSInterop());
    }

    @Override
    public void registerEvents() {
        MMKubeEvents.register();
    }
}
