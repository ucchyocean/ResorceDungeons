package com.thekarura.bukkit.plugin.resorcedungeons.manager.buillder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import com.thekarura.bukkit.plugin.resorcedungeons.ResorceDungeons;

public class DungeonMob {
	
	public static final Logger log = ResorceDungeons.log;
	private static final String logPrefix = ResorceDungeons.logPrefix;
	private static final String msgPrefix = ResorceDungeons.msgPrefix;
	
	// instance
	private static ResorceDungeons instance = ResorceDungeons.getInstance();
	
	private static LivingEntity entity = null;
	private static Creature mob = null;
	
	public void setMob(String mobname, int x, int y, int z, Location loc){
		
		String mobfile = instance.getConfigs().getMobsfile();
		
		System.out.println("DungeonMob.setMob 移動確認"+mobfile+mobname+".rd");
		
		try {
			
			File file = new File(mobfile+mobname+".rd");
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			if (checkfile(file)) {
				
				String str;
				int strint=1;
				boolean checkfile = true;
				EntityType load_mobtype = EntityType.ZOMBIE;
				boolean load_visible = false;
				
				int load_MaxHealth = 10;
				int load_Health = 10;
				
				Material load_hand = Material.AIR;
				Material load_helmet = Material.AIR;
				Material load_chestplate = Material.AIR;
				Material load_leggings = Material.AIR;
				Material load_boots = Material.AIR;
				
				boolean load_remove = true;
				
				while ((str = br.readLine()) != null ) {
					
					//ファイル検出用の処理
					if ( checkfile == true ){
						
						if (str.equalsIgnoreCase("Mob:")){ 
							log.info(logPrefix+"Mobファイルを検出しました。");
							str = br.readLine(); strint++;
							load_mobtype = EntityType.valueOf(str);
							if (str.equals("SLIME") || str.equals("MAGMA_CUBE")){
								entity = (LivingEntity)
								loc.getWorld().spawnEntity(loc.getBlock().
								getRelative(x, y, z).getLocation(), load_mobtype);
								setSlime(br,str,strint);
								return;
							} else {
								mob = (Creature)
								loc.getWorld().spawnEntity(loc.getBlock().
								getRelative(x, y, z).getLocation(), load_mobtype);
							}
							checkfile = false;
						}
						
					} else {
						
						//固有設定
						setNbtags(br,str,strint);
						
						//エフェクト効果を設定する命令。
						if (str.equalsIgnoreCase("Effect:")){
							//TODO 未実装の処理
						}
						
						//名前を指定する命令。
						if (str.equalsIgnoreCase("Name:")) {
							str = br.readLine(); strint++;
							mob.setCustomName(str);
							//常に表示する命令。
							if (str.equalsIgnoreCase("-Visible")) {
								str = br.readLine(); strint++;
								load_visible = Boolean.getBoolean(str);
								mob.setCustomNameVisible(load_visible);
							}
						}
						
						//HPの上限を設定する命令。
						if (str.equalsIgnoreCase("Health:")) {
							str = br.readLine(); strint++;
							load_MaxHealth = Integer.valueOf(str);
							str = br.readLine(); strint++;
							load_Health = Integer.valueOf(str);
							mob.setMaxHealth(load_MaxHealth);
							mob.setHealth(load_Health);
						}
						
						// ========== 装備品を設定する命令郡 ==========
						//手の装備を設定
						if (str.equalsIgnoreCase("Hand:")){
							str = br.readLine(); strint++;
							load_hand = Material.getMaterial(str);
							ItemStack load_hand_ = new ItemStack(load_hand);
							str = br.readLine(); strint++;
							addEnchantment(br, str, load_hand_, strint);
							mob.getEquipment().setItemInHand(load_hand_);
							
						}
						//頭の装備を設定
						if (str.equalsIgnoreCase("Helmet:")){
							str = br.readLine(); strint++;
							load_helmet = Material.getMaterial(str);
							ItemStack load_helmet_ = new ItemStack(load_helmet);
							str = br.readLine(); strint++;
							addEnchantment(br, str, load_helmet_, strint);
							mob.getEquipment().setHelmet(load_helmet_);
						}
						//胸の装備を設定
						if (str.equalsIgnoreCase("Chestplate:")){
							str = br.readLine(); strint++;
							load_chestplate = Material.getMaterial(str);
							ItemStack load_chestplate_ = new ItemStack(load_chestplate);
							str = br.readLine(); strint++;
							addEnchantment(br, str, load_chestplate_, strint);
							mob.getEquipment().setChestplate(load_chestplate_);
						}
						//足上の装備を設定
						if (str.equalsIgnoreCase("Leggings:")){
							str = br.readLine(); strint++;
							load_leggings = Material.getMaterial(str);
							ItemStack load_leggings_ = new ItemStack(load_leggings);
							str = br.readLine(); strint++;
							addEnchantment(br, str, load_leggings_, strint);
							mob.getEquipment().setLeggings(load_leggings_);
						}
						//足の装備を設定
						if (str.equalsIgnoreCase("Boots:")){
							str = br.readLine(); strint++;
							load_boots = Material.getMaterial(str);
							ItemStack load_boots_ = new ItemStack(load_boots);
							str = br.readLine(); strint++;
							addEnchantment(br, str, load_boots_, strint);
							mob.getEquipment().setBoots(load_boots_);
						}
						// ========== 装備設定ここまで ==========
						
						//デスポーン防止する命令。
						if (str.equalsIgnoreCase("Remove:")){
							str = br.readLine(); strint++;
							load_remove = Boolean.getBoolean(str);
							mob.setRemoveWhenFarAway(load_remove);
						}
						
						strint++; //行読み込み
						
					}
					
				}
				
			} else {
				br.close();
				//ファイル読み込みに失敗した場合
				log.info(logPrefix + "§4 Error : FileNotFound. (ファイルが存在しません)");
				Bukkit.broadcastMessage(msgPrefix + "§4 Error : FileNotFound");
				return;
			}
			
		} catch (FileNotFoundException e) {
			log.info(logPrefix+"[error]"+e.getMessage());
			return;
		} catch (IOException e) {
			log.info(logPrefix+"[error]"+e.getMessage());
			return;
		}
		
	}

