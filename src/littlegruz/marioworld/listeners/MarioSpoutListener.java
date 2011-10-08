package littlegruz.marioworld.listeners;

import littlegruz.marioworld.MarioMain;

import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.event.spout.SpoutListener;

public class MarioSpoutListener extends SpoutListener{
   MarioMain plugin;
   
   public MarioSpoutListener(MarioMain instance){
      plugin = instance;
   }
   
   public void onSpoutCraftEnable(SpoutCraftEnableEvent event){
      if(plugin.getWorldMap().containsKey(event.getPlayer().getWorld().getUID().toString())){
         plugin.getGui().update(event.getPlayer());
      }
   }
}
