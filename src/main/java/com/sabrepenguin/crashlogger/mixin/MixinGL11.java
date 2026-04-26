package com.sabrepenguin.crashlogger.mixin;

import com.sabrepenguin.crashlogger.helper.LogFileManager;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "org.lwjgl.opengl.GL11", remap = false)
public class MixinGL11 {

	@Inject(at = @At("HEAD"), method = "glCallList(I)V", remap = false)
	private static void glCallListHead(int list, CallbackInfo ci) {
		LogFileManager.enter(LogFileManager.FLAG_GL_CLEAR_HEAD);
	}

	@Inject(at = @At("RETURN"), method = "glCallList(I)V", remap = false)
	private static void glCallListExit(int list, CallbackInfo ci) {
		LogFileManager.exit(LogFileManager.FLAG_GL_CLEAR_HEAD);
	}
}
