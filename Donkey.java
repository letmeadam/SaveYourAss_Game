import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;

/**
 * BUGS:
 *    (1) Moves with the actor (is on a fixed position).
 *        Will move under "Moving Thing" once the bug 
 *        (unable to place item) there gets fixed.
 */
public class Donkey extends Character
{
   // Values for Image Animation
   private GreenfootImage[] anim;
   private int currentFrame = 0;

   public Donkey(int x, int y)
   {
      super(x, y);
      anim = initAnimation("Donkey", ".png", 4);
      facingRight = false;
   }
   
   public void act() 
   {
       checkFalling();
       movePos(getXSpeed(), getYSpeed());
       setPos();
        
       updateDialogue(getImage().getWidth() / 4, -getImage().getHeight() / 3, facingRight);
 
       currentFrame = (currentFrame + 1) % anim.length;
       setImage(anim[currentFrame]);
   }
   
   public GreenfootImage[] initAnimation(String file_name, String file_type, int num_frames) {
      GreenfootImage[] temp = new GreenfootImage[num_frames];
      Random random = new Random();
      int scale_factor = 2;
      
      for (int i = 0; i < num_frames; i++) {
         temp[i] = new GreenfootImage(file_name + i + file_type);
         temp[i].scale(temp[i].getWidth() / scale_factor, temp[i].getHeight() / scale_factor);
         temp[i].mirrorHorizontally();
      }
      
      currentFrame = random.nextInt(num_frames - 1);
      setImage(temp[currentFrame]);
      
      return temp;
   }
   
   public void flipAnimation() {
       flipAnimation(anim, 4);
   }
   
   public void flipAnimation(GreenfootImage[] array, int num_frames) {
      for (int i = 0; i < num_frames; i++) {
         array[i].mirrorHorizontally();      
      }
      
      //Note, 0 is the defaulted "starting" image
      setImage(array[0]);
   }
}
