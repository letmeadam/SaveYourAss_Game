import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Fireball here.
 * 
 * @author Lauren Kirk, Adam Levasseur
 */
public class Fireball extends MovingThing
{
    private static final int MAX_SPEED = 10;
    
    private GifImage fb = new GifImage("fireball.gif");
    
    public boolean rightward = true;
    
    public Fireball (int x, int y, boolean goingRight) {
        super(x, y);
        rightward = goingRight;
    }
   
    public void act() 
    {
            
        setImage(fb.getCurrentImage()); //cycle images
        //move towards player if he is on screen
        updatePos();
        setPos();
    }
    public void updatePos()
    {
        if (!rightward)
        {
            globalPos[0] -= MAX_SPEED;
            setRotation(180);
        }
        else
        {
            globalPos[0] += MAX_SPEED;
            setRotation(0);
        }
    }
    
    public boolean isHitting(Player p)
    {
        if ((globalPos[1] - getImage().getHeight() / 8 < p.globalPos[1] + p.getImage().getHeight() / 2) && 
            (globalPos[1] + getImage().getHeight() / 8 > p.globalPos[1] - p.getImage().getHeight() / 2))
            {
                if ((globalPos[0] - getImage().getWidth() / 6 < p.globalPos[0] + p.getImage().getWidth() / 2) && 
                    (globalPos[0] + getImage().getWidth() / 6 > p.globalPos[0] - p.getImage().getWidth() / 2))
                    {
                        return true;
                    }
            }
        return false;
    }
}

