import greenfoot.*;
import greenfoot.GreenfootSound;

/**
 * A world that is called after either MainMenuWorld or MainMenuResolutionWorld
 * Pregame menu that is called before the main game to chose game difficulty
 */
public class PregameWorld extends World
{
    //instance variables
    private int WIDTH, HEIGHT, gameType = 0, gameMusicVol;
    private Button returnButton, startGameButton, gameModeButton;
    private GreenfootSound BGmusic;
    
    //width and height are the worlds resolution,
    //gamemusicvol is carried through to save the volume settings
    //greenfootsound bgmusic caries the background music throuhg the worlds, allowing for starting,
    //stopping and changing music in other worlds
    public PregameWorld(int width, int height, GreenfootSound bgmusic, int gamemusicvol){    
        //creating a new world with default resolutions
        super(width, height, 1); 
        
        //setting entered varaibles into the instance variables
        WIDTH = width;
        HEIGHT = height;
        BGmusic = bgmusic;
        gameMusicVol = gamemusicvol;
        
        //setting and scaling background image
        GreenfootImage backgroundImage = new GreenfootImage("pregameBG.png");
        backgroundImage.scale((int)(WIDTH * 2), HEIGHT);
        
        GreenfootImage tempbg = new GreenfootImage(WIDTH, HEIGHT);
        
        tempbg.drawImage(backgroundImage, (int)((WIDTH * -1) / 2.05), 0);
        setBackground(tempbg);
        
        //drawing the buttons for the menu
        returnButton = new Button(WIDTH / 3, HEIGHT / 10, "Return");
        addObject(returnButton, (int)(WIDTH / 4.7), (int)(HEIGHT / 1.1));
        
        startGameButton = new Button(WIDTH / 3, HEIGHT / 10, "Start Game");
        addObject(startGameButton, (WIDTH / 2), (int)(HEIGHT / 1.4));
        
        gameModeButton = new Button(WIDTH / 3, HEIGHT / 10, "Easy mode");
        addObject(gameModeButton, (WIDTH / 2), (int)(HEIGHT / 1.8));
        
        //draw text
        getBackground().drawImage(new GreenfootImage("Pregame Settings", (int)(WIDTH / 9.375), Color.BLACK, null), (int)(WIDTH / 6), (int)(HEIGHT / 12));
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
        boolean isClickedReturn = returnButton.getIfSelected();
        boolean isClickedStart = startGameButton.getIfSelected();
        boolean isClickedGM = gameModeButton.getIfSelected();
        
        //start game if button is clicked
        if (isClickedStart){
            BGmusic.stop();
            Greenfoot.setWorld(new GameWorld(WIDTH, HEIGHT, gameType, BGmusic.getVolume(), gameMusicVol));
        }

        //switches gametype if butotn is clicked
        if (isClickedGM){
            if (gameType == 0){
                gameType = 1;
                
                removeObject(gameModeButton);
                gameModeButton = new Button(WIDTH / 3, HEIGHT / 10, "Hard mode");
                addObject(gameModeButton, WIDTH / 2, (int)(HEIGHT / 1.8));
                
            } else if (gameType == 1){
                gameType = 0;
                
                removeObject(gameModeButton);
                gameModeButton = new Button(WIDTH / 3, HEIGHT / 10, "Easy mode");
                addObject(gameModeButton, WIDTH / 2, (int)(HEIGHT / 1.8));
            }
        }
    
        //returns to main menu if clicked
        if (isClickedReturn){
            Greenfoot.setWorld(new MainMenuResolutionWorld(WIDTH, HEIGHT, BGmusic, BGmusic.getVolume(), gameMusicVol));
        }
    }
}
