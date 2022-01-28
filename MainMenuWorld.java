import greenfoot.*;

/**
 * WRITE HERE
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
