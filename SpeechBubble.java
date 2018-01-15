import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/** VERSION 1
 * The SpeechBubble is an actor which can be assigned to an actor. Once the actor has a
 * SpeechBubble assignment, they will be able to talk, or express emotion using the 
 * pre-made emoji dialogues.
 * 
 * Implementations under consideration (not yet applicable):
 *  ( 1 ) Constructor takes a player's current location & posts comment above(and slightly to the side) of the character.
 *  ( 2 ) Restrict a character's movements when speaking?
 * 
 * @author Lauren Kirk
 * @version 4.29.2017 V1
 * 
 * 
 ** VERSION 2
 * The SpeechBubble is an actor embedded into each Character class. Each Character
 * has the ability to call a "say()" method to display a speech bubble with the
 * desired emoji/expression. The images are now loaded in the World class upon 
 * start-up and copied upon each new call of "say()".
 * 
 * Implementation Notes:
 *  ( 1 ) Expected proper use of SpeechBubble class in World class -> "player.say(dialogues.get("angry"));"
 *  ( 2 ) Embedded SpeechBubble instance in each Character class will display as transparent initially and fade out after "say()" call
 * 
 * @author Lauren Kirk, Adam Levasseur
 * @version 4.30.2017 V2
 * 
 * VERSION X
 * Subsequent versions remain undocumented due to lack of organization and sanity of developers
 *  - Adam
 */

public class SpeechBubble extends Actor
{    
    // Note, FTL = "Frames to Live", number of frames that a dialogue box is be displayed
    private static final int DEFAULT_FTL = 10;
    private static final int TRANSPARENT = 0;
    private static final int OPAQUE      = 255;
    
    private GreenfootImage bubble;
    private GreenfootImage speechR;
    private GreenfootImage speechL;
    
    private boolean facingRight = true;
    
    private int image_ftl    = 0;
    private int transparency = TRANSPARENT;
   
    /*
     * A default constructor which allows for the drag-and-drop functionality of
     * the Greenfoot API to succeed without error. This is useful for testing basic
     * functionality (that is, functionality independent of other actors) of the
     * speech-bubble.
     * 
     * The subsequent constructors will be more applicable to an actual project where
     * speech-bubbles and actors are required to interact.
     */
    public SpeechBubble()
    {
    }
    
    public SpeechBubble(GreenfootImage bubble)
    {
       this.bubble = new GreenfootImage(bubble);
    }
    
    public SpeechBubble(GreenfootImage bubble, float scale)
    {
       this.bubble = new GreenfootImage(bubble);
       this.bubble.scale((int) (bubble.getWidth() / scale), (int) (bubble.getHeight() / scale));
    }
    
    public void setBubble(GreenfootImage bubble)
    {
       this.bubble = new GreenfootImage(bubble);
       this.speechR = new GreenfootImage(bubble);
       this.speechL = new GreenfootImage(bubble);
       this.speechL.mirrorHorizontally();
    }

    public void setDialogue(GreenfootImage emoji)
    {
        image_ftl = DEFAULT_FTL;
        
        speechR = new GreenfootImage(bubble);
        speechR.drawImage(emoji, speechR.getWidth() / 4, speechR.getHeight() / 6);
        
        speechL = new GreenfootImage(bubble);
        speechL.mirrorHorizontally();
        speechL.drawImage(emoji, speechL.getWidth() / 4, speechL.getHeight() / 6);
        
        transparency = OPAQUE;
    }
    
    public void act() 
    {        
        if (image_ftl > 0)
        {
           speechR.setTransparency(OPAQUE);
           speechL.setTransparency(OPAQUE);
           image_ftl--;
        }
        else
        {
           if (transparency > TRANSPARENT)
           {
              transparency -= 15;
              speechR.setTransparency(transparency);
              speechL.setTransparency(transparency);
           }
        }
        
        if (transparency > TRANSPARENT)
        {
            if (facingRight == true)
                 setImage(speechR);
            else
                 setImage(speechL);
        }
    }
   
    //Purpose, to be accessed by actor who has spoken such that
    //they can make the speech-bubble follow them when they move
    //and until the speech bubble expires
    public void updateLocation(int moveX, int moveY, boolean facingR)
    {
       if (bubble == null)
        return;
        
       facingRight = facingR;
       
       if (facingR == true)
       {
           setLocation(moveX + bubble.getWidth() / 2, moveY - bubble.getHeight() / 2);
       }
       else
       {
           setLocation(moveX - bubble.getWidth() / 2, moveY - bubble.getHeight() / 2);
       }
    }
}
