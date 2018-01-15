import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * @author Adam Levasseur
 */
public class Background extends Actor
{
    public int[] offset = {0, 0};
    public int[] globalPos = {0, 0};
    public Player player = null;
    
    public boolean cutSceneMode = false;
    
    public Background()
    {
        GreenfootImage img = new GreenfootImage("Intro5.png");
        img.scale(4800, 600);
        setImage(img);
    }
    
    /* This method sets the Player Actor to follow and base image updates from */
    public void ApplyPlayer(Player character)
    {
        player = character;
    }
    
    /* This method is automatically called upon calling "addObject(Background, int, int)" in the World class */
    protected void addedToWorld(World world)
    {
        /* Place bottom-left corner of background at bottom-left corner of world */
        offset[0] =  getImage().getWidth()  / 2;
        offset[1] = -getImage().getHeight() / 2 + world.getHeight();
    }
   
    public void act() 
    {
        updatePos();
        
        /* Place Image with Bottom-Left Corner matched with world Bottom-Left corner */
        setLocation(offset[0] + globalPos[0], offset[1] + globalPos[1]);
    }
    
    private void updatePos()
    {
        if (player.globalPos[0] <= getWorld().getWidth() / 2)
        {
            globalPos[0] = 0;
        }
        else if(player.globalPos[0] >= getWidth() - getWorld().getWidth() / 2)
        {
            globalPos[0] = -getImage().getWidth() + getWorld().getWidth();
        }
        else
        {
            globalPos[0] = -player.globalPos[0] + getWorld().getWidth() / 2;
        }
       
        if (player.globalPos[1] <= getWorld().getHeight() / 2)
        {
            globalPos[1] = getHeight() / 2 - offset[1];
        }
        else if(player.globalPos[1] >= getHeight() - getWorld().getHeight() / 2)
        {
            globalPos[1] = 0;
        }
        else
        {
            globalPos[1] = -player.globalPos[1] + getHeight() - getWorld().getHeight() / 2;
        }
    }
    
    public int getX() {
        return globalPos[0];
    }
    public int getY() {
        return globalPos[1];
    }
    public int getWidth() {
        return getImage().getWidth();
    }
    public int getHeight() {
        return getImage().getHeight();
    }
    
    public boolean getCutSceneMode()
    {
        return cutSceneMode;
    }
    public void setCutSceneMode(boolean value)
    {
        cutSceneMode = value;
    }
}
