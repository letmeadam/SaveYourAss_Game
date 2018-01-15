import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Donkey Object. Is the "Pet" and a "Friendly" of the Player. For the most part, simply does animation & dialogue.
 * 
 * To Do:
 *    (dialogue not yet implemented).
 *    (Noise, not yet implemented).
 *    (Character recognition, not yet implemented).
 * 
 * Bugs:
 *     1. Can't put donkey at edge of map...? 
 *
 * @author Lauren Kirk
 * @version 5.1.2017 V1
 */

public class UEPI_Donkey extends StaticThing
{
    private GreenfootImage[] standing;
    private int currentStanding = 0;
    
    private GreenfootImage[] walking;
    private int currentWalking = 0;

   private GreenfootImage[] happy;
    private int currentHappy = 0;

    
   public UEPI_Donkey(int x, int y) {
      super(x,y);
      
      standing = initAnimationState("Donkey_Standing_", ".gif", 3);
     
      happy = initAnimationState("Donkey_Happy_", ".gif", 2);

      walking = initAnimationState("Donkey_Walking_", ".gif", 4);
   }
    
   public void act() 
   {
       super.act();
       
       if (getObjectsInRange(200, Player.class).size() != 0) {
          animateHappy();          
       }
       else {
          animateStanding();
       }
   }
    
   public GreenfootImage[] initAnimationState(String file_name, String file_type, int num_frames) {
      GreenfootImage[] temp = new GreenfootImage[num_frames];
      int scale_factor = 2;
      
      for (int i = 0; i < num_frames; i++) {
         temp[i] = new GreenfootImage(file_name + i + file_type);
         temp[i].scale(temp[i].getWidth() / scale_factor, temp[i].getHeight() / scale_factor);
      }
      
      //Note, 0 is the defaulted "starting" image
      setImage(temp[0]);
      
      return temp;
   }
   
   public void flipAnimation(GreenfootImage[] array, int num_frames) {
      for (int i = 0; i < num_frames; i++) {
         array[i].mirrorHorizontally();      
      }
      
      //Note, 0 is the defaulted "starting" image
      setImage(array[0]);
   }
   
   public void animateStanding() {
      int num_frames = standing.length;
      int acts_per_frame = 3; //NOTE: frame increments every five acts
        
      currentStanding = (currentStanding + 1) % (num_frames * acts_per_frame);
      
      setImage(standing[currentStanding / acts_per_frame]);
   }
   
   public void animateHappy() {
      int num_frames = happy.length;
      int acts_per_frame = 3; //NOTE: frame increments every five acts
        
      currentHappy = (currentHappy + 1) % (num_frames * acts_per_frame);
      
      setImage(happy[currentHappy / acts_per_frame]);
   } 
   
   public void animateWalking() {
      int num_frames = walking.length;
      int acts_per_frame = 3; //NOTE: frame increments every five acts
        
      currentWalking = (currentWalking + 1) % (num_frames * acts_per_frame);
      setImage(walking[currentWalking / acts_per_frame]);
   }
}
