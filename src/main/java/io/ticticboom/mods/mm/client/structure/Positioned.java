package io.ticticboom.mods.mm.client.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.core.BlockPos;

@Getter
@AllArgsConstructor
public class Positioned<T> {
    protected T part;
    protected BlockPos pos;
}
