package net.josh.coolenchants.mixin;

import net.josh.coolenchants.ModUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LocalDifficulty.class)
public class LocalDifficultyMixin {
    @Overwrite
    private float setLocalDifficulty(Difficulty difficulty, long timeOfDay, long inhabitedTime, float moonSize) {
        if (ModUtils.challenge){
            return 3.75f;
        }
        if (difficulty == Difficulty.PEACEFUL) {
            return 0.0f;
        }
        boolean bl = difficulty == Difficulty.HARD;
        float f = 0.75f;
        float g = MathHelper.clamp(((float)timeOfDay + -72000.0f) / 1440000.0f, 0.0f, 1.0f) * 0.25f;
        f += g;
        float h = 0.0f;
        h += MathHelper.clamp((float)inhabitedTime / 3600000.0f, 0.0f, 1.0f) * (bl ? 1.0f : 0.75f);
        h += MathHelper.clamp(moonSize * 0.25f, 0.0f, g);
        if (difficulty == Difficulty.EASY) {
            h *= 0.5f;
        }
        return (float)difficulty.getId() * (f += h);
    }
}
