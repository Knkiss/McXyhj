package xyhj.knkiss.flyEnergy.speedCheck;

import net.minecraft.server.v1_16_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyhj.knkiss.McXyhj;
import xyhj.knkiss.flyEnergy.FlyEnergyListener;
import xyhj.knkiss.flyEnergy.FlyEnergyManager;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.HashMap;

public class SpeedCheckManager implements Listener {
	private static HashMap<String, Location> locs = new HashMap<String, Location>();
	private static HashMap<String, Double> playerMul = new HashMap<String, Double>();
	
	public SpeedCheckManager(){
		registerSpeedCheck();
	}
	
	public static void registerSpeedCheck() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(McXyhj.plugin, new Runnable() {
			@Override
			public void run() {
				runCheck();
			}
		}, 0, 20);
	}
	
	public static void runCheck() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!p.isFlying() || !FlyEnergyManager.inFly(p.getName())) {
				playerMul.replace(p.getName(),0.25);return;
			}
			//p.sendMessage(""+locs.get(p.getName()).distance(p.getLocation()));
			if (locs.get(p.getName()).distance(p.getLocation()) > 15) {
				if (getTps() > 10 && getPing(p) < 1000) playerMul.replace(p.getName(),2.0);
			}else if (locs.get(p.getName()).distance(p.getLocation()) > 9) {
				if (getTps() > 12 && getPing(p) < 700) playerMul.replace(p.getName(),1.0);
			}else if (locs.get(p.getName()).distance(p.getLocation()) > 3) {
				if (getTps() > 12 && getPing(p) < 400) playerMul.replace(p.getName(),0.5);
			}else {
				playerMul.replace(p.getName(),0.25);
			}
			
			//p.sendMessage(playerMul.get(p.getName())+"");
			
			locs.remove(p.getName());
			locs.put(p.getName(), p.getLocation());
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		locs.put(e.getPlayer().getName(), e.getPlayer().getLocation());
		playerMul.put(e.getPlayer().getName(), 0.25);
	}
	
	@EventHandler
	public void onLeft(PlayerQuitEvent e) {
		locs.remove(e.getPlayer().getName());
		playerMul.remove(e.getPlayer().getName());
	}
	
	public static void onReload(Player p){
		locs.put(p.getName(), p.getLocation());
		playerMul.put(p.getName(), 0.25);
	}
	
	public static int getPing(Player p) {
		try {
			Object entityPlayer = p.getClass().getMethod("getHandle").invoke(p);
			int ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
			return ping;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static double getTps(){
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.parseDouble(df.format(MinecraftServer.getServer().recentTps[1]));
	}
	
	public static double getPlayerMul(String name){
		return playerMul.get(name);
	}
}
