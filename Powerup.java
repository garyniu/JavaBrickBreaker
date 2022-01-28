import greenfoot.*; 

/**
 * Powerup actor, called by ball to give player powerups at random
 */
public class Powerup extends Actor
{
    //instance variables
    private int powerUpType, WIDTH, HEIGHT;
    
    public Powerup(int width, int height){
        //setting entered varaibles into the instance variables
        WIDTH = width;
        HEIGHT = height;
        powerUpType = (Greenfoot.getRandomNumber(6));
        
        //draw powerup
        setImage(drawPowerUp(powerUpType, WIDTH, HEIGHT));
    }
    
    //method that is called by player actor to determine what type of powerup it is
    public int returnPowerUpType(){
        return powerUpType;
    }
    
    public void act()
    {
        //move the actor down
        setLocation( getX(), getY() + 2);
        
        //check if the powerup is at the edge of the world, and remove
        if (isAtEdge()){
            getWorld().removeObject(this);
        }
    }
    
    private GreenfootImage drawPowerUp(int type, int width, int height){
        
        //draw the powerup depending on what type it is
        GreenfootImage image = new GreenfootImage((int)(width / 20), (int)(height / 20));
        
        if (type == 0){
            image = new GreenfootImage("power3.png");
        } else if (type == 1){
            image = new GreenfootImage("power4.png");
        } else if (type == 2){
            image = new GreenfootImage("power1.png");
        } else if (type == 3){
            image = new GreenfootImage("power2.png");
        } else if (type == 4){
            image = new GreenfootImage("power5.png");
        } else if (type == 5){
            image = new GreenfootImage("power6.png");
        }
        
        image.scale((int)(width / 20), (int)(height / 20));
        
        return image;
    }
}
