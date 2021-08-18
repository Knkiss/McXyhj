package cn.mcxyhj.knkiss;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
//import xyhj.knkiss.swapLocationGame.SwapLocationGameCommand;
//import xyhj.knkiss.swapLocationGame.SwapLocationGameManager;

import java.util.Objects;

/*public class McXyhjCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)){
            if(args.length == 0){
                sender.sendMessage("/mcxyhj slg 开启位置互换大逃杀");
            }
            if(args[0].equalsIgnoreCase("slg")){
                Objects.requireNonNull(McXyhj.getCommand("slg")).setExecutor(new SwapLocationGameCommand());
                SwapLocationGameManager.onEnable(McXyhj);
            }
        }
        return true;
    }
}*/
