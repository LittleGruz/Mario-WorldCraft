package littlegruz.marioworld.listeners;

import littlegruz.marioworld.MarioMain;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;
import org.getspout.spoutapi.SpoutManager;

public class MarioEntityListener extends EntityListener{
   private static MarioMain plugin;
   
   public MarioEntityListener(MarioMain instance){
      plugin = instance;
   }
   
   public void onEntityDamage(EntityDamageEvent event){
      if(plugin.getWorldMap().containsKey(event.getEntity().getWorld().getUID().toString()) && plugin.isMarioDamage()){
         if(plugin.isMarioDamage() && event.getEntity() instanceof Player){
            Player playa = (Player) event.getEntity();
            String entityName;
            
            /* Check if the damage taken is from a monster, if it is then demote
             * the players state. But if it is lava, then kill the player */
            if(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent){
               EntityDamageByEntityEvent entityDamageEvent = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
               entityName = entityDamageEvent.getDamager().toString().substring(5);
               // Check what caused the damage
               if(entityName.compareToIgnoreCase("arrow") == 0
                     || entityName.compareToIgnoreCase("zombie") == 0
                     || entityName.compareToIgnoreCase("spider") == 0
                     || entityName.compareToIgnoreCase("creeper") == 0
                     || entityName.compareToIgnoreCase("enderman") == 0){
                  if(plugin.getPlayerMap().get(playa.getName()).getState().compareToIgnoreCase("Large") == 0){
                     plugin.getPlayerMap().get(playa.getName()).setState("Small");
                     playa.sendMessage("You've shrunk");
                     SpoutManager.getSoundManager().playCustomMusic(plugin, SpoutManager.getPlayer(playa), "http://sites.google.com/site/littlegruzsplace/download/smb3_powerdown.wav", true);
                  }else if(plugin.getPlayerMap().get(playa.getName()).getState().compareToIgnoreCase("Small") == 0){
                     playa.setHealth(0);
                     SpoutManager.getSoundManager().playCustomMusic(plugin, SpoutManager.getPlayer(playa), "http://sites.google.com/site/littlegruzsplace/download/smb_gameover.wav", true);
                  }
               }
            }else{
               event.setCancelled(true);
               if(event.getCause().compareTo(DamageCause.FIRE_TICK) == 0
                     || event.getCause().compareTo(DamageCause.LAVA) == 0){
                  playa.setHealth(0);
                  SpoutManager.getSoundManager().playCustomMusic(plugin, SpoutManager.getPlayer(playa), "http://sites.google.com/site/littlegruzsplace/download/smb_gameover.wav", true);
               }
            }
         }
      }
   }
}
