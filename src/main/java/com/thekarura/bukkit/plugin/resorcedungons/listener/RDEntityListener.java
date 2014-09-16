package com.thekarura.bukkit.plugin.resorcedungons.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.thekarura.bukkit.plugin.resorcedungons.ResorceDungeons;

public class RDEntityListener implements Listener {
	
	private ResorceDungeons instance = ResorceDungeons.getInstance();
	
	public RDEntityListener(final ResorceDungeons resorcedungeons) {
		this.instance = resorcedungeons;
	}
	
	//ジャングル地下ダンジョン用スポーンレート
	@EventHandler
	public void JungleCaveEntityChenge(CreatureSpawnEvent event){
		
		//ダンジョンワールドのみ有効化
		if (event.getEntity().getWorld() == Bukkit.getWorld(instance.getConfigs().getDungeonWorld())){
			
			//バイオーム指定
			switch (event.getLocation().getBlock().getBiome()){
			
			//ジャングルのみ
			case JUNGLE:
			case JUNGLE_HILLS:
				
				//EntityType検索
				switch (event.getEntityType()){
				
				//毒グモへ置換するモブ
				case CREEPER:
				case ENDERMAN:
				case SLIME:
					
					event.setCancelled(true);
					
					//群生設定
					for (int limit = 0; limit < 5 ; limit++){
						event.getLocation().getWorld().spawnEntity(event.getLocation(), EntityType.CAVE_SPIDER);
					}
					
				break;
				//ゾンビのレートを蜘蛛に変更
				case ZOMBIE:
					
					event.setCancelled(true);
					event.getLocation().getWorld().spawnEntity(event.getLocation(), EntityType.SPIDER);
					
				break;
				
				//動物の湧を一部消します。
				case COW:
				case SHEEP:
				case CHICKEN:
				case PIG:
					
					event.setCancelled(true);
					
				default:break;
				}
				
			break;
			default:break;
			
			}
			
		}
		
	}
	
	//砂漠用スポーンレート
	//@EventHandler
	public void DessertEntityChenge(){
		//TODO まだ
	}
	
}
