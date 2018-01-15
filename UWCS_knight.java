import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class knight here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UWCS_knight extends Actor
{
    /**
     * Act - do whatever the knight wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    int i = 0;
    int play = 1;
    
    GreenfootSound intro;
    
    public UWCS_knight()
    {
        intro = new GreenfootSound("Underworld_intro.mp3");
        intro.playLoop();
    }
   public void act() 
    {
        setLocation(getX(), getY() + 2);
        setRotation(i);
        i += 1;
    }    
}
