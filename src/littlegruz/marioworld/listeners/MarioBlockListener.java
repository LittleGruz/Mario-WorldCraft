package littlegruz.marioworld.listeners;


import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import littlegruz.marioworld.MarioMain;
import littlegruz.marioworld.entities.MarioBlock;
import littlegruz.marioworld.entities.MarioPlayer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPhysicsEvent;

public class MarioBlockListener implements Listener{

   public static MarioMain plugin;
   
   public MarioBlockListener(MarioMain instance) {
           plugin = instance;
   }

   // Adding special Mario blocks
   @EventHandler
   public void onBlockDamage(BlockDamageEvent event){
      if(plugin.getWorldMap().containsKey(event.getPlayer().getWorld().getUID().toString())){
         if(!event.getPlayer().isOp() || event.getInstaBreak())
            return;
         if(event.getItemInHand().getType().compareTo(Material.REDSTONE_TORCH_ON) == 0){
            if(plugin.getBlockMap().get(event.getBlock().getLocation()) == null){
               plugin.getBlockMap().put(event.getBlock().getLocation(), new MarioBlock(event.getBlock().getLocation(), "question"));
               event.getPlayer().sendMessage("Question mark block saved");
            }
            else{
               plugin.getBlockMap().remove(event.getBlock().getLocation());
               event.getPlayer().sendMessage("Special block removed");
            }
         } else if(event.getItemInHand().getType().compareTo(Material.GOLD_INGOT) == 0){
            if(plugin.getBlockMap().get(event.getBlock().getLocation()) == null){
               plugin.getBlockMap().put(event.getBlock().getLocation(), new MarioBlock(event.getBlock().getLocation(), "coin"));
               event.getPlayer().sendMessage("Coin block saved");
            }
            else{
               plugin.getBlockMap().remove(event.getBlock().getLocation());
               event.getPlayer().sendMessage("Special removed");
            }
         } else if(event.getItemInHand().getType().compareTo(Material.BROWN_MUSHROOM) == 0){
            if(plugin.getBlockMap().get(event.getBlock().getLocation()) == null){
               plugin.getBlockMap().put(event.getBlock().getLocation(), new MarioBlock(event.getBlock().getLocation(), "poison"));
               event.getPlayer().sendMessage("Poison block saved");
            }
            else{
               plugin.getBlockMap().remove(event.getBlock().getLocation());
               event.getPlayer().sendMessage("Special block removed");
            }
         } else if(event.getItemInHand().getType().compareTo(Material.RED_MUSHROOM) == 0){
            if(plugin.getBlockMap().get(event.getBlock().getLocation()) == null){
               plugin.getBlockMap().put(event.getBlock().getLocation(), new MarioBlock(event.getBlock().getLocation(), "super"));
               event.getPlayer().sendMessage("Super mushroom block saved");
            }
            else{
               plugin.getBlockMap().remove(event.getBlock().getLocation());
               event.getPlayer().sendMessage("Special block removed");
            }
         } else if(event.getItemInHand().getType().compareTo(Material.RED_ROSE) == 0){
            if(plugin.getBlockMap().get(event.getBlock().getLocation()) == null){
               plugin.getBlockMap().put(event.getBlock().getLocation(), new MarioBlock(event.getBlock().getLocation(), "fire"));
               event.getPlayer().sendMessage("Fire flower block saved");
            }
            else{
               plugin.getBlockMap().remove(event.getBlock().getLocation());
               event.getPlayer().sendMessage("Special block removed");
            }
         } else if(event.getItemInHand().getType().compareTo(Material.COOKIE) == 0){
            if(plugin.getBlockMap().get(event.getBlock().getLocation()) == null){
               plugin.getBlockMap().put(event.getBlock().getLocation(), new MarioBlock(event.getBlock().getLocation(), "inv"));
               event.getPlayer().sendMessage("Invincibility block saved");
            }
            else{
               plugin.getBlockMap().remove(event.getBlock().getLocation());
               event.getPlayer().sendMessage("Special block removed");
            }
         } else if(event.getItemInHand().getType().compareTo(Material.BRICK) == 0){
            if(plugin.getBlockMap().get(event.getBlock().getLocation()) == null){
               plugin.getBlockMap().put(event.getBlock().getLocation(), new MarioBlock(event.getBlock().getLocation(), "break"));
               event.getPlayer().sendMessage("Breakable block saved");
            }
            else{
               plugin.getBlockMap().remove(event.getBlock().getLocation());
               event.getPlayer().sendMessage("Special block removed");
            }
         } else if(event.getItemInHand().getType().compareTo(Material.CACTUS) == 0){
            if(event.getBlock().getType().compareTo(Material.STONE_PLATE) == 0){
               if(plugin.getBlockMap().get(event.getBlock().getLocation()) == null){
                  plugin.getBlockMap().put(event.getBlock().getLocation(), new MarioBlock(event.getBlock().getLocation(), "cp"));
                  event.getPlayer().sendMessage("Checkpoint saved");
               }
               else{
                  plugin.getBlockMap().remove(event.getBlock().getLocation());
                  event.getPlayer().sendMessage("Special block removed");
               }
            } else{
               // Set or remove the default respawn place
               Iterator<Map.Entry<Location, MarioBlock>> it = plugin.getBlockMap().entrySet().iterator();
               while(it.hasNext()){
                  Entry<Location, MarioBlock> mb = it.next();
                  if(mb.getValue().getBlockType().compareTo("respawn") == 0){
                     plugin.getBlockMap().remove(mb.getKey());
                     event.getPlayer().sendMessage("Respawn block removed");
                     return;
                  }
               }
               /* This part will only be reached if no custom respawn points are
                * found */
               plugin.getBlockMap().put(event.getBlock().getLocation(), new MarioBlock(event.getBlock().getLocation(), "respawn"));
               event.getPlayer().getWorld().setSpawnLocation(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ());
               event.getPlayer().sendMessage("Respawn block saved");
            }
         }
      }
   }

   // The checkpoint plate trigger
   @EventHandler
   public void onBlockPhysics(BlockPhysicsEvent event){
      if(plugin.getWorldMap().containsKey(event.getBlock().getWorld().getUID().toString())
            && event.getBlock().getType().compareTo(Material.STONE_PLATE) == 0
            && plugin.getBlockMap().get(event.getBlock().getLocation()) != null){
         Location loc = event.getBlock().getLocation();

         Iterator<Map.Entry<String, MarioPlayer>> it = plugin.getPlayerMap().entrySet().iterator();
         while(it.hasNext()){
            Entry<String, MarioPlayer> mp = it.next();
            if(plugin.getServer().getPlayer(mp.getValue().getPlayaName()) != null){
               Location playerLoc = plugin.getServer().getPlayer(mp.getValue().getPlayaName()).getLocation();
               if(loc.getBlockX() >= playerLoc.getBlockX() - 1 && loc.getBlockX() <= playerLoc.getBlockX() + 1
                     && loc.getBlockY() == playerLoc.getBlockY()
                     && loc.getBlockZ() >= playerLoc.getBlockZ() - 1 && loc.getBlockZ() <= playerLoc.getBlockZ() + 2
                     && !mp.getValue().getCheckpoint().equals(loc)){
                  plugin.getServer().getPlayer(mp.getValue().getPlayaName()).sendMessage("Checkpoint set");
                  mp.getValue().setCheckpoint(loc);
               }
            }
         }
      }
   }
}
