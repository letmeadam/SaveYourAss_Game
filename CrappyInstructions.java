import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CrappyInstructions here.
 * 
 * @author Lauren Kirk
 */
public class CrappyInstructions extends Actor
{
    private GreenfootImage underworld_p1 = new GreenfootImage("CrappyInstructions.png");
    
    private int transparency = 255;
    
    public CrappyInstructions() {
       setImage(underworld_p1);
    }
    
    public void act() 
    {
        if (transparency >= 0) {
           underworld_p1.setTransparency(transparency);
        
           transparency -= 2; 
       }
    }    
}
