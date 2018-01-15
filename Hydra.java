import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;

/**
 * @author ?
 */

public class Hydra extends StaticThing
{
   private GreenfootImage[] neutral;
   private GreenfootImage[] one_off;
   private GreenfootImage[] two_off;
   private GreenfootImage[] three_off;
   private GreenfootImage[] four_off;
   private GreenfootImage[] five_off;
   private int currentFrame = 0;
   public  int head_off = 0; //5 means neutral /0 means dead
   private int slow = 0;

   public Hydra(int x, int y)
   {
      super(x, y);
      neutral   = initAnimation("hydraN",     ".png", 4);
      one_off   = initAnimation("hydraONE",   ".png", 4);
      two_off   = initAnimation("hydraTWO",   ".png", 4);
      three_off = initAnimation("hydraTHREE", ".png", 4);
      four_off  = initAnimation("hydraFOUR",  ".png", 4);
      five_off  = initAnimation("hydraFIVE",  ".png", 4);
   }
    
   public void act() 
   {
       super.act();
       slow++;
       if (slow == 4)
       {
           slow = 0;
           currentFrame = (currentFrame + 1) % 4; //all the arrays are length 4
       }
       
       
       switch (head_off)
       {
          case 0:
             setImage(neutral[currentFrame]);
             break;
          case 1:
             setImage(one_off[currentFrame]);
             break;
          case 2:
             setImage(two_off[currentFrame]); 
             break;
          case 3:
             setImage(three_off[currentFrame]); 
             break;
          case 4:
             setImage(four_off[currentFrame]);
             break;
          case 5:
             setImage(five_off[currentFrame]); 
             break;
       }
        
   }
    
    public GreenfootImage[] initAnimation(String file_name, String file_type, int num_frames)
    {
      GreenfootImage[] temp = new GreenfootImage[num_frames];
      Random random = new Random();
      int scale_factor = 2;
      
      for (int i = 0; i < num_frames; i++)
         temp[i] = new GreenfootImage(file_name + i + file_type);
      
      currentFrame = random.nextInt(num_frames - 1);
      setImage(temp[currentFrame]);
      
      return temp;
   }
   
   public void cutHead()
   {
       head_off++;
       if (head_off > 5)
          head_off = 5;
   }
   
   public boolean isDead()
   {
       return (head_off == 5);
   }
}
