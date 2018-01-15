import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.util.List;
import java.util.ArrayList;

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UnderWorldPartII extends World
{   
    private static final int FRAME_RATE = 80;
    private static final int DEAD_LONG_TIME = 100;
    
    private static final GreenfootSound hate_theme = new GreenfootSound("Shadows.wav");
    private static final GreenfootSound end_theme = new GreenfootSound("CountOnMe.mp3");
    
    //Sound Effects
    private static final GreenfootSound gainHeart = new GreenfootSound("GainHeart.wav");
    private static final GreenfootSound ouch = new GreenfootSound("Ouch.wav");
    private static final GreenfootSound youDead = new GreenfootSound("YouDead.wav");
    
    private boolean playDead = false; //make sound effect only happen once
    
    private HeartController lives = null;
    private HeartController badLives = null;
          
    private int timeDead = DEAD_LONG_TIME;
    
    private UWP2_Background uwp2_background;
    private Player p2_player = null;
    private Donkey p2_donkey = null;
    private SpiderEnemy spider = null;
    
    private List<Fire> fires = new ArrayList<Fire>();
    private int fireON = FRAME_RATE;
    private boolean fireHurt = true;
    
    /**
     * Constructor for objects of class MyWorld.
     */
    public UnderWorldPartII()
    {    
       super(1200, 600, 1, true);
       
       Greenfoot.setSpeed(40);
              
       prepBackground(); //Also Places Character
       buildPlatforms();
       
       placeDonkey();
       placeCarrots();
       placeSpider();
              
       //Add Fire
       placeGroundFire();
       placeSpiderWebs();
       
       hate_theme.play();
       
       /* Connect all StaticThings to Background */
        ArrayList<StaticThing> objs = new ArrayList<StaticThing>(getObjects(StaticThing.class));
        for (int i = 0; i < objs.size(); i++)
        {
            objs.get(i).setBackground(uwp2_background);
        }
    }
    
    public void act() {
       ArrayList<Fireball> fbs = new ArrayList<Fireball>(getObjects(Fireball.class));
       for (int i = 0; i < fbs.size(); i++)
       {
           if (fbs.get(i).isHitting(p2_player) == true)
           {
               if (p2_player.isBlocking() == false)
               {
                   ouch.setVolume(60);
                   ouch.play();
          
                   p2_player.getImage().setTransparency(100);
                   p2_player.jump();
                   lives.loseLife();    
               }
               removeObject(fbs.get(i));
           }
           else if (fbs.get(i).globalPos[0] < -100)
           {
               removeObject(fbs.get(i));
           }
       }
        
       if (!badLives.isAlive()) {
          //System.out.println("Spider Dead\n");
          if (spider != null)
            spider.death();
          
          if (spider != null && spider.fadeToDie()) {
              removeObject(spider);
              spider = null;
              
              int blockDim = 50;
              buildVerticalPlatform(blockDim, 12, 16, blockDim * 4 + blockDim / 2);
          }
          
          
       }
       
       if (p2_player.isTouching(Donkey.class)) {
          //System.out.println("HERE");
          hate_theme.stop();
          end_theme.play();
          
          Ending end = new Ending();
          addObject(end, 600, 300);
       }
        
       if (fireON > 0) {
          fireON--;    
       }
       else {
          fireON = FRAME_RATE;
          fireHurt = !fireHurt;
       }
       
       if (!fireHurt && fireON == FRAME_RATE) {
            for (int i=0; i < fires.size(); i++) {
               removeObject(fires.get(i).hitBox);
               removeObject(fires.get(i)); //transparent
            }
            
            if (spider != null) {
                Fireball fb = new Fireball(spider.globalPos[0], spider.globalPos[1], false);
                fb.setBackground(uwp2_background);
                addObject(fb, spider.getX(), spider.getY());
            }
       }
       
       if (fireHurt && fireON == FRAME_RATE) {
            placeGroundFire();
       }
        
       if (lives == null) {
           startingLives();
       }
        
        if (lives.isAlive()) {
             checkLives();
             checkHurt();
             p2_player.killThing(); // Spider not included
          }
          else {
             if (timeDead == 0) {
                //System.out.println("Is dead. Start Over");
                timeDead = DEAD_LONG_TIME;
                p2_player.setRotation(0);
                p2_player.setAliveStatus(true);
                restart();
             } else {
                if (!playDead) {
                   youDead.play();
                   playDead = true;
                }
                p2_player.setAliveStatus(false);
                p2_player.setRotation(-90);
                timeDead--;    
             }
          }
    }
    
    public void prepBackground() {
       uwp2_background = new UWP2_Background();
       uwp2_background.setImage(new GreenfootImage("HateMyLife.png"));
       addObject(uwp2_background, uwp2_background.getImage().getWidth() / 2, getHeight() - uwp2_background.getImage().getHeight() / 2);
    
       //Initialize Player
       p2_player = new Player(50, 50, 15);
       p2_player.giveShield();
       uwp2_background.ApplyPlayer(p2_player);
       addObject(p2_player, p2_player.globalPos[0], p2_player.globalPos[1]);
       p2_player.setBackground(uwp2_background);
       p2_player.setSword(true);
    }
    
    // This actually builds Horizontal Platforms
    public void buildVerticalPlatform(int blockDim, int xStart, int xEnd, int yConst) {       
       int xCoord = 0;
       
       for (int i = xStart; i < xEnd; i++) {
           xCoord = i * blockDim;
           Platform plat = new Platform(xCoord, yConst);
           plat.setBackground(uwp2_background);
           addObject(plat, xCoord, yConst);
       }
    }
    
    public void buildPlatforms() {
       //Build Ground - Level 0
       Platform initPlatform = new Platform(0, getHeight());
       addObject(initPlatform, 0, getHeight());
       int blockDim = initPlatform.getImage().getWidth();

       buildVerticalPlatform(blockDim, 1, 25, getHeight());
       buildVerticalPlatform(blockDim, 0, 4, getHeight() - blockDim * 1);

       //Spider Platforms - Level 1
       buildVerticalPlatform(blockDim, 6, 18, getHeight() - blockDim * 3);
       buildVerticalPlatform(blockDim, 12, 15, getHeight() - blockDim * 3 - blockDim / 2);
       
       //Spuder Platforms - Level 2
       buildVerticalPlatform(blockDim, 0, 9, getHeight() - blockDim * 6);
       buildVerticalPlatform(blockDim, 7, 10, getHeight() - blockDim * 6 + blockDim / 2);
       buildVerticalPlatform(blockDim, 18, 25, getHeight() - blockDim * 6);
       buildVerticalPlatform(blockDim, 17, 20, getHeight() - blockDim * 6 + blockDim / 2);
       
       // Player Spawn
       buildVerticalPlatform(blockDim, 0, 3, blockDim * 2 + blockDim / 2);
              
       // Donkey Trapped
       buildVerticalPlatform(blockDim, 18, 25, blockDim * 2 + blockDim / 2);        
    }
    
    public void placeDonkey() {
       int blockDim = 50; // I memorized it...
       
       p2_donkey = new Donkey(getWidth() - blockDim * 3, blockDim  + 10);
       addObject(p2_donkey, p2_donkey.globalPos[0], p2_donkey.globalPos[1]);
    }
    
    public void placeCarrots() {
       int blockDim = 50; // I hate my life right now
       
       Carrot carrot1 = new Carrot(blockDim * 6, getHeight() - blockDim - blockDim / 2);
       carrot1.getImage().scale(50, 25);
       addObject(carrot1, carrot1.globalPos[0], carrot1.globalPos[1]);

       Carrot carrot2 = new Carrot(blockDim * 9, getHeight() - blockDim - blockDim / 2);
       carrot2.getImage().scale(50, 25);
       addObject(carrot2, carrot2.globalPos[0], carrot2.globalPos[1]);

       Carrot carrot3 = new Carrot(blockDim * 12, getHeight() - blockDim - blockDim / 2);
       carrot3.getImage().scale(50, 25);
       addObject(carrot3, carrot3.globalPos[0], carrot3.globalPos[1]);

       Carrot carrot4 = new Carrot(blockDim * 15, getHeight() - blockDim - blockDim / 2);
       carrot4.getImage().scale(50, 25);
       addObject(carrot4, carrot4.globalPos[0], carrot4.globalPos[1]);

       Carrot carrot5 = new Carrot(blockDim * 18, getHeight() - blockDim - blockDim / 2);
       carrot5.getImage().scale(50, 25);
       addObject(carrot5, carrot5.globalPos[0], carrot5.globalPos[1]);

       Carrot carrot6 = new Carrot(blockDim * 21, getHeight() - blockDim - blockDim / 2);
       carrot6.getImage().scale(50, 25);
       addObject(carrot6, carrot6.globalPos[0], carrot6.globalPos[1]);
    }
    
    public void placeSpiderWebs() {
       int blockDim = 50;
        
       int xCoord = 0;
       int yConst = blockDim;
       
       //Ceiling Webs
       for (int j = 2; j < 10; j += 2) {
          for (int i = 0; i < 8; i += 2) {
             SpiderWebs webs;
             if (i % 2 == 0) {
                webs = new SpiderWebs(blockDim * i, blockDim * j + 15);
             } else {
                webs = new SpiderWebs(blockDim * i + 12, blockDim * j + 15);
             }
             
             addObject(webs, webs.globalPos[0], webs.globalPos[1]);
          }
       }
    }
    
    public void placeSpider() {
       int blockDim = 50;
       
       spider = new SpiderEnemy(getWidth() - blockDim * 2, getHeight() - blockDim * 7 - 5);
       addObject(spider, spider.globalPos[0], spider.globalPos[1]);
       
       badLives = new HeartController(true, "EvilHeart.png", 2, getWidth());
       badLives.addXLives(10, this);
    }
    
    public void startingLives() {
       lives = new HeartController();
       lives.addXLives(3, this); 
    }
    
    public void checkLives() {
       if(p2_player.isTouching(Carrot.class)) {
          p2_player.removeTouching(Carrot.class);
          
          gainHeart.setVolume(80);
          gainHeart.play();
     
          lives.addLife(this);
          
          //System.out.println("Gained Health.");
       }
       
       lives.act();
       badLives.act();
    }
    
    public void checkHurt() {
       if (p2_player.getImage().getTransparency() == 100) {
          p2_player.getImage().setTransparency(255);
       }
       
       //FIX THIS
       if ((p2_player.isTouching(FireHitBox.class) && fireHurt) || p2_player.isTouching(SpiderEnemy.class)) {
           if (p2_player.isTouching(SpiderEnemy.class) && !Greenfoot.isKeyDown("Space") && !p2_player.isBlocking()) {
              ouch.setVolume(60);
              ouch.play();
          
              p2_player.getImage().setTransparency(100);
              p2_player.jump();
              lives.loseLife();    
                    
              //System.out.println("Lost Life.");
           }
           else if (!Greenfoot.isKeyDown("Space")) {
              ouch.setVolume(60);
              ouch.play();
          
              p2_player.getImage().setTransparency(100);
              p2_player.jump();
              lives.loseLife();    
                    
              //System.out.println("Lost Life.");
           }
       }
       
       if (p2_player.isTouching(SpiderEnemy.class) && Greenfoot.isKeyDown("Space")) {
          //System.out.println("Killing Spider");
          badLives.loseLife();
       }
    }

    public void restart() {
          //System.out.println("Test 1");
          lives.addXLives(3, this); 
          
          p2_player.globalPos[0] = 50;
          p2_player.globalPos[1] = 50;
          
          
          badLives.addXLives(10 - badLives.numCurrentLives() ,this);
          
          playDead = false;
    }
    
    public void placeGroundFire() {
       int blockDim = 50;
       
       for (int i = 5; i < 26; i +=3) {
          Fire fire1 = new Fire(blockDim * i - blockDim / 2, getHeight() - blockDim);
          fire1.initScale(50, 60);
          fire1.setBackground(uwp2_background);
          addObject(fire1, fire1.globalPos[0], fire1.globalPos[1]);
          fire1.placeHitBox(this);
          fire1.hitBox.setBackground(uwp2_background);
          
          fires.add(fire1);
       }
    }
}
