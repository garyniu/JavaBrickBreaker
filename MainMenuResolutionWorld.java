import greenfoot.*;

/**
 * This world is called as an alternate to MainMenuWorld.
 * This menu is nearly identical to MainMenuWorld, but is called for different resolutions
 */
public class MainMenuResolutionWorld extends World
{
    //instance variables
    private int WIDTH, HEIGHT, gameMusicVol;
    private Button startGameButton, howToPlayButton, settingsButton;
    private GreenfootSound BGmusic;
    
    //width and height are the worlds resolution,
    //BGmusic and gamemusicvol are just variables carried through to save the volume settings
    //greenfootsound bgmusic caries the background music throuhg the worlds, allowing for starting,
    //stopping and changing music in other worlds
    public MainMenuResolutionWorld(int width, int height, GreenfootSound bgmusic, int BGmusicvol, int gamemusicvol){    
        //creating a new world with supplied width and height
        super(width, height, 1); 
        
        //setting entered varaibles into the instance variables
        WIDTH = width;
        HEIGHT = height;
        BGmusic = bgmusic;
        gameMusicVol = gamemusicvol;
        
        //setting background music if BGmusic is empty
        if (BGmusic == null){
            BGmusic = new GreenfootSound("menuMusic.mp3");
            BGmusic.setVolume(BGmusicvol);
            BGmusic.playLoop();
        }
        
        //setting and scaling background image
        GreenfootImage backgroundImage = new GreenfootImage("menuBG.png");
        backgroundImage.scale((int)(WIDTH *2 ), HEIGHT);
        
        GreenfootImage tempbg = new GreenfootImage(WIDTH, HEIGHT);
        
        tempbg.drawImage(backgroundImage, (int)((WIDTH * -1) / 2.05), 0);
        setBackground(tempbg);
        
        //drawing the buttons for the menu
        startGameButton = new Button(WIDTH / 3, HEIGHT / 10, "Start Game");
        addObject(startGameButton, WIDTH / 2, (int)(HEIGHT / 3.3));
        
        howToPlayButton = new Button(WIDTH / 3, HEIGHT / 10, "Tutorial");
        addObject(howToPlayButton, WIDTH / 2, (int)(HEIGHT / 2.4));
        
        settingsButton = new Button(WIDTH / 3, HEIGHT / 10, "Settings");
        addObject(settingsButton, WIDTH / 2, (int)(HEIGHT / 1.87));
        
    }
    
    //stopping and starting music
    public void stopped(){
        BGmusic.pause();
    }
    public void started(){
        BGmusic.play();
    }
    
    public void act(){
        //booleans that contains whether if a button is clicked on or not
        boolean isClickedGame = startGameButton.getIfSelected();
        boolean isClickedTutor = howToPlayButton.getIfSelected();
        boolean isClickedSet = settingsButton.getIfSelected();
        
        //go to new world if one of the buttons are clicked
        if (isClickedGame){
            Greenfoot.setWorld(new PregameWorld(WIDTH, HEIGHT, BGmusic, gameMusicVol));
        }
        if (isClickedTutor){
            Greenfoot.setWorld(new TutorialWorld(WIDTH, HEIGHT, BGmusic, gameMusicVol));
        }
        if (isClickedSet){
            Greenfoot.setWorld(new SettingsWorld(WIDTH, HEIGHT, BGmusic, gameMusicVol));
        }
    }
}
