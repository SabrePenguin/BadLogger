package com.sabrepenguin.crashlogger.mixin;

import com.sabrepenguin.crashlogger.helper.LogFileManager;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = GlStateManager.class)
public class MixinGLManager {
	@Inject(at = @At("HEAD"), method = "clear(I)V")
	private static void clearHead(int mask, CallbackInfo ci) {
		LogFileManager.enter(LogFileManager.FLAG_GL_CLEAR);
	}

	@Inject(at = @At("RETURN"), method = "clear(I)V")
	private static void clearEnd(int mask, CallbackInfo ci) {
		LogFileManager.exit(LogFileManager.FLAG_GL_CLEAR);
	}
}
