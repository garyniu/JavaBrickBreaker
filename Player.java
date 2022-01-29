import greenfoot.*;  
import java.util.List;

/**
 * player paddle, to deflect ball into bricks, called by the gameworld
 */
public class Player extends Actor
{
    //instance variables
    private int PaddleWidth, PaddleSpeed; 
    private int WIDTH, HEIGHT, powerupSoundIndex;
    private int powerupTemp, gameType, timer = 0, preventFire = 0;
    private GreenfootSound powerupSound[]; 
    private boolean hasLaser = false;
    
    public Player(int gametype, int width, int height){
        //setting entered varaibles into the instance variables
        PaddleWidth = (int)(width / 6);
        gameType = gametype;
        WIDTH = width;
        HEIGHT = height;
        
        //draw the player
        setImage(drawPlayer(height));
        
        //change paddle speed depending on gamemode
        if (gameType == 0){
            PaddleSpeed = (int)(WIDTH / 120);
        } else if (gameType == 1){
            PaddleSpeed = (int)(WIDTH / 150);
        }
        
        //creating powerup sound and filling array
        powerupSound = new GreenfootSound[5];
        for (int i = 0; i < powerupSound.length; i++){
            powerupSound[i] = new GreenfootSound("powerup.mp3");
        }
    }
    
    public void act()
    {
        //indent timer every act
        timer++;
        
        //check for collsisions with powerups
        Powerup p = (Powerup)getOneIntersectingObject(Powerup.class); 
        
        //if the player is near the border, move the paddle back
        if (getX() <= (PaddleWidth / 2)){
            setLocation ((PaddleWidth / 2) + 1, getY());
        } else if (getX() >= getWorld().getWidth() - (PaddleWidth / 2)){
            setLocation (getWorld().getWidth() - (PaddleWidth / 2) + 1, getY());
        }
        
        //if space is pressed, and the 1 second interval is passed, and the player has a laser, 
        //fire a laser
        if (Greenfoot.isKeyDown("space") && timer > preventFire && hasLaser){
            getWorld().addObject(new Laser(WIDTH, HEIGHT), getX(), getY());
            preventFire = timer + 100;
        }
        
        //list of balls in the world
        List balls = getWorld().getObjects(Ball.class);
        
        //if there is an interaction with a powerup
        if (p != null){
            //get the type of powerup, generated in powerup actor
            powerupTemp = p.returnPowerUpType();
            
            //if powerup is 0, make paddle larger
            if (powerupTemp == 0 && (PaddleWidth == (int)(WIDTH / 6) || PaddleWidth == (int)(WIDTH / 10))){
                PaddleWidth = (int)(WIDTH / 4);
                setImage(drawPlayer(HEIGHT));
            }
            
            //get the first ball in the list
            Actor ball = (Actor)balls.get(0);
            //if powerup is 1, spawn a ball at the same position as the first ball in the list
            if (powerupTemp == 1){
                getWorld().addObject(new Ball(0, gameType, WIDTH, HEIGHT), ball.getX(), ball.getY());
            }
            
            //if powerup is 2, make the paddle move faster up to a limit
            if (powerupTemp == 2 && PaddleSpeed < (int)(WIDTH / 60)){
                PaddleSpeed += (int)(WIDTH / 300);
            }
            
            //if powerup is 3, make the paddle smaller
            if (powerupTemp == 3 && (PaddleWidth == (int)(WIDTH / 6) || PaddleWidth == (int)(WIDTH / 4))){
                PaddleWidth = (int)(WIDTH / 10);
                setImage(drawPlayer(HEIGHT));
            }
            
            //if the powerup is 4, call the method in gameworld and add a live
            if (powerupTemp == 4){
                ((GameWorld)getWorld()).increaseLive();
            }
            
            //if powerup is 5, enable laser and redraw player
            if (powerupTemp == 5){
                hasLaser = true;
                setImage(drawPlayer(HEIGHT));
            }
            
            //play the powerup sound effect in array
            powerupSound[powerupSoundIndex].play();
            powerupSoundIndex++;
            if (powerupSoundIndex > powerupSound.length - 1){
                powerupSoundIndex = 0;
            }
            
            //remove the laser
            getWorld().removeObject(p);
        }
        //check for keypresses
        keyPress();
    }
    
    private GreenfootImage drawPlayer(int height){
        //draw the player
        GreenfootImage image = new GreenfootImage(PaddleWidth, (int)(height / 30));
        
        image = new GreenfootImage("player.png");
        image.scale(PaddleWidth, (int)(height / 30));
        
        //if the player has the laser powerup, switch images
        if (hasLaser){
            image = new GreenfootImage("playerL.png");
            image.scale(PaddleWidth, (int)(height / 30));
        }
        
        return image;
    }
    
    private void keyPress() {
        //check for left right keypresses
        String press = Greenfoot.getKey();
        
        if (Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left")){
            setLocation (getX() - PaddleSpeed, getY());
        }
        if (Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right")){
            setLocation (getX() + PaddleSpeed, getY());
        }
        
        //small cheat to get past levels faster
        if (Greenfoot.isKeyDown("h")){
            hasLaser = true;
            PaddleSpeed = (int)(WIDTH / 60);
            PaddleWidth = (int)(WIDTH / 4);
            setImage(drawPlayer(HEIGHT));
        }
    }
}
