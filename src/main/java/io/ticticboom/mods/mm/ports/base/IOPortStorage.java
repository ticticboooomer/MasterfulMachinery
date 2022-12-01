package io.ticticboom.mods.mm.ports.base;

public record IOPortStorage(
        String name,
        PortStorage port,
        boolean input
) {
    public IOPortStorage deepClone() {
        return new IOPortStorage(name , port.deepClone(), input);
    }
}
