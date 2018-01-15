import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;

/**
 * @author Lauren Kirk, Matt Moren
 */

public class Siren extends StaticThing
{
    private GreenfootImage[] anim;
    private int currentFrame = 0;
    
   public Siren(int x, int y)
   {
      super(x, y);
      anim = initAnimation("siren", ".png", 4);
   }
   
   public void act() 
   {
       super.act();
       
       currentFrame = (currentFrame + 1) % anim.length;
       setImage(anim[currentFrame]);
   }    

   public GreenfootImage[] initAnimation(String file_name, String file_type, int num_frames) {
      GreenfootImage[] temp = new GreenfootImage[num_frames];
      Random random = new Random();
      int scale_factor = 2;
      
      for (int i = 0; i < num_frames; i++) {
         temp[i] = new GreenfootImage(file_name + i + file_type);
      }
      
      currentFrame = random.nextInt(num_frames - 1);
      setImage(temp[currentFrame]);
      
      return temp;
   }
   

}
