/**
 * ResorceDungeons
 * TestDungeonを生成させます。
 * 主に生成関連の試験的な処理をします
 * useLicense : 
 * Auther the_karura
 **/

package com.thekarura.bukkit.plugin.resorcedungeons.manager;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.thekarura.bukkit.plugin.resorcedungeons.ResorceDungeons;
import com.thekarura.bukkit.plugin.resorcedungeons.manager.buillder.DungeonBuilder;

public class DungeonTest {
	
	// instance
	private ResorceDungeons instanse = ResorceDungeons.getInstance();
	
	public DungeonTest(ResorceDungeons resorcedungeons){
		this.instanse = resorcedungeons;
	}
	
	public void createDungeonTest(Location loc){
		
		Block block = loc.getBlock();
		
		new DungeonBuilder().DungeonGenerator("Test\\Test",loc);
		
		return;
		
	}
}
