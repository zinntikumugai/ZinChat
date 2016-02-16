/**
 *
 */
package com.github.zinntikumugai.zinchat;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author zinntikumugai
 * @Licence GPL v3.0
 */
public class ZinChat extends JavaPlugin {

	@Override
	public void onEnable() {

		getServer().getPluginManager().registerEvents(new ZinChatListener(),(this));

	}
}
