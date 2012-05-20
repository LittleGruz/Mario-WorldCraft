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
      if(plugin.getWorldMap().containsKey(event.getPlayer().getWorld().getName())){
         if(!event.getPlayer().isOp() || event.getInstaBreak())
            return;
         if(event.getItemInHand().getType().compareTo(Material.REDSTONE_TORCH_ON) == 0){
            if(plugin.getBlockMap().get(event.getBlock().getLocation()) == null){
               plugin.getBlockMap().put(event.getBlock().getLocation(), new MarioBlock(event.getBlock().getLocation(), "question"));
               event.getPlayer().sendMessage(plugin.getCurrentRB().getString("QBlockSaved"));
            }
            else{
               plugin.getBlockMap().remove(event.getBlock().getLocation());
               event.getPlayer().sendMessage(plugin.getCurrentRB().getString("SpecialBlockRemoved"));
            }
         } else if(event.getItemInHand().getType().compareTo(Material.GOLD_INGOT) == 0){
            if(plugin.getBlockMap().get(event.getBlock().getLocation()) == null){
               plugin.getBlockMap().put(event.getBlock().getLocation(), new MarioBlock(event.getBlock().getLocation(), "coin"));
               event.getPlayer().sendMessage(plugin.getCurrentRB().getString("CBlockSaved"));
            }
            else{
               plugin.getBlockMap().remove(event.getBlock().getLocation());
               event.getPlayer().sendMessage(plugin.getCurrentRB().getString("SpecialBlockRemoved"));
            }
         } else if(event.getItemInHand().getType().compareTo(Material.BROWN_MUSHROOM) == 0){
            if(plugin.getBlockMap().get(event.getBlock().getLocation()) == null){
               plugin.getBlockMap().put(event.getBlock().getLocation(), new MarioBlock(event.getBlock().getLocation(), "poison"));
               event.getPlayer().sendMessage(plugin.getCurrentRB().getString("PBlockSaved"));
            }
            else{
               plugin.getBlockMap().remove(event.getBlock().getLocation());
               event.getPlayer().sendMessage(plugin.getCurrentRB().getString("SpecialBlockRemoved"));
            }
         } else if(event.getItemInHand().getType().compareTo(Material.RED_MUSHROOM) == 0){
            if(plugin.getBlockMap().get(event.getBlock().getLocation()) == null){
               plugin.getBlockMap().put(event.getBlock().getLocation(), new MarioBlock(event.getBlock().getLocation(), "super"));
               event.getPlayer().sendMessage(plugin.getCurrentRB().getString("SBlockSaved"));
            }
            else{
               plugin.getBlockMap().remove(event.getBlock().getLocation());
               event.getPlayer().sendMessage(plugin.getCurrentRB().getString("SpecialBlockRemoved"));
            }
         } else if(event.getItemInHand().getType().compareTo(Material.RED_ROSE) == 0){
            if(plugin.getBlockMap().get(event.getBlock().getLocation()) == null){
               plugin.getBlockMap().put(event.getBlock().getLocation(), new MarioBlock(event.getBlock().getLocation(), "fire"));
               event.getPlayer().sendMessage(plugin.getCurrentRB().getString("FFBlockSaved"));
            }
            else{
               plugin.getBlockMap().remove(event.getBlock().getLocation());
               event.getPlayer().sendMessage(plugin.getCurrentRB().getString("SpecialBlockRemoved"));
            }
         } else if(event.getItemInHand().getType().compareTo(Material.COOKIE) == 0){
            if(plugin.getBlockMap().get(event.getBlock().getLocation()) == null){
               plugin.getBlockMap().put(event.getBlock().getLocation(), new MarioBlock(event.getBlock().getLocation(), "inv"));
               event.getPlayer().sendMessage(plugin.getCurrentRB().getString("IBlockSaved"));
            }
            else{
               plugin.getBlockMap().remove(event.getBlock().getLocation());
               event.getPlayer().sendMessage(plugin.getCurrentRB().getString("SpecialBlockRemoved"));
            }
         }  else if(event.getItemInHand().getType().compareTo(Material.CAKE) == 0){
            if(plugin.getBlockMap().get(event.getBlock().getLocation()) == null){
               plugin.getBlockMap().put(event.getBlock().getLocation(), new MarioBlock(event.getBlock().getLocation(), "1-up"));
               event.getPlayer().sendMessage(plugin.getCurrentRB().getString("1UPBlockSaved"));
            }
            else{
               plugin.getBlockMap().remove(event.getBlock().getLocation());
               event.getPlayer().sendMessage(plugin.getCurrentRB().getString("SpecialBlockRemoved"));
            }
         } else if(event.getItemInHand().getType().compareTo(Material.BRICK) == 0){
            if(plugin.getBlockMap().get(event.getBlock().getLocation()) == null){
               plugin.getBlockMap().put(event.getBlock().getLocation(), new MarioBlock(event.getBlock().getLocation(), "break"));
               event.getPlayer().sendMessage(plugin.getCurrentRB().getString("BBlockSaved"));
            }
            else{
               plugin.getBlockMap().remove(event.getBlock().getLocation());
               event.getPlayer().sendMessage(plugin.getCurrentRB().getString("SpecialBlockRemoved"));
            }
         } else if(event.getItemInHand().getType().compareTo(Material.CACTUS) == 0){
            if(event.getBlock().getType().compareTo(Material.STONE_PLATE) == 0){
               if(plugin.getBlockMap().get(event.getBlock().getLocation()) == null){
                  plugin.getBlockMap().put(event.getBlock().getLocation(), new MarioBlock(event.getBlock().getLocation(), "cp"));
                  event.getPlayer().sendMessage(plugin.getCurrentRB().getString("CPBlockSaved"));
               }
               else{
                  plugin.getBlockMap().remove(event.getBlock().getLocation());
                  event.getPlayer().sendMessage(plugin.getCurrentRB().getString("SpecialBlockRemoved"));
               }
            } else{
               // Set or remove the default respawn place
               Iterator<Map.Entry<Location, MarioBlock>> it = plugin.getBlockMap().entrySet().iterator();
               while(it.hasNext()){
                  Entry<Location, MarioBlock> mb = it.next();
                  if(mb.getValue().getBlockType().compareTo("respawn") == 0){
                     plugin.getBlockMap().remove(mb.getKey());
                     event.getPlayer().sendMessage(plugin.getCurrentRB().getString("RBlockRemoved"));
                     return;
                  }
               }
               /* This part will only be reached if no custom respawn points are
                * found */
               Location loc = event.getBlock().getLocation();
               loc.setY(loc.getY() + 1);
               plugin.getBlockMap().put(loc, new MarioBlock(loc, "respawn"));
               event.getPlayer().getWorld().setSpawnLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
               event.getPlayer().sendMessage(plugin.getCurrentRB().getString("RBlockSaved"));
            }
         }
      }
   }

   // The checkpoint plate trigger
   @EventHandler
   public void onBlockPhysics(BlockPhysicsEvent event){
      if(plugin.getWorldMap().containsKey(event.getBlock().getWorld().getName())
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
                  mp.getValue().setCheckpoint(loc);
                  plugin.getServer().getPlayer(mp.getValue().getPlayaName()).sendMessage(plugin.getCurrentRB().getString("CPSet"));
               }
            }
         }
      }
   }
}
