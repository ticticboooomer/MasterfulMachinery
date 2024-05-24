package io.ticticboom.mods.mm.log;

public class StringLogContextElement implements ILogContextElement{

    private final String data;

    public StringLogContextElement(String data) {
        this.data = data;
    }

    @Override
    public String format() {
        return data;
    }
}
