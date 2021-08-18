package cn.mcxyhj.dnkiss.roll;

import cn.mcxyhj.knkiss.McXyhj;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.regex.Pattern;

public class RollCommand implements CommandExecutor {
    private static final Pattern PATTERN = Pattern.compile("0|([-]?[1-9][0-9]*)");

    private static boolean isInt(String str) {
        return PATTERN.matcher(str).matches();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("命令输入者需要为玩家");
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            int point = new Random().nextInt(100) + 1;
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.sendMessage("§a" + p.getName() + "掷出了" + point + "点(于1-100之间)");
            });
            return true;
        } else if (args.length == 1) {
            if (isInt(args[0])) {
                int point = new Random().nextInt(Integer.parseInt(args[0])) + 1;
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.sendMessage("§a" + p.getName() + "掷出了" + point + "点(于1-" + Integer.parseInt(args[0]) + "之间)");
                });
            } else {
                p.sendMessage("§4请正确的输入一个整数");
            }
            return true;
        } else {
            p.sendMessage("§a/roll：在'1 - 100'范围内随机掷出一个数");
            p.sendMessage("§a/roll <整数>：在'1 - 整数'范围内随机掷出一个数");
            return true;
        }
    }
}
