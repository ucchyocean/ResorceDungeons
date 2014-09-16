/**
 * ResorceDungeons
 * TestDungeonを生成させます。
 * 主に生成関連の試験的な処理をします
 * useLicense : 
 * Auther the_karura
 **/

package com.thekarura.bukkit.plugin.resorcedungons.manager;

import java.io.File;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.thekarura.bukkit.plugin.resorcedungons.ResorceDungeons;
import com.thekarura.bukkit.plugin.resorcedungons.manager.buillder.DungeonBuilder;

public class DungeonFloating {
	
	public static final Logger log = ResorceDungeons.log;
	private static final String logPrefix = ResorceDungeons.logPrefix;
	private static final String msgPrefix = ResorceDungeons.msgPrefix;
	
	//メイン処理
	
	@SuppressWarnings("deprecation")
	public static void createDungeonFloating(Location loc){
		
		Block block = loc.getBlock();
		Random r = new Random();
		File test_test_dungeon = new File("plugins/ResorceDungeons/Dungeons/Floating/Village_1.rd");
		
		new DungeonBuilder().DungeonGenerator(test_test_dungeon,block,loc);
		
		return;
		
	}
	
}