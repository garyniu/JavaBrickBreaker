import greenfoot.*; 

/**
 * world called on by either mainmenu worlds.
 * displays a simple tutorial on how to play the game.
 */
public class TutorialWorld extends World
{
    //instance variables
    private int WIDTH, HEIGHT, gameMusicVol;
    private Button returnButton;
    private GreenfootSound BGmusic;
    
    public TutorialWorld(int width, int height, GreenfootSound bgmusic, int gamemusicvol)
    {    
        //creating a new world with supplied values
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
        
        //draw 2 texts, one for a shadow effect
        getBackground().drawImage(new GreenfootImage("How to Play", (int)(WIDTH / 8.571428), Color.LIGHT_GRAY, null), (int)(WIDTH / 4.4), (int)(HEIGHT / 30));
        getBackground().drawImage(new GreenfootImage("How to Play", (int)(WIDTH / 9.375), Color.BLACK, null), (int)(WIDTH / 4), (int)(HEIGHT / 24));
        
        //general text
        getBackground().drawImage(new GreenfootImage("Use either the a/d or arrow keys to \nmove the paddle.\nPrevent the ball from hitting the ground.\nCollect powerups to gain \nadvantages or disadvanges.\nPlay until you destroy all bricks.", (int)(WIDTH / 18.75), Color.BLACK, null), (int)(WIDTH / 8), (int)(HEIGHT / 6));
        
        //drawing the button for the menu
        returnButton = new Button(WIDTH / 3, HEIGHT / 10, "Return");
        addObject(returnButton, (int)(WIDTH / 4.7), (int)(HEIGHT / 1.1));
    
    }
    
    //stopping and starting music
    public void stopped(){
        BGmusic.pause();
    }
    public void started(){
        BGmusic.play();
    }
    
    public void act(){
        //boolean that contains whether if the button is clicked on or not
        boolean isClickedReturn = returnButton.getIfSelected();
        
        //if clicked, return to mainmenuresolutionworld with supplied variables
        if (isClickedReturn){
            Greenfoot.setWorld(new MainMenuResolutionWorld(WIDTH, HEIGHT, BGmusic, BGmusic.getVolume(), gameMusicVol));
        }
    }
}
