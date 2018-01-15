import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * @author Matt Moren
 */

public class UWCS_fall extends Actor
{
    /**
     * Act - do whatever the fall wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        
    }   
    
    public void adjust()
    {
        GreenfootImage img = new GreenfootImage(getImage());
        img.scale(img.getWidth(), 600);
        setImage(img);
    }
}
