package com.thekarura.bukkit.plugin.resorcedungeons.manager;

import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.thekarura.bukkit.plugin.resorcedungeons.ResorceDungeons;

public class DungeonMossy {
	
	public static final Logger log = ResorceDungeons.log;
	private static final String logPrefix = ResorceDungeons.logPrefix;
	private static final String msgPrefix = ResorceDungeons.msgPrefix;
	
	private ResorceDungeons instance = ResorceDungeons.getInstance();
	
	@SuppressWarnings("deprecation")
	public void setDungeonMossy(Location loc){
		
		// ** ダンジョン構成 **
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
		
		// ** 区画を分けます。 **
		//縦に分けます
		for(int l_1=0 ; l_1 < distrct ; l_1++){		//x
			for(int l_2=0 ; l_2 < distrct ; l_2++){	//y
				
				// ** 部屋の設定 **
				
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
				
				// ** 通路の生成 ** //
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
								
								p_xw_x.put(l_7, r_x+p_x+l_5);
								p_xw_z.put(l_8, r_z+ru_z+l_4+l_6);
								
							}
							if (!(l_2 == distrct - 1)){
								setBlock(block,r_x+ru_x+l_3+l_5, l_y, r_z+p_z+l_6, d_b_outline, d_b_outline1);
								
								p_xh_x.put(l_7, r_x+ru_x+l_3+l_5);
								p_xh_z.put(l_8, r_z+p_z+l_6);
							}
						}
					}
				}
				
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
		
		log.info(logPrefix + " ダンジョンを生成しました。");
		Bukkit.broadcastMessage(msgPrefix + ChatColor.BLUE + ChatColor.GREEN + " ダンジョンを生成しました。");
		
		return;
		
	}
	
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
