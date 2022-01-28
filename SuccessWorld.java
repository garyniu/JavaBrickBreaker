import greenfoot.*;  

/**
 * Success world is called from gameworld after beating all levels
 * calls a simple background and displays the users score, before going to highscore world.
 */

public class SuccessWorld extends World
{
    //instance variables
    private int WIDTH, HEIGHT, bgmusic, gameMusicVol, Score;
    private Button returnButton;
    
    //width and height are the worlds resolution, score is the score from the game,
    //gamemusicvol and BGmusic are carried through to save the volume settings
    public SuccessWorld(int width, int height, int score, int BGmusic, int gamemusicvol)
    {    
        //creating a new world with default resolutions
        super(width, height, 1); 
        
        //setting entered varaibles into the instance variables
        WIDTH = width;
        HEIGHT = height;
        Score = score;
        bgmusic = BGmusic;
        gameMusicVol = gamemusicvol;
        
        //setting and scaling background image
        GreenfootImage backgroundImage = new GreenfootImage("winBG.png");
        backgroundImage.scale((int)(WIDTH * 2), HEIGHT);
        
        GreenfootImage tempbg = new GreenfootImage(WIDTH, HEIGHT);
        
        tempbg.drawImage(backgroundImage, (int)((WIDTH * -1) / 2), 0);
        setBackground(tempbg);
        
        //draw text
        getBackground().drawImage(new GreenfootImage("Your score is: " + Integer.toString(score), (int)(width / 9), Color.BLACK, Color.WHITE), (int)(WIDTH / 6), (int)(HEIGHT / 4));
        
        //drawing the button to go to highscoreworld
        returnButton = new Button(WIDTH / 3, HEIGHT / 10, "To scores");
        addObject(returnButton, (int)(WIDTH / 2), (int)(HEIGHT / 1.1));
        
        //playing a sound effect once you enter the world
        GreenfootSound winSound = new GreenfootSound("win.mp3");
        winSound.play();
    }
    
    public void act () {
        //boolean that contains whether if the button is clicked on or not
        boolean isClickedReturn = returnButton.getIfSelected();
        
        //if the button clicked is true, then go to highscoreworld
        if (isClickedReturn){
            Greenfoot.setWorld(new HighScoreWorld(WIDTH, HEIGHT, Score, bgmusic, gameMusicVol));
        }
    }
}
