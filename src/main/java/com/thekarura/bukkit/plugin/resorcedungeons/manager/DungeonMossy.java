package com.thekarura.bukkit.plugin.resorcedungeons.manager;

import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import com.thekarura.bukkit.plugin.resorcedungeons.ResorceDungeons;
import com.thekarura.bukkit.plugin.resorcedungeons.manager.buillder.DungeonChest;

/**
 * Rcanhack構造の基本ダンジョンです。
 * 多数の変数が混合しあっています。
 * 
 * @author karura
 */
public class DungeonMossy {
	
	public static final Logger log = ResorceDungeons.log;
	private static final String logPrefix = ResorceDungeons.logPrefix;
	private static final String msgPrefix = ResorceDungeons.msgPrefix;
	
	private ResorceDungeons instance = ResorceDungeons.getInstance();
	
	@SuppressWarnings("deprecation")
	public void setDungeonMossy(Location loc){
		
		// ++ ダンジョン構成 ++
		Block block = loc.getBlock();
		//ブロックを指定
		Material d_b_outline = Material.COBBLESTONE;
		Material d_b_outline1= Material.MOSSY_COBBLESTONE;
		//区分け登録
		int distrct = Random(6, 2);
		
		//区の大きさ
		int d_width = Random(30,15);
		int d_height= Random(30,15);
		
		//高さ
		int heigth = 4;
		int roomheigth = 4;
		
		int l_5 = 0;
		int l_6 = 0;
		
		//スポナーの数
		int min_s = 2;	//最小数
		int max_s = 4;	//最大数
		
		//一つ前の通路開始始点を保存
		//
		HashMap<Integer,Integer> p_xw_x = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> p_xw_z = new HashMap<Integer,Integer>();
		
		HashMap<Integer,Integer> p_xh_x = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> p_xh_z = new HashMap<Integer,Integer>();
		
		HashMap<Integer,Integer> p_zw_x = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> p_zw_z = new HashMap<Integer,Integer>();
		
		HashMap<Integer,Integer> p_zh_x = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> p_zh_z = new HashMap<Integer,Integer>();
		
		int l_7 = 0;
		int l_8 = 0;
		
		// ++ 区画を分けます ++ //
		//縦に分けます
		for(int l_1=0 ; l_1 < distrct ; l_1++){		//x
			for(int l_2=0 ; l_2 < distrct ; l_2++){	//y
				
				// ++ 部屋の設定 ++ //
				
				//部屋の配置
				int r_x = Random(d_width  / 3, 3);
				int r_z = Random(d_height / 3, 3);
				
				//部屋の大きさ
				int ru_x = Random(d_width  - r_x - 5, 5);
				int ru_z = Random(d_height - r_z - 5, 5);
				
				//部屋の生成
				
				for (int l_y = 0; l_y < roomheigth; l_y++){
					for (int l_3=0;l_3<ru_x;l_3++){			//x
						for (int l_4=0;l_4<ru_z;l_4++){		//y
							setBlock(block ,r_x+l_3+l_5, l_y, r_z+l_4+l_6, d_b_outline, d_b_outline1);
						}
					}
				}
				
				// ++ スポナーとチェストを配置します ++ //
				
				//l_2におけるスポナーの数を決めます
				int sp_lmax = Random(max_s,min_s);
				
				for (int sp_l = 0; sp_l < sp_lmax ; sp_l++){
					
					//スポナーの配置場所を決めます
					int sp_x = Random((r_z + ru_z) - 1,r_z + 1);
					int sp_z = Random((r_x + ru_x) - 1,r_x + 1);
					
					//スポナーの中身をランダムで決めます
					int sp_r = Random(4,0);
					
					//チェストの中身を決めます(Name固定)
					block.getRelative(l_5+sp_z, 1, l_6+sp_x).
					setTypeIdAndData(Material.CHEST.getId(), (byte) 0, true);
					
					int c_r = Random(5,0);
					switch (c_r){
					case 1:
						new DungeonChest().setChest("Mossy_Life", new Location(loc.getWorld(),
						loc.getX() + l_5 + sp_z, loc.getY() + 1, loc.getZ() + l_6 + sp_x));
					break;
					case 2:
						new DungeonChest().setChest("Mossy_Food", new Location(loc.getWorld(),
						loc.getX() + l_5 + sp_z, loc.getY() + 1, loc.getZ() + l_6 + sp_x));
					break;
					case 3:
						new DungeonChest().setChest("Mossy_Weapon", new Location(loc.getWorld(),
						loc.getX() + l_5 + sp_z, loc.getY() + 1, loc.getZ() + l_6 + sp_x));
					break;
					case 4:
						new DungeonChest().setChest("Mossy_Protecto", new Location(loc.getWorld(),
						loc.getX() + l_5 + sp_z, loc.getY() + 1, loc.getZ() + l_6 + sp_x));
					break;
					default:
						new DungeonChest().setChest("Mossy_Other", new Location(loc.getWorld(),
						loc.getX() + l_5 + sp_z, loc.getY() + 1, loc.getZ() + l_6 + sp_x));
					break;
					}
					
					//スポナーの操作を行う為に設定します
					block.getRelative(l_5+sp_z, 2, l_6+sp_x).
					setTypeIdAndData(Material.MOB_SPAWNER.getId(), (byte) 0, true);
					CreatureSpawner csp = (CreatureSpawner) block.getRelative(l_5+sp_z, 2, l_6+sp_x).getState();
					
					//スポナーの中身を投入します
					EntityType type = null;
					switch (sp_r){
					case 1:
						//助さん
						type = EntityType.SKELETON;
					break;
					case 2:
						//蜘蛛
						type = EntityType.SPIDER;
					break;
					case 3:
						//クリーパー
						type = EntityType.CREEPER;
					break;
					default:
						//ゾンビ
						type = EntityType.ZOMBIE;
					break;
					}
					csp.setSpawnedType(type);
					
				}
				
				// ++ 通路の生成 ++ //
				//下側
				int p_x = Random(ru_x-1,1);
				int p_z = Random(ru_z-1,1);
				//上側
				int p_x_= Random(ru_x-1,1);
				int p_z_= Random(ru_z-1,1);
				
				//下方向
				for (int l_y = 0; l_y < heigth; l_y++){
					for (int l_3=0;l_3<d_width-(ru_x+r_x)+1;l_3++){
						for(int l_4=0;l_4<d_height-(ru_z+r_z)+1;l_4++){
							if (!(l_1 == distrct - 1)){
								setBlock(block,r_x+p_x+l_5, l_y, r_z+ru_z+l_4+l_6, d_b_outline, d_b_outline1);
								
								//一つ前の位置情報を保存
								p_xw_x.put(l_7, r_x+p_x+l_5);
								p_xw_z.put(l_8, r_z+ru_z+l_4+l_6);
								
							}
							if (!(l_2 == distrct - 1)){
								setBlock(block,r_x+ru_x+l_3+l_5, l_y, r_z+p_z+l_6, d_b_outline, d_b_outline1);
								
								//一つ前の位置情報を保存
								p_xh_x.put(l_7, r_x+ru_x+l_3+l_5);
								p_xh_z.put(l_8, r_z+p_z+l_6);
							}
						}
					}
				}
				
				//一つ前の位置情報を保存
				p_zw_x.put(l_7, r_x+p_x_+l_5);
				p_zw_z.put(l_8, l_6-1);
				
				p_zh_x.put(l_7, l_5-1);
				p_zh_z.put(l_8, r_z+p_z_+l_6);
				
				for (int l_y = 0; l_y < heigth; l_y++){
					for (int l_3=-1;l_3<r_x;l_3++){
						for (int l_4=-1;l_4<r_z;l_4++){
							if (!(l_1 == 0)){
								setBlock(block,r_x+p_x_+l_5, l_y, l_4+l_6, d_b_outline, d_b_outline1);
								
							}
							if (!(l_2 == 0)){
								setBlock(block,l_3+l_5, l_y, r_z+p_z_+l_6, d_b_outline, d_b_outline1);
								
							}
						}
					}
				}
				
				//一つ前の区画情報を使い通路を繋げます
				if (!( l_2 == 0 )){
					
					if (!( p_zh_z.get(l_8) == p_xh_z.get(l_8 - 1))){
						
						if ( p_zh_z.get(l_8) < p_xh_z.get(l_8 - 1)){
							for (int l_y = 0; l_y < heigth; l_y++){
								for (int l = 0; l < (p_xh_z.get(l_8 - 1) - p_zh_z.get(l_8) + 1) ; l++){
									setBlock(block,p_xh_x.get(l_7 - 1), l_y, p_xh_z.get(l_8 - 1) - l, d_b_outline, d_b_outline1);
								}
							}
						}
						if ( p_zh_z.get(l_8) > p_xh_z.get(l_8 - 1)){
							for (int l_y = 0; l_y < heigth; l_y++){
								for (int l = 0; l < (p_zh_z.get(l_8) - p_xh_z.get(l_8 - 1) + 1) ; l++){
									setBlock(block,p_zh_x.get(l_7) , l_y, p_zh_z.get(l_8) - l, d_b_outline, d_b_outline1);
								}
							}
						}
					}
					
				}
				
				if (!( l_1 == 0)){
					
					if (!( p_xw_x.get(l_7 - distrct) == p_zw_x.get(l_7) )){
						
						if ( p_xw_x.get(l_7 - distrct) < p_zw_x.get(l_7) ){
							for (int l_y = 0; l_y < heigth; l_y++){
								for (int l = 0; l < (p_zw_x.get(l_7)) - p_xw_x.get(l_7 - distrct) + 1 ; l++){
									setBlock(block,p_xw_x.get(l_7 - distrct) + l, l_y, p_xw_z.get(l_8 - distrct), d_b_outline, d_b_outline1);
								}
							}
						}
						if ( p_xw_x.get(l_7 - distrct) > p_zw_x.get(l_7) ){
							for (int l_y = 0; l_y < heigth; l_y++){
								for (int l = 0; l < (p_xw_x.get(l_7 - distrct) - p_zw_x.get(l_7) + 1) ; l++){
									setBlock(block,p_zw_x.get(l_7) + l, l_y, p_zw_z.get(l_8), d_b_outline, d_b_outline1);
								}
							}
						}
						
					}
					
				}
				
				l_7++; //横 多分
				l_8++; //縦
				
				l_5 = l_5 + (d_width+1);
				
			}
			
			l_5 = 0;
			l_6 = l_6 + (d_height+1);
			
		}
		
		log.info(logPrefix + " created the mossy dungeon!" + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ());
		
		return;
		
	}
	
	/**
	 * ブロック配置メソッド
	 * @param b
	 * @param x
	 * @param y
	 * @param z
	 * @param m1 丸石
	 * @param m2 苔石
	 */
	@SuppressWarnings("deprecation")
	public void setBlock(Block b ,int x ,int y,int z ,Material m1, Material m2){
		if (y == 0){
			Random run = new Random();
			boolean result;
			result = run.nextBoolean();
			Material mr = m1;
			if (result == true){
				mr = m1;
			} else {
				mr = m2;
			}
			b.getRelative(x,y,z).setTypeIdAndData(mr.getId(),(byte) 0, true);
		} else {
			b.getRelative(x,y,z).setTypeIdAndData(Material.AIR.getId(), (byte) 0, true);
		}
	}
	
	/**
	 * ランダマイズ生成メソッド
	 * @param par	上限
	 * @param par2	下限
	 * @return
	 */
	public int Random(int par,int par2){
		Random run = new Random();
		int result = run.nextInt(par-par2)+par2;
		return result;
	}
	
}
