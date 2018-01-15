import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    private static final int FRAME_RATE = 40;
    private static final int DEAD_LONG_TIME = 100;
    
    private static final GreenfootSound intro   = new GreenfootSound("Uplifting.wav");
    private static final GreenfootSound ouch    = new GreenfootSound("Ouch.wav");
    private static final GreenfootSound youDead = new GreenfootSound("YouDead.wav");
    
    private boolean cutSceneMode = false;//true;
    private int cutSceneNumber   = 0;
    private int cutSceneFrame    = 0;
    
    private HashMap<String, GreenfootImage> dialogues = new HashMap<String, GreenfootImage>();
    
    private HeartController lives = null;
    private boolean playDead = false; //make sound effect only happen once
    
    private int timeDead = DEAD_LONG_TIME;
    
    Background bg;
    Player player;
    Donkey donkey;
    Character blackSmith;
    Platform[] platforms;
    StaticThing[] carrots;
    Boat boat;
    Siren siren1;
    Siren siren2;
    StaticThing chest;
    Hydra hydra;
    StaticThing portal;
    StaticThing[] music = new StaticThing[3];
    
    /**
     * Constructor for objects of class MyWorld.
     */
    public MyWorld()
    {    
       super(1200, 600, 1, false);
        
       Greenfoot.setSpeed(FRAME_RATE);
       
       //Init Music
       intro.playLoop();
       intro.setVolume(30); //Volume 0(off) to 100(loud)
              
       prepare();
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        loadDialogues();
        
        /* Background is created with a offset in Global-space coordinates */
        bg = new Background();
        
        /* Player is created using Global-space coordinates */
        player = new Player(getWidth() * 3 / 2 - 45, 
                            bg.getHeight() - getHeight() / 4,
                            10);
      
        /* Set Background to follow Player (for side-scrolling) */
        bg.ApplyPlayer(player);
        
        /* Background is added using World-space coordinates*/
        addObject(bg, 
                  bg.getImage().getWidth() / 4 + 45,//bg.getImage().getWidth() / 3 - bg.getImage().getWidth() / 6 + 45,
                  getHeight() - bg.getImage().getHeight() / 2);
        
        /* Player is added using World-space coordinates */
        addObject(player, getWidth() / 2, getHeight() * 3 / 4);
       
        /** Donkey **/
        donkey = new Donkey(getWidth() * 3 / 2 + 125,
                            bg.getHeight() - getHeight() / 4);
        addObject(donkey, getWidth() / 2 + 175, getHeight() * 3 / 4);
        
        /** Hydra **/
        hydra = new Hydra(bg.getWidth() - 300, bg.getHeight() - getHeight() / 3 - 60);
        addObject(hydra, bg.getWidth() - 175, getHeight() * 3 / 4);
        
        /** Sirens **/
        siren1 = new Siren(bg.getWidth() - 1575, bg.getHeight() - getHeight() / 4 + 30);
        addObject(siren1, bg.getWidth() - 680, getHeight() * 3 / 4 + 65);
        
        siren2 = new Siren(bg.getWidth() - 1050, bg.getHeight() - getHeight() / 3 - 50);
        addObject(siren2, bg.getWidth() - 1100, getHeight() * 3 / 4 + 20);
        
        /** Blacksmith **/
        //bS_image.scale(bS_image.getWidth() * 2, bS_image.getHeight() * 2);
        blackSmith = new Character(-140, getHeight() - 200);
        addObject(blackSmith, -100, getHeight() / 2);
        
        /** Boat **/
        boat = new Boat(2200, getHeight() - 105);
        GreenfootImage boat_img = new GreenfootImage(boat.getImage());
        boat_img.scale(boat_img.getWidth() * 2, boat_img.getHeight() * 2);
        boat.setImage(boat_img);
        boat.resetBBox();
        addObject(boat, -100, -100);
        
        /** Music **/
        GreenfootImage music_img0 = new GreenfootImage("music_notes.png");
        music_img0.scale(100, 100);
        GreenfootImage music_img1 = new GreenfootImage("music_notes.png");
        music_img1.scale(100, 100);
        GreenfootImage music_img2 = new GreenfootImage("music_notes.png");
        music_img2.scale(100, 100);
        music[0] = new StaticThing(2700, getHeight() / 3 - 1000);
        music[1] = new StaticThing(2900, getHeight() / 3 + 50 - 1000);
        music[2] = new StaticThing(3100, getHeight() / 3 - 75 - 1000);
        music[0].setImage(music_img0);
        music[1].setImage(music_img1);
        music[2].setImage(music_img2);
        addObject(music[0], -100, -100);
        addObject(music[1], -100, -100);
        addObject(music[2], -100, -100);
        
        /** Islands (visual only) **/
        StaticThing island1 = new StaticThing(bg.getWidth() - bg.getWidth() / 7, getHeight() - 147);
        GreenfootImage island_img = new GreenfootImage("rock_island.png");
        island_img.scale(island_img.getWidth() * 2, island_img.getHeight()  * 6 / 4);
        island1.setImage(island_img);
        
        StaticThing island2 = new StaticThing(bg.getWidth() - bg.getWidth() / 7 - island_img.getWidth() * 3 / 4, getHeight() - 140);
        island2.setImage(island_img);
        addObject(island2, -200, -200);
        addObject(island1, -200, -200);
        
        /** Chest **/
        chest = new StaticThing(3500, 445);
        GreenfootImage chest_img = new GreenfootImage("chest_closed.png");
        chest_img.scale(chest_img.getWidth() * 2 / 3, chest_img.getHeight() * 2 / 3);
        chest.setImage(chest_img);
        addObject(chest, -100, -100);
        
        /** Portal **/
        portal = new StaticThing(-100, -500);//bg.getWidth() - 100, 550);
        GreenfootImage portal_img = new GreenfootImage("whirly.png");
        //portal_img.scale(portal_img.getWidth() / 2, portal_img.getHeight() / 2);
        //portal_img.rotate(90);
        portal.setImage(portal_img);
        addObject(portal, -100, -500);
        
        /** Adding objects to the world **/
        /* Most other objects are created in Global-space coordinates (origin referenced from top-left of Background image) - i.e. Object obj = new Object(g_posX, g_posY); */
        /* and added to the World using World-space coordinates (origin referenced from top-left of viewport) - i.e. addObject(Object, w_posX, w_posY);*/
        /**/
        
        AddWalls();
        AddCarrots();
        
        /* Connect all StaticThings to Background */
        ArrayList<StaticThing> objs = new ArrayList<StaticThing>(getObjects(StaticThing.class));
        for (int i = 0; i < objs.size(); i++)
        {
            objs.get(i).setBackground(bg);
        }
        
        /* Set all Characters' bubbles to generic speech bubble base */
        ArrayList<Character> chars = new ArrayList<Character>(getObjects(Character.class));
        for (int i = 0; i < chars.size(); i++)
        {
            chars.get(i).initialize();
            chars.get(i).dialogue.setBubble(dialogues.get("bubble1"));
        }
        
        /** Blacksmith - loading neverending image **/
        blackSmith.loadAnimation("Blacksmith", ".png", 4, 0.65f);
        
        setActOrder(Background.class, Obstacle.class, Character.class);
        setPaintOrder(SpeechBubble.class, Donkey.class, Player.class, Character.class, StaticThing.class);
    }
    
    public void checkHurt() {
       if (player.getImage().getTransparency() == 100) {
          player.getImage().setTransparency(255);
       }
       
       if (hydra.head_off < 5 && player.globalPos[0] > 4400) {
           if (!Greenfoot.isKeyDown("down") || !player.hasShield()) {
               ouch.setVolume(60);
               ouch.play();
          
               player.getImage().setTransparency(100);
               player.jump();
               lives.loseLife();    
           }
       }
    }
    
    public void restart() {
          lives.addLife(this);
          lives.addLife(this);
          lives.addLife(this);
          hydra.head_off = 0;
          player.globalPos[0] = 3120;
          player.globalPos[1] = 400;
          
          playDead = false;
    }
    
    public void act()
    {   
        // CHEAT CODES: LEVEL SELECT
        //if (Greenfoot.isKeyDown("0"))
        //    Greenfoot.setWorld(new UnderWorldCutScene());
        //if (Greenfoot.isKeyDown("1"))
        //    Greenfoot.setWorld(new UnderWorldPartI());
        //if (Greenfoot.isKeyDown("2"))
        //    Greenfoot.setWorld(new UnderWorldPartII());
        if (lives == null) {
            lives = new HeartController();
            lives.addXLives(3, this);
        }
        if (lives.isAlive()) {
            lives.act();
            checkHurt();
        }
        else {
             if (timeDead == 0) {
                //System.out.println("Is dead. Start Over");
                timeDead = DEAD_LONG_TIME;
                player.setRotation(0);
                player.setAliveStatus(true);
                restart();
             } else {
                if (!playDead) {
                   youDead.play();
                   playDead = true;
                }
                player.setAliveStatus(false);
                player.setRotation(-90);
                timeDead--;    
             }
          }
        
        ArrayList<Fireball> fbs = new ArrayList<Fireball>(getObjects(Fireball.class));
        for (int i = 0; i < fbs.size(); i++)
        {
            if (fbs.get(i).isHitting(player) == true)
            {
                if (player.isBlocking() == true)
                {
                    fbs.get(i).rightward = true;
                }
                else
                {
                    ouch.setVolume(60);
                    ouch.play();
          
                    player.getImage().setTransparency(100);
                    player.jump();
                    lives.loseLife();
                    removeObject(fbs.get(i));
                }
            }
            else if (fbs.get(i).globalPos[0] > hydra.globalPos[0])
            {
                if (fbs.get(i).rightward)
                {
                    hydra.cutHead();
                    removeObject(fbs.get(i));
                    if (hydra.isDead())
                    {
                        cutSceneMode = true;
                        cutSceneNumber++;
                        cutSceneFrame = 0;
                        break;
                    }
                }
            }
        }
        
        if (cutSceneMode == true)
        {
            ArrayList<Character> chars = new ArrayList<Character>(getObjects(Character.class));
            for (int i = 0; i < chars.size(); i++)
            {
                chars.get(i).setCutSceneMode(true);
            }
            
            bg.setCutSceneMode(true);
            
            executeCutScene();
        }
        else
        {
            ArrayList<Character> chars = new ArrayList<Character>(getObjects(Character.class));
            for (int i = 0; i < chars.size(); i++)
            {
                chars.get(i).setCutSceneMode(false);
            }
            
            bg.setCutSceneMode(false);
            
            //GreenfootImage temp = whirl.getImage();
            //temp.rotate(10);
            //whirl.setImage(temp);
            
            if (cutSceneNumber == 0)
                nonInvasiveCutScene();
            else if (player.globalPos[0] < 850 && cutSceneNumber == 2)
                cutSceneMode = true;
            else if (cutSceneNumber == 3)
            {
                if (player.globalPos[0] > 1950)
                {
                    player.say(dialogues.get("up"));
                    cutSceneMode = true;
                }
            }
            else if (cutSceneNumber == 5)
            {
                if (player.globalPos[0] > 3450 && player.globalPos[0] < 3550)
                {
                    if (player.hasShield() == false)
                    {
                        player.say(dialogues.get("T"));
                        if (Greenfoot.isKeyDown("shift") == true)
                        {
                            GreenfootImage chest_img = new GreenfootImage("chest_open.png");
                            chest_img.scale(chest_img.getWidth() * 2 / 3, chest_img.getHeight() * 2 / 3);
                            chest.setImage(chest_img);
                            player.giveShield();
                            player.say(dialogues.get("down"));
                            //cutSceneNumber++;
                        }
                    }
                }
                else if (player.globalPos[0] > 3550 && player.globalPos[0] < 3750 && player.hasShield() == true)
                {
                    player.say(dialogues.get("shield"));
                    cutSceneNumber++;
                }
            }
            else if (cutSceneNumber == 6)
            {
                if (cutSceneFrame == 100)
                {
                    if(player.isBlocking() == false)
                    {
                        Fireball fbomb = new Fireball(hydra.globalPos[0], hydra.globalPos[1]  - 50 + Greenfoot.getRandomNumber(250), false);
                        fbomb.setBackground(bg);
                        addObject(fbomb, hydra.getX(), hydra.getY());
                    }
                    
                    cutSceneFrame = 0;
                }
                cutSceneFrame++;
            }
        }
    } 
    
    public GreenfootSound getIntro() {
       return intro;    
    }
    
    public void pauseCurrentTheme() {
       if (intro.isPlaying()) {
          intro.pause();   
       }
    }
    
    public void resumeCurrentTheme() {
        if (!(intro.isPlaying())) {
           intro.play();    
        }
        
    }
    
    public void nonInvasiveCutScene()
    {
        if (cutSceneFrame == 0)
        {
            donkey.say(dialogues.get("carrot"));
            if (player.globalPos[0] < 1365 && player.globalPos[0] > 1245)
            {
                player.say(dialogues.get("T"));
                if (Greenfoot.isKeyDown("shift") == true)
                {
                    removeObject(carrots[2]);
                    cutSceneFrame++;
                }
            }
        }
        else if (cutSceneFrame == 1)
        {
            player.say(dialogues.get("carrot"));
            if (player.globalPos[0] < 1945 && player.globalPos[0] > 1845)
            {
                player.say(dialogues.get("T"));
                if (Greenfoot.isKeyDown("shift") == true)
                {
                    donkey.say(dialogues.get("heart eyes"));
                    player.say(dialogues.get("smiling with smiling eyes"));
                    cutSceneFrame++;
                }
            }
        }
        else if (cutSceneFrame < 21)
            cutSceneFrame++;
        else if (cutSceneFrame == 21)
        {
            donkey.say(dialogues.get("carrot"));
            if (player.globalPos[0] < 1365 && player.globalPos[0] > 1245)
            {
                player.say(dialogues.get("T"));
                if (Greenfoot.isKeyDown("shift") == true)
                {
                    removeObject(carrots[1]);
                    cutSceneFrame++;
                }
            }
        }
        else if (cutSceneFrame == 22)
        {
            player.say(dialogues.get("carrot"));
            if (player.globalPos[0] < 1945 && player.globalPos[0] > 1845)
            {
                player.say(dialogues.get("T"));
                if (Greenfoot.isKeyDown("shift") == true)
                {
                    donkey.say(dialogues.get("heart eyes"));
                    player.say(dialogues.get("smiling with smiling eyes"));
                    carrots[0].movePos(0, -1000);
                    //wind?
                    cutSceneFrame++;
                }
            }
        }
        else if (cutSceneFrame < 42)
            cutSceneFrame++;
        else if (cutSceneFrame == 42)
        {
            donkey.say(dialogues.get("carrot"));
            if (player.globalPos[0] < 1125 && player.globalPos[0] > 1035)
            {
                player.say(dialogues.get("T"));
                if (Greenfoot.isKeyDown("shift") == true)
                {
                    removeObject(carrots[0]);
                    removeObject(donkey);
                    cutSceneFrame++;
                }
            }
        }
        else if (cutSceneFrame >= 43)
        {
            player.say(dialogues.get("carrot"));
            if (player.globalPos[0] > 1345)
            {
                blackSmith.setPos(150, getHeight() - 200);
                cutSceneMode = true;
                //cutSceneNumber++;
                    //1755
                cutSceneFrame = 0;
            }
        }
    }
    
    public void executeCutScene()
    {
        if (cutSceneNumber == 0)
        {
            if (player.globalPos[0] < 1755) //Walk right to center of lawn
            {
                player.say(dialogues.get("worried"));
                player.csRight();
            }
            else
            {
                cutSceneNumber++;
                cutSceneFrame = 0;
            }
        }
        else if (cutSceneNumber == 1)
        {
            if (cutSceneFrame == 1)
                player.say(dialogues.get("worried"));
            else if (cutSceneFrame < 20) // wait for Worried dialogue to pass
                player.csStill();
            else if (cutSceneFrame == 20)
                player.say(dialogues.get("donkey"));
            else if (cutSceneFrame <  40)
                player.csStill();
            else if (cutSceneFrame == 40)
            {
                player.say(dialogues.get("donkey"));
                player.csLeft();
            }
            else if (cutSceneFrame <  60)
                player.csStill();
            else if (cutSceneFrame == 60)
            {
                player.say(dialogues.get("donkey"));
                player.csRight();
            }
            else if (cutSceneFrame <  80)
                player.csStill();
            else if (cutSceneFrame == 80)
            {
                player.say(dialogues.get("donkey"));
                player.csLeft();
            }
            else if (cutSceneFrame <  100)
                player.csStill();
            else if (cutSceneFrame == 100)
            {
                player.say(dialogues.get("weary"));
                player.csRight();
                player.csJump();
            }
            else if (cutSceneFrame <  120)
                player.csStill();
            else
            {
                //GreenfootImage temp = whirl.getImage();
                //temp.setTransparency(255);
                //whirl.setImage(temp);
                
                cutSceneFrame = 0;
                cutSceneMode = false;
                cutSceneNumber++;
            }
        }
        else if (cutSceneNumber == 2)
        {
            int ref = 55;
            int step = 30;
            
            if (cutSceneFrame < ref)
            {
                player.csLeft();
            }
            else if (cutSceneFrame == ref)
                blackSmith.say(dialogues.get("smiling with smiling eyes"));
            else if (cutSceneFrame <  ref + step * 1)
                player.csStill();
            else if (cutSceneFrame == ref + step * 1)
                player.say(dialogues.get("weary"));
            else if (cutSceneFrame <  ref + step * 2)
                player.csStill();
            else if (cutSceneFrame == ref + step * 2)
                player.say(dialogues.get("donkey"));
            else if (cutSceneFrame <  ref + step * 3)
                player.csStill();
            else if (cutSceneFrame == ref + step * 3)
                player.say(dialogues.get("very sad"));
            else if (cutSceneFrame <  ref + step * 4)
                player.csStill();
            else if (cutSceneFrame == ref + step * 4)
                blackSmith.say(dialogues.get("OMG"));
            else if (cutSceneFrame <  ref + step * 5)
                player.csStill();
            else if (cutSceneFrame == ref + step * 5)
                blackSmith.say(dialogues.get("thinking"));
            else if (cutSceneFrame <  ref + step * 6)
                player.csStill();
            else if (cutSceneFrame == ref + step * 6)
                blackSmith.say(dialogues.get("right"));
            else if (cutSceneFrame <  ref + step * 7)
                player.csStill();
            else if (cutSceneFrame == ref + step * 7)
                blackSmith.say(dialogues.get("wave"));
            else if (cutSceneFrame <  ref + step * 8)
                player.csStill();
            else if (cutSceneFrame == ref + step * 8)
                player.say(dialogues.get("grinmacing"));
            else if (cutSceneFrame <  ref + step * 9)
                player.csStill();
            else if (cutSceneFrame == ref + step * 9)
                blackSmith.say(dialogues.get("devil"));
            else if (cutSceneFrame <  ref + step * 10)
                player.csStill();
            else if (cutSceneFrame == ref + step * 10)
                player.say(dialogues.get("disappointed"));
            else if (cutSceneFrame <  ref + step * 11)
                player.csStill();
            else if (cutSceneFrame == ref + step * 11)
                blackSmith.say(dialogues.get("skull"));
            else if (cutSceneFrame <  ref + step * 12)
                player.csStill();
            else if (cutSceneFrame == ref + step * 12)
                player.say(dialogues.get("cold sweat"));
            else if (cutSceneFrame <  ref + step * 13)
                player.csStill();
            else if (cutSceneFrame == ref + step * 13)
                blackSmith.say(dialogues.get("right"));
            else if (cutSceneFrame <  ref + step * 14)
                player.csStill();
            else if (cutSceneFrame == ref + step * 14)
                blackSmith.say(dialogues.get("donkey"));
            else if (cutSceneFrame <  ref + step * 15)
                player.csStill();
            else if (cutSceneFrame == ref + step * 15)
                player.say(dialogues.get("surprised"));
            else if (cutSceneFrame <  ref + step * 16)
                player.csStill();
            else if (cutSceneFrame == ref + step * 16)
                player.say(dialogues.get("thumbs up"));
            else if (cutSceneFrame <  ref + step * 17)
                player.csRight();
            else if (cutSceneFrame == ref + step * 17)
            {
                blackSmith.say(dialogues.get("stop"));
                player.csLeft();
            }
            else if (cutSceneFrame <  ref + step * 18)
                player.csStill();
            else if (cutSceneFrame == ref + step * 18)
                blackSmith.say(dialogues.get("right"));
            else if (cutSceneFrame <  ref + step * 19)
                player.csStill();
            else if (cutSceneFrame == ref + step * 19)
                blackSmith.say(dialogues.get("boat"));
            else if (cutSceneFrame <  ref + step * 20)
                player.csStill();
            else if (cutSceneFrame == ref + step * 20)
                player.say(dialogues.get("smiling with smiling eyes"));
            else if (cutSceneFrame <  ref + step * 21)
                player.csStill();
            else if (cutSceneFrame == ref + step * 21)
                player.say(dialogues.get("thanks"));
            else if (cutSceneFrame <  ref + step * 22)
                player.csStill();
            else if (cutSceneFrame == ref + step * 22)
                blackSmith.say(dialogues.get("live long and prosper"));                
            else if (cutSceneFrame <  ref + step * 23)
                player.csRight();
                
                
            else
            {
                cutSceneFrame = 0;
                cutSceneMode = false;
                cutSceneNumber++;
            }
        }
        else if (cutSceneNumber == 3)
        {
            if (cutSceneFrame == 1)
            {
                removeObject(platforms[8]);
                waitOnKey("up");
                player.csJump();
            }
            else if (player.globalPos[0] < 2200)
                player.csRight();
            else
            {
                cutSceneFrame = 0;
                //cutSceneMode = false;
                cutSceneNumber++;
            }
        }
        else if (cutSceneNumber == 4)
        {
            if (cutSceneFrame < 20)
            {
                player.csStill();
                boat.movePos(1,0);
            }
            else if (cutSceneFrame < 40)
            {
                player.movePos(1,0);
                boat.movePos(1,0);
            }
            else if (cutSceneFrame < 150)
            {
                player.movePos(3, 0);
                boat.movePos(3, 0);
            }
            else if (cutSceneFrame == 200)
                music[0].movePos(0, 1000);
            else if (cutSceneFrame == 250)
                music[1].movePos(0, 1000);
            else if (cutSceneFrame == 300)
                music[2].movePos(0, 1000);
            else if (cutSceneFrame < 250)
            {
                player.movePos(4, 0);
                player.say(dialogues.get("surprised"));
                boat.movePos(3, 0);
            }
            else if (cutSceneFrame < 350)
            {
                player.movePos(3, 0);
                player.say(dialogues.get("two pink hearts"));
                boat.movePos(3, 0);
            }
            else
            {
                platforms[6].setPos(3030, 430);
                player.say(dialogues.get("cold sweat"));
                cutSceneFrame = 0;
                cutSceneMode = false;
                cutSceneNumber++;
            }
        }
        else if (cutSceneNumber == 7)
        {
            if (cutSceneFrame  < 10)
                portal.setPos(4650, 550);
            else if (cutSceneFrame == 10)
                removeObject(hydra);
            else if (player.globalPos[1] < 500)
            {
                portal.setRotation(cutSceneFrame * 4);
                player.csRight();
            }
            else
            {
                intro.stop();
                Greenfoot.setWorld(new UnderWorldCutScene());
            }
        }
        
        cutSceneFrame++;
    }
    
    private void AddCarrots()
    {
        carrots = new StaticThing[3];
        
        GreenfootImage c_img = new GreenfootImage("Carrot_T_0.png");
        c_img.scale(c_img.getWidth() / 4, c_img.getHeight() / 4);
        
        carrots[0] = new StaticThing(getWidth() - 140, 
                                     bg.getHeight() - getHeight() / 4 + 1015);
        carrots[1] = new StaticThing(getWidth() + 110, 
                                     bg.getHeight() - getHeight() / 4 - 3);
        carrots[2] = new StaticThing(getWidth() + 100,//- 125, 
                                     bg.getHeight() - getHeight() / 4 - 12);//+ 15);
                                     
        carrots[0].setImage(c_img);
        carrots[1].setImage(c_img);
        carrots[2].setImage(c_img);
        
        carrots[1].setRotation(180);
                                     
        addObject(carrots[0], -100, -100);
        addObject(carrots[1], -100, -100);
        addObject(carrots[2], -100, -100);
    }
    
    private void AddWalls()
    {
        /** Steps to adding new walls/platforms **/
        /* 1) increase numPlatforms to handle new wall/platform
         * 2) set up new image if not using previously defined size
         * 3) setImage of new wall/platform to appropriate GreenfootImage
         */
        int numPlatforms = 9;
        platforms = new Platform[numPlatforms];

        /** Ground Platforms **/
        platforms[0] = new Platform(400,  getHeight() -  85); //ground
        platforms[1] = new Platform(1600, getHeight() -  85); //ground
        platforms[2] = new Platform(2800, getHeight() -  85); //ground
        platforms[3] = new Platform(100,  getHeight() -  95); //blacksmith ground
        platforms[4] = new Platform(2200, getHeight() - 135); //boat ground **SPECIAL**
        platforms[7] = new Platform(3800, getHeight() -  85); //ground
        //platforms[8] = new Platform(4800, getHeight() -  85); //ground
        
        /** Wall Platforms **/
        platforms[5] = new Platform(215,   getHeight() - 85 - 175); //blacksmith wall
        platforms[6] = new Platform(2100,  getHeight() - 85 + 185); //boat wall
        platforms[8] = new Platform(2350,  getHeight() - 85 - 185);
        
        /** Image Setup **/
        /* img_ground (long) */
        GreenfootImage img_ground = platforms[0].getImage();
        img_ground.setTransparency(0); //temp, should be 0
        img_ground.scale(1200, img_ground.getHeight());
        
        /* img_medium */
        GreenfootImage img_medium = platforms[3].getImage();
        img_medium.setTransparency(0); //temp, should be 0
        img_medium.scale(200, img_medium.getHeight());
        
        /* img_wall */
        GreenfootImage img_wall = platforms[5].getImage();
        img_wall.setTransparency(0); //temp, should be 0
        img_wall.scale(img_wall.getWidth(), 300);
        
        /** Set each Platform's image appropriately **/
        platforms[0].setImage(img_ground); //ground
        platforms[1].setImage(img_ground); //ground
        platforms[2].setImage(img_ground); //ground
        platforms[3].setImage(img_medium); //blacksmith ground
        platforms[4].setImage(img_medium); //boat ground
        platforms[5].setImage(img_wall);   //blacksmith wall
        platforms[6].setImage(img_wall);   //boat wall
        platforms[7].setImage(img_ground); //ground
        platforms[8].setImage(img_wall); //ground
        
        /** Add Platforms to World **/
        for (int i = 0; i < numPlatforms; i++)
            addObject(platforms[i], -1000, -1000);
        removeObject(platforms[4]);
    }
    
    private void loadDialogues()
    {
        int bubbleSizeX, bubbleSizeY;
        int emojiScale;
        
        String filename = "SpeechBubbleWhite.png";
        GreenfootImage emoji = new GreenfootImage(filename);
        bubbleSizeX = emoji.getWidth()  / 2;
        bubbleSizeY = emoji.getHeight() / 2;
        emoji.scale(bubbleSizeX, bubbleSizeY);
        dialogues.put("bubble1", emoji);
        
        emojiScale = Math.max(bubbleSizeX / 2, bubbleSizeY / 2);
        
        filename = "Angry_Emoji.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("angry", emoji);
        
        filename = "skull-and-crossbones.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("skull", emoji);
        
        filename = "Cold_Sweat_Emoji.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("cold sweat", emoji);
        
        filename = "Disappointed_but_Relieved_Face_Emoji.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("disappointed", emoji);
        
        filename = "Face_with_Cold_Sweat_Emoji.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("face with cold sweat", emoji);
        
        filename = "spock-live-long-and-prosper-emoji.jpg";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("live long and prosper", emoji);
        
        filename = "Grinmacing_Face_Emoji.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("grinmacing", emoji);
        
        filename = "Grinning_Emoji_with_Smiling_Eyes.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("grinning", emoji);
        
        filename = "Heart_Eyes_Emoji.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("heart eyes", emoji);
        
        filename = "Key_Left.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("left", emoji);
        
        filename = "Key_Left.png";
        emoji = new GreenfootImage(filename);
        emoji.rotate(90);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("up", emoji);
        
        filename = "Key_Left.png";
        emoji = new GreenfootImage(filename);
        emoji.rotate(-90);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("down", emoji);
        
        filename = "white-right-pointing-backhand-index.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("right", emoji);
        
        filename = "OMG_Face_Emoji.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("OMG", emoji);
        
        filename = "smiling-face-with-horns.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("devil", emoji);
        
        filename = "flushed-face.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("surprised", emoji);
        
        filename = "Smiling_Emoji_with_Smiling_Eyes.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("smiling with smiling eyes", emoji);
        
        filename = "Two_Pink_Hearts_Emoji.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("two pink hearts", emoji);
        
        filename = "Very_Angry_Emoji.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("very angry", emoji);
        
        filename = "Very_Sad_Emoji.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("very sad", emoji);
        
        filename = "Weary_Face_Emoji.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("weary", emoji);
        
        filename = "Worried_Face_Emoji.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("worried", emoji);
        
        filename = "hand.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("stop", emoji);
        
        filename = "water-wave.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("wave", emoji);
        
        filename = "shift_button.gif";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("T", emoji);
        
        filename = "SPACE.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("space", emoji);
        
        filename = "Carrot_T_0.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale / 2);
        dialogues.put("carrot", emoji);
        
        filename = "thumbs-down-sign.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("thumbs down", emoji);
        
        filename = "thumbs-up-sign.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("thumbs up", emoji);
        
        filename = "Thinking_Face_Emoji.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("thinking", emoji);
        
        filename = "Sail_Boat_Emoji.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("boat", emoji);
        
        filename = "Horse_emoji_icon.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("donkey", emoji);
        
        filename = "person-with-folded-hands.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("thanks", emoji);
        
        filename = "shield.png";
        emoji = new GreenfootImage(filename);
        emoji.scale(emojiScale, emojiScale);
        dialogues.put("shield", emoji);
    }
    
    public void waitOnKey(String key)
    {
        while (Greenfoot.isKeyDown(key) == false)
        {
            Greenfoot.delay(1);
        }
    }
}
