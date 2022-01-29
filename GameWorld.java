import greenfoot.*;
import java.lang.Math;
import java.util.List;
import java.util.Collections;

/**
 * GameWorld is called from the pregamemenu.
 * This contains the main functions for the game.
 */

public class GameWorld extends World
{

    //integer variables for game, seperated by use case
    private int WIDTH, HEIGHT; 
    private int lives = 3, level = 0, score = 0, gameType;
    private int BGmusicmenu, gameMusicVol, delay = 0, timer = 0, backgroundNum = Greenfoot.getRandomNumber(3);

    //declaring actors that are going to be called regularly
    private Player player;
    private Ball ball;
    private GUI gui;
    private GreenfootImage backgroundImage;
    private GreenfootSound GameMusic;
    
    //width and height are the worlds resolution, gametype is either easy or hard gamemode,
    //BGmusic and gamemusicvol are just variables carried through to save the volume settings
    public GameWorld(int width, int height, int gametype, int bgmusicmenu, int gamemusicvol){    
        //create a new world with the resolution called
        super(width, height, 1); 
        
        //setting entered varaibles into the instance variables
        WIDTH = width;
        HEIGHT = height;
        gameType = gametype;
        BGmusicmenu = bgmusicmenu;
        gameMusicVol = gamemusicvol;
        
        //random music selection from 3 choices
        int whichGameMusic = Greenfoot.getRandomNumber(3);
        if (whichGameMusic == 0){
            GameMusic = new GreenfootSound("gameMusic1.mp3");
        } else if (whichGameMusic == 1){
            GameMusic = new GreenfootSound("gameMusic2.mp3");
        } else if (whichGameMusic == 2){
            GameMusic = new GreenfootSound("gameMusic3.mp3");
        } 
        
        //sets the volume to what is defined in the settingsmenu,
        //and starts playing on loop
        GameMusic.setVolume(gameMusicVol);
        GameMusic.playLoop();
        
        //sets the background from the background method, 
        //method used to keep image consistent
        GreenfootImage backgroundImage = new GreenfootImage(width, height);
        backgroundImage = backgroundImageCheck();
        backgroundImage.scale((int)(WIDTH * 2), HEIGHT);
        setBackground(backgroundImage);
        
        //adding player and ball objects to the world
        player = new Player(gameType, WIDTH, HEIGHT);
        addObject (player, (int)(WIDTH / 2), (int)(HEIGHT / 1.2765));
        
        ball = new Ball(1, gameType, WIDTH, HEIGHT);
        addObject (ball, (int)(width / 2), (int)(HEIGHT / 2));
        
        //draw the first level
        levelDrawer(level, WIDTH, HEIGHT);
        
        //setting the pregame delay to zero,
        //so there would be a 3 second
        //wait before the game starts, 
        //and supplies this number to the GUI
        delay = 0;
        
        //draw and add the gui to game
        gui = new GUI(WIDTH, HEIGHT, timer, score, lives, gameType, level + 1, 1, delay);
        addObject (gui, (int)(WIDTH / 2), (int)(HEIGHT / 2));
    }
    
    //methods that are called from actors
    public int returnWidth(){
        return WIDTH;
    }
    public int returnHeight(){
        return HEIGHT;
    }
    
    //stopped and started to start and stop the music when the game does the respective actions
    public void stopped(){
        GameMusic.pause();
    }
    public void started(){
        GameMusic.play();
    }
    
    //add to the score, usually called by actors
    public void scoreIndent(){
        score++;
    }
    
    //method to keep background images consistent when refreshing the screen
    private GreenfootImage backgroundImageCheck(){
        if (backgroundNum == 1){
            backgroundImage = new GreenfootImage("bg1.png");
        } else if (backgroundNum == 2){
            backgroundImage = new GreenfootImage("bg2.png");
        } else {
            backgroundImage = new GreenfootImage("bg3.png");
        }
        backgroundImage.scale((int)(WIDTH * 2), HEIGHT);
        return backgroundImage;
    }
    
