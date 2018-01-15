import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;

/**
 * @author Adam Levasseur, Lauren Kirk
 */

public class Skull extends MovingThing
{
   private static final int STATIONARY_IMAGE_NUM = 0;
   private static final int INACTIVE_SIGNAL = 20;
   private static final int DEFAULT_ROCKING_SPEED = 5;
   private static final int MAX_RIGHT = 10;
   private static final int MAX_LEFT = -10;
   private static final int MIN_DEGREE = 0;
 
   // Values for Image Animation
   private GreenfootImage[] skull;
   private int currentSkull = 0;
   private int inactive_frames = 0;
   
   // Values for Idle Rocking
   private int currentAngle = MIN_DEGREE;
   private boolean rockRight = true;
   private int rockingSpeed = DEFAULT_ROCKING_SPEED;
   
   private boolean goLeft = true;
   private int initDimX = 0;
   private int initDimY = 0;
   private int setGoLeft = 0;
   private int setGoRight = 0;
    
   public Skull(int x, int y, int setGoLeft, int setGoRight)
   {
      super(x, y);
      
      initDimX = x;
      initDimY = y;
      
      this.setGoLeft = setGoLeft;
      this.setGoRight = setGoRight;
      
      skull = initAnimation("Skull_T_", ".png", 4);
   }
   
   public void act() 
   {
       super.act();
       
       if (actionTriggered()) {
          inactive_frames = 0;
       }
       
       if (inactive_frames >= INACTIVE_SIGNAL) {
          setImage(skull[STATIONARY_IMAGE_NUM]);
          startRocking(rockingSpeed);
       }
       else {
          currentSkull = (currentSkull + 1) % skull.length;
          setImage(skull[currentSkull]);
          
          inactive_frames++;
       }
       
       wander();
   }
   
   public GreenfootImage[] initAnimation(String file_name, String file_type, int num_frames) {
      GreenfootImage[] temp = new GreenfootImage[num_frames];
      Random random = new Random();
      int scale_factor = 40;
      
      for (int i = 0; i < num_frames; i++) {
         temp[i] = new GreenfootImage(file_name + i + file_type);
         temp[i].scale(temp[i].getWidth() - scale_factor, temp[i].getHeight() - scale_factor);
      }
      
      currentSkull = random.nextInt(num_frames - 1);
      setImage(temp[currentSkull]);
      
      return temp;
   }

   public boolean actionTriggered() {
      return Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("down") ||
             Greenfoot.isKeyDown("space");
   }
   
   public void startRocking(int rotationSpeed) {      
      if (rockRight) {
         if (currentAngle < MAX_RIGHT) {
            currentAngle += rotationSpeed;    
         }
         else {
            rockRight = false;
         }
      }
      else { //if rotateLeft
         if (currentAngle > MAX_LEFT) {
            currentAngle -= rotationSpeed;
         }
         else {
            rockRight = true;
         }
      }
    
      setRotation(currentAngle);
   }
   
   public void wander() {
       int centerPoint = initDimX;
      
       if (goLeft) {
          if (globalPos[0] < centerPoint - setGoLeft) {
              goLeft = false;
          }
          else {
             movePos(-5, 0);
          }
       }
       else {
          if (globalPos[0] > centerPoint + setGoRight) {
             goLeft = true;
          }
          else {
            movePos(5, 0);    
          }
       }
   }
}

