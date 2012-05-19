package littlegruz.marioworld.commands;

import java.util.Locale;
import java.util.ResourceBundle;

import littlegruz.marioworld.MarioMain;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LanguageStuff implements CommandExecutor{
   private MarioMain plugin;
   
   public LanguageStuff(MarioMain instance){
      plugin = instance;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command cmd,
         String commandLabel, String[] args){
      if(cmd.getName().compareToIgnoreCase("changelanguage") == 0){
         if(args.length == 1){
            if(sender.hasPermission("marioworld.admincommands")){
               if(args[0].compareToIgnoreCase("english") == 0){
                  plugin.setCurrentRB(ResourceBundle.getBundle("littlegruz/marioworld/languages/language", Locale.ENGLISH));
                  plugin.getConfig().set("language", "english");
                  sender.sendMessage(plugin.getCurrentRB().getString("LanguageChange"));
                  if(plugin.isSpoutEnabled() && sender instanceof Player)
                     plugin.getGui().update((Player) sender);
               }
               else if(args[0].compareToIgnoreCase("spanish") == 0){
                  plugin.setCurrentRB(ResourceBundle.getBundle("littlegruz/marioworld/languages/language", plugin.getSpanishLocale()));
                  plugin.getConfig().set("language", "spanish");
                  sender.sendMessage(plugin.getCurrentRB().getString("LanguageChange"));
                  if(plugin.isSpoutEnabled() && sender instanceof Player)
                     plugin.getGui().update((Player) sender);
               }
               else if(args[0].compareToIgnoreCase("aussie") == 0){
                  plugin.setCurrentRB(ResourceBundle.getBundle("littlegruz/marioworld/languages/language", plugin.getAussieLocale()));
                  plugin.getConfig().set("language", "aussie");
                  sender.sendMessage(plugin.getCurrentRB().getString("LanguageChange"));
                  if(plugin.isSpoutEnabled() && sender instanceof Player)
                     plugin.getGui().update((Player) sender);
               }
               else if(args[0].compareToIgnoreCase("romanian") == 0){
                  plugin.setCurrentRB(ResourceBundle.getBundle("littlegruz/marioworld/languages/language", plugin.getRomanianLocale()));
                  plugin.getConfig().set("language", "romanian");
                  sender.sendMessage(plugin.getCurrentRB().getString("LanguageChange"));
                  if(plugin.isSpoutEnabled() && sender instanceof Player)
                     plugin.getGui().update((Player) sender);
               }
               else if(args[0].compareToIgnoreCase("german") == 0){
                  plugin.setCurrentRB(ResourceBundle.getBundle("littlegruz/marioworld/languages/language", Locale.GERMAN));
                  plugin.getConfig().set("language", "german");
                  sender.sendMessage(plugin.getCurrentRB().getString("LanguageChange"));
                  if(plugin.isSpoutEnabled() && sender instanceof Player)
                     plugin.getGui().update((Player) sender);
               }
            } else{
               sender.sendMessage(plugin.getCurrentRB().getString("PermissionDeny"));
            }
         }
         else
            sender.sendMessage(plugin.getCurrentRB().getString("WrongArguments"));
      }
      return true;
   }

}
