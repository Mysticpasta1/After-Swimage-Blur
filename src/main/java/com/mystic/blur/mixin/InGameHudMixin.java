package com.mystic.blur.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin{

	@Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShader(Ljava/util/function/Supplier;)V"), method = "renderVignetteOverlay(Lnet/minecraft/entity/Entity;)V")
	public void renderVignetteOverlay(Entity entity, CallbackInfo info) {
		/*if (entity instanceof PlayerEntity player) {
			if (player.isSubmergedInWater()) {
				float i = 1;
				while (i >= 0) {
					RenderSystem.setShader(() -> {
						try {
							return new Shader(id -> MinecraftClient.getInstance().getResourceManager().getResource(id), "transparency", VertexFormats.POSITION);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return null;
					});
					i = i - 0.1f;
				}
			} else {
				float i = 0;
				while (i <= 1) {
					RenderSystem.setShader(() -> {
						try {
							return new Shader(id -> MinecraftClient.getInstance().getResourceManager().getResource(id), "transparency", VertexFormats.POSITION);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return null;
					});
					i = i + 0.1f;
				}
			}
		}*/
	}

	@Inject(method = "render", at = @At("HEAD"))
	public void renderOverlayMixin(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
	}
}