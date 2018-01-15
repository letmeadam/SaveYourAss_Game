import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The purpose of HeartLife class is to scale a *.png to appropriate size
 * such that it can be applied to existing world.
 * 
 * @author Lauren Kirk
 * @version 4.30.2017 V1
 */

public class HeartLife extends Actor
{    
    private static final int SCALING_FACTOR = 4;
    private static final String DEFAULT_HEART = "CarrotHearts.png"; 
    
    private GreenfootImage heart = null;
    
    //Protagonist's Heart (Default)
    public HeartLife()
    {
       this(DEFAULT_HEART, SCALING_FACTOR);
    }
    
    //Specified Heart (E.G. For Non-Protagonist Actors)
    public HeartLife(String filename, int scaling_factor)
    {
       heart = new GreenfootImage(filename);
       
       heart.scale(heart.getWidth() / scaling_factor,
                   heart.getHeight() / scaling_factor);
                   
       setImage(heart);
    }
}
