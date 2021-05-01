package xyhj.knkiss.swapLocationGame;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import xyhj.knkiss.McXyhj;
import xyhj.knkiss.flyEnergy.FlyEnergyConfig;
import xyhj.knkiss.flyEnergy.FlyEnergyListener;
import xyhj.knkiss.flyEnergy.FlyEnergyManager;
import xyhj.knkiss.flyEnergy.speedCheck.SpeedCheckManager;

public class SwapLocationGameManager {
    static Plugin plugin;
    public static void onEnable(Plugin plugin){
        SwapLocationGameManager.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(new SwapLocationGameListener(plugin),plugin);
    }
}
