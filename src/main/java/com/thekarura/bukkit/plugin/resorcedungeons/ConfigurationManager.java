package com.thekarura.bukkit.plugin.resorcedungeons;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigurationManager {
	
	/* 
	 * Configration一覧にある設定はconfig.ymlの代理扱いです
	 * 追加の度に変更するのが大変なので
	 */
	
	// Prefixの参照 //
	public static final Logger log = ResorceDungeons.log;
	private static final String logPrefix = ResorceDungeons.logPrefix;
	private static final String msgPrefix = ResorceDungeons.msgPrefix;
	
	// ** 内部設定 ** //
	private String auther = "the_karura";
	private String version = "1.6.4_0001";
	private String name = "ResorceDungeons";
	
	// ** ファイル ** //
	//ダンジョンディレクトリー
	private String plugindir = "plugins\\Dungeons\\";
	private String dungeondir = plugindir+"dungeons\\";
	//チェストディレクトリー
	private String CHEST_FILE = plugindir+"chests\\";
	//Mobsディレクトリー
	private String MOBS_FILE = plugindir+"mobs\\";
	
	private JavaPlugin plugin;
	private FileConfiguration config;
	
	// ** 初期設定 ** //
	
	//重要設定項目
	private boolean ENABLE_PLUGIN =					new Boolean(true);	//pluginを有効化させるかどうか
	private String DUNGEON_GENERATE_WORLD =			"dungeon";	//ダンジョン自動生成がされるワールド
	private boolean DUNGEON_GENERATE_COMMAND =		new Boolean(true);	//コマンド実行に上を適応させるか
	
	//ダンジョン生成
	private boolean ENABLE_DUNGEON_MOSSY =			new Boolean(true);	//MossyDungeonの自動生成を許可
	private boolean ENABLE_DUNGEON_JUNGLECAVE =		new Boolean(true);	//JungleCaveの自動生成を許可
	private boolean ENABLE_DUNGEON_RUINS =			new Boolean(true);	//Ruinsの自動生成を許可
	private boolean ENABLE_DUNGEON_TOWERS =			new Boolean(true);	//Towersの自動生成を許可
	
	//生成確立
	private int DUNGEON_GENERATE_PERCENT_MOSSY =	new Integer(10);		//Mossyの生成確立
	private int DUNGEON_GENERATE_PERCENT_RUINS =	new Integer(30);		//Ruinsの生成確率
	private int DUNGEON_GENERATE_PERCENT_TOWERS=	new Integer(10);		//Twoersの生成確立
	
	//ダンジョン個別設定
	//MossyDungeon
	private int DUNGEON_MOSSY_DISTRCT_MIN = 3;	//生成する部屋数(削除予定)
	private int DUNGEON_MOSSY_DISTRCT_MAX = 3;	//生成する部屋数(削除予定)
	
	//生成関連
	private int DUNGEON_GENERATE_TRIGGER_RADIUS = new Integer(60);	//ダンジョントリガー検知範囲
	private int DUNGEON_GENERATE_DISTANCE = new Integer(100);		//ダンジョンとダンジョンの距離
	private int DUNGEON_GENERATE_SPAWND_ISTANCE = new Integer(50);	//ダンジョンを生成しない範囲(spawn基準)
	private int DUNGEON_GENERATE_UNDERGROUND_HIGTH = new Integer(45);//地下の限界高度を指定します
	
	// ** コンストラクター ** //
	public ConfigurationManager(ResorceDungeons plugin) {
		this.plugin = plugin ;
	}
	
	public void save() throws Exception{
		log.info(logPrefix + "is Config save");
		plugin.saveConfig();
	}
	
	public void load() throws Exception{
		
		log.info(logPrefix + "is File load");
		
		createDirs();
		plugin.saveDefaultConfig();
		
		config = plugin.getConfig();
		
		// == フォルダー生成 == //
		createDir(new File(this.getChestfile()));
		createDir(new File(this.getMobsfile()));
		createDir(new File(this.getMobsfile()));
		
		plugin.reloadConfig();
		
		// == ここからconfig.ymlに書かれた情報を読み取ります。 == //
		
		// == 内部的な設定 == //
		ENABLE_PLUGIN				= config.getBoolean("ENABLE_PLUGIN", true);
		DUNGEON_GENERATE_COMMAND	= config.getBoolean("DUNGEON_GENERATE_COMMAND", true);
		
		// == Dungeon生成switch == //
		ENABLE_DUNGEON_MOSSY		= config.getBoolean("Generate.ENABLE_DUNGEON_MOSSY", true);
		ENABLE_DUNGEON_JUNGLECAVE	= config.getBoolean("Generate.ENABLE_DUNGEON_JUNGLECAVE", true);
		ENABLE_DUNGEON_RUINS		= config.getBoolean("Generate.ENABLE_DUNGEON_RUINS", true);
		ENABLE_DUNGEON_TOWERS		= config.getBoolean("Generate.ENABLE_DUNGEON_TOWERS", true);
		
		// == Dungeon生成確立 == //
		
		
		// == Dungeon生成関連 == //
		DUNGEON_GENERATE_TRIGGER_RADIUS		= config.getInt("DUNGEON_GENERATE_TRIGGER_RADIUS", 60);
		DUNGEON_GENERATE_UNDERGROUND_HIGTH	= config.getInt("DUNGEON_GENERATE_UNDERGROUND_HIGTH", 62);
		
		DUNGEON_GENERATE_PERCENT_MOSSY = config.getInt("DUNGEON_GENERATE_PERCENT_MOSSY", 10);
		
		//指定されたワールドがなければ警告します。
		if (Bukkit.getWorld(DUNGEON_GENERATE_WORLD) == null) {
			log.info(logPrefix + "this Server is not Dungeon World! :" + DUNGEON_GENERATE_WORLD);
		}
		
	}
	
	/**
	 * pluginsフォルダー内にResorceDungeonsフォルダーを生成します。
	 */
	private void createDirs() {
		createDir(plugin.getDataFolder());
	}
	
	/**
	 * フォルダーを生成します。
	 * @param dir 生成する階層と名前
	 */
	private static void createDir(File dir) {
		// 既に存在すれば作らない
		if (dir.isDirectory()) { return; }
		if (!dir.mkdirs()) { log.warning(logPrefix + "Can't create directory: " + dir.getName()); }
	}
	
	// ** ゲッター **
	
	/** 作者のIDを習得 */
	public String getAuther(){
		return this.auther;
	}
	
	/** バージョンの習得 */
	public String getVersion(){
		return this.version;
	}
	
	/** pluginの名前を習得 */
	public String getName(){
		return this.name;
	}
	
	/** プラグインの階層を習得 */
	public String getPlugindir(){
		return this.plugindir;
	}
	
	/** ダンジョンの階層を習得 */
	public String getDungeondir(){
		return this.dungeondir;
	}
	
	/** チェストの階層を習得 */
	public String getChestfile() {
		return this.CHEST_FILE;
	}
	
	/** モブの階層を習得 */
	public String getMobsfile() {
		return this.MOBS_FILE;
	}
	
	/** pluginを実行するかどうかを習得 */
	public boolean getEnablePlugin(){
		return this.ENABLE_PLUGIN;
	}
	
	/** ダンジョンワールドを習得 */
	public String getDungeonWorld(){
		return this.DUNGEON_GENERATE_WORLD;
	}
	
	/** ダンジョンワールドでの実行をするかどうかを習得 */
	public boolean getDUNGEON_GENERATE_COMMAND(){
		return this.DUNGEON_GENERATE_COMMAND;
	}
	
	/** ダンジョンワールドの地下高度を習得します */
	public int getDUNGEON_GENERATE_UNDERGROUND_HIGTH(){
		return this.DUNGEON_GENERATE_UNDERGROUND_HIGTH;
	}
	
	/** プレイヤーの有効範囲を習得します */
	public int getDUNGEON_GENERATE_TRIGGER_RADIUS(){
		return this.DUNGEON_GENERATE_TRIGGER_RADIUS;
	}
	
	/** MossyDungeonの生成をするかどうかを習得します */
	public boolean getENABLE_DUNGEON_MOSSY(){
		return this.ENABLE_DUNGEON_MOSSY;
	}
	
	public boolean getENABLE_DUNGEON_JUNGLECAVE(){
		return this.ENABLE_DUNGEON_JUNGLECAVE;
	}
	
	public boolean getENABLE_DUNGEON_RUINS(){
		return this.ENABLE_DUNGEON_RUINS;
	}
	public boolean getENABLE_DUNGEON_TOWERS(){
		return this.ENABLE_DUNGEON_TOWERS;
	}
	
	public int getDUNGEON_GENERATE_PERCENT_MOSSY(){
		return this.DUNGEON_GENERATE_PERCENT_MOSSY;
	}
	
	public int getDUNGEON_GENERATE_PERCENT_RUINS(){
		return this.DUNGEON_GENERATE_PERCENT_RUINS;
	}
	
}
