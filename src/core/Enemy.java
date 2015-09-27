package core;

import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Circle;
import org.newdawn.fizzy.DynamicBody;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 * Enemy Class
 * Baddies to fight
 * @author Kieran Clare
 *
 */

public class Enemy extends Actor{
	
	//Slick 2D Hitbox
	Rectangle hitbox;
	
	//Speed Variables
    private float speed = 30;
    float currentSpeed;
	float topSpeed = 5;
	
	//Jump Variables
	int jumpNumber = 0;
	int maxJump = 1;
	
	//Stats
    private int maxHP = 150;
    private int HP = 150;
    private int strength = 50;
    private int special = 80;
    private int maxEnergy = 100;
    private int energy = 100;
    
    //Timer
    int timer = 0;
    
    //Physics
    Circle oBody;
    Body<Object> body;
    
    //Debug Switch
    Boolean debugHitbox = false;
    
    //Offsets
	float objectOffsetX = 100;
	float objectOffsetY = 100;

	/**
	 * Enemy
	 * Initialises Enemy
	 * @param name
	 * @param spriteImage
	 * @param moveSprites
	 * @param moveTileWidth
	 * @param moveTileHeight
	 * @param jumpTileWidth
	 * @param jumpTileHeight
	 * @param idleTileWidth
	 * @param idleTileHeight
	 * @param camX
	 * @param camY
	 * @param locationX
	 * @param locationY
	 * @throws SlickException
	 */
	public Enemy(String name, 
			String spriteImage,
			int moveSprites, int moveTileWidth, int moveTileHeight,
    		int jumpTileWidth, int jumpTileHeight,
    		int idleTileWidth, int idleTileHeight,
			float camX, float camY, float locationX, float locationY)
			throws SlickException {
		//Sends stuff to the Actor class
		super(name, spriteImage, 
				moveSprites, moveTileWidth, moveTileHeight,
	    		jumpTileWidth, jumpTileHeight,
	    		idleTileWidth, idleTileHeight);
		
		//Physics Model
		oBody = new Circle(10);
		body = new DynamicBody<Object>(oBody, locationX, locationY);
		body.setFixedRotation(true);
		body.setFriction(0.5f);
		body.setRestitution(-0.3f);
		body.setDensity(2f);
		
		//Slick 2D Hitbox
		hitbox = new Rectangle((body.getX() -10) + (camX + objectOffsetX), (body.getY() -11) + (camY + objectOffsetY), 20, 20);
	}
	
	/**
	 * Render
	 * Draws Assets
	 */
	public void render(Graphics g, float camX, float camY){
		//Draws Hitbox
		if(debugHitbox){
			g.draw(hitbox);
		}
		//Draws HP
		g.drawString("HP: " + HP, ((body.getX() - 10) + objectOffsetX) + camX, ((body.getY() - 45) + objectOffsetY) + camY);
		//Draws Sprite
		super.render(g, (((body.getX() - 15) + camX) + objectOffsetX), (((body.getY() - 27) + camY) + objectOffsetY));
	}
	
	/**
	 * Update
	 * Updates Assets
	 * @param delta
	 * @param camX
	 * @param camY
	 */
	public void update(int delta, float camX, float camY){
		//Hitbox
		hitbox.setLocation((((body.getX() -10) + camX) + objectOffsetX), (((body.getY() -11) + camY) + objectOffsetY));
		//Timer
    	timer += delta;
    	//Basic AI
    	if(timer > 3000){
    		double a = Math.random() * 10;
	    	if(a >= 0 && a < 5){
	    		moveLeft();
	    	}
	    	else{
	    		moveRight();
	    	}
	    	if(a == 0 || a == 10){
	    		jump();
	    	}
	    	timer = 0;
    	}
	}
	
	/**
	 * Set Debug
	 * Controls Debugs
	 * @param Hitbox
	 */
    public void setDebug(Boolean Hitbox){
    	debugHitbox = Hitbox;
    }

    /**
     * Move Left
     * Sets up a left movement.
     * @param grid
     */
    public void moveLeft(){
    	currentSpeed = body.getXVelocity();
  	  if(currentSpeed > -topSpeed){
		  body.applyForce(-speed, 0);
	  }
    }
    
    /**
     * Move Right
     * Sets up a right movement.
     * @param grid
     */
    public void moveRight(){
    	currentSpeed = body.getXVelocity();
  	  if(speed < topSpeed){
  		  body.applyForce(speed, 0);
  	  }
    }
    
    /**
     * Jump
     * Makes the character Jump
     */
    public void jump(){
    	body.applyImpulse(0, -50f);
    }
    
    /**
     * Get Speed
     * For Battles
     * @return
     */
    public float getSpeed(){
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
     * Get HP
     * For Battles
     * @return
     */
    public int getHP(){
    	return HP;
    }
    
    /**
     * Get Max HP
     * Fpr Battles
     * @return
     */
    public int getMaxHP(){
    	return maxHP;
    }
    
    /**
     * Increase HP
     * For Battles
     * @param hpModifier
     */
    public void increaseHP(int hpModifier){
    	if(HP < maxHP){
    		if((HP + hpModifier) <= maxHP){
    			HP += hpModifier;
    		}
    	}
    }
    
    
    /**
     * Decrease HP
     * For Battles
     * @param hpModifier
     */
    
    public void decreaseHP(int hpModifier){
    	HP -= hpModifier;
    }
    
    /**
     * Set HP
     * For Battles
     * @param newHP
     */
    
    public void setHP(int newHP){
    	HP = newHP;
    }
}
