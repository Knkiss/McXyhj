package cn.mcxyhj.dnkiss.armor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicBoolean;

public class ArmorCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("mcxyhj.admin")) {
                if (args.length == 2) {
                    String name = args[1];
                    AtomicBoolean isPlayerOnline = new AtomicBoolean(false);
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if(player.getName().equalsIgnoreCase(name)){
                            isPlayerOnline.set(true);
                        }
                    });
                    if(!isPlayerOnline.get()){
                        p.sendMessage("找不到该玩家");
                        return true;
                    }

                    if(args[0].equalsIgnoreCase("gethelmet")){
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            if(player.getInventory().getHelmet()!=null){
                                p.getInventory().addItem(player.getInventory().getHelmet());
                                p.sendMessage("已将该玩家的帽子放到你的背包内");
                            }else p.sendMessage("该玩家没戴帽子");
                        });
                    }
                    if(args[0].equalsIgnoreCase("getchestplate")){
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            if(player.getInventory().getChestplate()!=null){
                                p.getInventory().addItem(player.getInventory().getChestplate());
                                p.sendMessage("已将该玩家的胸甲放到你的背包内");
                            }else p.sendMessage("该玩家没穿衣服");
                        });
                    }
                    if(args[0].equalsIgnoreCase("getleggings")){
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            if(player.getInventory().getLeggings()!=null){
                                p.getInventory().addItem(player.getInventory().getLeggings());
                                p.sendMessage("已将该玩家的裤子放到你的背包内");
                            }else p.sendMessage("该玩家没穿裤子");
                        });
                    }
                    if(args[0].equalsIgnoreCase("getboots")){
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            if(player.getInventory().getBoots()!=null){
                                p.getInventory().addItem(player.getInventory().getBoots());
                                p.sendMessage("已将该玩家的帽子放到你的背包内");
                            }else p.sendMessage("该玩家没穿鞋");
                        });
                    }

                    if(args[0].equalsIgnoreCase("sethelmet")){
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            if(player.getInventory().getHelmet()!=null){
                                player.getWorld().dropItem(player.getLocation(),player.getInventory().getHelmet());
                            }
                            player.getInventory().setHelmet(p.getInventory().getItemInMainHand());
                            p.sendMessage("已将该玩家帽子设置为手上物品，原位置装备掉落");
                        });
                    }
                } else {
                    p.sendMessage("§4你没有权限这么做");
                }
            }
        }else{
            sender.sendMessage("控制台不可用此命令");
        }
        return false;
    }
}
