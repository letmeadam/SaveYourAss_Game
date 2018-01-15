import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Current Bugs in Player: 
 *    (1) Some images not transparent.
 */
public class Player extends Character
{
   private static final int IDLE_WAKE_STATE = 0;
   private static final int IDLE_SLEEP_STATE = 1;
   private static final int MOVING_STATE = 2;
   private static final int FIGHTING_STATE = 3;
   
   private static final int INACTIVE_SIGNAL = 80;
   
   private static final int TRAVEL_SPEED = 10;
    
   private GreenfootImage[] idleWake;
   private int currentIdleWake = 0;
         
   private GreenfootImage[] idleSleep;
   private int currentIdleSleep = 0;

   private GreenfootImage[] walking;
   private int currentWalking = 0;
   
   private GreenfootImage[] sword;
   private int currentSword = 0;
   
   private GreenfootImage[] shield;
   private int currentShield = 0;
   
   //public boolean facingRight = true;  // moved to Character class  

   private int state = IDLE_WAKE_STATE;
   private int inactive_frames = 0;

   private boolean hasSword  = false;
   private boolean hasShield = false;
   private boolean isAlive   = true;
   private boolean blocking  = false;

   private World myWorld = getWorld();
   private GreenfootSound snoring = new GreenfootSound("Snoring.wav");
   
   private boolean[] cutSceneMove = {false, false, false};
    
   public Player(int x, int y)
   {
      super(x, y);

      sword     = initAnimationState("Sword",       ".png", 3);
      idleWake  = initAnimationState("Idle_Awake_", ".png", 2);
      idleSleep = initAnimationState("Idle_Sleep_", ".png", 4);
      walking   = initAnimationState("Walking",     ".png", 6);
      shield    = initAnimationState("Shield",      ".png", 3);
  
      setCollisionBorders(walking[0]);
   }

   public Player(int x, int y, int jumpS)
   {
      super(x, y, jumpS);

      sword     = initAnimationState("Sword",       ".png", 3);
      idleWake  = initAnimationState("Idle_Awake_", ".png", 2);
      idleSleep = initAnimationState("Idle_Sleep_", ".png", 4);
      walking   = initAnimationState("Walking",     ".png", 6);
      shield    = initAnimationState("Shield",      ".png", 3);
        
      setCollisionBorders(walking[0]);
   }

   public void act() 
   {
       blocking = false; // reset blocking state
       updatePos();     //update the position
       setPos();        //set appropriate position
       updateDialogue(getImage().getWidth() / 2, -getImage().getHeight() / 2, facingRight); //update dialogue
       //checkDialogue(); //set appropriate dialogue
   }
   
   public void setCollisionBorders(GreenfootImage img) {
      //Defining Bounding Box Around Character (For Dimensions)
      bBox[0] = -img.getWidth()  / 2 - 2; //Left
      bBox[1] =  img.getWidth()  / 2 - 2; //Right
      bBox[2] = -img.getHeight() / 2; //Top
      bBox[3] =  img.getHeight() / 2; //Bottom
   }
   
