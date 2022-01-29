import greenfoot.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

/**
 * A world that is called after sucessworld or deathworld.
 * This world creates and reads from a file containing highscores from other plays, and displays them.
 */

public class HighScoreWorld extends World
{
    //instance variables
    private int WIDTH, HEIGHT, bgmusic, gameMusicVol;
    private String highscorestring;
    private Button returnButton;
    
    //width and height are the worlds resolution, score is the score from the game,
    //BGmusic and gamemusicvol are just variables carried through to save the volume settings
    public HighScoreWorld(int width, int height, int score, int BGmusic, int gamemusicvol)
    {    
        //creating a new world with supplied width and height
        super(width, height, 1); 
        
        //setting entered varaibles into the instance variables
        WIDTH = width;
        HEIGHT = height;
        bgmusic = BGmusic;
        gameMusicVol = gamemusicvol;
        
        //try creating a file for highscores, but catch if there are read/write problems
        try {
            File highScoreStorage = new File("highscores.txt");
            highScoreStorage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }        
        
        //create two arrays to store new highscores, to be written to the file,
        //and old highscores, to be used to compare for a new high score
        int [] highscores = new int [100];
        int [] oldhighscores = new int [100];
        
        //try reading the file and putting it into both of the arrays
        try {
            Scanner scanner = new Scanner(new File("highscores.txt"));
            int i = 0;
            while(scanner.hasNextInt()){
                highscores[i++] = scanner.nextInt();
            }
            
            scanner = new Scanner(new File("highscores.txt"));
            int temp = 0;
            while(scanner.hasNextInt()){
                oldhighscores[temp++] = scanner.nextInt();                
            }
            
            //insert new score and sort both arrays
            highscores[0] = score;
            Arrays.sort(highscores);
            Arrays.sort(oldhighscores);
            
        } catch (Exception e){
            e.printStackTrace();
        }
        
        
        //try writing to the file, using the highscores array
        try {
            FileWriter myWriter = new FileWriter("highscores.txt");
            
            for (int x = 0; x < highscores.length; x++){
                if (Array.get(highscores, x) != null){
                    myWriter.write(Array.get(highscores, x) + "\n");
                }
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //setting and scaling background image
        GreenfootImage backgroundImage = new GreenfootImage("pregameBG.png");
        backgroundImage.scale((int)(WIDTH * 2), HEIGHT);
        
        GreenfootImage tempbg = new GreenfootImage(WIDTH, HEIGHT);
        
        tempbg.drawImage(backgroundImage, (int)((WIDTH * -1) / 2.05), 0);
        setBackground(tempbg);
        
        //drawing the scores display
        getBackground().drawImage(new GreenfootImage("High scores", (int)(WIDTH / 8), Color.BLACK, null), (int)(WIDTH / 5), 0);
        
        getBackground().drawImage(new GreenfootImage("1. " + highscores[99] + " points", (int)(WIDTH / 12), Color.BLACK, null), (int)(WIDTH / 5), (int)(HEIGHT / 6));
        getBackground().drawImage(new GreenfootImage("2. " + highscores[98] + " points", (int)(WIDTH / 12), Color.BLACK, null), (int)(WIDTH / 5), (int)(HEIGHT / 4));
        getBackground().drawImage(new GreenfootImage("3. " + highscores[97] + " points", (int)(WIDTH / 12), Color.BLACK, null), (int)(WIDTH / 5), (int)(HEIGHT / 3));
        getBackground().drawImage(new GreenfootImage("4. " + highscores[96] + " points", (int)(WIDTH / 12), Color.BLACK, null), (int)(WIDTH / 5), (int)(HEIGHT / 2.4));
        getBackground().drawImage(new GreenfootImage("5. " + highscores[95] + " points", (int)(WIDTH / 12), Color.BLACK, null), (int)(WIDTH / 5), (int)(HEIGHT / 2));
        
        //comparing and seeing if the new score is bigger than the old high score
        if (score >= oldhighscores[99] && score != 0){
            getBackground().drawImage(new GreenfootImage("Congratulations!\nNew high score!", (int)(WIDTH / 10), Color.BLACK, Color.LIGHT_GRAY), (int)(WIDTH / 5), (int)(HEIGHT / 1.5));
        }
         
        //adding a return button
        returnButton = new Button(WIDTH / 3, HEIGHT / 10, "To menu");
        addObject(returnButton, (int)(WIDTH / 5), (int)(HEIGHT / 1.1));
    }
    
    public void act(){
        //a boolean that contains whether if the button is clicked on or not
        boolean isClickedReturn = returnButton.getIfSelected();
        
        //goes to the main menu world if true
        if (isClickedReturn){
            Greenfoot.setWorld(new MainMenuResolutionWorld(WIDTH, HEIGHT, null, bgmusic, gameMusicVol));
        }
    }
    
}
