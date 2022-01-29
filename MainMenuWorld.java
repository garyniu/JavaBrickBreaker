import greenfoot.*;

/**
 * Break-The-Brick By Gary Niu
 * 
 * Features:
 * - Hand drawn artwork
 * - Full menu system
 * - Trigonometry for trajectory calculations
 * - Fully working High Score menu with file saving
 * - 2 different gamemodes, for a total of 13 levels
 * - Powerups, most notably the Laser Gun
 * - Choosable resolutions, every number is a fraction for scaling
 * - Music and sound effects, volume controllable through settings
 * 
 * Bugs:
 * - Moving the paddle into the ball will cause the ball to accelerate rapidly
 * - Balls sometimes bounce incorrectly when hitting top corners of bricks
 * 
 * Instructions:
 * - Use either A/D, or Left/Right arrow to move the paddle.
 * - After a countdown, the ball will start moving down and at an angle.
 * - Bounce the ball using the paddle to destroy the bricks.
 * - Once all the bricks are destroyed, you will move onto the next level.
 * - Collect powerups that are dropped from bricks to gain advantages.
 * - The ball movement depends on the angle when the ball bounces off the paddle.
 * - When you receive the Laser powerup, press Space to fire a laser at the bricks.
 * - Hard mode will have a 3 minute countdown for each level. Paddle movement speed will also
 * be affected, and powerups will be disabled. 
 * 
 * Cheats:
 * - Press H key to receive the Laser powerup, the Paddle Expand powerup, and the Paddle Speedup powerup.
 * - Press J key to skip a level.
 * 
 * 
 * Credits for borrowed assets:
 * - All artwork created by Gary Niu
 * 
 * - All music, Menu sounds, and sound effects are from Epidemic Sound
 * - epidemicsound.com
 * 
 * - Ball falling out of the world sound from YouTube
 * - https://www.youtube.com/watch?v=EIA1iX7Ooz8
 * 
 * Credits for borrowed / modified code:
 * - Loading and reading files, darkmist225, https://www.greenfoot.org/topics/2295
 * - Pausing an actor, super_hippo, https://www.greenfoot.org/topics/57766/0
 * - Passing background music, danpost, https://www.greenfoot.org/topics/4667
 * - Changing orders of actors, danpost, https://www.greenfoot.org/topics/7966/0
 * - Changing variables from actors in world, danpost, https://www.greenfoot.org/topics/63215/0
 * - Resizing world resolution, lordhershey, https://www.greenfoot.org/topics/8490/0
 * - Recalling super constructor / changing resolutions, bourne, https://www.greenfoot.org/topics/454
 * - Scaling images, danpost, https://www.greenfoot.org/topics/60435/0
 * - Accessing variables from actors, danpost, https://www.greenfoot.org/topics/3584
 * - Checking if actors exist, danpost, https://www.greenfoot.org/topics/63143/0
 * - Adding objects to lists, danpost, https://www.greenfoot.org/topics/4641
 * - Collision detection, danpost, https://www.greenfoot.org/topics/4605
 * - General code of Main Menu / Transitioning worlds, Mr.Cohen
 */

public class MainMenuWorld extends World
{
    //instance variables
    private int WIDTH = 600, HEIGHT = 600;
    private Button startGameButton, howToPlayButton, settingsButton;
    private GreenfootSound backgroundMusic;

    public MainMenuWorld(){    
        //creating a new world with default resolutions
        super(600, 600, 1); 
        
        //setting and scaling background image
        GreenfootImage backgroundImage = new GreenfootImage("menuBG.png");
        backgroundImage.scale((int)(WIDTH *2 ), HEIGHT);
        
        GreenfootImage tempbg = new GreenfootImage(WIDTH, HEIGHT);
        
        tempbg.drawImage(backgroundImage, (int)((WIDTH * -1) / 2.05), 0);
        setBackground(tempbg);
        
        //set background music
        backgroundMusic = new GreenfootSound("menuMusic.mp3");
        
        //drawing the buttons for the menu
        startGameButton = new Button(WIDTH / 3, HEIGHT / 10, "Start Game");
        addObject(startGameButton, WIDTH / 2, (int)(HEIGHT / 3.3));
        
        howToPlayButton = new Button(WIDTH / 3, HEIGHT / 10, "Tutorial");
        addObject(howToPlayButton, WIDTH / 2, (int)(HEIGHT / 2.4));
        
        settingsButton = new Button(WIDTH / 3, HEIGHT / 10, "Settings");
        addObject(settingsButton, WIDTH / 2, (int)(HEIGHT / 1.87));
    }
    
    //stopping and starting music
    public void started(){
        backgroundMusic.setVolume(80);
        backgroundMusic.playLoop();
    }
    public void stopped(){
        backgroundMusic.pause();
    }
    
    public void act(){
        //booleans that contains whether if a button is clicked on or not
        boolean isClickedGame = startGameButton.getIfSelected();
        boolean isClickedTutor = howToPlayButton.getIfSelected();
        boolean isClickedSet = settingsButton.getIfSelected();
        
        //go to new world if one of the buttons are clicked
        if (isClickedGame){
            Greenfoot.setWorld(new PregameWorld(WIDTH, HEIGHT, backgroundMusic, 30));
        }
        if (isClickedTutor){
            Greenfoot.setWorld(new TutorialWorld(WIDTH, HEIGHT, backgroundMusic, 30));
        }
        if (isClickedSet){
            Greenfoot.setWorld(new SettingsWorld(WIDTH, HEIGHT, backgroundMusic, 30));
        }
    }
}
