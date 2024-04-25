package io.ticticboom.mods.mm.structure.layout;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class StructureCharacterGrid {
    private List<List<String>> rawLayout;

    @Getter
    private Vec3i bounds;

    @Getter
    private BlockPos controllerPos;

    public StructureCharacterGrid(List<List<String>> rawLayout) {
        this.rawLayout = rawLayout;
        calculateBounds();
    }

    public void calculateBounds() {
        int ySize = 0;
        int zSize = 0;
        int xSize = 0;

        for (int y = rawLayout.size() - 1; y > 0; y--) {
            var layer = rawLayout.get(y);
            for (int z = 0; z < layer.size(); z++) {
                var row = layer.get(z);
                for (int x = 0; x < row.length(); x++) {
                    if (row.charAt(x) == 'C') {
                        controllerPos = new BlockPos(x, rawLayout.size() - y, z);
                    }
                    xSize = x;
                }
                zSize = z;
            }
            ySize = y;
        }
        bounds = new Vec3i(xSize, ySize, zSize);
    }

    public void runFor(BiConsumer<BlockPos, BlockPos> consumer) {
        for (int y = rawLayout.size() - 1; y > 0; y--) {
            var layer = rawLayout.get(y);
            for (int z = 0; z < layer.size(); z++) {
                var row = layer.get(z);
                for (int x = 0; x < row.length(); x++) {
                    consumer.accept(new BlockPos(x, y, z), controllerPos);
                }
            }
        }
    }

    public char getValueAt(int x, int y, int z) {
        return rawLayout.get(y).get(z).charAt(x);
    }

    public char getValueAt(BlockPos pos) {
        return getValueAt(pos.getX(), pos.getY(), pos.getZ());
    }
}
