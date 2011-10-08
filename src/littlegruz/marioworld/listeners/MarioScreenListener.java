package littlegruz.marioworld.listeners;

import littlegruz.marioworld.MarioMain;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.screen.ScreenListener;
import org.getspout.spoutapi.event.screen.ScreenOpenEvent;
import org.getspout.spoutapi.gui.ScreenType;

public class MarioScreenListener extends ScreenListener{
   MarioMain plugin;
   
   public MarioScreenListener(MarioMain instance){
      plugin = instance;
   }
   
   public void onScreenOpen(ScreenOpenEvent event){
      if(event.getScreenType().compareTo(ScreenType.INGAME_MENU) == 0){
         // File size 31KB
         SpoutManager.getSoundManager().playCustomMusic(plugin, SpoutManager.getPlayer(event.getPlayer()), "http://sites.google.com/site/littlegruzsplace/download/smb_pause.wav", false);
      }
   }
}
