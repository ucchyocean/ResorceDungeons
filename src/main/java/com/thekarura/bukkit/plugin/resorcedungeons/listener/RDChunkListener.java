package com.thekarura.bukkit.plugin.resorcedungeons.listener;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

import com.thekarura.bukkit.plugin.resorcedungeons.ConfigurationManager;
import com.thekarura.bukkit.plugin.resorcedungeons.ResorceDungeons;
import com.thekarura.bukkit.plugin.resorcedungeons.manager.DungeonJungleCave;

public class RDChunkListener implements Listener {
	
	private ResorceDungeons instance = ResorceDungeons.getInstance();
	
	public Random random = new Random();
	public int percent;
	
	//生成IDを指定します。
	public String plID = "[RDungeons]";	//pluginID
	public String duID = "Dungeon";		//dungeonID
	
	public RDChunkListener(ResorceDungeons resorcedungeons){
		this.instance = resorcedungeons;
	}
	
	/**
	 * チャンク生成時に確立によりダンジョンジェネレーターが配置されるかどうか
	 * 
	 * @param event ChunkPopulateEvent チャンク生成イベント
	 */
	@EventHandler(ignoreCancelled = true)
	public void RDChunkPopulateEvent(ChunkPopulateEvent event){
		
		/* 
		 * 拡張性を持たせるためここは今後変更を加えていきます
		 */
		
		FileConfiguration config = instance.getConfig();
		ConfigurationManager config_ = instance.getConfigs();
		
		percent = random.nextInt(100);
		
		//ダンジョンワールドのみ有効化
		if (event.getWorld().getName().equals(config_.getDungeonWorld())){
			
			//チャンクの0,0,0座標を習得
			Location loc = event.getChunk().getBlock(0, 0, 0).getLocation();
			
			// ++ JungleCaveDungeon ++ //
			if (config_.getENABLE_DUNGEON_JUNGLECAVE()){
				switch (loc.getBlock().getBiome()){
				case JUNGLE:
				case JUNGLE_EDGE:
				case JUNGLE_EDGE_MOUNTAINS:
				case JUNGLE_HILLS:
				case JUNGLE_MOUNTAINS:
					new DungeonJungleCave().setJungleCave(loc);
				break;
				default: break;
				}
			}
			
			// ++ MossyDungeon ++ //
			dungeonMossy(loc);
			
			// ++ RuinDungeon ++ //
			
		}
	}
	
	/**
	 * 地上の高さを検索します。
	 * @param loc 探す座標
	 * @return 地上のy座標を返します
	 */
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
	
	/**
	 * MossyDungeonの生成条件
	 * @param loc 生成座標
	 */
	private void dungeonMossy(Location loc){
		
		FileConfiguration config = instance.getConfig();
		ConfigurationManager config_ = instance.getConfigs();
		
		//コンフィグから生成が有効か確認
		if (config_.getENABLE_DUNGEON_MOSSY()){
			
			if (percent < config_.getDUNGEON_GENERATE_PERCENT_MOSSY()){
				
				//チャンク中央の座標を習得
				int x = loc.getBlockX() + 8;
				int z = loc.getBlockZ() + 8;
				
				Block block = loc.getBlock();
				
				//地下の限界高度を習得
				int ug_max = config_.getDUNGEON_GENERATE_UNDERGROUND_HIGTH();
				
				int y = random.nextInt(ug_max - 5) + 5;
				
				if (block.getRelative(x, y, z).getType() == Material.STONE
				&& block.getRelative(x, y + 4, z).getType() == Material.STONE) y = 0;
				
				if (y != 0){
				
					//ブロックを配置します。
					block.getRelative(x , y, z).setType(Material.COMMAND);
					block.getRelative(x , y + 1, z).setType(Material.SIGN_POST);
					Sign sign = (Sign) block.getRelative(x, y + 1, z).getState();
					
					//看板に記入します
					sign.setLine(0, plID); sign.setLine(1, duID);
					sign.setLine(2, "Mossy"); sign.update();
					
				}
				
			}
			
		}
		
	}
	
	/**
	 * RuinsDungeonの生成条件
	 * @param loc 生成座標
	 */
	public void dungeonruins(Location loc){
		
		FileConfiguration config = instance.getConfig();
		ConfigurationManager config_ = instance.getConfigs();
		
		if (config_.getENABLE_DUNGEON_RUINS()){
			
			if (percent < config_.getDUNGEON_GENERATE_PERCENT_RUINS()){
				
				//チャンク中央の座標を習得
				int x = loc.getBlockX() + 8;
				int z = loc.getBlockZ() + 8;
				
				//中央へ設定
				Location loc_ = new Location(loc.getWorld() , x, 0, z);
				
				//地表高さを習得
				int y = getGround(loc_);
				
				Block block = loc_.getBlock();
				
				//ブロックを配置します。
				block.getRelative(x, y, z).setType(Material.COMMAND);
				block.getRelative(x, y + 1, z).setType(Material.SIGN_POST);
				Sign sign = (Sign) block.getRelative(x, y, z).getState();
				
				//看板を記入します。
				sign.setLine(0, plID); sign.setLine(1, duID);
				sign.setLine(2, "Ruins"); sign.update();
				
			}
			
		}
		
	}
	
}