	private void setSlime(BufferedReader br, String str, int strint) throws IOException {
		
		while((str = br.readLine()) != null){
			
			boolean load_visible = false;
			
			int load_MaxHealth = 10;
			int load_Health = 10;
			
			boolean load_remove = true;
			
			//固有設定
			setNbtags(br,str,strint);
			
			//エフェクト効果を設定する命令。
			if (str.equalsIgnoreCase("Effect:")){
				//TODO 未実装の処理
			}
			
			//名前を指定する命令。
			if (str.equalsIgnoreCase("Name:")) {
				str = br.readLine(); strint++;
				entity.setCustomName(str);
				//常に表示する命令。
				if (str.equalsIgnoreCase("-Visible")) {
					str = br.readLine(); strint++;
					load_visible = Boolean.getBoolean(str);
					entity.setCustomNameVisible(load_visible);
				}
			}
			
			//HPの上限を設定する命令。
			if (str.equalsIgnoreCase("Health:")) {
				str = br.readLine(); strint++;
				load_MaxHealth = Integer.valueOf(str);
				str = br.readLine(); strint++;
				load_Health = Integer.valueOf(str);
				entity.setMaxHealth(load_MaxHealth);
				entity.setHealth(load_Health);
			}
			
			//デスポーン防止する命令。
			if (str.equalsIgnoreCase("Remove:")){
				str = br.readLine(); strint++;
				load_remove = Boolean.getBoolean(str);
				entity.setRemoveWhenFarAway(load_remove);
			}
			
			strint++; //行読み込み
			
		}
	}

	public void addEnchantment(BufferedReader br, String str, ItemStack item, int strint) throws IOException{
		
		if (!(str.equals(null))){
			while (str.equalsIgnoreCase("-Enchantment")){
				str = br.readLine(); strint++;
				if (str.equalsIgnoreCase("-Enchant")){
					Enchantment enchant = Enchantment.DAMAGE_ALL;
					int level = 1;
					
					str = br.readLine(); strint++;
					enchant = Enchantment.getByName(str);
					
					str = br.readLine(); strint++;
					if (str.equalsIgnoreCase("-Level")){
						str = br.readLine(); strint++;
						level = Integer.valueOf(str);
						str = br.readLine(); strint++;
					}
					
					System.out.println(enchant+","+level);
					item.addUnsafeEnchantment(enchant, level);
				}
			}
		}
	}
	
	public void setNbtags(BufferedReader br, String str, int strint) throws IOException{
		
		if (str.equalsIgnoreCase("NbtTag:")){
			
			str = br.readLine(); strint++;
			if (str.equalsIgnoreCase("-Zombie")){
				str = br.readLine(); strint++;
				Zombie zombie = (Zombie) mob;
				boolean baby = Boolean.valueOf(str);
				zombie.setBaby(baby);
				str = br.readLine(); strint++;
				boolean villager = Boolean.valueOf(str);
				zombie.setVillager(villager);
				str = br.readLine(); strint++;
			}
			if (str.equalsIgnoreCase("-Skeleton")){
				str = br.readLine(); strint++;
				Skeleton skelton = (Skeleton) mob;
				SkeletonType skeltontype = SkeletonType.valueOf(str);
				skelton.setSkeletonType(skeltontype);
				//WITHER
				//NORMAL
			}
			if (str.equalsIgnoreCase("-Creeper")){
				str = br.readLine(); strint++;
				Creeper creeper = (Creeper) mob;
				boolean lightning = Boolean.getBoolean(str);
				creeper.setPowered(lightning);
				str = br.readLine(); strint++;
			}
			if (str.equalsIgnoreCase("-Enderman")){
				str = br.readLine(); strint++;
				Enderman enderman = (Enderman) mob;
				Material material = Material.getMaterial(str);
				MaterialData materialdate = new MaterialData(material);
				enderman.setCarriedMaterial(materialdate);
				str = br.readLine(); strint++;
			}
			if (str.equalsIgnoreCase("-PigZombie")){
				str = br.readLine(); strint++;
				PigZombie pigzombie = (PigZombie) mob;
				str = br.readLine(); strint++;
				boolean baby = Boolean.valueOf(str);
				pigzombie.setBaby(baby);
				str = br.readLine(); strint++;
				boolean angry = Boolean.valueOf(str);
				pigzombie.setAngry(angry);
				str = br.readLine(); strint++;
			}
			if (str.equalsIgnoreCase("-Slime")){
				str = br.readLine(); strint++;
				Slime slime = (Slime) entity;
				int size = Integer.valueOf(str);
				slime.setSize(size);
				str = br.readLine(); strint++;
			}
			if (str.equalsIgnoreCase("-MagmaCube")){
				str = br.readLine(); strint++;
				MagmaCube magmacube = (MagmaCube) entity;
				int size = Integer.valueOf(str);
				magmacube.setSize(size);
				str = br.readLine(); strint++;
			}
		}
		
		return;
		
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
