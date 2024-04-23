package io.ticticboom.mods.mm.util;

import io.ticticboom.mods.mm.model.config.PortModel;
import io.ticticboom.mods.mm.ports.IPortBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

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
