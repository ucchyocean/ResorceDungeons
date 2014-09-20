package com.thekarura.bukkit.plugin.resorcedungeons.listener;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

import com.thekarura.bukkit.plugin.resorcedungeons.ResorceDungeons;
import com.thekarura.bukkit.plugin.resorcedungeons.manager.DungeonJungleCave;

public class RDChunkListener implements Listener {
	
	private ResorceDungeons instance = ResorceDungeons.getInstance();
	
	public RDChunkListener(ResorceDungeons resorcedungeons){
		this.instance = resorcedungeons;
	}
	
	/**
	 * チャンク生成時に確立によりダンジョンジェネレーターが配置されるかどうか
	 * 
	 * @param event
	 */
	@EventHandler(ignoreCancelled = true)
	public void RDChunkPopulateEvent(ChunkPopulateEvent event){
		
		/* 
		 * 拡張性を持たせるためここは今後変更を加えていきます
		 */
		
		//ダンジョンワールドのみ有効化
		if (event.getWorld().getName().equals(instance.getConfigs().getDungeonWorld())){
			
			//チャンクの0,0,0座標を習得
			Location loc = event.getChunk().getBlock(0, 0, 0).getLocation();
			
			//ブロックの座標をセット
			Block block = loc.getBlock();
			
			
			
			//バイオーム検索をします。
			switch (loc.getBlock().getBiome()){
			
			case BEACH:
				
				break;
			case DESERT:
				
				break;
			case DESERT_HILLS:
				
				break;
			case EXTREME_HILLS:
				
				break;
			case FOREST:
				
				break;
			case FOREST_HILLS:
				
				break;
			case FROZEN_OCEAN:
				
				break;
			case FROZEN_RIVER:
				
				break;
			case HELL:
				
				break;
			case ICE_MOUNTAINS:
				
				break;
			case ICE_PLAINS:
				
				break;
				
			// ## ジャングルバイオーム ##
			case JUNGLE:
			case JUNGLE_HILLS:
				
				//ジャングル地下ダンジョン
				new DungeonJungleCave().setJungleCave(loc);
				
				break;
			case MUSHROOM_ISLAND:
				
				break;
			case MUSHROOM_SHORE:
				
				break;
			case OCEAN:
				
				break;
			case PLAINS:
				
				break;
			case RIVER:
				
				break;
			case SKY:
				
				break;
			case SMALL_MOUNTAINS:
				
				break;
			case SWAMPLAND:
				
				break;
			case TAIGA:
				
				break;
			case TAIGA_HILLS:
				
				break;
			default:
				
				break;
				
			}
		}
	}
	
	public int getGround(Location loc){
		
		loc = loc.getWorld().getHighestBlockAt(loc).getLocation();
		
		int ground = loc.getBlockY();
		
		for (int y = loc.getBlockY(); y != 0; y--) {
			
			loc.setY(y);
			
			if (loc.getBlock().getLightFromSky() >= 8
			&& !loc.getBlock().getType().isSolid()) {
				
				ground = y;
				
			}
		}
		
		return ground;
	}
	
}
