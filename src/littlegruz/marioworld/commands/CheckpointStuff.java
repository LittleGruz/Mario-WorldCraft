package littlegruz.marioworld.commands;

import littlegruz.marioworld.MarioMain;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckpointStuff implements CommandExecutor{
   private MarioMain plugin;
   
   public CheckpointStuff(MarioMain instance){
      plugin = instance;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command cmd,
         String commandLabel, String[] args){
      if(cmd.getName().compareToIgnoreCase("clearmariocheckpoint") == 0){
         Player player;
         if(sender instanceof Player){
            player = (Player) sender;
            if(args.length == 0){
               plugin.clearCheckpoint(player.getName(), player.getWorld().getUID());
               player.sendMessage(plugin.getCurrentRB().getString("CheckpointReset"));
            }
         }else if(args.length == 1 && sender.hasPermission("marioworld.admincommands")){
            if(plugin.getServer().getPlayer(args[0]) != null){
               sender.sendMessage(plugin.getCurrentRB().getString("PlayerCheckpointResetP1") + 
                  plugin.clearCheckpoint(args[0], plugin.getServer().getPlayer(args[0]).getWorld().getUID()));
            } else
               sender.sendMessage(plugin.getCurrentRB().getString("NoneOnline"));
         }
      }
      return true;
   }

}
