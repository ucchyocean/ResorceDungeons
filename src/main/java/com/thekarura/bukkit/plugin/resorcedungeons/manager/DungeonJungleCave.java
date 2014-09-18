package com.thekarura.bukkit.plugin.resorcedungeons.manager;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;

/**
 * 
 * ジャングルバイオーム地下を書き換える自然系のダンジョンです。
 * ver 1000 初期(地面、天井の変更)
 * 
 * 
 * @author karura
 *
 */
public class DungeonJungleCave {
	
	@SuppressWarnings("deprecation")
	public void setJungleCave(Location loc){
		
		Random random = new Random();
		Chunk chunk = loc.getChunk();
		
		//チャンク内の全座標を習得
		for (int cx = 0 ; cx < 16 ; cx++){
			for (int cz = 0 ; cz < 16 ; cz++){
				
				//地形を変更
				replaceBlock(loc, chunk, random, cx, cz);
				
				//天井の処理
				int topy = getTop(loc ,chunk ,cx ,cz);
				if (topy == 0){ continue; } //座標0であれば処理をせず次へ渡す
				for (int y = 0; y < random.nextInt(10-1)+1; y++){
					if (!chunk.getBlock(cx, topy - y, cz).getType().isSolid()){
						chunk.getBlock(cx, topy - y, cz).setTypeIdAndData(Material.VINE.getId(), (byte) 15, false);
					}
				}
				
				//床の処理
				int flooty = getFloot(loc, chunk, cx, cz);
				if (flooty == 0){ continue; } //座標0であれば処理をせず次へ渡す
				int blockid = random.nextInt(2);
				
				chunk.getBlock(cx, flooty, cz).setTypeIdAndData(Material.GRASS.getId(),(byte) 0,false);
				
			}
		}
		
	}
	
	/**
	 * 指定した地面の洞窟天井を返す (ない場合は0を返す)
	 * @param loc
	 * @param cx 一チャンクのx座標
	 * @param cz 一チャンクのz座標
	 * @return 洞窟の天井位置
	 */
	public int getTop(Location loc ,Chunk chunk ,int cx ,int cz){
		
		/*
		 * 条件:
		 * 一チャンク内にあるジャングルバイオームの洞窟内天井が石などか
		 * そしてその下に空気が存在するかどうか
		 */
		
		//取得したxとz座標を習得
		int x = loc.getBlockX() + cx;
		int z = loc.getBlockZ() + cz;
		
		int top = 0;
		
		//下へ向かって天井を探します
		for (int y = chunk.getWorld().getMaxHeight() ; y != 0 ; y--){
			
			//バイオームを指定します。
			switch (chunk.getBlock(x, y, z).getBiome()){
			
			case JUNGLE:
			case JUNGLE_HILLS:
				
				switch (chunk.getBlock(x, y, z).getType()){
				
				//洞窟内自然生成物
				case STONE:
				case DIRT:
				case GRAVEL:
				case CLAY:
					
				//自然生成人工物
				case BRICK:
				case SMOOTH_BRICK:
				case WOOD:
				case FENCE:
					
					//一つ下が空気がどうか
					if (chunk.getBlock(x, y - 1, z).getType() == Material.AIR){
						
						top = y - 1;
						
					}
					
				break;
				default:break;
				}
			
			break;
			default:break;
			}
			
		}
		
		return top;
		
	}
	
	public int getFloot(Location loc ,Chunk chunk ,int cx ,int cz){
		
		//取得したxとz座標を習得
		int x = loc.getBlockX() + cx;
		int z = loc.getBlockZ() + cz;
		
		int floot = 0;
		
		//下へ向かって洞窟の床を探します。
		for (int y = chunk.getWorld().getMaxHeight() ; y != 0 ; y--){
			
			//バイオームを指定します。
			if(chunk.getBlock(x, y, z).getBiome() == Biome.JUNGLE
			|| chunk.getBlock(x, y, z).getBiome() == Biome.JUNGLE_HILLS){
				
				switch (chunk.getBlock(x, y, z).getType()){
				
				//洞窟内自然生成物
				case STONE:
				case DIRT:
				case GRAVEL:
				case CLAY:
				
				//鉱石関連
				case COAL_ORE:
				case IRON_ORE:
				case GOLD_ORE:
				case REDSTONE_ORE:
					
					//一つ上が空気がどうか
					if (chunk.getBlock(x, y + 1, z).getType() == Material.AIR){
						
						floot = y;
						
					}
					
				break;
				default:break;
				}
			}
			
		}
		
	return floot;
	
	}
	
	/**
	 * 洞窟の地層を変更します。
	 * @param loc
	 * @param chunk
	 * @param random
	 * @param cx
	 * @param cz
	 */
	@SuppressWarnings("deprecation")
	public void replaceBlock(Location loc ,Chunk chunk , Random random,int cx ,int cz){
		
		Random raundom = random;
		
		int x = cx;
		int z = cz;
		
		for (int y = chunk.getWorld().getMaxHeight() ; y != 0 ; y--){
			
			//バイオームを指定します。
			switch (chunk.getBlock(x, y, z).getBiome()){
			
			case JUNGLE:
			case JUNGLE_HILLS:
				
				switch(chunk.getBlock(x, y, z).getType()){
				case STONE:
				case DIRT:
				case CLAY:
					
					int blockid = raundom.nextInt(3);
					
					
					Material material = Material.AIR;
					
					switch (blockid){
					
					case 0:
						material = Material.STONE;
					break;
					case 1:
						material = Material.DIRT;
					break;
					case 2:
						material = Material.CLAY;
					break;
					}
					
					chunk.getBlock(x, y, z).setTypeIdAndData(material.getId(),(byte) 0, false);
				break;
				default:break;
				}
				
			default: break;
			}
			
		}
		
	}
	
}
