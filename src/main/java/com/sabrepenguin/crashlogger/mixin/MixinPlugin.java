package com.sabrepenguin.crashlogger.mixin;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {
	private boolean isLwjgl3;
	@Override
	public void onLoad(String mixinPackage) {
		String resourcePath = "org/lwjgl/opengl/GL11C.class";
		isLwjgl3 = this.getClass().getClassLoader().getResource(resourcePath) != null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		if (mixinClassName.contains("MixinGL11C"))
			return isLwjgl3;
		if (mixinClassName.contains("MixinGL11") && !mixinClassName.contains("MixinGL11C"))
			return !isLwjgl3;
		return true;
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
	}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}
}
