import java.util.ArrayList;


import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

/**
 * The Base Actor Class
 * 
 * Used to represent the player.
 * @author Kieran
 *
 */

public class Actor {
    Animation current, movingUp, movingDown, movingLeft, movingRight, idleLeft, idleRight;
    Image moveUp, moveDown, moveLeft, moveRight, stayLeft, stayRight;
    Boolean moveInProgress;
    int currentMoveStep = 14;
	Cell currentPosition;
	Cell nextPosition;
	Cell actionZone;
	Cell [][] grid;
	
	boolean busy = false;
	long timer = 0;
	float gravity = 0.3f;
	float jumpForce = 0.6f;
	String name;
	
    private int maxHP = 150;
    private int HP = 150;
    private int speed = 20;
    private int strength = 10;
    private int special = 50;
    private int maxEnergy = 70;
    private int energy = 70;
	
	Rectangle boundingBox;
	
	boolean finishedX = false;
	boolean finishedY = false;
	
	   int positionX = 5;
	   int positionY = 5;
	   float x = 0;
	   float y = 0;	   
    
    public Actor(String name, Cell[][] grid, String upImage, String downImage, String leftImage, String rightImage, String standLeft, String standRight, int tileWidth, int tileHeight) throws SlickException{
		//Updates the grid.
    	this.grid = grid;
    	
		this.name = name;
		//Sets up sprites.
    	moveUp = new Image(upImage);
        SpriteSheet up = new SpriteSheet(moveUp, tileWidth, tileHeight);
        moveDown = new Image(downImage);
        SpriteSheet down = new SpriteSheet(moveDown, tileWidth, tileHeight);
        moveLeft = new Image(leftImage);
        SpriteSheet left = new SpriteSheet(moveLeft, tileWidth, tileHeight);
        moveRight = new Image(rightImage);
        SpriteSheet right = new SpriteSheet(moveRight, tileWidth, tileHeight);
        
        stayLeft = new Image(standLeft);
        SpriteSheet idleL = new SpriteSheet(standLeft, tileWidth, tileHeight);
        stayRight = new Image(standRight);
        SpriteSheet idleR = new SpriteSheet(standRight, tileWidth, tileHeight);
        
        //Links sprites with animations.
        movingUp = new Animation(true); //each animation takes array of images, duration for each image, and autoUpdate (just set to false)   
  		movingUp.addFrame(up.getSprite(0,0), 100);
        
        movingDown = new Animation(true);
        movingDown.addFrame(down.getSprite(0,0), 100);
        
        movingLeft = new Animation(true);
  		movingLeft.addFrame(left.getSprite(0,0), 100);
        
        movingRight = new Animation(true);
  		movingRight.addFrame(right.getSprite(0,0), 100);
        
  		idleLeft = new Animation(true);  
  	 	idleLeft.addFrame(idleL.getSprite(0,0), 130);
  	 	
  	 	idleRight = new Animation(true);  
  	 	idleRight.addFrame(idleR.getSprite(0,0), 130);
  			
  		current = new Animation();
  		current = movingRight;
  		
  		//Positions character within grid.
		currentPosition = this.grid[positionX][positionY];
		
  		nextPosition = this.grid[positionX][positionY];
  		actionZone = this.grid[positionX - 1][positionY];
		
		x = this.grid[positionX][positionY].rect.getX();
		y = this.grid[positionX][positionY].rect.getY();
		
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
     * Render
     * Draws action assets to the screen.
     * @param g
     * @param grid
     */
	public void render(Graphics g, Cell[][] grid){
		//Updates grid.
		this.grid = grid;
		
		boundingBox.setX(x);
		boundingBox.setY(y);
		
		float a = boundingBox.getCenterX() - 80;
		float b = boundingBox.getCenterY() - 120;
		
		//Draw Assets.
		g.setColor(Color.green);
		g.fill(boundingBox);
		g.setColor(Color.white);
		
        g.drawAnimation(current, a, b);
        this.grid[positionX][positionY].cellType = 5;
        g.draw(nextPosition.rect);
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
     * Move Left
     * Sets up a left movement.
     * @param grid
     */
    public void moveLeft(Cell[][] grid){
    	
    	if(grid[positionX - 1][positionY].cellType != 1){
    		
    		grid[positionX - 1][positionY].cellType = 6;
    		nextPosition = grid[positionX - 1][positionY];
    		
    		actionZone = grid[positionX - 2][positionY];
    		
    		current = movingLeft;
    	}
    	
    }
    
    /**
     * Move Right
     * Sets up a right movement.
     * @param grid
     */
    public void moveRight(Cell[][] grid){
    	
    	if(grid[positionX + 1][positionY].cellType != 1){
    		
    		grid[positionX + 1][positionY].cellType = 6;
    		nextPosition = grid[positionX + 1][positionY];
    		
    		actionZone = grid[positionX + 2][positionY];;
    		
    		current = movingRight;
    	}
    	
    }
    
    /**
     * Move Up
     * Sets up an Up movement.
     * @param grid
     */
    public void moveUp(Cell[][] grid){
    	
    	if(grid[positionX][positionY - 1].cellType != 1){
    		
    		grid[positionX][positionY - 1].cellType = 6;
    		nextPosition = grid[positionX][positionY - 1];
    		
    		actionZone = grid[positionX][positionY - 2];
    		
    		current = movingUp;
    	}
    	
    }

    /**
     * Move Down
     * Sets up a Down movement.
     * @param grid
     */
    public void moveDown(Cell[][] grid){

    	if(grid[positionX][positionY + 1].cellType != 1){
    		
    		grid[positionX][positionY + 1].cellType = 6;
    		nextPosition = grid[positionX][positionY + 1];
    		
    		actionZone = grid[positionX][positionY + 2];
    		
    		current = movingDown;
    	}
    	
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
     * updateCharacter
     * Moves the character based on what movements have been ordered by the player.
     * @param grid
     * @param delta
     */
    public void updateCharacter(Cell[][] grid, int delta){
    	
    	
    	float time = (float) 0.09;
 	    
    	//Sets the player sprites position in the correct place.
 	    if(!busy){
 	    	positionX = nextPosition.position.x;
	    	positionY = nextPosition.position.y;
			busy = true;
 	    }
    	
 	    // Moves the player forwards and then lerps the sprite to the new position smoothly.
    	if(busy){
			x = lerp(x, grid[positionX][positionY].getX(), time);
			y = lerp(y, grid[positionX][positionY].getY(), time);
			grid[positionX][positionY].cellType = 0;
			currentPosition = grid[positionX][positionY];
			grid[positionX][positionY].cellType = 5;
    	}
  
    	//Times snapping the sprite in place.
  		  timer+=delta;
  		if (timer > 360){
  			timer = 0;{
				busy = false;

  			}
  		}
    	
    }
    
    /**
     * Get Current Location
     * Gets current location
     * @param grid
     * @return
     */
    public Cell getCurrentLocation(Cell[][] grid){
		return grid[positionX][positionY];
    	
    }
    
    /**
     * Get Action Zone
     * Get area of interaction
     * @return
     */
    public Cell getActionZone(){
    	return actionZone;
    }
    
    /**
     * Get Speed
     * For Battles
     * @return
     */
    public int getSpeed(){
    	return speed;
    }
    
    /**
     * Get Strength
     * For Battles
     * @return
     */
    public int getStrength(){
    	return strength;
    }
    
    /**
     * Get Special
     * For Battles
     * @return
     */
    public int getSpecial(){
    	return special;
    }
    
    /**
     * Increase Energy
     * For Battles
     * @param eModifier
     */
    public void increaseEnergy(int eModifier){
    	if(energy < maxEnergy){
    		if((energy + eModifier) < maxEnergy){
    			energy += eModifier;
    		}
    		if((energy + eModifier) > maxEnergy){
    			energy = maxEnergy;
    		}
    	}
    }
    
    /**
     * Decrease Energy
     * For Battles
     * @param eModifier
     */
    public void decreaseEnergy(int eModifier){
    	energy -= eModifier;
    }
    
    /**
     * Get Energy
     * For Battles
     * @return
     */
    public int getEnergy(){
    	return energy;
    }
    
    /**
     * Set Energy
     * For Battles
     * @param e
     */
    public void setEnergy(int e){
    	energy = e;
    }
    
    /**
     * Returns the player character's name.
     * @return
     */
    public String getPlayerName(){
        return name;
    }
    
    
    /**
     * For Battles
     * @return
     */
    public int getHP(){
    	return HP;
    }
    
    /**
     * For Battles
     * @param hpModifier
     */
    public void increaseHP(int hpModifier){
    	if(HP < maxHP){
    		if((HP + hpModifier) < maxHP){
    			HP += hpModifier;
    		}
    		if((energy + hpModifier) > maxHP){
    			HP = maxHP;
    		}
    	}
    }
    
    /**
     * For Battles
     * @param hpModifier
     */
    
    public void decreaseHP(int hpModifier){
    	HP -= hpModifier;
    }
    
    /**
     * For Battles
     * @param newHP
     */
    
    public void setHP(int newHP){
    	HP = newHP;
    }

    /**
     * For the Inventory
     * @param g
     * @param x
     * @param y
     */
	public void render(Graphics g, int x, int y) {
		g.drawAnimation(idleLeft, x, y);
		
	}
    
    
}

