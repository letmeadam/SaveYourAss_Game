import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Carrot here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Carrot extends StaticThing
{
    private GreenfootImage carrot = new GreenfootImage("Carrot_T_0.png");
    
    public Carrot(int x, int y) {
       super(x,y);             
    }
    
    public void act() 
    {
        super.act();
        
        setRotation(getRotation() + 5);
    }    
}
