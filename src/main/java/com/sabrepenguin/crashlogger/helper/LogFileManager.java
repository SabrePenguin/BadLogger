package com.sabrepenguin.crashlogger.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class LogFileManager {
	private static final MappedByteBuffer crashBuffer;
	private static final int MAX_FUNCS = 2;
	public static final String[] MESSAGES = {
			"GlStateManager has crashed",
			"GL11.glCallList has crashed"
	};
	public static final int FLAG_GL_CLEAR = 0;
	public static final int FLAG_GL_CLEAR_HEAD = 1;

	static {
		try (RandomAccessFile raFile = new RandomAccessFile(new File("crash_states.bin"), "rw")) {
			raFile.setLength(MAX_FUNCS);
			crashBuffer = raFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, MAX_FUNCS);
			File output = new File("bad_logger.log");
			boolean crashFound = false;
			for (int i = 0; i < MAX_FUNCS; i++) {
				if (crashBuffer.get(i) == (byte) 1) {
					if (!crashFound) {
						if (output.exists())
							output.delete();
						appendToLog(output, String.valueOf(System.currentTimeMillis()));
					}
					crashFound = true;
					appendToLog(output, MESSAGES[i]);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void appendToLog(File output, String message) {
		try (FileWriter writer = new FileWriter(output, true)) {
			writer.append(message).append("\n");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void enter(int index) {
		if (crashBuffer != null)
			crashBuffer.put(index, (byte) 1);
	}

	public static void exit(int index) {
		if (crashBuffer != null)
			crashBuffer.put(index, (byte) 0);
	}
}
