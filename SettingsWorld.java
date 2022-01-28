import greenfoot.*;

/**
 * Settings world is called from either mainmenu worlds.
 * This allows you to change game resolution and volume settings, then returns you to mainmenu.
 */

public class SettingsWorld extends World
{
    //instance variables
    private int WIDTH, HEIGHT, resolution, BGvolume, GameVolume, gameMusicVol;
    private Button returnButton, resButton, BGvolumeButton, GameMusicButton;    
    private GreenfootSound BGmusic;
    
    //width and height are the worlds resolution,
    //gamemusicvol is carried through to save the volume settings
    //greenfootsound bgmusic caries the background music throuhg the worlds, allowing for starting,
    //stopping and changing music in other worlds
    public SettingsWorld(int width, int height, GreenfootSound bgmusic, int gamemusicvol){    
        //creating a new world with default resolutions
        super(width, height, 1); 
        
        //translates entered resolution into current resolution number
        if (width == 600){
            resolution = 1;
        } else if (width == 1200){
            resolution = 2;
        } else if (width == 1600){
            resolution = 3;
        }
        
        //setting entered varaibles into the instance variables
        WIDTH = width;
        HEIGHT = height;
        BGmusic = bgmusic;
        BGvolume = BGmusic.getVolume();
        gameMusicVol = gamemusicvol;
        
        //setting and scaling background image
        GreenfootImage backgroundImage = new GreenfootImage("settingsBG.png");
        backgroundImage.scale((int)(WIDTH * 2), HEIGHT);
        
        GreenfootImage tempbg = new GreenfootImage(WIDTH, HEIGHT);
        
        tempbg.drawImage(backgroundImage, (int)((WIDTH * -1) / 2.1), 0);
        setBackground(tempbg);
        
        //draw text
        getBackground().drawImage(new GreenfootImage("Settings Panel", (int)(WIDTH / 9.375), Color.BLACK, Color.WHITE), 10, 50);
    
        //drawing the buttons to return to the menu
        returnButton = new Button(WIDTH / 3, HEIGHT / 10, "Return");
        addObject(returnButton, (int)(WIDTH / 4.7), (int)(HEIGHT / 1.1));
        
        resButton = new Button(WIDTH / 3, HEIGHT / 10, WIDTH + "x" + WIDTH);
        addObject(resButton, (int)(WIDTH / 2), (int)(HEIGHT / 2));
        
        //volume button to display text depending on what variable is entered
        if (BGvolume <= 0){
            BGvolumeButton = new Button(WIDTH / 3, HEIGHT / 10, "Menu m: off");
        } else {
            BGvolumeButton = new Button(WIDTH / 3, HEIGHT / 10, "Menu m: on");
        }
        addObject(BGvolumeButton, (int)(WIDTH / 2), (int)(HEIGHT / 2.6));
        
        if (gameMusicVol <= 0){
            GameMusicButton = new Button(WIDTH / 3, HEIGHT / 10, "Game m: off");
        } else {
            GameMusicButton = new Button(WIDTH / 3, HEIGHT / 10, "Game m: on");
        }
        addObject(GameMusicButton, (int)(WIDTH / 2), (int)(HEIGHT / 3.7));
    }
    
    //stopping and starting music
    public void stopped(){
        BGmusic.pause();
    }
    public void started(){
        BGmusic.play();
    }
    
    public void act() {
        //booleans that contains whether if a button is clicked on or not
        boolean isClickedReturn = returnButton.getIfSelected();
        boolean isClickedRes = resButton.getIfSelected();
        boolean isClickedBGvol = BGvolumeButton.getIfSelected();
        boolean isClickedGMvol = GameMusicButton.getIfSelected();
        
        //returns to the main menu world with new resolution setting
        if (isClickedReturn){
            if (resolution == 1){
                Greenfoot.setWorld(new MainMenuResolutionWorld(600, 600, BGmusic, BGmusic.getVolume(), gameMusicVol));
            } else if (resolution == 2){
                Greenfoot.setWorld(new MainMenuResolutionWorld(1200, 1200, BGmusic, BGmusic.getVolume(), gameMusicVol));
            } else if (resolution == 3){
                Greenfoot.setWorld(new MainMenuResolutionWorld(1600, 1600, BGmusic, BGmusic.getVolume(), gameMusicVol));
            }
        }
        
        //game music volume setting
        if (isClickedGMvol){
            if (gameMusicVol <= 0){
                gameMusicVol = 30; 
                
                removeObject(GameMusicButton);
                GameMusicButton = new Button(WIDTH / 3, HEIGHT / 10, "Game m: on");
                addObject(GameMusicButton, (int)(WIDTH / 2), (int)(HEIGHT / 3.7));
            } else if (gameMusicVol > 0){
                gameMusicVol = 0; 
                
                removeObject(GameMusicButton);
                GameMusicButton = new Button(WIDTH / 3, HEIGHT / 10, "Game m: off");
                addObject(GameMusicButton, (int)(WIDTH / 2), (int)(HEIGHT / 3.7));
            }
        }
        
        //main menu music volume settings
        if (isClickedBGvol){
            if (BGmusic.getVolume() <= 0){
                BGmusic.setVolume(80); 
                
                removeObject(BGvolumeButton);
                BGvolumeButton = new Button(WIDTH / 3, HEIGHT / 10, "Menu m: on");
                addObject(BGvolumeButton, (int)(WIDTH / 2), (int)(HEIGHT / 2.6));
            } else if (BGmusic.getVolume() > 0){
                BGmusic.setVolume(0); 
                
                removeObject(BGvolumeButton);
                BGvolumeButton = new Button(WIDTH / 3, HEIGHT / 10, "Menu m: off");
                addObject(BGvolumeButton, (int)(WIDTH / 2), (int)(HEIGHT / 2.6));
            }
        }
        
        //looping through 3 different resolutions when the butotn is clicked
        if (isClickedRes){
            resolution++;
            if (resolution == 4){
                resolution = 1;
            }
            
            if (resolution == 1){
                removeObject(resButton);
                resButton = new Button(WIDTH / 3, HEIGHT / 10, "600x600");
                addObject(resButton, (int)(WIDTH / 2), (int)(HEIGHT / 2));
            } else if (resolution == 2){
                removeObject(resButton);
                resButton = new Button(WIDTH / 3, HEIGHT / 10, "1200x1200");
                addObject(resButton, (int)(WIDTH / 2), (int)(HEIGHT / 2));
            } else if (resolution == 3){
                removeObject(resButton);
                resButton = new Button(WIDTH / 3, HEIGHT / 10, "1600x1600");
                addObject(resButton, (int)(WIDTH / 2), (int)(HEIGHT / 2));
            }
        }
    }
}
