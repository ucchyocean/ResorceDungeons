package com.thekarura.bukkit.plugin.resorcedungeons.manager;

import java.util.Random;

import org.bukkit.Location;

import com.thekarura.bukkit.plugin.resorcedungeons.ResorceDungeons;
import com.thekarura.bukkit.plugin.resorcedungeons.manager.buillder.DungeonBuilder;

/**
 * 地上に配置される小さな構造物郡です。
 * @author karura
 */
public class DungeonRuins {
	
	private ResorceDungeons instance = ResorceDungeons.getInstance();
	
	private DungeonBuilder build = new DungeonBuilder();
	
	private int trigger = 0;
	
	public DungeonRuins(ResorceDungeons resorcedungeons){
		this.instance = resorcedungeons;
	}
	
	/**
	 * 指定した廃墟を生成します。(コマンド側)
	 * @param loc	配置場所
	 * @param name	ダンジョン名
	 */
	public void setDungeonRuins(Location loc, String name){
		
		build.DungeonGenerator("Ruins\\" + name, loc);
		
	}
	
	/**
	 * 廃墟を生成します。(自動生成)
	 * @param loc
	 */
	public void setDungeonRuins(Location loc){
		
		Random run = new Random();
		
		// ++ バイオーム指定 ++ //
		switch (loc.getBlock().getBiome()){
		
		// ++ 森林関連 ++ //
		case FOREST:
		case FOREST_HILLS:
		case BIRCH_FOREST:
		//地形が不安定な所に生成しない。
		//case BIRCH_FOREST_HILLS_MOUNTAINS:
		//case BIRCH_FOREST_HILLS:
		//case BIRCH_FOREST_MOUNTAINS:
			
			switch (trigger = run.nextInt(2)){
			case 0:
				build.DungeonGenerator("Ruins\\ruins_fortress", loc);
			break;
			}
			
		break;
		
		// ++ 草原関連 ++ //
		case PLAINS:
		case SUNFLOWER_PLAINS:
		case SAVANNA:
		case SAVANNA_MOUNTAINS:
		case SAVANNA_PLATEAU:
		case SAVANNA_PLATEAU_MOUNTAINS:
			
			switch (trigger = run.nextInt(4)){
			case 0:
				build.DungeonGenerator("Ruins\\ruins_plain_villag_house", loc);
			break;
			case 1:
				build.DungeonGenerator("Ruins\\ruins_plain_stone_circle", loc);
			break;
			case 2:
				build.DungeonGenerator("Ruins\\ruins_plain_camp", loc);
			break;
			case 3:
				build.DungeonGenerator("Ruins\\ruins_fortress", loc);
			break;
			}
			
		break;
		
		// ++ 氷原関連 ++ //
		case ICE_PLAINS:
		case ICE_MOUNTAINS:
		case ICE_PLAINS_SPIKES:
			
			switch(trigger = run.nextInt(2)){
			case 0:
				build.DungeonGenerator("Ruins\\ruins_iceplain_house", loc);
			break;
			case 1:
				build.DungeonGenerator("Ruins\\ruins_iceplain_house2", loc);
			break;
			}
			
		break;
		
		// ++ 砂漠関連 ++ //
		case DESERT:
		case DESERT_HILLS:
		case DESERT_MOUNTAINS:
			
			//switch (trigger = run.nextInt(2)){
			//case 0:
				build.DungeonGenerator("Ruins\\ruins_fortress", loc);
			//break;
			//}
			
		break;
		
		//該当バイオームがない場合は生成しない。
		default: break;
		}
		
		return;
		
	}
	
}
