package littlegruz.marioworld.listeners;

import littlegruz.marioworld.MarioMain;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.screen.ScreenOpenEvent;
import org.getspout.spoutapi.gui.ScreenType;

public class MarioScreenListener implements Listener{
   MarioMain plugin;
   
   public MarioScreenListener(MarioMain instance){
      plugin = instance;
   }

   @EventHandler
   public void onScreenOpen(ScreenOpenEvent event){
      if(event.getScreenType().compareTo(ScreenType.INGAME_MENU) == 0 && plugin.getWorldMap().containsKey(event.getPlayer().getWorld().getUID().toString())){
         // File size 31KB
         SpoutManager.getSoundManager().playCustomMusic(plugin, SpoutManager.getPlayer(event.getPlayer()), "http://sites.google.com/site/littlegruzsplace/download/smb_pause.wav", false);
      }
   }
}
