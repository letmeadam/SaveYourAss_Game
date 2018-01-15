import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * @author Adam Levasseur
 */

public class MovingThing extends StaticThing
{
    public MovingThing(int x, int y)
    {
        super(x, y);
    }

    public void act() 
    {
        updatePos();
    }

    private void updatePos()
    {
        setLocation(globalPos[0] + getBackground().getX(), globalPos[1] + getBackground().getY());
    }
}
