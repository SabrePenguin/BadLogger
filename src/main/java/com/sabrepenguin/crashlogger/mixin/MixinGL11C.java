package com.sabrepenguin.crashlogger.mixin;

import com.sabrepenguin.crashlogger.helper.LogFileManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "org.lwjgl.opengl.GL11C", remap = false)
public class MixinGL11C {

	@Unique
	private static final Logger badLogger$logger = LogManager.getLogger("GL11C");

	static {
		badLogger$logger.info("Loaded GL11C");
	}

	@Inject(at = @At("HEAD"), method = "glCallList(I)V")
	private static void glCallListHead(int list, CallbackInfo ci) {
		LogFileManager.enter(LogFileManager.FLAG_GL_CLEAR_HEAD);
	}

	@Inject(at = @At("RETURN"), method = "glCallList(I)V")
	private static void glCallListExit(int list, CallbackInfo ci) {
		LogFileManager.exit(LogFileManager.FLAG_GL_CLEAR_HEAD);
	}
}
