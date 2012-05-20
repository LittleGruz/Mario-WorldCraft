package littlegruz.marioworld.listeners;

import littlegruz.marioworld.MarioMain;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;

public class MarioSpoutListener implements Listener{
   MarioMain plugin;
   
   public MarioSpoutListener(MarioMain instance){
      plugin = instance;
   }

   @EventHandler
   public void onSpoutCraftEnable(SpoutCraftEnableEvent event){
      if(plugin.getWorldMap().containsKey(event.getPlayer().getWorld().getName())){
         plugin.getGui().update(event.getPlayer());
      }
   }
}
