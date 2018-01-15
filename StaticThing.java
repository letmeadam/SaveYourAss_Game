import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * @author Adam Levasseur
 */

public class StaticThing extends Actor
{
    public int[] globalPos = new int[2];
    public int[] bBox = {0, 0, 0, 0}; // L, R, T, B
    
    public ArrayList<GreenfootImage> anim = new ArrayList<GreenfootImage>();
    private Background my_bg = null;
    
    public boolean cutSceneMode = false;
    
    public StaticThing(int x, int y)
    {
        globalPos[0] = x;
        globalPos[1] = y;
        
        resetBBox();
    }
    
    public void act() 
    {
        setPos();
    }

    public void setPos()
    {
        /* Static Things dont move, therefore they move with the background */
        setLocation(globalPos[0] + getBackground().getX(), globalPos[1] + getBackground().getY());
    }
    
    public void setPos(int x, int y)
    {
        globalPos[0] = x;// + getBackground().getX();
        globalPos[1] = y;// + getBackground().getY();
    }
    
    public void movePos(int dx, int dy)
    {
        globalPos[0] += dx;
        globalPos[1] += dy;
    }
    
    public Background getBackground()
    {
        return my_bg;
    }
    
    public void setBackground(Background bg)
    {
        my_bg = bg;
    }
    
    public boolean getCutSceneMode()
    {
        return cutSceneMode;
    }
    
    public void setCutSceneMode(boolean value)
    {
        cutSceneMode = value;
    }
    
    public boolean findBackground()
    {
        ArrayList<Background> bg = new ArrayList<Background>(getIntersectingObjects(Background.class));
        if (bg.size() > 0)
        {
            my_bg = bg.get(0);
            return true;
        }
        else
        {
            my_bg = null;
            return false;
        }
    }
    
    public void resetBBox()
    {
        bBox[0] = -getImage().getWidth()  / 2;
        bBox[1] =  getImage().getWidth()  / 2;
        bBox[2] = -getImage().getHeight() / 2;
        bBox[3] =  getImage().getHeight() / 2;
    }
    
    public int getLeft()
    {
        return bBox[0];
    }
    public int getRight()
    {
        return bBox[1];
    }
    public int getTop()
    {
        return bBox[2];
    }
    public int getBot()
    {
        return bBox[3];
    }
}
