import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * @author Lauren Kirk
 */

public class UnderWorldPartI extends World
{
    private static final int FRAME_RATE = 40;
    private static final int DEAD_LONG_TIME = 100;
    
    //Musical Themes
    private static final GreenfootSound intro = new GreenfootSound("Uplifting.wav");
    private static final GreenfootSound cave_theme = new GreenfootSound("Shadows.wav");
    private static final GreenfootSound end_theme = new GreenfootSound("CountOnMe.mp3");
    
    //Sound Effects
    private static final GreenfootSound gainHeart = new GreenfootSound("GainHeart.wav");
    private static final GreenfootSound ouch = new GreenfootSound("Ouch.wav");
    private static final GreenfootSound youDead = new GreenfootSound("YouDead.wav");
    
    private boolean playDead = false; //make sound effect only happen once
    
    private HeartController lives = null;
    private GreenfootSound theme;
    private Player player;
    private Sword sword;
    private UEPI_Donkey donkey;
    private Skull skull1, skull2, skull3, skull4; 
    private Fire fire2, fire3, fire4, fire5, fire6, fire7, fire8, fire9;
          
    private GreenfootImage sword_img;
    private int timeDead = DEAD_LONG_TIME;
    
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public UnderWorldPartI()
    {    
       super(1000, 600, 1, false);
        
       Greenfoot.setSpeed(FRAME_RATE);
       
       //Cave Theme
       theme = cave_theme;
       theme.playLoop();
       
       prepare();
       
       sword_img = new GreenfootImage("SPACE.png");
       sword_img.scale(50, 50);
       
       String filename = "SpeechBubbleWhite.png";
       GreenfootImage emoji = new GreenfootImage(filename);
       int bubbleSizeX = emoji.getWidth()  / 2;
       int bubbleSizeY = emoji.getHeight() / 2;
       emoji.scale(bubbleSizeX, bubbleSizeY);
       player.initialize();
       player.dialogue.setBubble(emoji);
    }
    
