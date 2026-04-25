package com.sabrepenguin.crashlogger.mixin;

import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mixin(value = GlStateManager.class)
public class MixinGLManager {
	@Unique
	private static final Logger badLogger$LOGGER = LogManager.getLogger("CrashLogManager");
	@Unique
	private static final MappedByteBuffer badLogger$crashFlag;

	static {
		try (RandomAccessFile raFile = new RandomAccessFile(new File("gl_clear.bin"), "rw")) {
			raFile.setLength(1);
			badLogger$crashFlag = raFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 1);
			badLogger$LOGGER.info("Crash flag initialized. Checking previous state");
			if (badLogger$crashFlag.get(0) == (byte) 1) {
				badLogger$LOGGER.error("Previous session crashed inside GLStateManager");
				badLogger$crashFlag.put(0, (byte) 0);
				throw new RuntimeException("Please report your crash to Sabre, resetting");
			} else {
				badLogger$LOGGER.info("Previous session crash was not inside the targeted function");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Inject(at = @At("HEAD"), method = "clear(I)V")
	private static void clearHead(int mask, CallbackInfo ci) {
		badLogger$crashFlag.put(0, (byte) 1);
	}

	@Inject(at = @At("RETURN"), method = "clear(I)V")
	private static void clearEnd(int mask, CallbackInfo ci) {
		badLogger$crashFlag.put(0, (byte) 0);
	}
}
