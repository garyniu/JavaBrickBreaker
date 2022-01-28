import greenfoot.*;

/**
 * Laser actor called by the player to collide with brick
 */
public class Laser extends Actor
{
    //instance variables
    private int WIDTH, HEIGHT;
    private GreenfootSound laserSound;
    
    public Laser(int width, int height){
        //draw laser
        setImage(drawLaser(width, height));
        
        //setting entered varaibles into the instance variables
        WIDTH = width;
        HEIGHT = height;
        
        //setting up laser sound effect and playing once the actor is called
        laserSound = new GreenfootSound("laser.mp3");
        laserSound.setVolume(50);
        laserSound.play();
    }
    
    public void act()
    {
        //moves the laser up
        setLocation(getX(), getY() - (int)(WIDTH / 75));
        
        //if the laser is at edge, remove itself
        if (isAtEdge()){
            getWorld().removeObject(this);
        }
    }
    
    //drawing laser
    private GreenfootImage drawLaser(int width, int height){
        GreenfootImage image = new GreenfootImage((int)(width / 60), (int)(height / 20));
        
        image.setColor(Color.RED);
        image.fillRect(0, 0, (int)(width / 60), (int)(height / 20));
        
        return image;
    }
}
