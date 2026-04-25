package com.sabrepenguin.crashlogger.core;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.Name("CrashLogger")
@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.TransformerExclusions("com.sabrepenguin.crashlogger.core")
@IFMLLoadingPlugin.SortingIndex(1500)
public class MixinLoader implements IEarlyMixinLoader, IFMLLoadingPlugin {
	@Override
	public List<String> getMixinConfigs() {
		return Arrays.asList("mixins.crashlogger.json");
	}

	@Override
	public String[] getASMTransformerClass() {
		return new String[0];
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Nullable
	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {

	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
