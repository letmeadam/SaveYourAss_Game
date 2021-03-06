import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * @author Lauren Kirk
 */

public class SpiderWebs extends StaticThing
{
    public SpiderWebs(int x, int y) {
       super(x,y);
       
       getImage().scale(150, 150);
    }
    
    public void act() 
    {
        super.act();
        
        hideWebs();
    }
    
    public void hideWebs() {
       if (getObjectsInRange(600, Donkey.class).size() != 0) {
          getImage().setTransparency(0);    
       }
       else {
          getImage().setTransparency(255);
       }
    }
}
