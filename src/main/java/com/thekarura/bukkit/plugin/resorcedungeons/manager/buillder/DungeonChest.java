package com.thekarura.bukkit.plugin.resorcedungeons.manager.buillder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.thekarura.bukkit.plugin.resorcedungeons.ResorceDungeons;

public class DungeonChest {
	
	public static final Logger log = ResorceDungeons.log;
	private static final String logPrefix = ResorceDungeons.logPrefix;
	private static final String msgPrefix = ResorceDungeons.msgPrefix;
	
	private static ResorceDungeons instance = ResorceDungeons.getInstance();
	
	private int strint = 1;
	
	public void setChest(String chestid, Location loc){
		
		String chestdir = instance.getConfigs().getChestfile();
		
		try {
			
			Random run = new Random();
			File file = new File(chestdir+chestid+".rd");
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			if (checkfile(file)) {
				
				String str;
				boolean checkfile = true;
				
				Chest chest = (Chest) loc.getBlock().getState();
				Inventory inv = chest.getBlockInventory();
				inv.clear();
				
				int maxinvslot = inv.getSize();
				int maxint = 0;
				
				HashMap<Integer,Enchantment> enchantment = new HashMap<Integer,Enchantment>();
				HashMap<Integer,Integer> level = new HashMap<Integer,Integer>();
				
				while ((str = br.readLine()) != null ) {
					
					//ファイル検出用の処理
					if ( checkfile == true ){
						if (str.equalsIgnoreCase("Chest:")){ 
							log.info(logPrefix+"Chestファイルを検出しました。");
							
							checkfile = false;
						}
						
					} else {
						
						//開始命令
						if (str.equalsIgnoreCase("item:")){
							
							//初期設定
							Material material = Material.STONE;
							int amount = 1;
							short damage = 0;
							String name = "unknow name";
							ArrayList<String> lore = new ArrayList<String>();
							int percent = 100 ;
							
							//アイテムの種類を設定します。
							str = br.readLine(); strint++;
							material = Material.getMaterial(str);
							
							//アイテムの量を設定します。
							str = br.readLine(); strint++;
							amount = Integer.valueOf(str);
							
							//ダメージ値を設定します。
							str = br.readLine(); strint++;
							damage = Short.valueOf(str);
							
							ItemStack item = new ItemStack(material ,amount ,damage);
							ItemMeta itemm = item.getItemMeta();
							
							//閉じる命令
							while (!(str.equals(":item"))){
								
								//アイテム名を追加させます。
								if (str.equalsIgnoreCase("-Name")){
									str = br.readLine(); strint++;
									name = str;
									itemm.setDisplayName(ColorFormat(name));
								}
								//アイテム説明を追加させます。
								if (str.equalsIgnoreCase("-lore")){
									str = br.readLine(); strint++;
									while (!(str.equalsIgnoreCase(":lore"))){
										str = br.readLine(); strint++;
										if (!(str.equalsIgnoreCase(":lore"))){
											lore.add(ColorFormat(str));
										}
									}
									itemm.setLore(lore);
								}
								//エンチャントを追加させます。
								if (str.equalsIgnoreCase("-Enchant")){
									int l = 0;
									while (!(str.equalsIgnoreCase(":Enchant"))){
										str = br.readLine(); strint++;
										if (!(str.equalsIgnoreCase(":Enchant"))){
											enchantment.put(l, Enchantment.getByName(str));
											str = br.readLine(); strint++;
											level.put(l, Integer.valueOf(str));
										}
										l++;
									}
								}
								//配置される確率
								if (str.equalsIgnoreCase("-Percent")){
									str = br.readLine(); strint++;
									percent = Integer.valueOf(str);
								}
								//配置命令
								if (str.equalsIgnoreCase("-Put")){
									
									boolean Put = setChestInventory(run, percent);
									
									if (Put){
										if (maxint == maxinvslot){
											br.close();
											return;
										}
										item.setItemMeta(itemm);
										for (int l = 0;enchantment.get(l) != null;l++){
											item.addUnsafeEnchantment(enchantment.get(l), level.get(l));
										}
										inv.addItem(item);
										maxint++;
										System.out.println("-配置完了");
									}
									
								}
								str = br.readLine(); strint++;
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
				return;
			}
			
		} catch (Exception e) {
			log.info(logPrefix+"[error]"+ e.getMessage() + "line " + strint);
			
			return;
		}
		
	}
	
	private boolean setChestInventory(Random run,int percent) {
		
		BitSet bit = new BitSet();
		int i;
		int max = 100;
		int result = run.nextInt(max);
		
		for (i = 0 ; i < max ; i++){
			if ( i > max - percent){
				bit.set(i);
			}
		}
		
		boolean result_ = bit.get(result);
		return result_;
	}
	
	/**
	 * カラーを追加させます。§でも良かったのですが&の方が馴染み深いので；
	 * @param mes
	 * @return
	 */
	private String ColorFormat(String mes){
		return ChatColor.translateAlternateColorCodes('&', mes);
	}
	
	public boolean checkfile(File file){
		if (file.exists()){
			if (file.isFile() && file.canRead()){
			return true;
			}
		}
	return false;
	}
	
}
