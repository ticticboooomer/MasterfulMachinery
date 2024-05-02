package io.ticticboom.mods.mm.cap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.NoArgsConstructor;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@NoArgsConstructor
public class ScannerSelection implements IScannerSelection {

    public static final Codec<ScannerSelection> CODEC = RecordCodecBuilder.create(b -> b.group(
            BlockPos.CODEC.optionalFieldOf("start").forGetter(x -> Optional.ofNullable(x.start)),
            BlockPos.CODEC.optionalFieldOf("end").forGetter(x -> Optional.ofNullable(x.end))
    ).apply(b, (s, e) -> new ScannerSelection(s.orElse(null), e.orElse(null))));

    private BlockPos start = null;
    private BlockPos end = null;

    public ScannerSelection(final BlockPos start, final BlockPos end) {
        this.start = start;
        this.end = end;
    }


    @Nullable
    @Override
    public BlockPos getStartSelection() {
        return start;
    }

    @Nullable
    @Override
    public BlockPos getEndSelection() {
        return end;
    }

    @Override
    public void setStartSelection(BlockPos pos) {
        start = pos;
    }

    @Override
    public void setEndSelection(BlockPos pos) {
        end = pos;
    }

    public void from(ScannerSelection selection) {
        this.start = selection.getStartSelection();
        this.end = selection.getEndSelection();
    }
}
