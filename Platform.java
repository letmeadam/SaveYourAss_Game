import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Platform here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Platform extends Obstacle
{   
    public Platform(int x, int y)
    {
        super(x, y);
    }
    
    public void act() 
    {
        setPos();
    }    
}
