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
      if(event.getScreenType().compareTo(ScreenType.INGAME_MENU) == 0 && plugin.getWorldMap().containsKey(event.getPlayer().getWorld().getName())){
         // File size 31KB
         SpoutManager.getSoundManager().playCustomSoundEffect(plugin, SpoutManager.getPlayer(event.getPlayer()), "https://sites.google.com/site/littlegruzsplace/download/smb_pause.wav", false);
      }
   }
}
