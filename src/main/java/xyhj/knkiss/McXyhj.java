package xyhj.knkiss;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import xyhj.knkiss.flyEnergy.FlyEnergyCommand;
import xyhj.knkiss.flyEnergy.FlyEnergyManager;
import xyhj.knkiss.scoreboard.Scoreboard;

import java.util.Objects;

public final class McXyhj extends JavaPlugin{
	
	public static Economy econ = null;
	public static Plugin plugin;
	
	@Override
	public void onEnable() {
		plugin = this;
		this.getLogger().info("星夜幻境插件开始启用");
		Bukkit.getPluginManager().registerEvents(new McXyhjListener(),this);
		Objects.requireNonNull(this.getCommand("bd")).setExecutor(new Scoreboard());
			
		//飞行能量
		if (getServer().getPluginManager().getPlugin("Vault") != null) {
			setupEconomy();
			Objects.requireNonNull(this.getCommand("flyEnergy")).setExecutor(new FlyEnergyCommand());
			new FlyEnergyManager(this);
		}
		//统计
		//new StatisticsManager(this);
	}
	
	@Override
	public void onDisable() {
		this.getLogger().info("星夜幻境插件关闭");
		Scoreboard.onDisable();
		if (getServer().getPluginManager().getPlugin("Vault") != null) FlyEnergyManager.onDisable();
		//统计
		//StatisticsManager.addStartOrStopInfo(false);
	}
	
	private void setupEconomy() {
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		assert rsp != null;
		econ = rsp.getProvider();
	}
}
