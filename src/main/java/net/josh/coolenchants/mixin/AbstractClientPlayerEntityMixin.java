
package net.josh.coolenchants.mixin;

import net.josh.coolenchants.IEntityDataSaver;
import net.josh.coolenchants.data.EnchantmentData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin {
    AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) (Object) this;
    float scopedLevel = 1.0f;
    /**
     * @author
     * @reason
     */
    @Overwrite
    public float getFovMultiplier() {
        float f = 1.0f;
        if (player.getAbilities().flying) {
            f *= 1.1f;
        }
        if (player.getAbilities().getWalkSpeed() == 0.0f || Float.isNaN(f *= ((float)player.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) / player.getAbilities().getWalkSpeed() + 1.0f) / 2.0f) || Float.isInfinite(f)) {
            f = 1.0f;
        }
        ItemStack itemStack = player.getActiveItem();
        if (player.isUsingItem()) {
            if (itemStack.isOf(Items.BOW)) {
                int i = player.getItemUseTime();
                float g = (float)i / 20.0f;
                g = g > 1.0f ? 1.0f : (g *= g);
                f *= 1.0f - g * 0.15f;
                if (MinecraftClient.getInstance().options.getPerspective().isFirstPerson() && EnchantmentData.getScope((IEntityDataSaver) ((ClientPlayerEntity) player))){
                    if (MinecraftClient.getInstance().options.sneakKey.isPressed()){
                        if (scopedLevel > 0.4f) {
                            scopedLevel-= 0.02f;
                        }
                        return 0.4f;
                    } else {
                        scopedLevel = 1.0f;
                    }
                }
            } else if (MinecraftClient.getInstance().options.getPerspective().isFirstPerson() && player.isUsingSpyglass()) {
                return 0.1f;
            }
        }
        return MathHelper.lerp(MinecraftClient.getInstance().options.getFovEffectScale().getValue().floatValue(), 1.0f, f);
    }
}
