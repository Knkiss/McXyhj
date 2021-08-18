package cn.mcxyhj.knkiss.flyEnergy.speedCheck;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import cn.mcxyhj.knkiss.McXyhj;

import java.util.HashMap;

public class SpeedCheckManager implements Listener {
	private static final HashMap<String, Location> locs = new HashMap<>();
	private static final HashMap<String, Double> playerMul = new HashMap<>();
	
	public SpeedCheckManager(){
		registerSpeedCheck();
	}
	
	public static void registerSpeedCheck() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(McXyhj.plugin, SpeedCheckManager::runCheck, 0, 20);
	}
	
	public static void runCheck() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!p.isFlying()) {
				playerMul.replace(p.getName(),0.25);return;
			}
			
			if (locs.get(p.getName()).distance(p.getLocation()) > 15) {
				playerMul.replace(p.getName(),2.0);
			}else if (locs.get(p.getName()).distance(p.getLocation()) > 9) {
				playerMul.replace(p.getName(),1.0);
			}else if (locs.get(p.getName()).distance(p.getLocation()) > 3) {
				playerMul.replace(p.getName(),0.5);
			}else {
				playerMul.replace(p.getName(),0.25);
			}
			//p.sendMessage(""+locs.get(p.getName()).distance(p.getLocation()));
			//p.sendMessage(playerMul.get(p.getName())+"");
			
			locs.remove(p.getName());
			locs.put(p.getName(), p.getLocation());
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		locs.put(e.getPlayer().getName(), e.getPlayer().getLocation());
		if(playerMul.containsKey(e.getPlayer().getName()))playerMul.replace(e.getPlayer().getName(), 0.25);
		else playerMul.put(e.getPlayer().getName(), 0.25);
	}
	
	@EventHandler
	public void onLeft(PlayerQuitEvent e) {
		locs.remove(e.getPlayer().getName());
		playerMul.remove(e.getPlayer().getName());
	}
	
	public static void onReload(Player p){
		locs.put(p.getName(), p.getLocation());
		if(playerMul.containsKey(p.getName()))playerMul.replace(p.getName(), 0.25);
		else playerMul.put(p.getName(), 0.25);
	}
	
	public static double getPlayerMul(String name){
		return playerMul.get(name);
	}
}
