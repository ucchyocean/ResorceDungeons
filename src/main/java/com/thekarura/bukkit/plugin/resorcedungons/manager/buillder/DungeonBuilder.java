/**
e * ファイル読み込みから自動生成までを管理
 * 
 * @author karura
 **/

package com.thekarura.bukkit.plugin.resorcedungons.manager.buillder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import com.thekarura.bukkit.plugin.resorcedungons.ResorceDungeons;

public class DungeonBuilder {
	
	public static final Logger log = ResorceDungeons.log;
	private static final String logPrefix = ResorceDungeons.logPrefix;
	private static final String msgPrefix = ResorceDungeons.msgPrefix;
	
	private ResorceDungeons instance = ResorceDungeons.getInstance();
	
	@SuppressWarnings("deprecation")
	public void DungeonGenerator(File file,Block block,Location loc){
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			Random r = new Random();
			
			if (checkfile(file)) {
				
				String str;
				
				int strint=1;
				boolean file_tri_Dungeon = true;
				Material load_block = Material.AIR;
				int load_posx = 0;
				int load_posy = 0;
				int load_posz = 0;
				byte load_matadate = 0;
				boolean load_Physics = true;
				
				while ((str = br.readLine()) != null ) {
					
					//System.out.println(logPrefix+strint+" : "+str);
					
					//Dungeon:を検出させます。検出後はfalseに
					if ( file_tri_Dungeon == true ) {
						
						if (str.equalsIgnoreCase("Dungeon:")){ 
							log.info(logPrefix+"Dungeonファイルを検出しました。");
							file_tri_Dungeon = false;
						}
						
					} else {
						
						//ブロックの習得命令
						if (str.equalsIgnoreCase("BlockID:")) {
							str = br.readLine(); strint++;
							if (errorCheck(str, br, strint)){errorMessage(0,"Material"); return;}
							load_block = Material.getMaterial(str);
						}
						
						//座標習得命令
						if (str.equalsIgnoreCase("pos:")) {
							str = br.readLine(); strint++;
							if (errorCheck(str, br, strint)){errorMessage(0,"x座標"); return;}
							load_posx = Integer.valueOf(str);
							str = br.readLine(); strint++;
							if (errorCheck(str, br, strint)){errorMessage(0,"y座標"); return;}
							load_posy = Integer.valueOf(str);
							str = br.readLine(); strint++;
							if (errorCheck(str, br, strint)){errorMessage(0,"z座標"); return;}
							load_posz = Integer.valueOf(str);
						}
						
						//ダメージ値を付属させる命令
						if (str.equalsIgnoreCase("Metadate:")) {
							str = br.readLine(); strint++;
							if (errorCheck(str, br, strint)){errorMessage(0,"Metadate"); return;}
							load_matadate = Byte.valueOf(str);
						}
						
						//落下適応命令
						if (str.equalsIgnoreCase("Physics:")) {
							str = br.readLine(); strint++;
							if (errorCheck(str, br, strint)){errorMessage(0,"true/false"); return;}
							load_Physics = Boolean.getBoolean(str);
							
						}
						
						//全ての設定を実行命令
						if (str.equalsIgnoreCase("Placement:")) {
							str = br.readLine(); strint++;
							if (errorCheck(str, br, strint)){errorMessage(1,"-[パラメーター]"); return;}
							//標準的なパラメーターです。ブロックを起きます
							if (str.equalsIgnoreCase("-Block")) {
								block.getRelative(load_posx, load_posy, load_posz).
								setTypeIdAndData(load_block.getId(), load_matadate, load_Physics);
							}
							//mobを配置します
							if (str.equalsIgnoreCase("-Entity")) {
								str = br.readLine(); strint++;
								new DungeonMob().setMob(str,load_posx,load_posy,load_posz,loc);
							}
							//MobSpawnerを配置します。
							if (str.equalsIgnoreCase("-MobSpawner")){
								CreatureSpawner spawner = (CreatureSpawner) block.getState();
								str = br.readLine(); strint++;
								spawner.setSpawnedType(EntityType.valueOf(str));
								if (errorCheck(str, br, strint)){errorMessage(1,"-[パラメーター]"); return;}
								block.getRelative(load_posx, load_posy, load_posz).
								setTypeIdAndData(Material.MOB_SPAWNER.getId(),(byte) 0, load_Physics);
							}
							//チェストの配置をします。
							if (str.equalsIgnoreCase("-Chest")) {
								str = br.readLine(); strint++;
								block.getRelative(load_posx, load_posy, load_posz).
								setTypeIdAndData(Material.CHEST.getId(),(byte) 0, load_Physics);
								new DungeonChest().setChest(str, load_posz, load_posz, load_posz, loc);
							}
							//鉱石をランダムで配置します。
							if (str.equalsIgnoreCase("-Ore")) {
								setOre(str,br,r);
							}
						}
						
					}
					
					strint++; //行読み込み
					
				}
				
				br.close();
				
			} else {
				br.close();
				//ファイル読み込みに失敗した場合
				log.info(logPrefix + "§4 Error : FileNotFound. (ファイルが存在しません)");
				Bukkit.broadcastMessage(msgPrefix + "§4 Error : FileNotFound");
				return;
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e){
			System.out.println(e);
		}
		
		Bukkit.broadcastMessage(msgPrefix + ChatColor.BLUE + ChatColor.GREEN + " ダンジョンを生成しました。");
		
	}
	
	private boolean errorCheck(String str, BufferedReader br,int strint) throws IOException{
		if (str.length() == 0) {
			Bukkit.broadcastMessage(msgPrefix+ChatColor.RED+"Error : File Could not load.");
			log.info(logPrefix+strint+"行目にてエラーが発生しました");
			br.close();
			return true;
		}
		return false;
	}
	
	/**
	 * 読み込み時のエラーメッセージを扱います
	 * @param error
	 * 0. NotFound
	 * 1. NotParameter
	 * 2. IllegalArgument
	 * @param string
	 */
	private static void errorMessage(int error, String string){
		
		//NotFound : 何も書かれていない場合のエラーです。
		if (error == 0) {
			log.info(logPrefix+string+"は該当行に記述する必要があります");
			return;
		}
		
		//NotParameter : 何も書かれていないパラメーターが検出された場合のエラーです。
		if (error == 1){
			log.info(logPrefix+string+"は該当行に記述する必要があるパラメーターです");
			return;
		}
		
		//IllegalArgument 異常な数値を検出された場合のエラーです
		if (error == 2) {
			log.info(logPrefix+string+"は不正な因数です");
			return;
		}
		
	}
	
	public boolean checkfile(File file){
		if (file.exists()){
			if (file.isFile() && file.canRead()){
			return true;
			}
		}
	return false;
	}
	
	private void setOre(String str ,BufferedReader br ,Random r) throws IOException{
		int loop=0;
		while(str.equalsIgnoreCase("")||(loop == 100)){
			str = br.readLine();
			Material m = Material.getMaterial(str);
			loop++;
		}
	}
	
}
