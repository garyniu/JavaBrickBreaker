import greenfoot.*; 

/**
 * This actor creates the ball and manages the majority of its physics.
 */
public class Ball extends Actor
{
    //instance variables
    private int ballSpeedX, oneHitOnly, ballSpeedY;
    private int brickHealth, tempPlayerX, tempBallX, delay = 0, delayEnd, NewOrOld;
    private int score = 0, angle, gameType, WIDTH, HEIGHT;
    private GreenfootSound ballSound[], breakSound[], deathSound; 
    private int ballSoundsIndex, breakSoundIndex;
    
    public Ball(int newOrOld, int gametype, int width, int height)
    {
        //draw the ball from the method
        setImage(drawBall(width, height));
        
        //setting entered varaibles into the instance variables
        ballSoundsIndex = 0;
        breakSoundIndex = 0;
        WIDTH = width;
        HEIGHT = height;
        NewOrOld = newOrOld;
        gameType = gametype;
        
        //generate the inital speed of the ball
        ballSpeedX = (Greenfoot.getRandomNumber((int)(WIDTH / 600)) + (int)(WIDTH / 300));
        ballSpeedY = (Greenfoot.getRandomNumber((int)(WIDTH / 600)) + (int)(WIDTH / 300));
    
        //part of random num generation, flips the x direction
        int temp = Greenfoot.getRandomNumber(2);
        if (temp == 1){
            ballSpeedX *= -1;
        }
        
        //if the ball is for the inital spawn, the delay is set to 3 seconds
        if (newOrOld == 1){
            ballSpeedY *= -1;
            delayEnd = 3;
        }
        
        //declaring sound in arrays
        ballSound = new GreenfootSound[15];
        breakSound = new GreenfootSound[5];
        deathSound = new GreenfootSound("death.mp3");
        
        //filling arrays with sound objects
        for (int i = 0; i < ballSound.length; i++){
            ballSound[i] = new GreenfootSound("ballBounce.wav");
        }
        for (int i = 0; i < breakSound.length; i++){
            breakSound[i] = new GreenfootSound("brickBreak.mp3");
        }
    }
    
    //method to indent score in gameworld, called by brick method
    public void indentScore()
    {
        ((GameWorld)getWorld()).scoreIndent();
    }
    
    public void act()
    {
        //get the object that the ball is colliding with
        Brick b = (Brick)getOneIntersectingObject(Brick.class);  
        Player p = (Player)getOneIntersectingObject(Player.class);
        
        //have a pregame delay 
        delay++;
        if ((delay / 60) < delayEnd && NewOrOld == 1){
            return;
        }
        
        //if there is a colision with a brick
        if (b != null){
            //put the bricks health in a temp variable
            brickHealth = b.getBrickHP();
            
            //invert balls speed depending on which side it hit, and move over to said side
            //to prevent double hiting
            if (getX() <= b.getX() - (int)(WIDTH / 24)|| getX() >= b.getX() + (int)(WIDTH / 24)) {
                ballSpeedX *= -1;
                
                if ((getX() - b.getX()) < 0){
                    setLocation( getX() - (int)(WIDTH / 300), getY());
                }
                if ((getX() - b.getX()) > 0){
                    setLocation( getX() + (int)(WIDTH / 300), getY());
                }
            }
            if (getY() >= b.getY() + (int)(WIDTH / 120) || getY() <= b.getY() - (int)(WIDTH / 120)) {
                ballSpeedY *= -1;
                
                if ((getY() - b.getY()) < 0){
                    setLocation( getX(), getY() - (int)(WIDTH / 300));
                }
                if ((getY() - b.getY()) > 0){
                    setLocation( getX(), getY() + (int)(WIDTH / 300));
                }
            }
            
            //if brickhealth is not 16, randomly spawn a powerup at the bricks coordinates
            if (brickHealth != 16){
                int temp = Greenfoot.getRandomNumber(3);
                if (temp == 2 && gameType == 0){
                    //change
                    getWorld().addObject(new Powerup(WIDTH, HEIGHT), b.getX(), b.getY());
                }
            }
            
            //if the ball speeds are too fast, remove one bit of speed 
            if (ballSpeedX > (int)(WIDTH / 200)){
                ballSpeedX--;
            } else if (ballSpeedX < -(int)(WIDTH / 200)){
                ballSpeedX++;
            }
            
            if (ballSpeedY > (int)(WIDTH / 200)){
                ballSpeedY--;
            } else if (ballSpeedY < -(int)(WIDTH / 200)){
                ballSpeedY++;
            }
            
            //playing sound effects from the array
            ballSound[ballSoundsIndex].play();
            ballSoundsIndex++;
            if (ballSoundsIndex > ballSound.length - 1){
                ballSoundsIndex = 0;
            }
            
            breakSound[breakSoundIndex].play();
            breakSoundIndex++;
            if (breakSoundIndex > breakSound.length - 1){
                breakSoundIndex = 0;
            }
        }
         
        //if there is a collision with the player
        if (p != null){
            
            //use trigonmetry to determine what speed that the player should move off the paddle
            ballSpeedY *= -1;
            int offset = getX() - p.getX();
            ballSpeedX = ballSpeedX + (offset / (int)(WIDTH / 85.714285));
            
            //add a bit to the y axis speed
            ballSpeedY++;
            
            //move the ball out of the paddle to prevent double hitting
            if ((getX() - p.getX()) < 0){
                setLocation( getX() + (int)(WIDTH / 300), getY());
            }
            if ((getX() - p.getX()) > 0){
                setLocation( getX() - (int)(WIDTH / 300), getY());
            }
            
            if ((getY() - p.getY()) < 0){
                setLocation( getX(), getY() - (int)(WIDTH / 300));
            }
            if ((getY() - p.getY()) > 0){
                setLocation( getX(), getY() + (int)(WIDTH / 300));
            }
            
            //play sound effect from array
            ballSound[ballSoundsIndex].play();
            ballSoundsIndex++;
            if (ballSoundsIndex > ballSound.length - 1){
                ballSoundsIndex = 0;
            }
        }
        
        //if the ball is at the edge of the world
        if (isAtEdge()){
            
            //if the ball is at the left, right, or top of the world, invert speed
            if (getY() == 0){
                ballSpeedY *= -1;
            }
            if (getX() == 0 || getX() == getWorld().getWidth()-1){
                ballSpeedX *= -1;
            }
            
            //if the ball is too fast, remove some speed
            if (ballSpeedX > (int)(WIDTH / 200)){
                ballSpeedX--;
            } else if (ballSpeedX < -(int)(WIDTH / 200)){
                ballSpeedX++;
            }
            
            //play ball bounce sound effect from array
            ballSound[ballSoundsIndex].play();
            ballSoundsIndex++;
            if (ballSoundsIndex > ballSound.length - 1){
                ballSoundsIndex = 0;
            }
            
            //if the ball hits the ground, remove the ball (just in case)
            if (getY() == getWorld().getHeight() - 1){
                getWorld().removeObject(this);
                return;
            }
        }
        
        //if the ball hits the space above the GUI, remove the ball and play the death sound
        if (getY() >= getWorld().getHeight() - (int)(WIDTH / 6)){
            deathSound.play();
            getWorld().removeObject(this);
            return;
        }
        
        //moving the ball
        setLocation(getX() + ballSpeedX, getY() - ballSpeedY);
    }
    
    //drawing the ball
    private GreenfootImage drawBall(int width, int height){
        
        GreenfootImage image = new GreenfootImage("ball.png");
        image.scale((int)(width / 30), (int)(height / 30));
        
        return image;
    }
}
