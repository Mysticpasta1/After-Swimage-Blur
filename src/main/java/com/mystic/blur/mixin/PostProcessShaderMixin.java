package com.mystic.blur.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.JsonEffectGlShader;
import net.minecraft.client.gl.PostProcessShader;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PostProcessShader.class)
public class PostProcessShaderMixin {
	@Shadow
	@Final
	private JsonEffectGlShader program;

	@Inject(method = "render(F)V", at = @At("HEAD"))
	public void render(float time, CallbackInfo cir) {
		if(MinecraftClient.getInstance().player != null) {
			if(((PlayerEntity) MinecraftClient.getInstance().player).isSubmergedInWater()) {
				float i = 1;
				while(i >= 0) {
					program.attachReferencedShaders();
					i = i - 0.1f;
				}
			} else {
				float i = 0;
				while(i <= 1) {
					program.getUniformByNameOrDummy("blur").set(i);
					i = i + 0.1f;
				}
			}
		}
	}

	private PlayerEntity getCameraPlayer() {
		return !(MinecraftClient.getInstance().getCameraEntity() instanceof PlayerEntity) ? null : (PlayerEntity)MinecraftClient.getInstance().getCameraEntity();
	}
}