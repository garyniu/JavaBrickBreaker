import greenfoot.Actor;
import greenfoot.GreenfootImage;
import greenfoot.Color;

/**
 * GUI actor, drawn in gameworld
 */
public class GUI extends Actor
{
    //instance variables
    private int Timer = 0, Score, Lives, gameType, Level, NewOrOld, altTimer = 0 ;
    private int WIDTH, HEIGHT;
    
    public GUI(int width, int height, int timer, int score, int lives, int gametype, int level, int newOrOld, int AltTimer)
    {
        //setting entered varaibles into the instance variables
        WIDTH = width;
        HEIGHT = height;
        NewOrOld = newOrOld;
        altTimer = AltTimer;
        gameType = gametype;
        Timer = timer;
        Score = score;
        Lives = lives;
        Level = level;
        
        //draw the gui in initial form
        setImage(drawGUI());
    }

    public void act(){
        //redraw gui every act
        setImage(drawGUI());
         
        //if GUI is called for the first time, delay and add a timer to the top right screen
        if (NewOrOld == 0){
            if ((int)(altTimer / 40) == 1){
               getImage().drawImage(new GreenfootImage("3", (int)(WIDTH / 5), Color.BLACK, null), (int)(WIDTH / 20), 0);
         
            } else if ((int)(altTimer / 40) == 2){
               getImage().drawImage(new GreenfootImage("2", (int)(WIDTH / 5), Color.BLACK, null), (int)(WIDTH / 20), 0);
         
            } else if ((int)(altTimer / 40) == 3){
               getImage().drawImage(new GreenfootImage("1", (int)(WIDTH / 5), Color.BLACK, null), (int)(WIDTH / 20), 0);
         
            } else if ((int)(altTimer / 40) == 4 || (int)(altTimer / 40) == 5){
               getImage().drawImage(new GreenfootImage("Go!", (int)(WIDTH / 5), Color.BLACK, null), (int)(WIDTH / 20), 0);
         
            }
        }
        
        //if easy mode, just display timer regularly
        //if hard mode, display timer counting down from 180
        if (gameType == 0){
            getImage().drawImage(new GreenfootImage(Integer.toString(Timer / 60) + " seconds", (int)(WIDTH / 20), Color.BLACK, null), (int)(WIDTH / 30), (int)(HEIGHT / 1.1650485));
        } else {
            getImage().drawImage(new GreenfootImage(Integer.toString(180 - (Timer / 60)) + " seconds", (int)(WIDTH / 20), Color.BLACK, null), (int)(WIDTH / 30), (int)(HEIGHT / 1.1650485));
        }
        
        //draw rest of gui and information to update every act
        getImage().drawImage(new GreenfootImage(Integer.toString(Score) + " | Score", (int)(WIDTH / 20), Color.BLACK, null), (int)(WIDTH / 30), (int)(HEIGHT / 1.1111));
        getImage().drawImage(new GreenfootImage(Integer.toString(Lives) + " | Lives", (int)(WIDTH / 20), Color.BLACK, null), (int)(WIDTH / 30), (int)(HEIGHT / 1.06194));
        getImage().drawImage(new GreenfootImage("Level " + Integer.toString(Level), (int)(WIDTH / 15), Color.BLACK, null), (int)(WIDTH / 2), (int)(HEIGHT / 1.090909));
    }
        
    //draw the gui, which is just an image
    private GreenfootImage drawGUI(){
        GreenfootImage image = new GreenfootImage(WIDTH, HEIGHT);
        
        image = new GreenfootImage("gui.png");
        image.scale(WIDTH, HEIGHT);
        
        return image;
    }
}
