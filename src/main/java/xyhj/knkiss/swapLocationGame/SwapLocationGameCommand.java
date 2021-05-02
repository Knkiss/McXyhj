package xyhj.knkiss.swapLocationGame;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

public class SwapLocationGameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("控制台无法使用此指令");
            return true;
        }
        Player p = (Player) sender;
        String name = p.getName();
        if(args.length == 0){
            p.sendMessage("--------------------------------");
            p.sendMessage("/slg join 加入游戏");
            p.sendMessage("/slg ready 切换准备状态");
            p.sendMessage("/slg list 查看加入游戏的玩家及准备状态");
            p.sendMessage("/slg time 查看下次交换剩余时间");
            p.sendMessage("当加入人数大于1，且所有人准备就绪时游戏将开始");
            p.sendMessage("--------------------------------");
        }
        else if(args[0].equalsIgnoreCase("join")){
            boolean bool = false;
            for(Player player : SwapLocationGameManager.all){
                if(player.getName().equals(p.getName())){
                    bool = true;
                    break;
                }
            }
            if(!bool){
                p.sendMessage("成功加入游戏，输入/slg ready准备，当加入人数大于1，且所有人准备就绪时游戏将开始");
                SwapLocationGameManager.all.add(p);
            }
            else p.sendMessage("你已在游戏中，输入/slg ready准备，当加入人数大于1，且所有人准备就绪时游戏将开始");
        }

        else if(args[0].equalsIgnoreCase("ready")){
            boolean bool = false;
            for(Player player : SwapLocationGameManager.all){
                if(player.getName().equals(p.getName())){
                    bool = true;
                    break;
                }
            }
            if(!bool){
                p.sendMessage("你还未加入游戏，输入/slg join加入游戏");
            }
            else {
                bool = false;
                for(Player player : SwapLocationGameManager.ready) {
                    if (player.getName().equals(p.getName())) {
                        bool = true;
                        break;
                    }
                }
                if(!bool){
                    p.sendMessage("已准备就绪，当加入人数大于1，且所有人准备就绪时游戏将开始");
                    SwapLocationGameManager.ready.add(p);
                    if(SwapLocationGameManager.ready.size() == SwapLocationGameManager.all.size() && SwapLocationGameManager.ready.size() > 1){
                        for(Player player : SwapLocationGameManager.all) player.sendMessage("游戏开始!");
                        SwapLocationGameManager.players.addAll(SwapLocationGameManager.all);
                        SwapLocationGameManager.start(SwapLocationGameManager.players);
                    }
                }
                else{
                    p.sendMessage("已取消准备状态");
                    Queue<Player> temp = new LinkedList<>();
                    while(SwapLocationGameManager.ready.peek()!=null){
                        if(SwapLocationGameManager.ready.poll().getName().equals(p.getName()));
                        else temp.add(SwapLocationGameManager.ready.poll());
                    }
                    SwapLocationGameManager.ready.addAll(temp);
                }
            }
        }

        else if(args[0].equalsIgnoreCase("list")){
            p.sendMessage("已加入玩家:");
            for(Player player : SwapLocationGameManager.all) p.sendMessage(player.getName());
            p.sendMessage("已准备玩家:");
            for(Player player : SwapLocationGameManager.ready) p.sendMessage(player.getName());
            p.sendMessage("战局内玩家");
            for(Player player : SwapLocationGameManager.players) p.sendMessage(player.getName());
        }

        else if(args[0].equalsIgnoreCase("time")){
            p.sendMessage(String.valueOf(SwapLocationGameManager.time));
        }

            return true;
    }
}
