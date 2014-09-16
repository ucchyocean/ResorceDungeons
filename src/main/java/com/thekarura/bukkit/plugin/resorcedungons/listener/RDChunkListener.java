package com.thekarura.bukkit.plugin.resorcedungons.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

import com.thekarura.bukkit.plugin.resorcedungons.ResorceDungeons;

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
	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true)
	public void RDChunkPopulateEvent(ChunkPopulateEvent event){
		
		/* 
		 * 拡張性を持たせるためここは今後変更を加えていきます
		 */
		
		//ダンジョンワールドのみ有効化
		if (event.getWorld() == Bukkit.getWorld(instance.getConfigs().getDungeonWorld())){
			
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
				
				//地面の高さを習得
				int y = getGround(loc);
				
				block.getRelative(0, y, 0).setTypeIdAndData(Material.COMMAND.getId(),(byte) 0, false);
				block.getRelative(0, y + 1, 0).setTypeIdAndData(Material.SIGN_POST.getId(),(byte) 0, false);
				System.out.println(block.getLocation(loc).add(0, y, 0).getBlock());
				
				//ダンジョンジャングルを登録
				Sign sign = (Sign) block.getRelative(0, y + 1, 0).getState();
				sign.setLine(0, "[RDungeons]");
				sign.setLine(1, "JungleCave");
				sign.update();
				
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
