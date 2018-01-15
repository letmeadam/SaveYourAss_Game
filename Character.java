import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Character here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Character extends StaticThing
{
    private int xSpeed = 0;
    private int ySpeed = 0;
    
    public int fallingSpeed = 0;
    public int acceleration = 1;
    public int max_speed = 10;
 
    private GreenfootImage[] anim;
    public int currentFrame = 0;
    
    public boolean facingRight = true;
    
    public boolean landed = false;
    public int jumpSpeed = 15;
    
    public SpeechBubble dialogue;
    
    public Character(int x, int y)
    {
        super(x, y);
        dialogue = new SpeechBubble();
        
        GreenfootImage img = new GreenfootImage(dialogue.getImage());
        img.setTransparency(0);
        dialogue.setImage(img);
    }
    
    public Character(int x, int y, GreenfootImage my_image)
    {
        super(x, y);
        dialogue = new SpeechBubble();
        setImage(my_image);
        
        GreenfootImage img = new GreenfootImage(dialogue.getImage());
        img.setTransparency(0);
        dialogue.setImage(img);
    }
    
    public Character(int x, int y, int jumpS)
    {
        super(x, y);
        dialogue = new SpeechBubble();
        jumpSpeed = jumpS;
        
        GreenfootImage img = new GreenfootImage(dialogue.getImage());
        img.setTransparency(0);
        dialogue.setImage(img);
    }
    
    public void initialize()
    {
        getWorld().addObject(dialogue, getX(), getY());
        anim = new GreenfootImage[] {getImage()};
    }
    
    public void act() 
    {
        checkFalling();
        movePos(getXSpeed(), getYSpeed());
        setPos();
        
        updateDialogue(getImage().getWidth() / 2, -getImage().getHeight() / 2, facingRight);
 
        currentFrame = (currentFrame + 1) % anim.length;
        setImage(anim[currentFrame]);
    }

    public void loadAnimation(String file_name, String file_type, int num_frames, float scale) {
        anim = initAnimation(file_name, file_type, num_frames, scale).clone();
    }
    
    public GreenfootImage[] initAnimation(String file_name, String file_type, int num_frames) {
        return initAnimation(file_name, file_type, num_frames, 1.0f);
    }
    
    public GreenfootImage[] initAnimation(String file_name, String file_type, int num_frames, float scale) {
       GreenfootImage[] temp = new GreenfootImage[num_frames];
      
       for (int i = 0; i < num_frames; i++) {
          temp[i] = new GreenfootImage(file_name + i + file_type);
          temp[i].scale((int) (temp[i].getWidth() / scale), (int) (temp[i].getHeight() / scale));
       }
      
       currentFrame = 0;
       setImage(temp[currentFrame]);
       resetBBox();
       return temp;
    }
   
    public void flipAnimation(GreenfootImage[] array, int num_frames) {
       for (int i = 0; i < num_frames; i++) {
          array[i].mirrorHorizontally();      
       }
      
       //Note, 0 is the defaulted "starting" image
       setImage(array[0]);
    }
    
    public void setPos()
    {
        setLocation(globalPos[0] + getBackground().getX(), globalPos[1] + getBackground().getY());
        //setLocation(globalPos[0], globalPos[1]);
    }
    
    public void setPos(int x, int y)
    {
        globalPos = new int[]{x, y};
    }
    
    public void movePos(int dx, int dy)
    {
        //System.out.println("Attempting to move X: " + dx + " Y: " + dy);
        int signX, signY, i;
        int objFound = 0;

        if (dx != 0)
        {
            signX = dx / Math.abs(dx);
            
            // Check horizontal collision
            for (i = 0; i < Math.abs(dx); i++)
            {
                int offsetX;
                
                if (signX < 0)
                {
                    offsetX = getLeft() + signX * i;
                }
                else
                {
                    offsetX = getRight() + signX * i;
                }
                
                // Check horizonal collision
                if(getOneObjectAtOffset(offsetX, getTop(), Obstacle.class) != null || 
                   getOneObjectAtOffset(offsetX, getBot(), Obstacle.class) != null)
                {
                    objFound = 1;
                    break;
                }
            }
            
            // Set horizontal position to a valid one
            if (i != 0)
            {
                globalPos[0] += signX * (i - objFound);
            }
            else
            {
                globalPos[0] += -1 * signX;
            }

            // Check/Correct attempt to run off edge of world
            if (globalPos[0] <= getImage().getWidth() / 2)
            {
                globalPos[0] = getImage().getWidth() / 2 + 1;
            }
            else if (globalPos[0] >= getBackground().getWidth() - getImage().getWidth() / 2)
            {
                globalPos[0] = getBackground().getWidth() - getImage().getWidth() / 2 - 1;
            }
        }
        
        objFound = 0;
        
        if (dy != 0)
        {
            signY = dy / Math.abs(dy);
            
            // Check vertical collision
            for (i = 0; i < Math.abs(dy); i++)
            {
                int offsetY;
                
                if (signY < 0)
                {
                    offsetY = getTop() + signY * i;
                }
                else
                {
                    offsetY = getBot() + signY * i;
                }
                
                // Check horizonal collision
                if(getOneObjectAtOffset(getLeft(),  offsetY, Obstacle.class) != null || 
                   getOneObjectAtOffset(getRight(), offsetY, Obstacle.class) != null)
                {   
                    objFound = 1;
                    fallingSpeed = 0;
                    break;
                }
            }
            
            // Set vertical position to a valid one}
            if (i != 0)
            {
                globalPos[1] += signY * (i - objFound);
            }
            else
            {
                globalPos[1] += -1 * signY;
            }
   
            // Check/Correct attempt to run off edge of world
            if (globalPos[1] <= getImage().getHeight() / 2)
            {
                globalPos[1] = getImage().getHeight() / 2 + 1;
            }
            else if (globalPos[1] >= getBackground().getHeight() - getImage().getHeight() / 2)
            {
                globalPos[1] = getBackground().getHeight() - getImage().getHeight() / 2 - 1;
            }
        }
        
        correctCollision();
        
        //Stop
        xSpeed = 0;
        ySpeed = 0;
        //targetVec[0] = targetVec[1] = 0;
    }
   
    public void correctCollision()
    {
        //Collision at Left edge, move Right
        if (getOneObjectAtOffset(getLeft(), getTop(), Obstacle.class) != null || 
            getOneObjectAtOffset(getLeft(), getBot(), Obstacle.class) != null)
        {
            globalPos[0]++;
            if (globalPos[0] >= getBackground().getWidth() - getImage().getWidth() / 2)
            {
                globalPos[0] = getBackground().getWidth() - getImage().getWidth() / 2 - 1;
                //NOTE: really don't want to handle this case of Player between rock and a hard - edge of the map
            }
        }
        
        //Collision at Right edge, move Left
        if (getOneObjectAtOffset(getRight(), getTop(), Obstacle.class) != null || 
            getOneObjectAtOffset(getRight(), getBot(), Obstacle.class) != null)
        {
            globalPos[0]--;
            if (globalPos[0] <= getImage().getWidth() / 2)
            {
                globalPos[0] = getImage().getWidth() / 2 + 1;
                //NOTE: really don't want to handle this case of Player between rock and a hard - edge of the map
            }
        }
        
        //Collision at Top edge, move Down
        if (getOneObjectAtOffset(getLeft(),  getTop(), Obstacle.class) != null || 
            getOneObjectAtOffset(getRight(), getTop(), Obstacle.class) != null)
        {
            globalPos[1]++;
            if (globalPos[1] >= getBackground().getHeight() - getImage().getHeight() / 2)
            {
                globalPos[1] = getBackground().getHeight() - getImage().getHeight() / 2 - 1;
                //NOTE: really don't want to handle this case of Player between rock and a hard - edge of the map
            }
        }
        
        //Collision at Bot edge, move Up
        if (getOneObjectAtOffset(getLeft(),  getBot(), Obstacle.class) != null || 
            getOneObjectAtOffset(getRight(), getBot(), Obstacle.class) != null)
        {
            globalPos[1]--;
            if (globalPos[1] <= getImage().getHeight() / 2)
            {
                globalPos[1] = getImage().getHeight() / 2 + 1;
                //NOTE: really don't want to handle this case of Player between rock and a hard - edge of the map
            }
        }
    }
    
    public void checkFalling()
    {
        Obstacle bottomLeft =   (Obstacle) getOneObjectAtOffset(getLeft(),  getBot() + 1, Obstacle.class);
        Obstacle bottomRight =  (Obstacle) getOneObjectAtOffset(getRight(), getBot() + 1, Obstacle.class);
        Obstacle bottomCenter = (Obstacle) getOneObjectAtOffset((getLeft() + getRight()) / 2, getBot() + 1, Obstacle.class);
        
        int groundScore = 0;
        if (bottomLeft != null)
            groundScore++;
        if (bottomCenter != null)
            groundScore++;
        if (bottomRight != null)
            groundScore++;
      
        if (groundScore < 2)
        {
            fallingSpeed += acceleration;
            if (fallingSpeed > max_speed)
                fallingSpeed = max_speed;
            //System.out.println("Should Fall. " + fallingSpeed);
            
            //targetVec[1] = fallingSpeed;
            ySpeed = fallingSpeed;
            landed = false;
        }
        else
        {
            //System.out.println("Should NOT Fall.");
            fallingSpeed = 0;
            //targetVec[1] = 0;
            ySpeed = 0;
            landed = true;
        }
    }
    
    public void jump()
    {
        if (landed == true && jumpSpeed > 0)
        {
            fallingSpeed = -jumpSpeed;
            //targetVec[1] = fallingSpeed;
            ySpeed = fallingSpeed;
            //System.out.println("Jump done.");
            landed = false;
        }
    }
    
    public int getXSpeed(){
       return xSpeed;
    }
    
    public void setXSpeed(int xSpeed) {
       this.xSpeed = xSpeed;
    }
    
    public int getYSpeed() {
       return ySpeed;
    }
    
    public void setYSpeed(int ySpeed) {
       this.ySpeed = ySpeed;
    }
    
    public void updateDialogue(int offsetX, int offsetY, boolean facingR)
    {
        if (facingR == true)
        {
            dialogue.updateLocation(getX() + offsetX, getY() + offsetY, true);
        }
        else
        {
            dialogue.updateLocation(getX() - offsetX, getY() + offsetY, false);
        }
    }
    
    public void say(GreenfootImage emoji) {
        dialogue.setDialogue(emoji);
    }
}
