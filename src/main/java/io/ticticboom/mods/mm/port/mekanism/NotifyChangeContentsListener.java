package io.ticticboom.mods.mm.port.mekanism;

import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;
import mekanism.api.IContentsListener;

public class NotifyChangeContentsListener implements IContentsListener {

    private final INotifyChangeFunction changed;

    public NotifyChangeContentsListener(INotifyChangeFunction changed) {
        this.changed = changed;
    }

    @Override
    public void onContentsChanged() {
        changed.call();
    }
}
