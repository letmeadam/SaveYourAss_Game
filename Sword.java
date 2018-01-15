import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Sword here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sword extends StaticThing
{
    private GreenfootImage swordStone = new GreenfootImage("excalibur.png");
    private GreenfootImage noSwordStone = new GreenfootImage("excalibur_out.png");
    
    private boolean swordInStone = true;
    private boolean swordInHand = false; //changed to false, might destroy everything
    
    public Sword(int x, int y) {
       super(x,y);
       
       swordStone.scale(100,100);
       noSwordStone.scale(100,100);
       
       setImage(swordStone);
    }

    /**
     * Act - do whatever the Sword wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.act();
    }
    
    public boolean takeSword() {
       if (swordInStone && isTouching(Player.class) && Greenfoot.isKeyDown("Shift")) {
          swordInStone = false;
          swordInHand = true;
          
          setImage(noSwordStone);
          
          //System.out.println("Insert Dialogue: You now have the sword.");
          
          return true;
       }
       
       return false;
    }
    
    public boolean isSwordInHand() {
       return swordInHand;    
    }
}