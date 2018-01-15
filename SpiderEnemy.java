import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * @author Lauren Kirk, Matt Moren
 */

public class SpiderEnemy extends MovingThing
{
    private GreenfootImage[] spider;
    private int currentSpider = 0;
    
    //private int maxLeft = 930;
    //private int maxRight = 1100;
   
    private boolean goLeft = true;
    private int initDimX = 0;
    private int initDimY = 0;
    private int setGoLeft = 150;
    private int setGoRight = 30;
    
    private static final int OPAQUE = 255;
    private static final int TRANSPARENT = 0;
    private int fadeRate = OPAQUE; 
    
    public SpiderEnemy(int x, int y) {
       super(x, y);
       
       initDimX = x;
       initDimY = y;
       
       spider = initAnimationState("spider", ".png", 8);
    }
    
    /**
     * Act - do whatever the SpiderEnemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.act();
        
        currentSpider = (currentSpider + 1) % spider.length;
        setImage(spider[currentSpider]);
        
        wander();
    }
    
    public GreenfootImage[] initAnimationState(String file_name, String file_type, int num_frames) {
       GreenfootImage[] temp = new GreenfootImage[num_frames];
       int scale_factor = 2;
      
       for (int i = 0; i < num_frames; i++) {
          temp[i] = new GreenfootImage(file_name + i + file_type);
          temp[i].scale(temp[i].getWidth() * scale_factor, temp[i].getHeight() * scale_factor);
          temp[i].mirrorHorizontally();
       }
       
       //Note, 0 is the defaulted "starting" image
       setImage(temp[0]);
       
       return temp;
    }
    
    public void wander() {
       int centerPoint = initDimX;
      
       if (goLeft) {
          if (globalPos[0] < centerPoint - setGoLeft) {
              goLeft = false;
          }
          else {
             movePos(-10, 0);
          }
       }
       else {
          if (globalPos[0] > centerPoint + setGoRight) {
             goLeft = true;
          }
          else {
            movePos(10, 0);    
          }
       }
    }
    
    /* boolean returns true if totally faded*/
    public boolean fadeToDie() {
        //System.out.println("Fading them");
        
        fadeRate -= 2;
        
        if (fadeRate < 0) {
           return true;    
        }
        
        for (int i = 0; i < spider.length; i++) {
            spider[i].setTransparency(fadeRate);
        }
        
        return false;
    }
    
    //Stop Wandering Motion
    public void death() {
       setGoLeft = 0;
       setGoRight = 0;
    }
}
