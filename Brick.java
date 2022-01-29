import greenfoot.*;  
import greenfoot.GreenfootImage;

/**
 * brick actor, for the ball to interact and destory.
 */
public class Brick extends Actor
{
    //instance variables
    private int brickHP, antiBrick, score;
    private GreenfootSound breakSound[]; 
    private int breakSoundIndex, WIDTH, HEIGHT;
    
    //bricktype determines the hp value of the brick
    public Brick(int brickType, int width, int height){
        //setting entered varaibles into the instance variables
        brickHP = brickType;
        WIDTH = width;
        HEIGHT = height;
        
        //drawing the brick with entered values
        setImage(drawBrick(brickHP, width, height));
        
        //creating break sound and putting it into an array
        breakSound = new GreenfootSound[5];
        
        for (int i = 0; i < breakSound.length; i++){
            breakSound[i] = new GreenfootSound("brickBreak.mp3");
        }
    }
    
    //methods called by Ball actor to update score and brickhp (might be unused)
    public int getBrickHP(){
        return brickHP;
    }
    public int getScore(){
        return score;
    }
    
    public void act()
    {
        //add variables to detect for an intersection object, which could be the ball or the laser
        Ball b = (Ball)getOneIntersectingObject(Ball.class); 
        Laser l = (Laser)getOneIntersectingObject(Laser.class);
        
        //if the brick has no remaining hp, remove it
        if (brickHP <= 0){
            getWorld().removeObject(this);
            return;
        }
        
        //if there is a colision with the ball, and the brick is not an invinicble brick,
        //remove an hp, and indent score with the method inside Ball actor, 
        //and update image
        if (b != null && brickHP != 16){
            brickHP--;
            score++;
            b.indentScore();
            setImage(drawBrick(brickHP, WIDTH, HEIGHT));
        }
        
        //if there is a colision with a laser actor, 
        //remove the laser, and check if the brick is invincible,
        //if not, remove a brickhp and indent score with method inside ball, 
        //draw image, and play brickbreaking sound
        if (l != null){
            getWorld().removeObject(l);
            if (brickHP != 16){
                brickHP--;
                score++;
                ((GameWorld)getWorld()).scoreIndent();
            }
            setImage(drawBrick(brickHP, WIDTH, HEIGHT));
            
            breakSound[breakSoundIndex].play();
            breakSoundIndex++;
            if (breakSoundIndex > breakSound.length - 1){
                breakSoundIndex = 0;
            }
        }
    }
    
    private GreenfootImage drawBrick(int col, int WIDTH, int HEIGHT){
        //draw the image of the brick depending on what hp it has
        GreenfootImage image = new GreenfootImage((int)(WIDTH / 12), (int)(HEIGHT / 30));
        
        if (col == 1){
            image = new GreenfootImage("brick3.png");
            image.scale((int)(WIDTH / 12), (int)(HEIGHT / 30));
        } else if (col == 2){
            image = new GreenfootImage("brick2.png");
            image.scale((int)(WIDTH / 12), (int)(HEIGHT / 30));
        } else if (col == 3){
            image = new GreenfootImage("brick1.png");
            image.scale((int)(WIDTH / 12), (int)(HEIGHT / 30));
        } else if (col == 16){
            image = new GreenfootImage("brickgod.png");
            image.scale((int)(WIDTH / 12), (int)(HEIGHT / 30));    
        }
        
        return image;
    }
}
