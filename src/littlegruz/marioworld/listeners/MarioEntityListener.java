package littlegruz.marioworld.listeners;

import littlegruz.marioworld.MarioMain;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityInteractEvent;
import org.getspout.spoutapi.SpoutManager;

public class MarioEntityListener implements Listener{
   private static MarioMain plugin;
   
   public MarioEntityListener(MarioMain instance){
      plugin = instance;
   }

   @EventHandler
   public void onEntityDamage(EntityDamageEvent event){
      if(plugin.getWorldMap().containsKey(event.getEntity().getWorld().getUID().toString())){
         if(plugin.isMarioDamage() && event.getEntity() instanceof Player){
            Player playa = (Player) event.getEntity();
            if(plugin.getPlayerMap().get(playa.getName()).isInvincible()){
               /* If player is invincible then kill whatever touched the player
                * or just ignore any projectile or environment damage*/
               if(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent){
                  EntityDamageByEntityEvent entityDamageEvent = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
                  if(entityDamageEvent.getDamager() instanceof Monster)
                     ((Monster) entityDamageEvent.getDamager()).damage(1000);
               }
               event.setCancelled(true);
               return;
            }
            
            /* Check if the damage taken is from a monster, if it is then demote
             * the players state. But if it is lava, then kill the player */
            if(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent){
               EntityDamageByEntityEvent entityDamageEvent = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
               // Check what caused the damage
               String entityName = entityDamageEvent.getDamager().toString().substring(5);
               if(entityDamageEvent.getDamager() instanceof Monster
                     || entityName.compareToIgnoreCase("arrow") == 0){
                  if(plugin.getPlayerMap().get(playa.getName()).getState().compareToIgnoreCase("Large") == 0){
                     plugin.getPlayerMap().get(playa.getName()).setState("Small");
                     playa.sendMessage("You've shrunk");
                     SpoutManager.getSoundManager().playCustomSoundEffect(plugin, SpoutManager.getPlayer(playa), "https://sites.google.com/site/littlegruzsplace/download/smb3_powerdown.wav", true);
                  }else if(plugin.getPlayerMap().get(playa.getName()).getState().compareToIgnoreCase("Small") == 0){
                     plugin.deathSequence(playa);
                     playa.damage(1000);
                  }else if(plugin.getPlayerMap().get(playa.getName()).getState().compareToIgnoreCase("Fire") == 0){
                     plugin.getPlayerMap().get(playa.getName()).setState("Large");
                     playa.sendMessage("You've shrunk");
                     SpoutManager.getSoundManager().playCustomSoundEffect(plugin, SpoutManager.getPlayer(playa), "https://sites.google.com/site/littlegruzsplace/download/smb3_powerdown.wav", true);                      
                  }
               }
               plugin.getGui().update(playa);
            }else{
               event.setCancelled(true);
               if(event.getCause().compareTo(DamageCause.FIRE) == 0
                     || event.getCause().compareTo(DamageCause.LAVA) == 0){
                  plugin.deathSequence(playa);
                  playa.damage(1000);
               }
            }
         } else if(event.getCause().compareTo(DamageCause.PROJECTILE) == 0
               && event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent){
            EntityDamageByEntityEvent entityDamageEvent = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
            if(entityDamageEvent.getDamager() instanceof Egg){
               Egg egg = (Egg) entityDamageEvent.getDamager();
               if(egg.getShooter() instanceof Player){
                  Player playa = (Player) egg.getShooter();
                  if(event.getEntity() instanceof Creature){
                     Creature cretin = (Creature) event.getEntity();
                     if(plugin.getPlayerMap().get(playa.getName()) != null
                           && plugin.getPlayerMap().get(playa.getName()).getState().compareToIgnoreCase("fire") == 0){
                        cretin.damage(20);
                     }
                  }
               }
            }
         }
      }
   }

   @EventHandler
   public void onEntityInteract(EntityInteractEvent event){
      if(event.getEntity() instanceof Player){
         Player player = (Player) event.getEntity();
         player.sendMessage(event.getEventName());
      }
   }
}
