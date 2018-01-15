import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Fireball here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Fireball extends MovingThing
{
    private static final int MAX_SPEED = 10;
    
    private GifImage fb = new GifImage("fireball.gif");
    
    public boolean rightward = true;
    
    public Fireball (int x, int y, boolean goingRight) { //int dx, int dy) {
        super(x, y);
        rightward = goingRight;
        
        //float max = Math.abs((float) dx);// / (float) dy);
        //if (Math.abs(dy) > Math.abs(dx))
        //    max = Math.abs((float) dy);
            
        //max = 1.0f / max;
        //if (Math.abs(dx) > MAX_SPEED)
        //    dir[0] = (dx / Math.abs(dx)) * (int) ((float) MAX_SPEED * (float) Math.abs(dx) * max);
        //if (Math.abs(dy) > MAX_SPEED)
        //    dir[1] = (dy / Math.abs(dy)) * (int) ((float) MAX_SPEED * (float) Math.abs(dy) * max);
        
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

