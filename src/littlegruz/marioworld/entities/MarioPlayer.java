package littlegruz.marioworld.entities;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class MarioPlayer{
   private String playaName;
   private String state;
   private long prevFall;
   private Block hitBlock;
   private Location checkpoint;
   private int coins;
   private int lives;

   public MarioPlayer(String player, Location spawn){
      playaName = player;
      checkpoint = spawn;
      state = "Small";
      prevFall = 0;
      hitBlock = null;
      coins = 0;
      lives = 0;
   }
   
   public MarioPlayer(String player, String state, Location spawn, int coins, int lives){
      playaName = player;
      this.state = state;
      checkpoint = spawn;
      prevFall = 0;
      hitBlock = null;
      this.coins = coins;
      this.lives = lives;
   }

   public String getPlayaName(){
      return playaName;
   }

   public void setPlayaName(String playa){
      this.playaName = playa;
   }

   public String getState(){
      return state;
   }

   public void setState(String state){
      this.state = state;
   }

   public void setPrevFall(long prevFall){
      this.prevFall = prevFall;
   }

   public long getPrevFall(){
      return prevFall;
   }

   public void setHitBlock(Block hitBlock){
      this.hitBlock = hitBlock;
   }

   public Block getHitBlock(){
      return hitBlock;
   }

   public void setCheckpoint(Location checkpoint){
      this.checkpoint = checkpoint;
   }

   public Location getCheckpoint(){
      return checkpoint;
   }

   public void setCoins(int coins){
      this.coins = coins;
   }

   public int getCoins(){
      return coins;
   }

   public void setLives(int lives){
      this.lives = lives;
   }

   public int getLives(){
      return lives;
   }
}