    public void act(){
        //indent the timer and delay 
        timer++;
        delay++;
        
        //only let timer start counting up if delay reaches 3 seconds
        if ((delay / 60) < 3){
            timer = 0;
        }

        //refreshing gui (this is a really dumb method but im too lazy to make it better)
        removeObject(gui);
        gui = new GUI(WIDTH, HEIGHT, timer, score, lives, gameType, level + 1, 0, delay);
        addObject (gui, (int)(WIDTH / 2), (int)(HEIGHT / 2));
        
        
        
        if (lives == 0 || level == 8 || (gameType == 1 && level == 5)){
            //sets the background one more time
            backgroundImage = backgroundImageCheck();
            setBackground(backgroundImage);
            
            //stop game music before calling new world
            GameMusic.stop();
            
            //prevent lives from equalling 0, so score wont end up as zero when you die
            lives = 1;
            score = (score * lives);
            
            //if the level count is 8 or 5, go to the new world
            if (level == 8 || (gameType == 1 && level == 5)){
                Greenfoot.setWorld(new SuccessWorld(WIDTH, HEIGHT, score, BGmusicmenu, gameMusicVol));
                return;
            }
            
            //go to death world if sucess world is not called
            Greenfoot.setWorld(new DeathWorld(WIDTH, HEIGHT, score, BGmusicmenu, gameMusicVol));
            return;
        }
        
        //refresh background
        backgroundImage = backgroundImageCheck();
        setBackground(backgroundImage);
        
        //generate a list of ball objects
        List<Ball> ballArr = getObjects(Ball.class);
        
        //check if there are no balls remaning in game, or if the timer had counted to 3 minutes
        if (ballArr.isEmpty() || ((timer / 60) == 180 && gameType == 1)){
            //remove a live and add to the final score for later calculatiosn
            lives--; 
            
            //if timer is greater than 180, remove balls
            if ((timer / 60) >= 180){
                removeObjects(getObjects(Ball.class)); 
            }
            //remove all objects
            removeObjects(getObjects(GUI.class)); 
            removeObjects(getObjects(Powerup.class)); 
            removeObjects(getObjects(Laser.class));
            removeObject(player); 
            removeObject(gui);
            
            //reset delay to 0 so timer counts down again
            delay = 0;
            
            //add back the ball, player, and the gui
            ball = new Ball(1, gameType, WIDTH, HEIGHT); 
            addObject (ball, (int)(WIDTH / 2), (int)(HEIGHT / 2)); 
            
            player = new Player(gameType, WIDTH, HEIGHT); 
            addObject (player, (int)(WIDTH / 2), (int)(HEIGHT / 1.2765)); 
            
            gui = new GUI(WIDTH, HEIGHT, timer, score, lives, gameType, level + 1, 1, delay);
            addObject (gui, (int)(WIDTH / 2), (int)(HEIGHT / 2));
            
            //if the game is in hard mode, reset the timer
            if (gameType == 1){
                timer = 0;
            }
        }
        
        //create a list of brick objects
        List<Brick> brickArr = getObjects(Brick.class);
        
        //remove all bricks that have an hp of 16 in the lsit
        for (int i = 0; i < brickArr.size(); i++){
            if (brickArr.get(i).getBrickHP() == 16){
                brickArr.remove(i);                
                //thanks Mr.Cohen
                i--;
            }
        }
        
        //small cheat to skip to next level
        if (Greenfoot.isKeyDown("j") && delay > 200){
            brickArr = Collections.emptyList();
        }
        
        //checks if brickarray is empty
        if (brickArr.isEmpty()){
            //adds one to the level, so you go to the next level
            level++;
            
            //remove all objects from the world
            removeObjects(getObjects(GUI.class)); 
            removeObjects(getObjects(Ball.class));
            removeObjects(getObjects(Brick.class));
            removeObjects(getObjects(Powerup.class));
            removeObjects(getObjects(Laser.class));
            
            //draws next level
            levelDrawer(level, WIDTH, HEIGHT);
            
            //adds ball and gui back, and resets player location to the center of the screen
            ball = new Ball(1, gameType, WIDTH, HEIGHT);
            addObject (ball, (int)(WIDTH / 2), (int)(HEIGHT / 2));
            
            player.setLocation((int)(WIDTH / 2), (int)(HEIGHT / 1.2765));
            
            delay = 0;
            gui = new GUI(WIDTH, HEIGHT, timer, score, lives, gameType, level + 1, 1, delay);
            addObject (gui, (int)(WIDTH / 2), (int)(HEIGHT / 2));
        }
    }
    
    //increase life method, called by player method when you get the powerup
    public void increaseLive(){
        lives++;
    }
    
