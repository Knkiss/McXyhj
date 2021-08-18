package cn.mcxyhj.knkiss.flyEnergy;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import cn.mcxyhj.knkiss.McXyhj;
import cn.mcxyhj.knkiss.flyEnergy.speedCheck.SpeedCheckManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FlyEnergyManager {
	
	static Plugin plugin;
	static HashMap<String,Double> fly = new HashMap<>();
	static HashMap<String,Integer> flyTime = new HashMap<>();
	static HashMap<String,Double> walk = new HashMap<>();
	static int valuePerSecond = 1;
	static double valuePer1Power = 1;
	static BukkitRunnable br;
	static PotionEffect pe = new PotionEffect(PotionEffectType.SLOW_FALLING,15*20,1);

	public static void onEnable(Plugin plugin){
		FlyEnergyManager.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(new FlyEnergyListener(plugin),plugin);
		new FlyEnergyConfig(plugin);
		checkReload();
		newTask();
		Bukkit.getPluginManager().registerEvents(new SpeedCheckManager(), McXyhj.plugin);
	}
	
	public static void onDisable(){
		FlyEnergyConfig.savePlayerConfig();
	}
	
	public static void checkReload(){
		//服务器reload重载时检测服务器在线玩家并重载数据 config
		Bukkit.getOnlinePlayers().forEach(player -> {
			FlyEnergyConfig.getPlayerConfig(player);
			SpeedCheckManager.onReload(player);
		});
	}
	
	private static void newTask(){
		br = new BukkitRunnable() {
			@Override
			public void run() {
				List<String> noFlyList = new ArrayList<>();
				
				FlyEnergyManager.flyTime.forEach((name, time) -> {
					FlyEnergyManager.flyTime.replace(name,time-1);
					if(time - 1==0){
						//Objects.requireNonNull(Bukkit.getPlayer(name)).sendMessage("消耗 1s，已续费");
						if(FlyEnergyManager.fly.get(name) >= FlyEnergyManager.valuePerSecond*SpeedCheckManager.getPlayerMul(name)){
							FlyEnergyManager.flyTime.replace(name,1);
							FlyEnergyManager.fly.replace(name,FlyEnergyManager.fly.get(name)-FlyEnergyManager.valuePerSecond*SpeedCheckManager.getPlayerMul(name));
							//Objects.requireNonNull(Bukkit.getPlayer(name)).sendMessage("消耗 "+valuePerSecond*SpeedCheckManager.getPlayerMul(name)+" 点能量，还有 "+FlyEnergyManager.fly.get(name)+" 点能量");
						}else{
							noFlyList.add(name);
						}
					}//Objects.requireNonNull(Bukkit.getPlayer(name)).sendMessage("消耗 1s，还有 "+(time-1)+"s");
					
				});
				if(!noFlyList.isEmpty()) noFlyList.forEach(s -> stopFly(Objects.requireNonNull(Bukkit.getPlayer(s))));
			}
		};
		br.runTaskTimer(plugin,0,20);
	}
	
	public static boolean inFly(String name){
		if(fly.containsKey(name))return true;
		if(!walk.containsKey(name))walk.put(name,0.0);
		return false;
	}
	
	public static void stopFly(Player p){
		String name = p.getName();
		if(inFly(name)){
			walk.put(name,fly.get(name));
			fly.remove(name);
			flyTime.remove(name);

			if(p.getGameMode() != GameMode.SPECTATOR){
				p.setAllowFlight(false);
				p.setFlying(false);
			}
			p.addPotionEffect(pe);
			p.sendMessage("你已经关闭飞行能力，剩余 "+walk.get(name)+" 点能量");
		}else{
			p.sendMessage("你没有开启飞行能力");
		}
	}
	
	public static void startFly(Player p){
		if(!p.getWorld().getName().equalsIgnoreCase("world")){
			p.sendMessage("非主世界不能开启飞行能力");
			return;
		}
		
		String name = p.getName();
		if(inFly(name)){
			p.sendMessage("你已经开启飞行能力，已消耗 "+ valuePerSecond*15 +" 点能量");
		}else{
			if(walk.get(name) >= valuePerSecond*15){
				fly.put(name,walk.get(name)-valuePerSecond*15);
				flyTime.put(name,15);
				walk.remove(name);
				p.setAllowFlight(true);
				p.setFlying(true);
				p.sendMessage("你已经开启飞行能力，已消耗 "+ valuePerSecond*15 +" 点能量");
			}else{
				p.sendMessage("你需要消耗 "+(valuePerSecond*15)+" 点能量才可开启飞行");
			}
		}
	}
}
