package xyhj.knkiss;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class McXyhjListener implements Listener {
	
	@EventHandler//怪物防爆
	public void onCreeperBomb(EntityExplodeEvent e){
		if(e.getEntityType().equals(EntityType.CREEPER))e.setCancelled(true);
		if(e.getEntityType().equals(EntityType.WITHER))e.setCancelled(true);
	}
	
	@EventHandler//登录卡地狱门
	public void onPlayerJoinInPortalEvent(PlayerJoinEvent e){
		if(e.getPlayer().getLocation().getBlock().getType().equals(Material.NETHER_PORTAL)){
			e.getPlayer().getLocation().getBlock().setType(Material.AIR);
		}
	}
}
