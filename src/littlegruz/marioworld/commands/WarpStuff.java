package littlegruz.marioworld.commands;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import littlegruz.marioworld.MarioMain;
import littlegruz.marioworld.entities.MarioBlock;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WarpStuff implements CommandExecutor{
   private MarioMain plugin;
   
   public WarpStuff(MarioMain instance){
      plugin = instance;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command cmd,
         String commandLabel, String[] args){
      if(cmd.getName().compareToIgnoreCase("displaywarppipes") == 0){
         if(sender.hasPermission("marioworld.admincommands")){
            sender.sendMessage("Entrance | Exit");
            Iterator<Map.Entry<Location, MarioBlock>> it = plugin.getBlockMap().entrySet().iterator();
            while(it.hasNext()){
               Entry<Location, MarioBlock> mp = it.next();
               sender.sendMessage(mp.getKey().getBlockX() + "," + mp.getKey().getBlockY() + "," + mp.getKey().getBlockZ() + " | " + mp.getValue().getLocation().getBlockX() + "," + mp.getValue().getLocation().getBlockY() + "," + mp.getValue().getLocation().getBlockZ());
            }
         }
      }
      else if(cmd.getName().compareToIgnoreCase("cancelwarppipe") == 0){
         if(sender.hasPermission("marioworld.admincommands")){
            plugin.setWarpPlacement(0);
            plugin.setFirstWarp(null);
            sender.sendMessage("Warp placement canceled");
         }
      }
      return true;
   }
}
