import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * @author Adam Levasseur
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
