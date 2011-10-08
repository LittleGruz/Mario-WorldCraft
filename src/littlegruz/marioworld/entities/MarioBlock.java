package littlegruz.marioworld.entities;

import org.bukkit.Location;
import org.bukkit.Material;

public class MarioBlock{
   private Location loc;
   private Material original;
   private boolean hit;
   private String blockType;
   
   public MarioBlock(Location loc, String type){
      this.loc = loc;
      hit = false;
      original = loc.getBlock().getType();
      blockType = type;
   }
   
   public MarioBlock(Location loc, Material mat, String type){
      this.loc = loc;
      hit = false;
      original = mat;
      blockType = type;
   }
   
   public boolean isHit(){
      return hit;
   }
   
   public void setHit(boolean hit){
      this.hit = hit;
   }
   
   public void setType(Material type){
      original = type;
   }
   
   public Material getType(){
      return original;
   }
   
   public Location getLocation(){
      return loc;
   }

   public void setBlockType(String blockType){
      this.blockType = blockType;
   }

   public String getBlockType(){
      return blockType;
   }
}
