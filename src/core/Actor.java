package core;

import org.newdawn.fizzy.Body;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

/**
 * Actor
 * Basis for Enemies and Player Characters
 * @author Kieran Clare
 *
 */
public class Actor {
	//Graphics Essencials
    Animation current, movingUp, movingDown, movingLeft, movingRight, idleLeft, idleRight, jumpLeft, jumpRight,
    landLeft, landRight;
    Image spriteImage;
	long timer = 0;
	
	//Character Name
	String name;
    
    //Movement Essencials
	Rectangle boundingBox;
	boolean finishedX = false;
	boolean finishedY = false;
	
	//Character Positions
	int positionX = 5;
	int positionY = 5;
	float x;
	float y;	   
    
	/**
	 * Actor
	 * Initialises Assets
	 * @param name
	 * @param spritesheet
	 * @param moveSprites
	 * @param moveTileWidth
	 * @param moveTileHeight
	 * @param jumpTileWidth
	 * @param jumpTileHeight
	 * @param idleTileWidth
	 * @param idleTileHeight
	 * @throws SlickException
	 */
    public Actor(String name, 
    		String spritesheet, 
    		int moveSprites, int moveTileWidth, int moveTileHeight,
    		int jumpTileWidth, int jumpTileHeight,
    		int idleTileWidth, int idleTileHeight) throws SlickException{
    	
    	//Name
		this.name = name;
		
		//Images
		spriteImage = new Image(spritesheet);
		//Movement
        SpriteSheet spriteSheetMoveLeft = new SpriteSheet(spriteImage, moveTileWidth, moveTileHeight);
        
        //Animations
        //Movement
        movingLeft = new Animation(true);
  		for (int frame=moveSprites-1;frame>0;frame--) {
  			movingLeft.addFrame(spriteSheetMoveLeft.getSprite(frame, 1), 130);
  		}
  		
  		SpriteSheet spriteSheetMoveRight = new SpriteSheet(spriteImage, moveTileWidth, moveTileHeight);
        
        movingRight = new Animation(true);
  		for (int frame=moveSprites;frame<moveSprites*2;frame++) {
  			movingRight.addFrame(spriteSheetMoveRight.getSprite(frame, 1), 130);
  		}
  		
        //Static
  		SpriteSheet spriteSheetIdle = new SpriteSheet(spriteImage, idleTileWidth, idleTileHeight);
  		idleLeft = new Animation(true);  
  	 	idleLeft.addFrame(spriteSheetIdle.getSprite(0,0), 130);
  	 	
  	 	idleRight = new Animation(true);  
  	 	idleRight.addFrame(spriteSheetIdle.getSprite(1,0), 130);
  		
  	 	//Jumping
  	 	SpriteSheet spriteSheetJump = new SpriteSheet(spriteImage, jumpTileWidth, jumpTileHeight);
  	 	this.jumpLeft = new Animation(true);
  	 	this.jumpLeft.addFrame(spriteSheetJump.getSprite(2,0), 130);
  	 	this.jumpRight = new Animation(true);
  	 	this.jumpRight.addFrame(spriteSheetJump.getSprite(3,0), 130);
  	 	
  		//The Currently Displayed Animation
  		current = new Animation();
  		current = idleRight;
  		
  		//Positions
		//The Position of the character's animation
		x = 60;
		y = 100;
		//Bounding Box
		//The shape that encompases the player for various reasons
		boundingBox = new Rectangle(x, y, 32, 32);
		
  		
    }
    
    /**
     * Set Position
     * Sets the player's current position.
     * @param x
     * @param y
     */
    public void setPosition(float x, float y){
    	this.x = x;
    	this.y = y;
    	
    }
    
	/**
	 * getBox
	 * Gets bounding box
	 * @return
	 */
    public Rectangle getBox(){
    	return boundingBox;
    }
    
    /**
     * Lerp
     * Linear Interpolation calculation
     * @param a
     * @param b
     * @param t
     * @return
     */
    public float lerp(float a, float b, float t) {
        if (t < 0)
           return a;

        return a + t * (b - a);
     }
    
    /**
     * Get Player Name
     * Returns the player character's name.
     * @return
     */
    public String getPlayerName(){
        return name;
    }

    /**
     * Render
     * For the Inventory
     * @param g
     * @param f
     * @param h
     */
	public void render(Graphics g, float f, float h) {
		g.drawAnimation(current, f, h);
		
	}
    
}
