package littlegruz.marioworld.commands;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import littlegruz.marioworld.MarioMain;
import littlegruz.marioworld.entities.MarioBlock;
import littlegruz.marioworld.entities.MarioPlayer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameplayStuff implements CommandExecutor{
   private MarioMain plugin;
   
   public GameplayStuff(MarioMain instance){
      plugin = instance;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command cmd,
         String commandLabel, String[] args){
      if(cmd.getName().compareToIgnoreCase("mariorestart") == 0){
         Location loc;
         if(sender instanceof Player){
            Player playa = (Player) sender;
            if(sender.hasPermission("marioworld.admincommands")){
               // Change the MarioBlocks back to their initial state
               Iterator<Map.Entry<Location, MarioBlock>> it = plugin.getBlockMap().entrySet().iterator();
               while(it.hasNext()){
                  Entry<Location, MarioBlock> mb = it.next();
                  if(mb.getValue().isHit()){
                     loc = mb.getValue().getLocation().clone();
                     loc.getBlock().setType(mb.getValue().getType());
                     loc.setY(loc.getY() + 1);
                     loc.getBlock().setType(Material.AIR);
                     mb.getValue().setHit(false);
                  }
               }
               // Reset the players stats to the default
               Iterator<Map.Entry<String, MarioPlayer>> itPlayer = plugin.getPlayerMap().entrySet().iterator();
               while(itPlayer.hasNext()){
                  Entry<String, MarioPlayer> mp = itPlayer.next();
                  /* If the UUID isn't valid, then replace it with the world
                   * the current player is in*/
                  try{
                     mp.getValue().getCheckpoint().getWorld().getUID().equals(playa.getWorld().getUID());
                  }catch(Exception e){
                     plugin.getServer().broadcastMessage(plugin.getCurrentRB().getString("BadCheckpoint"));
                     mp.getValue().getCheckpoint().setWorld(playa.getWorld());
                  }
                  if(mp.getValue().getCheckpoint().getWorld().getUID().equals(playa.getWorld().getUID())){
                     plugin.clearCheckpoint(mp.getValue().getPlayaName(), playa.getWorld().getName());
                     /* Reset coins to 0 if coin persistence is off or if it is
                      * on and the command line argument is "c"*/
                     if(plugin.isCoinPersistence()){
                        if(args.length == 1){
                           if(args[0].compareTo("c") == 0)
                              mp.getValue().setCoins(0);
                        }
                     }
                     else
                        mp.getValue().setCoins(0);
                     mp.getValue().setLives(plugin.getDefaultLives());
                     mp.getValue().setState("Small");
                     
                     Player player = plugin.getServer().getPlayer(mp.getValue().getPlayaName());
                     if(player != null && player.getItemInHand().getType().compareTo(Material.EGG) == 0){
                        plugin.getServer().getPlayer(mp.getValue().getPlayaName()).setItemInHand(null);
                     }
                  }
               }
               if(plugin.isSpoutEnabled())
                  plugin.getGui().update(playa);
               playa.sendMessage(plugin.getCurrentRB().getString("MWRestart"));
            } else{
               playa.sendMessage(plugin.getCurrentRB().getString("PermissionDeny"));
            }
         } else{
            sender.sendMessage(plugin.getCurrentRB().getString("CanNotBeConsole"));
         }
      }
      else if(cmd.getName().compareToIgnoreCase("mariodamage") == 0){
         if(sender.hasPermission("marioworld.admincommands")){
            if(plugin.isMarioDamage()){
               plugin.setMarioDamage(false);
               plugin.getConfig().set("damage", false);
               sender.sendMessage(plugin.getCurrentRB().getString("MWDamageDisabled"));
            }else{
               plugin.setMarioDamage(true);
               plugin.getConfig().set("damage", true);
               sender.sendMessage(plugin.getCurrentRB().getString("MWDamageEnabled"));
            }
         }else
            sender.sendMessage(plugin.getCurrentRB().getString("PermissionDeny"));
      }
      else if(cmd.getName().compareToIgnoreCase("keepmariocoins") == 0){
         if(sender.hasPermission("marioworld.admincommands")){
            if(plugin.isCoinPersistence()){
               plugin.setCoinPersistence(false);
               plugin.getConfig().set("coin_persistence", false);
               sender.sendMessage(plugin.getCurrentRB().getString("CoinLose"));
            }else{
               plugin.setCoinPersistence(true);
               plugin.getConfig().set("coin_persistence", true);
               sender.sendMessage(plugin.getCurrentRB().getString("CoinKeep"));
            }
         }else
            sender.sendMessage(plugin.getCurrentRB().getString("PermissionDeny"));
      }
      else if(cmd.getName().compareToIgnoreCase("changedefaultlives") == 0){
         if(sender.hasPermission("marioworld.admincommands")){
            if(args.length == 1){
               plugin.getConfig().set("lives", Integer.parseInt(args[0]));
               plugin.setDefaultLives(Integer.parseInt(args[0]));
               sender.sendMessage(plugin.getCurrentRB().getString("Lives") + ": " + args[0]);
            }
            else
               sender.sendMessage(plugin.getCurrentRB().getString("WrongArguments"));
         }else
            sender.sendMessage(plugin.getCurrentRB().getString("PermissionDeny"));
      }
      else if(cmd.getName().compareToIgnoreCase("changecoinlevelup") == 0){
         if(sender.hasPermission("marioworld.admincommands")){
            if(args.length == 1){
               plugin.getConfig().set("coin_level", Integer.parseInt(args[0]));
               plugin.setDefaultLives(Integer.parseInt(args[0]));
               sender.sendMessage(plugin.getCurrentRB().getString("Coins") + ": " + args[0]);
            }
            else
               sender.sendMessage(plugin.getCurrentRB().getString("WrongArguments"));
         }else
            sender.sendMessage(plugin.getCurrentRB().getString("PermissionDeny"));
      }
      return true;
   }

}
