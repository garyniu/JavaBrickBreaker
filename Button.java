import greenfoot.*;
import greenfoot.GreenfootImage;
import greenfoot.Color;

/**
 * Button actor, used in menus to redirect to another world
 */

public class Button extends Actor
{
    //instance variables
    private boolean selected = false;
    private int WIDTH, HEIGHT, clickSoundIndex;
    private String TEXTTODISP;
    private GreenfootSound clickSound[];
    
    //not sure why width and height are floats but i could not care anymore
    //i am solely surviving off of monster energy drink and hershey choclate chips 
    //mixed with whole wheat uncooked oatmeal
    public Button(float width, float height, String textToDisplay)
    {
        //setting entered varaibles into the instance variables
        WIDTH = (int)width;
        HEIGHT = (int)height;
        TEXTTODISP = textToDisplay;
        
        //drawing image of button with included variables
        setImage(drawButton(WIDTH, HEIGHT, Color.DARK_GRAY, textToDisplay));
        
        //create new sound for click sfx, and put into an array
        clickSound = new GreenfootSound[5];
        
        for (int i = 0; i < clickSound.length; i++){
            clickSound[i] = new GreenfootSound("click.mp3");
        }
    }
    
    public void act(){
        //reset selected to false every act
        selected = false;
        
        //check if the mouse is hovering over the button
        if (Greenfoot.mouseMoved(this)){
            setImage(drawButton(WIDTH, HEIGHT, Color.LIGHT_GRAY, TEXTTODISP));
        }
        //check if mouse move off the button
        if (Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(this)){
            setImage(drawButton(WIDTH, HEIGHT, Color.DARK_GRAY, TEXTTODISP));
        }
        
        //if mouse clicks, selected becomes true and play the click sound
        if (Greenfoot.mousePressed(this)){
            selected = true;
            clickSound[clickSoundIndex].play();
            clickSoundIndex++;
            if (clickSoundIndex > clickSound.length - 1){
                clickSoundIndex = 0;
            }
        }
    }
    
    //method called by worlds to return selected
    public boolean getIfSelected(){
        return selected;
    }

    //draw the image of the button
    private GreenfootImage drawButton(int width, int height, Color c, String textToDisplay){
        GreenfootImage image = new GreenfootImage(width, height);
        
        image.setColor(c);
        image.fillRect(0, 0, width, height);
        image.drawImage(new GreenfootImage(textToDisplay, (int)(width / 5), Color.WHITE, null), (int)(WIDTH / 40), (int)(height / 6));
        
        return image;
    }
}