    // Cave Phase Act
    public void act() {
        
       if (getObjects(Skull.class).isEmpty()) {
          fireFade(fire2);
          fireFade(fire3);
          fireFade(fire4);
          fireFade(fire5);
          fireFade(fire6);
          fireFade(fire7);
          fireFade(fire8);
          fireFade(fire9);
       }
        
       if (player.globalPos[0] >= 2500) {
          cave_theme.stop();
          Greenfoot.setWorld(new UnderWorldPartII());
       }
       else {
        
          if (lives == null) {
             lives = new HeartController();
             lives.addXLives(3, this);
          }
       
          if (lives.isAlive()) {
             checkLives();
             checkHurt();

             if (sword.takeSword()) {
                player.setSword(true);
                player.initialize();
                player.say(sword_img);
                
                ///* Donkey Kidnapped */
                donkey.movePos(600, 0);
             }

             player.killThing();
          }
          else {
             if (timeDead == 0) {
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
       }
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
       /** Initalize Background and Player **/
       Background bg = new Background();
       player = new Player(100, 50, 15);
       player.giveShield();
       bg.ApplyPlayer(player);
       bg.setImage(new GreenfootImage("mario-1-1.gif"));
        
       addObject(bg, bg.getImage().getWidth() / 2, getHeight() - bg.getImage().getHeight() / 2);
       addObject(player, 100, 50);

       
       /** Initalize Platforms **/
       Platform initPlatform = new Platform(0, getHeight());
       addObject(initPlatform, 0, getHeight());

       int bgDim = bg.getImage().getWidth();
       int blockDim = initPlatform.getImage().getWidth();
       int halfBlockDim = blockDim / 2;
       
       /* Build Ground Platforms */
       buildVerticalPlatform(blockDim, 1, 55, getHeight());
       buildVerticalPlatform(blockDim, 1, 5, getHeight() - blockDim); // LeftmostGround Nub
       buildVerticalPlatform(blockDim, 15, 25, getHeight() - blockDim); // RightmostGround Nub
       buildVerticalPlatform(blockDim, 20, 22, getHeight() - halfBlockDim * 3); // RightmostGround Nub
       buildVerticalPlatform(blockDim, 20, 21, getHeight() - blockDim * 2); // RightmostGround Nub
       buildVerticalPlatform(blockDim, 32, 35, getHeight() - halfBlockDim); // RightmostGround Nub
       
       /* Build Ceiling Platforms */
       buildVerticalPlatform(blockDim, 4, 55, 0);
       buildVerticalPlatform(blockDim, 4, 12, 3 * blockDim);
       buildHorizontalPlatform(blockDim, 1, 3, 4 * blockDim);
       
       /* Platform - Left Wall */
       buildHorizontalPlatform(blockDim, 0, 12, 0); //Wall
       buildVerticalPlatform(blockDim, 1, 7, 8 * blockDim); //Horizontal Landing
       buildHorizontalPlatform(blockDim, 6, 9, 7 * blockDim); //Vertical Nub on Landing Platform
       buildSingleBlock(6 * blockDim, 7 * blockDim);
 
       /* Platform - Right Wall */
       buildHorizontalPlatform(blockDim, 1, 10, bgDim - 4 * blockDim);     
       buildVerticalPlatform_FromWall(blockDim, bgDim, 0, 12, 9 * blockDim);

       buildSingleBlock(bgDim - 5 * blockDim, 7 * blockDim);
       buildSingleBlock(bgDim - 6 * blockDim, 8 * blockDim);
       buildSingleBlock(bgDim - 5 * blockDim, 8 * blockDim);
       buildSingleBlock(bgDim - 11 * blockDim, 8 * blockDim);
       buildSingleBlock(bgDim - 14 * blockDim, 9 * blockDim - halfBlockDim);
       
       buildVerticalPlatform_FromWall(blockDim, bgDim, 14, 19, 9 * blockDim + halfBlockDim);
       
       /* Platform - 2nd Level */
       buildHorizontalPlatform_Offset(blockDim, 8, 10, 10 * blockDim, halfBlockDim); //Immediate Right of Landing Platform
       buildVerticalPlatform(blockDim, 11, 19, 8 * blockDim - halfBlockDim); //Main
       buildVerticalPlatform(blockDim, 23, 31, 8 * blockDim); //Main
       
       buildVerticalPlatform(blockDim, 33, 39, 7 * blockDim); //Hidden Cavern, Horizontal
       buildHorizontalPlatform(blockDim, 4, 8, 38 * blockDim); //Hidden Cavern, Vertical
       
       /* Platform - 3rd Level */
       buildVerticalPlatform(blockDim, 14, 16, 5 * blockDim);
       buildVerticalPlatform(blockDim, 15, 17, 5 * blockDim - halfBlockDim);
       buildVerticalPlatform(blockDim, 16, 33, 4 * blockDim); //Left, Lower
       buildVerticalPlatform(blockDim, 33, 44, 4 * blockDim - halfBlockDim); //Right, Upper
       buildVerticalPlatform(blockDim, 43, 45, 4 * blockDim);
       buildVerticalPlatform(blockDim, 41, 46, 5 * blockDim - halfBlockDim);
       buildVerticalPlatform(blockDim, 44, 47, 5 * blockDim);

       
       /** Add Items to World **/
       addDonkey(blockDim, halfBlockDim, bgDim);
       addCarrots(blockDim, halfBlockDim);
       addSword(blockDim, halfBlockDim);
       addFire(blockDim, halfBlockDim);
       addSkulls(blockDim, halfBlockDim);
       addSpiderWebs(blockDim, halfBlockDim, bgDim);
       
       ArrayList<StaticThing> objs = new ArrayList<StaticThing>(getObjects(StaticThing.class));
       for (int i = 0; i < objs.size(); i++)
       {
          objs.get(i).setBackground(bg);
       }
    
       setActOrder(Background.class, Obstacle.class, Character.class);
    }
        
    public GreenfootSound getIntro() {
       return intro;    
    }
    
    public void pauseCurrentTheme() {
       if (theme.isPlaying()) {
          theme.pause();   
       }
    }
    
    public void resumeCurrentTheme() {
        if (!(theme.isPlaying())) {
           theme.play();    
        }
    }
    
    // This actually builds Horizontal Platforms
    public void buildVerticalPlatform(int blockDim, int xStart, int xEnd, int yConst) {       
       int xCoord = 0;
       
       for (int i = xStart; i < xEnd; i++) {
           xCoord = i * blockDim;
           addObject(new Platform(xCoord, yConst), xCoord, yConst);
       }
    }
    
    // This actually builds Horizontal Platforms
    public void buildVerticalPlatform_Offset(int blockDim, int xStart, int xEnd, int yConst, int Offset) {       
       int xCoord = 0;
       
       for (int i = xStart; i < xEnd; i++) {
           xCoord = i * blockDim - Offset;
           addObject(new Platform(xCoord, yConst), xCoord, yConst);
       }
    }
    
    // This actually builds Horizontal Platforms
    public void buildVerticalPlatform_FromWall(int blockDim, int bgDim, int xStart, int xEnd, int yConst) {
       int xCoord = 0;
       
       for (int i = xStart; i < xEnd; i++) {
          xCoord = bgDim - i * blockDim;
           
          addObject(new Platform(xCoord, yConst), xCoord, yConst);
       }
    }
    
    // This actually builds Vertical Platforms
    public void buildHorizontalPlatform(int blockDim, int yStart, int yEnd, int xConst) {
       int yCoord = 0;
       
        for (int i = yStart; i < yEnd; i++) {
           yCoord = i * blockDim; 
           addObject(new Platform(xConst, yCoord), xConst, yCoord);
       }
    }
    
    // This actually builds Vertical Platforms
    public void buildHorizontalPlatform_Offset(int blockDim, int yStart, int yEnd, int xConst, int Offset) {
       int yCoord = 0;
       
        for (int i = yStart; i < yEnd; i++) {
           yCoord = i * blockDim - Offset; 
           addObject(new Platform(xConst, yCoord), xConst, yCoord);
       }
    }
    
    // This actually builds Vertical Platforms
    public void buildHorizontalPlatform_FromWall(int blockDim, int bgDim, int yStart, int yEnd, int xConst) {
       int yCoord = 0;
       
       for (int i = yStart; i < yEnd; i++) {
          yCoord = bgDim - i * blockDim;
           
          addObject(new Platform(xConst, yCoord), xConst, yCoord);
       }
    }
    
    public void buildSingleBlock(int xCoord, int yCoord) {
        addObject(new Platform(xCoord, yCoord), xCoord, yCoord);
    }
    
    public void addCarrots(int blockDim, int halfBlockDim) {
       Carrot carrot1 = new Carrot(blockDim * 37 - halfBlockDim, blockDim * 6 - halfBlockDim);
       carrot1.getImage().scale(50, 25);
       addObject(carrot1, carrot1.globalPos[0], carrot1.globalPos[1]);
       
       Carrot carrot2 = new Carrot(blockDim * 6, blockDim * 2 - halfBlockDim);
       carrot2.getImage().scale(50, 25);
       addObject(carrot2, carrot2.globalPos[0], carrot2.globalPos[1]);

       Carrot carrot3 = new Carrot(blockDim * 21, getHeight() - blockDim * 4);
       carrot3.getImage().scale(50, 25);
       addObject(carrot3, carrot3.globalPos[0], carrot3.globalPos[1]);
    }
    
    public void addFire(int blockDim, int halfBlockDim) {       
       //Below Landing Platform
       fire2 = new Fire(blockDim * 2, getHeight() - halfBlockDim  - halfBlockDim / 2);
       fire2.initScale(40, 50);
       addObject(fire2, fire2.globalPos[0], fire2.globalPos[1]);
       fire2.placeHitBox(this);
       
       fire3 = new Fire(blockDim * 3, getHeight() - halfBlockDim);
       fire3.initScale(40, 50);
       addObject(fire3, fire3.globalPos[0], fire3.globalPos[1]);
       fire3.placeHitBox(this);
       
       fire4 = new Fire(blockDim * 4, getHeight() - halfBlockDim - halfBlockDim / 2);
       fire4.initScale(40, 50);
       addObject(fire4, fire4.globalPos[0], fire4.globalPos[1]);
       fire4.placeHitBox(this);
       
       //Around Donkey
       fire5 = new Fire(blockDim * 45 - halfBlockDim, getHeight() - blockDim);
       fire5.initScale(40, 75);
       addObject(fire5, fire5.globalPos[0], fire5.globalPos[1]);
       fire5.placeHitBox(this);
       //addObject(fire5.getHitBox(), fire5.globalPos[0], fire5.globalPos[1]);
       
       fire9 = new Fire(blockDim * 48 - halfBlockDim, getHeight() - blockDim);
       fire9.initScale(40, 75);
       addObject(fire9, fire9.globalPos[0], fire9.globalPos[1]);
       fire9.placeHitBox(this);
       
       fire6 = new Fire(blockDim * 45, getHeight() - halfBlockDim);
       fire6.initScale(50, 50);
       addObject(fire6, fire6.globalPos[0], fire6.globalPos[1]);
       fire6.placeHitBox(this);
       
       fire8 = new Fire(blockDim * 47, getHeight() - halfBlockDim);
       fire8.initScale(50, 50);
       addObject(fire8, fire8.globalPos[0], fire8.globalPos[1]);
       fire8.placeHitBox(this);
       
       fire7 = new Fire(blockDim * 46 , getHeight() - halfBlockDim);
       fire7.initScale(60, 50);
       addObject(fire7, fire7.globalPos[0], fire7.globalPos[1]);
       fire7.placeHitBox(this);
       
    }
    
    public void addSkulls(int blockDim, int halfBlockDim) {
       skull1 = new Skull(blockDim * 20, blockDim * 2, blockDim * 4, blockDim * 22);
       addObject(skull1, skull1.globalPos[0], skull1.globalPos[1]);
       
       skull2 = new Skull(blockDim * 26, blockDim * 6, blockDim * 3, blockDim * 4);
       addObject(skull2, skull2.globalPos[0], skull2.globalPos[1]);
       
       skull3 = new Skull(blockDim * 45 + halfBlockDim, blockDim * 7, blockDim * 1, blockDim * 1);
       addObject(skull3, skull3.globalPos[0], skull3.globalPos[1]);
    }
    
    public void addDonkey(int blockDim, int halfBlockDim, int bgWidth) {
       donkey = new UEPI_Donkey(bgWidth - blockDim * 8, getHeight() - blockDim - halfBlockDim / 2);
       
       addObject(donkey, donkey.globalPos[0], donkey.globalPos[1]);
    }
    
    public void addSword(int blockDim, int halfBlockDim) {
       sword = new Sword(blockDim * 3, getHeight() - blockDim * 2);
       addObject(sword, sword.globalPos[0], sword.globalPos[1]); 
    }
    
    public void addSpiderWebs(int blockDim, int halfBlockDim, int bgDim) {       
       SpiderWebs web1 = new SpiderWebs(bgDim - blockDim * 10, getHeight() - blockDim - halfBlockDim);
       addObject(web1, web1.globalPos[0], web1.globalPos[1]);
       
       SpiderWebs web2 = new SpiderWebs(bgDim - blockDim * 8, getHeight() - blockDim - halfBlockDim);
       addObject(web2, web2.globalPos[0], web2.globalPos[1]);
       
       SpiderWebs web3 = new SpiderWebs(bgDim - blockDim * 6, getHeight() - blockDim - halfBlockDim);
       addObject(web3, web3.globalPos[0], web3.globalPos[1]);

       SpiderWebs web4 = new SpiderWebs(bgDim - blockDim * 4, getHeight() - blockDim - halfBlockDim);
       addObject(web4, web4.globalPos[0], web4.globalPos[1]);
       
       SpiderWebs web5 = new SpiderWebs(bgDim - blockDim * 2, getHeight() - blockDim - halfBlockDim);
       addObject(web5, web5.globalPos[0], web5.globalPos[1]);
    }
    
    public void checkLives() {
       if(player.isTouching(Carrot.class)) {
          player.removeTouching(Carrot.class);
          
          gainHeart.setVolume(80);
          gainHeart.play();
          
          lives.addLife(this);
       }
       
       lives.act();
    }
    
    public void checkHurt() {
       if (player.getImage().getTransparency() == 100) {
          player.getImage().setTransparency(255);
       }
       
       if (player.isTouching(Skull.class)) {
           if (!player.isBlocking() || !Greenfoot.isKeyDown("space") || !sword.isSwordInHand()) {
              ouch.setVolume(60);
              ouch.play();
          
              player.getImage().setTransparency(100);
              player.jump();
              lives.loseLife();    
           }
       }
      
       if (player.isTouching(FireHitBox.class)) {
          ouch.setVolume(60);
          ouch.play();
          
          player.getImage().setTransparency(100);
          player.jump();
          lives.loseLife();     
       }       
    }
    
    public void restart() {
          lives.addLife(this);
          player.globalPos[0] = 100;
          player.globalPos[1] = 50;
          
          playDead = false;
    }
    
    public void fireFade(Fire fire) {
       if (fire == null) {
          return;
       }
       
       //try {
          int scale_down = 1;
          int smallest = 1;
       
          int height = fire.getImage().getHeight();
          height = Math.abs(height - scale_down);
          
          if (height > smallest && height > 0) {
             fire.initScale(height, fire.getImage().getWidth());    
          }
          else {
             removeObject(fire.getHitBox());
             removeObject(fire);
             return;
          }
          
          int width = fire.getImage().getWidth();
          width = Math.abs(width - scale_down);
          
          if (width > smallest && width > 0) {
             fire.initScale(fire.getImage().getHeight(), width);
          }
          else {
             removeObject(fire.getHitBox());
             removeObject(fire);
             return;
          }          
    }
}
