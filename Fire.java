import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;

/**
 * Fixes Scaling Issue
 */
public class Fire extends StaticThing
{
    private static final int DEFAULT_ERROR = 40;
    
    Random random = new Random();
    
    private GreenfootImage fire[];
    private int currentFire = 0;
    
    private int initDimX = 0;
    private int initDimY = 0;
    
    private int fireAct = 3; //Slow FrameRate of Fire by a Third
    
    private int scaleError = DEFAULT_ERROR;
    
    public FireHitBox hitBox = null;
    private int hitX = 0;
    private int hitY = 0;
    
    public Fire(int x, int y) {
       super(x,y);
       
       fire = initAnimation("Fire_T_", ".png", 8);
       
       // L, R, T, B      
       bBox[0] = -getImage().getWidth()  - 30;
       bBox[1] =  getImage().getWidth()  - 30;
       bBox[2] = -getImage().getHeight() - 50;
       bBox[3] =  getImage().getHeight() / 2;
       
       hitBox = new FireHitBox(x,y);
    }
    
    public void act() 
    {
        super.act();
        
        if (scaleError == 0) {
           fire = initAnimation("Fire_T_", ".png", 8);
           initScale(initDimX, initDimY);
           scaleError = DEFAULT_ERROR;
        }
        
        if (fireAct < 0) {
           //Random Scale Width Growth, Random Scale Height Growth
           int xScale = initDimX + random.nextInt(8) - 4;
           int yScale = initDimY + random.nextInt(16) - 8;
 
           if (xScale <= 0) {
              xScale = 1;    
           }
           
           if (yScale <= 0) {
               yScale = 1;
           }
           
           fire[currentFire].scale(xScale, yScale);
           scaleError--;
        
           //Animate
           currentFire = (currentFire + 1) % fire.length;
           setImage(fire[currentFire]);
           
           fireAct = 3;
        }
        else {
           fireAct--;
        }
    }
    
    public GreenfootImage[] initAnimation(String file_name, String file_type, int num_frames) {
      GreenfootImage[] temp = new GreenfootImage[num_frames];
      int scale_factor = 10;
      
      for (int i = 0; i < num_frames; i++) {
         temp[i] = new GreenfootImage(file_name + i + file_type);
      }
      
      currentFire = random.nextInt(num_frames - 1);
      setImage(temp[currentFire]);
      
      return temp;
    }
    
    public void initScale(int xScale, int yScale) {
        initDimX = xScale;
        initDimY = yScale;
        
        for (int i = 0; i < fire.length; i++) {
            fire[i].scale(xScale, yScale);
        }
    }
    
    public void placeHitBox(World world) {
       world.addObject(hitBox, hitX, hitY);    
    }
    
    public FireHitBox getHitBox() {
       return hitBox;    
    }
}
