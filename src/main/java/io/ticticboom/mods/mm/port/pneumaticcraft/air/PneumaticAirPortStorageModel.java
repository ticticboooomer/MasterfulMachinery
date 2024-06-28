package io.ticticboom.mods.mm.port.pneumaticcraft.air;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import me.desht.pneumaticcraft.api.pressure.PressureTier;

public record PneumaticAirPortStorageModel (
        int volume,
        PressureTier tier
) implements IPortStorageModel {
    public static PneumaticAirPortStorageModel parse(JsonObject json) {
        float danger = json.get("danger").getAsFloat();
        float critical = json.get("critical").getAsFloat();
        int volume = json.get("volume").getAsInt();
        PressureTier tier1 = new PressureTier() {
            public float getDangerPressure() {
                return danger;
            }

            public float getCriticalPressure() {
                return critical;
            }
        };
        return new PneumaticAirPortStorageModel(volume, tier1);
    }
}
