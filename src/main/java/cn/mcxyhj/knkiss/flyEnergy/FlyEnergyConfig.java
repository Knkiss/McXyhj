package cn.mcxyhj.knkiss.flyEnergy;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class FlyEnergyConfig {
	
	static Plugin plugin;
	private static File flyEnergyFile = null;
	private static FileConfiguration flyEnergyConfig = null;
	private static File File = null;
	private static FileConfiguration Config = null;
	
	//config和file初始化
	public FlyEnergyConfig(Plugin plugin){
		FlyEnergyConfig.plugin = plugin;
		saveDefaultConfig();
		flyEnergyConfig = getFlyEnergyConfig();
		Config = getConfig();
		reloadConfig();
	}
	
	public static void reloadConfig(){
		if (!File.exists()) {
			plugin.saveResource("FlyEnergyConfig.yml", false);
		}
		Config = YamlConfiguration.loadConfiguration(File);
		FlyEnergyManager.valuePerSecond = Config.getInt("valuePerSecond");
		FlyEnergyManager.valuePer1Power = Config.getDouble("valuePer1Power");
	}
	
	//读取玩家数据 从内存或配置文件
	public static void getPlayerConfig(Player p){
		if(!FlyEnergyManager.walk.containsKey(p.getName())){
			if(flyEnergyConfig.contains(p.getName().toLowerCase()))
				FlyEnergyManager.walk.put(p.getName(),flyEnergyConfig.getDouble(p.getName().toLowerCase()));
			else
				FlyEnergyManager.walk.put(p.getName(),0.0);
		}
	}
	
	//服务器关闭时保存数据
	public static void savePlayerConfig(){
		
		FlyEnergyManager.fly.forEach((name,number)->{
			Player player = Bukkit.getPlayer(name);
			if(player != null){
				if(!player.isOp()){
					player.sendMessage("服务器重载，你的飞行已关闭");
					player.addPotionEffect(FlyEnergyManager.pe);
					if(player.getGameMode() != GameMode.SPECTATOR){
						player.setAllowFlight(false);
						player.setFlying(false);
					}
				}
			}
			FlyEnergyManager.walk.put(name,number);
		});
		
		FlyEnergyManager.walk.forEach((name, number) -> {
			flyEnergyConfig.set(name.toLowerCase(),number);
		});
		saveCustomConfig();
	}
	
	//配置文件默认方法 不必更改
	public static void saveDefaultConfig() {
		if (flyEnergyFile == null || File == null) {
			flyEnergyFile = new File(plugin.getDataFolder(), "FlyEnergyInfo.yml");
			File = new File(plugin.getDataFolder(), "FlyEnergyConfig.yml");
		}
		if (!flyEnergyFile.exists()) {
			plugin.saveResource("FlyEnergyInfo.yml", false);
		}
		if (!File.exists()) {
			plugin.saveResource("FlyEnergyConfig.yml", false);
		}
	}
	
	public static void saveCustomConfig() {
		if (flyEnergyConfig == null || flyEnergyFile == null) {
			return;
		}
		try {
			getFlyEnergyConfig().save(flyEnergyFile);
		} catch (IOException ex) {
			plugin.getLogger().warning("Could not save config to " + flyEnergyFile);
		}
	}
	
	public static FileConfiguration getFlyEnergyConfig() {
		if (flyEnergyConfig == null) {
			if (flyEnergyFile == null) {
				flyEnergyFile = new File(plugin.getDataFolder(), "FlyEnergyInfo.yml");
			}
			flyEnergyConfig = YamlConfiguration.loadConfiguration(flyEnergyFile);
		}
		return flyEnergyConfig;
	}
	
	public static FileConfiguration getConfig() {
		if (Config == null) {
			if (File == null) {
				File = new File(plugin.getDataFolder(), "FlyEnergyConfig.yml");
			}
			Config = YamlConfiguration.loadConfiguration(File);
		}
		return Config;
	}
}
