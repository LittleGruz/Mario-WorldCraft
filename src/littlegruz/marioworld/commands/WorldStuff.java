package littlegruz.marioworld.commands;

import littlegruz.marioworld.MarioMain;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldStuff implements CommandExecutor{
   private MarioMain plugin;
   
   public WorldStuff(MarioMain instance){
      plugin = instance;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command cmd,
         String commandLabel, String[] args){
      if(cmd.getName().compareToIgnoreCase("addmarioworld") == 0){
         Player player;
         if(sender instanceof Player){
            player = (Player) sender;
            if(sender.hasPermission("marioworld.admincommands")){
               if(plugin.getWorldMap().get(player.getWorld().getUID().toString()) != null){
                  player.sendMessage(plugin.getCurrentRB().getString("WorldIsAdded"));
               }else{
                  plugin.getWorldMap().put(player.getWorld().getUID().toString(), player.getWorld().getUID().toString());
                  player.sendMessage(plugin.getCurrentRB().getString("WorldAdded"));
                  if(plugin.isSpoutEnabled())
                     plugin.getGui().update(player);
               }
            }else
               player.sendMessage(plugin.getCurrentRB().getString("PermissionDeny"));
         }
      }else if(cmd.getName().compareToIgnoreCase("removemarioworld") == 0){
         Player player;
         if(sender instanceof Player){
            player = (Player) sender;
            if(sender.hasPermission("marioworld.admincommands")){
               if(plugin.getWorldMap().get(player.getWorld().getUID().toString()) == null){
                  player.sendMessage(plugin.getCurrentRB().getString("WorldNotAdded"));
               }else{
                  plugin.getWorldMap().remove(player.getWorld().getUID().toString());
                  player.sendMessage(plugin.getCurrentRB().getString("WorldRemoved"));
                  if(plugin.isSpoutEnabled())
                     plugin.getGui().remove(player);
               }
            }else
               player.sendMessage(plugin.getCurrentRB().getString("PermissionDeny"));
         }
      }
      return true;
   }

}
