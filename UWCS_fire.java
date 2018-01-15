import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class fire here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UWCS_fire extends Actor
{
    /**
     * Act - do whatever the fire wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    //setRotation(90);
    
    private String img;
    private GifImage fire = new GifImage("fire.gif");
    
    public UWCS_fire (String img) {
        this.img = img;
        fire = new GifImage(img);
    }
    //GifImage fire = new GifImage("fire.gif");
    public void act() 
    {
        setImage(fire.getCurrentImage());
    }    
}
