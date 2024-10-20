package net.josh.coolenchants.mixin;

import net.josh.coolenchants.ModUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public abstract class MouseMixin {
/*
astral project (partially, most of the code is in playerentitymixin)
 */
    @Shadow public abstract double getX();

    @Shadow public abstract double getY();

    @Shadow private double eventDeltaWheel;
    private final MinecraftClient client;

    public MouseMixin(MinecraftClient client) {
        this.client = client;
    }
//...............USED FOR ASTRAL PROJECT...............
    @Inject(method = "onMouseScroll", at = @At("HEAD"))
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (window == MinecraftClient.getInstance().getWindow().getHandle()) {
            double d = (this.client.options.getDiscreteMouseScroll().getValue() != false ? Math.signum(vertical) : vertical) * this.client.options.getMouseWheelSensitivity().getValue();
            if (this.client.getOverlay() == null) {
                if (this.client.currentScreen != null) {
                    double e = this.getX() * (double)this.client.getWindow().getScaledWidth() / (double)this.client.getWindow().getWidth();
                    double f = this.getY() * (double)this.client.getWindow().getScaledHeight() / (double)this.client.getWindow().getHeight();
                    this.client.currentScreen.mouseScrolled(e, f, d);
                    this.client.currentScreen.applyMousePressScrollNarratorDelay();
                } else if (this.client.player != null) {
                    if (this.eventDeltaWheel != 0.0 && Math.signum(d) != Math.signum(this.eventDeltaWheel)) {
                        this.eventDeltaWheel = 0.0;
                    }
                    this.eventDeltaWheel += d;
                    int i = (int)this.eventDeltaWheel;

                    if (i == 0) {
                        return;
                    }
                    this.eventDeltaWheel -= (double)i;
                    if (this.client.player.isSpectator()) {
                        if (this.client.inGameHud.getSpectatorHud().isOpen()) {
                            this.client.inGameHud.getSpectatorHud().cycleSlot(-i);
                        } else {
                            float g = MathHelper.clamp(this.client.player.getAbilities().getFlySpeed() + (float)i * 0.005f, 0.0f, 0.2f);
                            this.client.player.getAbilities().setFlySpeed(g);
                        }
                    } else {
                        ModUtils.scroll_amount = i;
                        this.client.player.getInventory().scrollInHotbar(i);
                    }
                }
            }
        }
    }
}
