import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
  * HeartControls Purpose: Add's player's lives to screen. Will need to be modified to implement
  * life-points to give "lives" to other actors. (To do so, look into updating the xHearts Offset)
  * @author Lauren Kirk
  */

public class HeartController extends Actor
{
    private static final String DEFAULT_HEART = "CarrotHearts.png"; 
    private static final String ENEMY_HEART = "EvilHeart.png";
    private static final int GRACE_PERIOD_TIMER = 15;
    
    private static final int MAXIMUM_NUM_LIVES = 10;
    public static final int DEFAULT_NUM_LIVES = 3; //is public
    private static final int INITIAL_OFFSET = 25;
    
    private int scaling_factor = 4;
    private boolean isRightJustified = false;
    
    private HeartLife[] lives = new HeartLife[MAXIMUM_NUM_LIVES];
    private int heart_idx = 0;
    
    //Offset
    private int yHeartsOffset = INITIAL_OFFSET;
    private int xHeartsOffset = INITIAL_OFFSET;
    
    private String heartName = DEFAULT_HEART;
    
    private int lostLifeGracePeriod = GRACE_PERIOD_TIMER;
    
    public HeartController() {       
       //Note, adding lives cannot be done in the constructor as a "world"
       //must exist for the life-image to be placed. Unfortunately, for an
       //actors in the constructor stage do not know of a "world" with which
       //they will be placed. (I know, its stupid).
       
       //Default Constructor Used for Player's Lives
    }
    
    //Populates Hearts in Right-hand side of Screen. To be used for "Bosses'"
    public HeartController(boolean isRightJustified, String heartName, int scaling_factor, int offset_adjust) {
       //int width = 400; //Width of Viewable World
                        //Can't use GetWorld().getWidth() b/c null pointer exception
        
       this.isRightJustified = isRightJustified;
       this.heartName = heartName;
       this.scaling_factor = scaling_factor;
       
        if (isRightJustified) {
           xHeartsOffset = offset_adjust - INITIAL_OFFSET;
       }
    }
    
    public void act() 
    {
       if (lostLifeGracePeriod < GRACE_PERIOD_TIMER) {
          lostLifeGracePeriod--;
          
          if (lostLifeGracePeriod == 0) {
             lostLifeGracePeriod = GRACE_PERIOD_TIMER;    
          }
       }
    }
    
    //Add Heart to Existing Lives (Heart).
    public void addLife(World world) {
       if (heart_idx < MAXIMUM_NUM_LIVES) {
          lives[heart_idx] = new HeartLife(heartName, 4);
          
          if (isRightJustified) {
             world.addObject(lives[heart_idx],
                             xHeartsOffset - lives[heart_idx].getImage().getWidth() * heart_idx,
                             yHeartsOffset);
          }
          else {
             world.addObject(lives[heart_idx],
                              xHeartsOffset + lives[heart_idx].getImage().getWidth() * heart_idx,
                              yHeartsOffset);
          }
          
          heart_idx++;
       }
       else {
          System.out.println();
       }
    }
    
    //Remove Heart From Existing Lives (Hearts).
    public void loseLife()
    {
       if(heart_idx > 0)
       {
          if (lostLifeGracePeriod == GRACE_PERIOD_TIMER)
          {
             heart_idx--;
          
             //Shitty Hack to "Remove" Heart from Screen
             lives[heart_idx].getImage().setTransparency(0);
             
             lostLifeGracePeriod--;
          }
       }
    }
    
    public void addXLives(int x, World world)
    {
       for (int i = 0; i < x; i++)
           addLife(world);    
    }
    
    public boolean isAlive()
    {
       return heart_idx > 0;    
    }
    
    public int numCurrentLives()
    {
       return heart_idx + 1;
    }
}
