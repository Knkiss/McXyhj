package cn.mcxyhj.knkiss;

import cn.mcxyhj.dnkiss.armor.ArmorCommand;
import cn.mcxyhj.dnkiss.roll.RollCommand;
import cn.mcxyhj.knkiss.flyEnergy.FlyEnergyCommand;
import cn.mcxyhj.knkiss.flyEnergy.FlyEnergyManager;
import cn.mcxyhj.knkiss.scoreboard.Scoreboard;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
//import xyhj.knkiss.swapLocationGame.SwapLocationGameCommand;
//import xyhj.knkiss.swapLocationGame.SwapLocationGameManager;

import java.util.Objects;

public final class McXyhj extends JavaPlugin{
	
	public static Economy econ = null;
	public static Plugin plugin;

	private boolean hasEconomy = false;
	
	@Override
	public void onEnable() {
		plugin = this;
		this.getLogger().info("星夜幻境插件开始启用");
		Bukkit.getPluginManager().registerEvents(new McXyhjListener(),this);
		Objects.requireNonNull(this.getCommand("bd")).setExecutor(new Scoreboard());


		//选择开启功能
		//Objects.requireNonNull(this.getCommand("mcxyhj")).setExecutor(new McXyhjCommand());

		//飞行能量
		if (getServer().getPluginManager().getPlugin("Vault") != null) {
			if(setupEconomy()){
				Objects.requireNonNull(this.getCommand("flyEnergy")).setExecutor(new FlyEnergyCommand());
				FlyEnergyManager.onEnable(this);
			}
		}
		//统计
		//new StatisticsManager(this);

		//位置互换大逃杀
		//Objects.requireNonNull(this.getCommand("slg")).setExecutor(new SwapLocationGameCommand());
		//SwapLocationGameManager.onEnable(this);

		//玩家护甲相关
		Objects.requireNonNull(this.getCommand("armor")).setExecutor(new ArmorCommand());

		//roll
		Objects.requireNonNull(this.getCommand("roll")).setExecutor(new RollCommand());
	}
	
	@Override
	public void onDisable() {
		this.getLogger().info("星夜幻境插件关闭");
		Scoreboard.onDisable();
		if (hasEconomy) FlyEnergyManager.onDisable();
		//统计
		//StatisticsManager.addStartOrStopInfo(false);
		//SwapLocationGameManager.bossBar.removeAll();
	}
	
	private boolean setupEconomy() {
		try {
			RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
			assert rsp != null;
			econ = rsp.getProvider();
		}catch (Exception e){
			this.hasEconomy = false;
			return false;
		}
		this.hasEconomy = true;
		return true;
	}


	public static void main(String[] args){}
}
