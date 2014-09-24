/**
 * ResorceDungeons
 * TestDungeonを生成させます。
 * 主に生成関連の試験的な処理をします
 * useLicense : 
 * Auther the_karura
 **/

package com.thekarura.bukkit.plugin.resorcedungeons.manager;

import java.util.logging.Logger;

import org.bukkit.Location;

import com.thekarura.bukkit.plugin.resorcedungeons.ResorceDungeons;
import com.thekarura.bukkit.plugin.resorcedungeons.manager.buillder.DungeonBuilder;

public class DungeonFloating {
	
	public static final Logger log = ResorceDungeons.log;
	private static final String logPrefix = ResorceDungeons.logPrefix;
	private static final String msgPrefix = ResorceDungeons.msgPrefix;
	
	//メイン処理
	
	public void createDungeonFloating(Location loc){
		
		new DungeonBuilder().DungeonGenerator("Floating\\Floating",loc);
		
		return;
		
	}
	
}