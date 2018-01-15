import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Boat here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Boat extends Platform
{
    private int floatNum = 0;
    private int floatAmp = 10;
    
    public Boat(int x, int y)
    {
        super(x, y);
    }
    
    public void act() 
    {
        updatePos();
        setPos();
        floatNum = (floatNum + 1) % floatAmp;
    }
    
    private void updatePos()
    {
        if (floatNum < floatAmp / 2)
            movePos(0, -1);
        else
            movePos(0,  1);
    }
}
