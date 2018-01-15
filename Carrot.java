import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * @author Lauren Kirk, Adam Levasseur
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