    //level drawer
    private void levelDrawer(int level, int WIDTH, int HEIGHT){
        int brickSpawnX = (int)(HEIGHT / 6.6666);
        
        //if the game is in easy mode
        if (gameType == 0){
            if (level == 0){
                
                brickSpawnX = (int)(HEIGHT / 3.33333);
                for (int i = 0; i < 5; i++){
                    addObject(new Brick(3, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 12));
                    brickSpawnX += (int)(WIDTH / 10);
                }
            } else if (level == 1){
            
                brickSpawnX = (int)(HEIGHT / 6.6666);
                for (int i = 0; i < 8; i++){
                    addObject(new Brick(3, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 12));
                    brickSpawnX += (int)(WIDTH / 10);
                }
            } else if (level == 2){
                
                brickSpawnX = (int)(HEIGHT / 6.6666);
                for (int i = 0; i < 8; i++){
                    addObject(new Brick(3, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 15));
                    brickSpawnX += (int)(WIDTH / 10);
                }
                brickSpawnX = (int)(HEIGHT / 5);
                for (int i = 0; i < 7; i++){
                    addObject(new Brick(3, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 8.571428));
                    brickSpawnX += (int)(WIDTH / 10);
                }
                brickSpawnX = (int)(HEIGHT / 4);
                for (int i = 0; i < 6; i++){
                    addObject(new Brick(3, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 6));
                    brickSpawnX += (int)(WIDTH / 10);
                }
            } else if (level == 3){
                
                brickSpawnX = (int)(HEIGHT / 3.3333);
                for (int i = 0; i < 3; i++){
                    
                    addObject(new Brick(16, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 6));
                    brickSpawnX += (int)(WIDTH / 5);
                }
                
                brickSpawnX = (int)(HEIGHT / 5);
                for (int i = 0; i < 7; i++){
                    addObject(new Brick(3, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 12));
                    brickSpawnX += (int)(WIDTH / 10);
                }
            } else if (level == 4){
                
                brickSpawnX = (int)(HEIGHT / 5);
                for (int i = 0; i < 7; i++){
                    addObject(new Brick(3, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 4));
                    brickSpawnX += (int)(WIDTH / 10);
                }
                
                brickSpawnX = (int)(HEIGHT / 4);
                for (int i = 0; i < 6; i++){
                    addObject(new Brick(2, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 3.3333));
                    brickSpawnX += (int)(WIDTH / 10);
                }
                
                brickSpawnX = (int)(HEIGHT / 4);
                for (int i = 0; i < 6; i++){
                    addObject(new Brick(2, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 5));
                    brickSpawnX += (int)(WIDTH / 10);
                }
                
                brickSpawnX = (int)(HEIGHT / 3.3333);
                for (int i = 0; i < 5; i++){
                    addObject(new Brick(1, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 6.6666));
                    brickSpawnX += (int)(WIDTH / 10);
                }
                
                brickSpawnX = (int)(HEIGHT / 3.3333);
                for (int i = 0; i < 5; i++){
                    addObject(new Brick(1, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 2.857142));
                    brickSpawnX += (int)(WIDTH / 10);
                }
            } else if (level == 5){
                
                brickSpawnX = (int)(HEIGHT / 3.3333);
                for (int i = 0; i < 5; i++){
                    addObject(new Brick(1, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 10));
                    brickSpawnX += (int)(WIDTH / 10);
                }
                
                brickSpawnX = (int)(HEIGHT / 3.3333);
                for (int i = 0; i < 5; i++){
                    addObject(new Brick(1, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 2.2222));
                    brickSpawnX += (int)(WIDTH / 10);
                }
                
                brickSpawnX = (int)(HEIGHT / 5);
                for (int i = 0; i < 7; i++){
                    addObject(new Brick(2, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 2.857142));
                    brickSpawnX += (int)(WIDTH / 10);
                }
                
                brickSpawnX = (int)(HEIGHT / 4);
                for (int i = 0; i < 6; i++){
                    addObject(new Brick(16, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 5));
                    brickSpawnX += (int)(WIDTH / 10);
                }
            } else if (level == 6){
                
                brickSpawnX = (int)(HEIGHT / 6.6666);
                for (int i = 0; i < 8; i++){
                    addObject(new Brick(16, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 4));
                    brickSpawnX += (int)(WIDTH / 10);
                }
                
                brickSpawnX = (int)(HEIGHT / 3.3333);
                for (int i = 0; i < 5; i++){
                    addObject(new Brick(3, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 10));
                    brickSpawnX += (int)(WIDTH / 10);
                }
                
                brickSpawnX = (int)(HEIGHT / 3.3333);
                for (int i = 0; i < 5; i++){
                    addObject(new Brick(3, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 6.6666));
                    brickSpawnX += (int)(WIDTH / 10);
                }
            } else if (level == 7){
                
                brickSpawnX = (int)(HEIGHT / 6.6666);
                for (int i = 0; i < 8; i++){
                    addObject(new Brick(16, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 10));
                    brickSpawnX += (int)(WIDTH / 10);
                }
                
                brickSpawnX = (int)(HEIGHT / 6.6666);
                for (int i = 0; i < 8; i++){
                    addObject(new Brick(3, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 6.6666));
                    brickSpawnX += (int)(WIDTH / 10);
                }
                
                brickSpawnX = (int)(HEIGHT / 6.6666);
                for (int i = 0; i < 8; i++){
                    addObject(new Brick(2, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 5));
                    brickSpawnX += (int)(WIDTH / 10);
                }
                
                brickSpawnX = (int)(HEIGHT / 6.6666);
                for (int i = 0; i < 8; i++){
                    addObject(new Brick(2, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 4));
                    brickSpawnX += (int)(WIDTH / 10);
                }
                
                brickSpawnX = (int)(HEIGHT / 6.6666);
                for (int i = 0; i < 8; i++){
                    addObject(new Brick(1, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 3.3333));
                    brickSpawnX += (int)(WIDTH / 10);
                }
            } 
        }
        
        //if the game is in hard mode
        if (gameType == 1){
            if (level == 0){
                
                brickSpawnX = (int)(HEIGHT / 6.6666);
                for (int i = 0; i < 4; i++){
                    addObject(new Brick(1, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 5));
                    brickSpawnX += (int)(HEIGHT / 5);
                }
                
                brickSpawnX = (int)(HEIGHT / 4);
                for (int i = 0; i < 4; i++){
                    addObject(new Brick(16, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 5));
                    brickSpawnX += (int)(HEIGHT / 5);
                }
            } else if (level == 1){
                
                brickSpawnX = (int)(HEIGHT / 6.6666);
                for (int i = 0; i < 4; i++){
                    addObject(new Brick(1, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 6.6666));
                    brickSpawnX += (int)(WIDTH / 10);
                    addObject(new Brick(16, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 6.6666));
                    brickSpawnX += (int)(WIDTH / 10);
                }
                
                brickSpawnX = (int)(HEIGHT / 6.6666);
                for (int i = 0; i < 4; i++){
                    addObject(new Brick(16, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 3.3333));
                    brickSpawnX += (int)(WIDTH / 10);
                    addObject(new Brick(1, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 3.3333));
                    brickSpawnX += (int)(WIDTH / 10);
                }
            } else if (level == 2){
                
                brickSpawnX = (int)(HEIGHT / 6.6666);
                for (int i = 0; i < 4; i++){
                    addObject(new Brick(16, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 3.3333));
                    brickSpawnX += (int)(WIDTH / 10);
                    addObject(new Brick(1, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 4));
                    brickSpawnX += (int)(WIDTH / 10);
                }
                
                brickSpawnX = (int)(HEIGHT / 3.3333);
                for (int i = 0; i < 5; i++){
                    addObject(new Brick(3, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 10));
                    brickSpawnX += (int)(WIDTH / 10);
                }
            } else if (level == 3){
                
                brickSpawnX = (int)(HEIGHT / 6.6666);
                for (int i = 0; i < 8; i++){
                    addObject(new Brick(3, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 3.3333));
                    brickSpawnX += (int)(WIDTH / 10);
                }
                
                brickSpawnX = (int)(HEIGHT / 6.6666);
                for (int i = 0; i < 4; i++){
                    addObject(new Brick(16, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 4));
                    brickSpawnX += (int)(WIDTH / 10);
                    addObject(new Brick(2, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 4));
                    brickSpawnX += (int)(WIDTH / 10);
                }
                
                brickSpawnX = (int)(HEIGHT / 6.6666);
                for (int i = 0; i < 8; i++){
                    addObject(new Brick(1, WIDTH, HEIGHT), brickSpawnX, (int)(HEIGHT / 5));
                    brickSpawnX += (int)(WIDTH / 10);
                }
            } else if (level == 4){
                
                addObject(new Brick(16, WIDTH, HEIGHT), (int)(HEIGHT / 1.6666), (int)(HEIGHT / 6));
                addObject(new Brick(16, WIDTH, HEIGHT), (int)(HEIGHT / 2.5), (int)(HEIGHT / 6));
                addObject(new Brick(16, WIDTH, HEIGHT), (int)(HEIGHT / 2.5), (int)(HEIGHT / 10));
                addObject(new Brick(16, WIDTH, HEIGHT), (int)(HEIGHT / 1.6666), (int)(HEIGHT / 10));
                
                addObject(new Brick(1, WIDTH, HEIGHT), (int)(HEIGHT / 2), (int)(HEIGHT / 10));
                addObject(new Brick(1, WIDTH, HEIGHT), (int)(HEIGHT / 1.6666), (int)(HEIGHT / 8));
                addObject(new Brick(1, WIDTH, HEIGHT), (int)(HEIGHT / 2.5), (int)(HEIGHT / 8));
                addObject(new Brick(3, WIDTH, HEIGHT), (int)(HEIGHT / 2), (int)(HEIGHT / 8));
            }
        }
    }
}
//amogus