/**
 * ResorceDungeons
 * TestDungeonを生成させます。
 * 主に生成関連の試験的な処理をします
 * useLicense : 
 * Auther the_karura
 **/

package com.thekarura.bukkit.plugin.resorcedungons.manager;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.thekarura.bukkit.plugin.resorcedungons.ResorceDungeons;
import com.thekarura.bukkit.plugin.resorcedungons.manager.buillder.DungeonBuilder;

public class DungeonTest {
	
	// instance
	private static ResorceDungeons instanse = ResorceDungeons.getInstance();
	
	//メイン処理
	
	public static void createDungeonTest(Location loc){
		
		String dungeonsdir = instanse.getConfigs().getDungeondir();
		Block block = loc.getBlock();
		
		System.out.println(dungeonsdir+"/Test/Test.rd");
		
		File test_test_dungeon = new File(dungeonsdir+"/Test/Test.rd");
		
		new DungeonBuilder().DungeonGenerator(test_test_dungeon,block,loc);
		
		return;
		
	}
}