   public GreenfootImage[] initAnimationState(String file_name, String file_type, int num_frames) {
      GreenfootImage[] temp = new GreenfootImage[num_frames];
      int scale_factor = 4;
      
      for (int i = 0; i < num_frames; i++) {
         temp[i] = new GreenfootImage(file_name + i + file_type);
         temp[i].scale(temp[i].getWidth() / scale_factor, temp[i].getHeight() / scale_factor - 5);
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
    
   private void updatePos()
   {
       checkFalling();
       
       if (isAlive) {   
           checkControls();
       }
       //checkControls();
       
       movePos(getXSpeed(), getYSpeed());
   }
    
   private void checkControls()
   {
       boolean keyPressed = false;
       
       if (getBackground() == null)
       {
           return;
       }
             
       if ((Greenfoot.isKeyDown("right") && cutSceneMode == false) || (cutSceneMode == true && cutSceneMove[1] == true))
       {
           /* Character moves in right direction of the world */
           setXSpeed(TRAVEL_SPEED);
           keyPressed = true;
            
           if (!facingRight)
           {
               flipAnimation(walking, 6);
               flipAnimation(idleWake, 2);
               flipAnimation(idleSleep, 4);
               flipAnimation(sword, 3);
               flipAnimation(shield, 3);
       
               facingRight = true;
           }
       }
       else if ((Greenfoot.isKeyDown("left") && cutSceneMode == false) || (cutSceneMode == true && cutSceneMove[0] == true))
       {
           /* Character moves in left direction of the world */
           setXSpeed(-TRAVEL_SPEED);
           keyPressed = true;
           if (facingRight)
           {
               flipAnimation(walking, 6);
               flipAnimation(idleWake, 2);
               flipAnimation(idleSleep, 4);
               flipAnimation(sword, 3);
               flipAnimation(shield, 3);
                             
               facingRight = false;
           }
       }
       else if (Greenfoot.isKeyDown("Space") && hasSword) {
          //Add Dialogue
          keyPressed = true;
       }
       else if (Greenfoot.isKeyDown("down") && hasShield) {
          //Add Dialogue
          keyPressed = true;
       }
       else
       {
           setXSpeed(0);
       }
        
       if ((Greenfoot.isKeyDown("up") && cutSceneMode == false) || (cutSceneMode == true && cutSceneMove[2] == true) || isTouching(FireHitBox.class))
       {
           if (isTouching(FireHitBox.class)) {
              //System.out.println("Playe: TODO: Add Dialogue Saying: 'Ouch! Touched Fire!");;    
            
              if (facingRight) {
                 movePos(-20, 0);
              }
              else {
                 movePos(20, 0);
              }
           }
           jump();
           keyPressed = true;
       }
        
       if (keyPressed || (cutSceneMode == true && (cutSceneMove[0] == true || cutSceneMove[1] == true)))
       {
          if (Greenfoot.isKeyDown("Space") & hasSword)
            animateFighting();     
          else if (Greenfoot.isKeyDown("down") & hasShield)
          {
              blocking = true;
              animateBlocking();
          }
          else
             animateWalking();
          inactive_frames = 0;
       } 
       else
       {
         if (inactive_frames < INACTIVE_SIGNAL || cutSceneMode == true)
         {
            animateIdleWake();
            inactive_frames++;
         }
         else
         {  
            if (!snoring.isPlaying())
            {
               snoring.setVolume(50);
               snoring.play();
            }
            
            animateIdleSleep();
         }
       }
       
       if (keyPressed && snoring.isPlaying()){
          snoring.pause();
       }
    }
    
    public void animateWalking() {
       currentWalking = (currentWalking + 1) % walking.length;
       setImage(walking[currentWalking]);   
    }
    
    public void animateIdleWake() {
        int num_frames = idleWake.length;
        int acts_per_frame = 3; //NOTE: frame increments every three acts
        
        currentIdleWake = (currentIdleWake + 1) % (num_frames * acts_per_frame);
        setImage(idleWake[currentIdleWake / acts_per_frame]);
    }
    
    public void animateIdleSleep() {
       int num_frames = idleSleep.length;      
       int acts_per_frame = 5; //NOTE: frame increments every five acts
       
       currentIdleSleep = (currentIdleSleep + 1) % (num_frames * acts_per_frame);
       setImage(idleSleep[currentIdleSleep / acts_per_frame]);
    }
    
    public void animateFighting() {
        int num_frames = sword.length;
        int acts_per_frame = 2; //NOTE: frame increments every two acts
        
        currentSword = (currentSword + 1) % (num_frames * acts_per_frame);
        setImage(sword[currentSword / acts_per_frame]);
    }
    
    public void animateBlocking() {
        int num_frames = shield.length;
        int acts_per_frame = 2; //NOTE: frame increments every two acts
        
        currentShield = (currentShield + 1) % (num_frames * acts_per_frame);
        setImage(shield[currentShield / acts_per_frame]);
    }
        
    public void setPos()
    {
        int x, y;
        
        if (getBackground() == null || globalPos[0] <= getWorld().getWidth() / 2)
        {
            x = globalPos[0];
        }
        else if (globalPos[0] >= getBackground().getWidth() - getWorld().getWidth() / 2)
        {
            x = globalPos[0] - getBackground().getWidth() + getWorld().getWidth();
        }
        else
        {
            x = getWorld().getWidth() / 2;
        }
            
        if (getBackground() == null || globalPos[1] <= getWorld().getHeight() / 2)
        {
            y = globalPos[1];
        }
        else if (globalPos[1] >= getBackground().getHeight() - getWorld().getHeight() / 2)
        {
            y = globalPos[1] - getBackground().getHeight() + getWorld().getHeight();
        }
        else
        {
            y = getWorld().getHeight() / 2;
        }
        
        setLocation(x, y);
        //updateDialogue(getImage().getWidth() / 2, -getImage().getHeight() / 2, facingRight);
        //say.updateLocation(x + getImage().getWidth() / 2, y - getImage().getHeight() / 2);
    }
    
    public int getSpeed()
    {
        return TRAVEL_SPEED;
    }
    
    public void csLeft()
    {
        cutSceneMove[0] = true;
        cutSceneMove[1] = false;
    }
    
    public void csRight()
    {
        cutSceneMove[0] = false;
        cutSceneMove[1] = true;
    }
    
    public void csJump()
    {
        cutSceneMove[2] = true;
    }
    
    public void csStill()
    {
        cutSceneMove[0] = false;
        cutSceneMove[1] = false;
        cutSceneMove[2] = false;
    }
    
    //Override Protected Declaration
    public boolean isTouching(java.lang.Class<?> cls) {
       return super.isTouching(cls);
    }
    
    //Override Protected Declaration
    public void removeTouching(java.lang.Class<?> cls) {
        super.removeTouching(cls);
    }
    
    //Override Protected Declaration
    public <A> java.util.List<A> getObjectsInRange(int radius, Donkey donkey) {
        return getObjectsInRange(radius, donkey);
        //super(radius, cls);
    }
    
    //Override ProtectedDeclaration
    public boolean intersects(Actor actor) {
       return super.intersects(actor); 
    }
    
    //Override Protected Declaration
    public Fire getOneOffsetFire() {
       return (Fire) super.getOneObjectAtOffset(0, -25, Fire.class); 
    }
    
    //For all images
    public void setTransparency(int transparency) {
      for (int i = 0; i < 6; i++) {
         walking[i].setTransparency(transparency);
         
         if (i < 2) {
            idleWake[i].setTransparency(transparency);
         }
      }     
    }
    
    //For attacking
    public void killThing() {
       if(Greenfoot.isKeyDown("Space") && hasSword) {
          if (hasSword) { //a for attack!
             //System.out.println("Kill thing.");

             /*if(isTouching(Fire.class)) {
                removeTouching(Fire.class);    
             }*/
          
             if(isTouching(Skull.class)) {
                removeTouching(Skull.class);
             }
             
             if (isTouching(SpiderWebs.class)) {
                //System.out.println("Touching SpiderWeb.");
                removeTouching(SpiderWebs.class);    
             }
          }
       }
    }
    
    public void setSword(boolean hasSword) {
       this.hasSword = hasSword;    
    }
    
    public boolean hasSword() {
       return hasSword;    
    }
    
    public boolean hasShield() {
       return hasShield;
    }
    
    public void giveShield() {
        hasShield = true;
    }
    
    public boolean isBlocking() {
        return blocking;
    }
    
    public void setAliveStatus(boolean isAlive) {
       this.isAlive = isAlive;     
    }
}
