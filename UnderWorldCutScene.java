import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UnderWorldCutScene extends World
{
    private int currentFrame = 0;
    
    public UWCS_knight knight;
    
    public UnderWorldCutScene()
    {    
        super(1200, 600, 1, false);
        //Greenfoot.playSound("Underworld_intro.mp3");
        knight = new UWCS_knight();
        UWCS_fall fall = new UWCS_fall();
        fall.adjust();
        addObject(fall, 600, 300);
        addObject(knight, 600, 10);
        addObject(new UWCS_intro(), 540, 550);
        addObject(new UWCS_side_wall(), 125, 350);
        addObject(new UWCS_side_wall(), 1075, 350);
        
        UWCS_fire f1 = new UWCS_fire("fire.gif");
        UWCS_fire f2 = new UWCS_fire("fire.gif");
        f1.setRotation(90);
        f2.setRotation(90);
        addObject(f1, 150, 200);
        addObject(f2, 150, 600);
        
        UWCS_fire f3 = new UWCS_fire("fire.gif");
        UWCS_fire f4 = new UWCS_fire("fire.gif");
        f3.setRotation(270);
        f4.setRotation(270);
        addObject(f3, 1050, 140);
        addObject(f4, 1050, 530);
    }
    
    public void act()
    {
        if (knight.getY() > getHeight() + knight.getImage().getHeight() / 2) {
            knight.intro.stop();
            Greenfoot.setWorld(new UnderWorldPartI());
        }
    }
}
