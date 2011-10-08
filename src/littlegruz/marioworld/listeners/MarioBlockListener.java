package littlegruz.marioworld.listeners;


import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import littlegruz.marioworld.MarioMain;
import littlegruz.marioworld.entities.MarioBlock;
import littlegruz.marioworld.entities.MarioPlayer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class MarioBlockListener extends BlockListener{

   public static MarioMain plugin;
   
   public MarioBlockListener(MarioMain instance) {
           plugin = instance;
   }
   
   public void onBlockDamage(BlockDamageEvent event){
      // Add a block as a MarioBlock if hit by an op with a redstone torch
      if(plugin.getWorldMap().containsKey(event.getPlayer().getWorld().getUID().toString())){
         if(!event.getPlayer().isOp())
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
         }
      }
   }
   
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
