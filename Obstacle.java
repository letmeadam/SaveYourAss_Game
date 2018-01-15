import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * @author Adam Levasseur
 */

public class Obstacle extends StaticThing
{
    public Obstacle(int x, int y)
    {
        super(x, y);
    }
    
    public void act() 
    {
        setPos();
    }    
}
