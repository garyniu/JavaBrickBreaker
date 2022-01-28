import greenfoot.*;

/**
 * A world that is called after the user runs out of lives.
 * This world includes an image and the users score, right before going to the highscore world.
 */
public class DeathWorld extends World
{
    //instance variables
    private int WIDTH, HEIGHT, bgmusic, gameMusicVol, Score;
    private Button returnButton;
    
    //width and height are the worlds resolution, score is the score from the game,
    //BGmusic and gamemusicvol are just variables carried through to save the volume settings
    public DeathWorld(int width, int height, int score, int BGmusic, int gamemusicvol)
    {    
        //creating a new world with supplied width and height
        super(width, height, 1); 
        
        //setting entered varaibles into the instance variables
        WIDTH = width;
        HEIGHT = height;
        Score = score;
        bgmusic = BGmusic;
        gameMusicVol = gamemusicvol;
        
        //setting and scaling background image
        GreenfootImage backgroundImage = new GreenfootImage("deathBG.png");
        backgroundImage.scale((int)(WIDTH * 2), HEIGHT);
        GreenfootImage tempbg = new GreenfootImage(WIDTH, HEIGHT);
        tempbg.drawImage(backgroundImage, (int)((WIDTH * -1) / 2), 0);
        setBackground(tempbg);
        
        //telling user their score
        getBackground().drawImage(new GreenfootImage("Your score is: " + Integer.toString(score), (int)(width / 9), Color.BLACK, null), (int)(WIDTH / 6), (int)(HEIGHT / 1.5));
        
        //drawing the button to high scores
        returnButton = new Button(WIDTH / 3, HEIGHT / 10, "To scores");
        addObject(returnButton, (int)(WIDTH / 2), (int)(HEIGHT / 1.1));
        
        //play the loss sound right when the world is called
        GreenfootSound loseSound= new GreenfootSound("lose.mp3");
        loseSound.setVolume(40);
        loseSound.play();
    }
    
    public void act(){
        
        //a boolean that contains whether if the button is clicked on or not
        boolean isClickedReturn = returnButton.getIfSelected();
        
        //goes to the highscore world if true
        if (isClickedReturn){
            Greenfoot.setWorld(new HighScoreWorld(WIDTH, HEIGHT, Score, bgmusic, gameMusicVol));
        }
        
    }
}
