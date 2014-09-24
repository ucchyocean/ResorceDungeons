package com.thekarura.bukkit.plugin.resorcedungeons;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.thekarura.bukkit.plugin.resorcedungeons.command.GenerateCommand;
import com.thekarura.bukkit.plugin.resorcedungeons.command.HelpCommand;
import com.thekarura.bukkit.plugin.resorcedungeons.command.ReloadCommand;
import com.thekarura.bukkit.plugin.resorcedungeons.listener.RDBlockListener;
import com.thekarura.bukkit.plugin.resorcedungeons.listener.RDChunkListener;
import com.thekarura.bukkit.plugin.resorcedungeons.listener.RDEntityListener;
import com.thekarura.bukkit.plugin.resorcedungeons.listener.RDPlayerListener;
import com.thekarura.bukkit.plugin.resorcedungeons.listener.RDSignListener;

public class ResorceDungeons extends JavaPlugin {
	
	// ** Logger **
	public final static Logger log = Logger.getLogger("ResorceDungeons");
	public final static String logPrefix = "[ResorceDungeons]";
	public final static String msgPrefix = ChatColor.GREEN + "[ResorceDungeons]" + ChatColor.RESET;
	
	// ** Listener **
	private final RDPlayerListener playerListener = new RDPlayerListener(this);
	private final RDBlockListener blockListener = new RDBlockListener(this);
	private final RDEntityListener entityListener = new RDEntityListener(this);
	private final RDChunkListener chunkListener = new RDChunkListener(this);
	private final RDSignListener signListener = new RDSignListener(this);
	
	// ** Private classes **
	private ConfigurationManager config;
	
	// ** Instance **
	private static ResorceDungeons instance;
	
	// ** Enable ** //
	public void onEnable(){
		
		log.info(logPrefix + "は起動処理を開始しました。");
		instance = this ;
		PluginManager pm = getServer().getPluginManager();
		
		// loadingconfig
		config = new ConfigurationManager(this);
		
		// ** registerEvent **
		//あまりに重いので心当たりのある処理を停止
		pm.registerEvents(playerListener, this);
		pm.registerEvents(blockListener, this);
		pm.registerEvents(entityListener, this);
		pm.registerEvents(chunkListener, this);
		pm.registerEvents(signListener, this);
		
		// ** registerCommands **
		getCommand("RDHelp").setExecutor(new HelpCommand(this));
		getCommand("RDGenerate").setExecutor(new GenerateCommand());
		getCommand("RDReload").setExecutor(new ReloadCommand(this));
		
		try {
			
			config.load();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			e.getMessage();
			return;
			
		}
		
		//pluginの起動前に確認をします
		if(!config.getEnablePlugin()){
			pm.disablePlugins();
			log.info(logPrefix + config.getName() + " is Disabled! if plugin enable ENABLE_PLUGIN if true.");
		}
		
		// サーバーリロード時に、既に接続中のプレイヤーに対して、
		// 移動チェックタスクを仕掛けておく
		for ( Player player : getServer().getOnlinePlayers() ) {
			PlayerMoveCheckTask task = new PlayerMoveCheckTask(instance, player);
			task.runTaskTimerAsynchronously(this, 20, 20);
		}
		
		log.info(logPrefix + "起動完了しました。");
		
	}
	
	// ** Disable ** //
	public void onDisable(){
		
		log.info(logPrefix + "停止処理を開始します。");
		
		try {
			
			//config.save();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return;
		}
		
		log.info(logPrefix + "停止完了しました。");
		
	}
	
	public ConfigurationManager getConfigs() {
		return config;
	}
	
	public static ResorceDungeons getInstance() {
		return instance;
	}
	
}