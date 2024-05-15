package io.ticticboom.mods.mm.util;

import java.util.Optional;
import java.util.function.Supplier;

public class ConditionalLazy<T> {

    protected Optional<T> value = Optional.empty();
    private final Supplier<Boolean> checker;
    protected final T defaultValue;
    protected final Supplier<T> supplier;

    protected ConditionalLazy(Supplier<T> supplier, final Supplier<Boolean> checker, final T defaultValue) {
        this.supplier = supplier;
        this.checker = checker;
        this.defaultValue = defaultValue;
    }

    public static <T> ConditionalLazy<T> create(final Supplier<T> supplier, final Supplier<Boolean> checker, final T defaultValue) {
        return new ConditionalLazy<>(supplier, checker, defaultValue);
    }

    public T get() {
        if (value.isEmpty() && checker.get()) {
            value = Optional.of(supplier.get());
        }

        return value.orElse(defaultValue);
    }
}