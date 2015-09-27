package core;

import java.util.ArrayList;

import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Circle;
import org.newdawn.fizzy.DynamicBody;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

/**
 *Player Class
 *
 *The player controlled characters
 * @author Kieran Clare
 *
 */

public class Player extends Actor{
	
	//Speed
    private float speed = 0;
    float currentSpeed;
	float topSpeed = 50;
	float minimumSpeed = 0;
	float speedMultiplier = 0;
	
	//Jumping
	int jumpNumber = 0;
	int maxJump = 1;
	float jumpPower = 0;
	
	//Character Stats
    private int maxHP = 150;
    private int HP = 150;
    private int strength = 50;
    private int special = 80;
    private int maxEnergy = 100;
    private int energy = 100;
    
    //Slick 2D Assets
    Rectangle attackBox, attackBoxB, landSensor, hitbox, projectile;
    
    //Character Flags
    boolean actionA = false, actionB = false, 
    		lingerA = false, lingerB = false,
    		hitA = false, hitB = false,
    		projectileA = false, projectileB = false,
    		projectileSet = false,
    		movementOverride = false;
    
    //Attack Offsets
    float attackBoxOffset = 0, attackBoxOffsetB = 0,
    		projectileOffsetX = 0, projectileOffsetY = 0;
    
    //JBox2D Assets
    Circle oBody;
    Body<Object> body;
    
    //Projectile Asset
    Projectile bullet;
    
    //Actions
    Action aAction, bAction, sMControl;
    
    //Debug Flag
    Boolean debugHitbox = false;
    
    //In Air Flag
    boolean inAir = false;
    
    //Player Offset
	float playerOffsetX = 305;
	float playerOffsetY = 475;
	
	//Sprite Offset
	float spriteOffsetX = 0;
	float spriteOffsetY = 0;
	
	//Camera Offset
	float camX = 0, camY = 0;
	
	//Camera Focus Offset
	float focusX, focusY;
	
	//Action Timer
	float timer = 0;
	
	/**
	 * Player
	 * Initialises Player
	 * @param name
	 * @param spriteImage
	 * @param moveSprites
	 * @param moveTileWidth
	 * @param moveTileHeight
	 * @param jumpTileWidth
	 * @param jumpTileHeight
	 * @param idleTileWidth
	 * @param idleTileHeight
	 * @param focusX
	 * @param focusY
	 * @param sOffsetX
	 * @param sOffsetY
	 * @param projectileSet
	 * @param friction
	 * @param restitution
	 * @param density
	 * @param speed
	 * @param jumpPower
	 * @param jumpMaxNumber
	 * @param startMultiplier
	 * @throws SlickException
	 */
	public Player(String name, 
			String spriteImage,
			int moveSprites, int moveTileWidth, int moveTileHeight,
    		int jumpTileWidth, int jumpTileHeight,
    		int idleTileWidth, int idleTileHeight,
			float focusX, float focusY,
			float sOffsetX, float sOffsetY,
			boolean projectileSet,
			float friction, float restitution, float density,
			float speed, float jumpPower, int jumpMaxNumber,
			float startMultiplier)
			throws SlickException {
		
		//Sends things back to Actor class
		super(name, 
				spriteImage, 
				moveSprites, moveTileWidth, moveTileHeight,
	    		jumpTileWidth, jumpTileHeight,
	    		idleTileWidth, idleTileHeight);
		
		//Character Physics Body
		oBody = new Circle(10);
		body = new DynamicBody<Object>(oBody, 100, 0);
		body.setFixedRotation(true);
		body.setFriction(friction);
		body.setRestitution(restitution);
		body.setDensity(density);
		body.setCharacter(true);
		
		//Projectile Switch
		this.projectileSet = projectileSet;
		
		//Initialises Projectiles if the Switch is on
		if(projectileSet){
			bullet = new Projectile(0, 0, sOffsetY, sOffsetY, sOffsetY, sOffsetY);
		}
		
		//Initialises speed variables
		this.speed = speed;
		minimumSpeed = speed;
		speedMultiplier = startMultiplier;
		this.jumpPower = jumpPower;
		maxJump = jumpMaxNumber;
		
		//Initialises Camera Focus Offset
		this.focusX = focusX;
		this.focusY = focusY;
		
		//Initialises Sprite Offset
		spriteOffsetX = sOffsetX;
		spriteOffsetY = sOffsetY;
		
		//Initialises Slick 2D Offsets
		landSensor = new Rectangle((-6 + playerOffsetX) + focusX, (10 + playerOffsetY) + focusY, 15, 5);
		attackBox = new Rectangle(((-10 + playerOffsetX) + focusX) + attackBoxOffset, (-11 + playerOffsetY) + focusY, 40, 20);
		attackBoxB = new Rectangle(((-10 + playerOffsetX) + focusX) + attackBoxOffsetB, (-11 + playerOffsetY) + focusY, 40, 20);
		hitbox = new Rectangle((-10 + playerOffsetX) + focusX, (-11 + playerOffsetY) + focusY, 20, 20);
		projectile = new Rectangle(0, 0, 10, 10);
	}
	
	/**
	 * Render
	 * Draws Assets
	 * @param g
	 * @param focusX
	 * @param focusY
	 * @param camX
	 * @param camY
	 */
	public void render(Graphics g, float focusX, float focusY, float camX, float camY){
		//A Action Hit Box
		if(hitA){
			g.setColor(Color.red);
			g.fill(attackBox);
			g.setColor(Color.white);
		}
		
		//B Action Hit Box
		if(hitB){
			g.setColor(Color.green);
			g.fill(attackBoxB);
			g.setColor(Color.white);
		}
		
		//A Action Projectile Hit Box
		if(projectileA){
			g.setColor(Color.red);
			g.fill(projectile);
			if(projectileSet){
				bullet.render(g);
			}
			g.setColor(Color.white);
		}
		
		//B Action Projectile Hit Box
		if(projectileB){
			g.setColor(Color.green);
			g.fill(projectile);
			if(projectileSet){
				bullet.render(g);
			}
			g.setColor(Color.white);
		}
		
		//Camera Focus Offsets
		this.focusX = focusX;
		this.focusY = focusY;

		//Camera Offsets
		this.camX = camX;
		this.camY = camY;
		
		//Draws HP
		g.drawString("HP: " + HP, (-10 + playerOffsetX) + focusX, (-45 + playerOffsetY) + focusY);
		
		//Renders Sprite
		super.render(g, ((-15 + playerOffsetX) + focusX) + spriteOffsetX, 
				((-27 + playerOffsetY) + focusY) + spriteOffsetY);
		
		//Renders Debug
		if(debugHitbox){
			g.drawRect((-10 + playerOffsetX) + focusX, (-11 + playerOffsetY) + focusY, 20, 20);
			g.draw(landSensor);
		}
	}
	
	/**
	 * Check Jump Reset
	 * Checks whether the character has landed
	 * @param map
	 * @param grid
	 * @param slopeList
	 */
    public void checkJumpReset(TiledMap map, Cell[][] grid, ArrayList<Slope> slopeList){
    	//In Air Flag
    	inAir = true;
    	
    	//Checks if the character lands on the floor
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				if(grid[x][y].cellType == 1){
					if(landSensor.intersects(grid[x][y].rect)){					
						jumpNumber = 0;	
						inAir = false;
					}
				}
			}
		}
		
		//Checks if the character lands on a slope
		if(!slopeList.isEmpty()){
			for(Slope i : slopeList){
				if(landSensor.intersects(i.rect)){
					jumpNumber = 0;	
					inAir = false;
				}
			}
		}
    }
    
    /**
     * Do A Action
     * @param e
     * @param delta
     */
    public void doAAction(Enemy e, int delta){
    	if(!actionB){
	        aAction.actionEffect(this, e, delta);
    	}
    }
    
    /**
     * Do B Action
     * @param e
     * @param delta
     */
    public void doBAction(Enemy e, int delta){
    	if(!actionA){
	        bAction.actionEffect(this, e, delta);
    	}
    }
    
    /**
     * Lingering Actions Check
     * Performs Lingering Actions Until they're turned off
     * as well as the lingering effect of the Speed Modifier
     * Control
     * @param e
     * @param delta
     */
    public void lingeringActionsCheck(Enemy e, int delta){
    	if(lingerA){
    		aAction.lingeringEffect(this, e, delta);
    	}
    	if(lingerB){
    		bAction.lingeringEffect(this, e, delta);
    	}
    	sMControl.lingeringEffect(this, e, delta);
    }
    
    /**
     * Hitbox A Check
     * Checks if Hitbox A hit anything
     * @param e
     * @return
     */
    public boolean hitboxACheck(Enemy e){
    	boolean hit = false;
    	
    	if(hitA){
			if(attackBox.intersects(e.hitbox) && getHP() > 0){
				hit = true;
	       	}
    	}
		
		return hit;
    }
    
    /**
     * Hitbox B Check
     * Checks if Hitbox B hit anything
     * @param e
     * @return
     */
    public boolean hitboxBCheck(Enemy e){
    	boolean hit = false;
    	
    	if(hitB){
			if(attackBoxB.intersects(e.hitbox) && getHP() > 0){
				hit = true;
	       	}
    	}
		return hit;
    }
    
    /**
     * Projectile Check
     * Checks if the projectile hit anything
     * @param e
     * @return
     */
    public boolean projectileCheck(Enemy e){
    	boolean hit = false;
    	if(projectileA || projectileB){
//			if(bullet.hitbox.intersects(e.hitbox) && getHP() > 0){
//				hit = true;
//	       	}
    		
    		if(bullet.body.isTouching(e.body)){
    			hit = true;
    		}
    	}
		return hit;
    }
    
    /**
     * Damage Check
     * Checks if the Character is touching anything
     * @param e
     */
    public void damageCheck(Enemy e){
    	if(body.isTouching(e.body) && HP > 0){
    		decreaseHP(5);
    		if(current == movingLeft || current == idleLeft || current == jumpLeft){
    			body.applyForce(70f, -100);
    		}
    		else{
    			body.applyForce(-70f, -100);
    		}
    	}
    	if(HP == 0){
    		body.setActive(false);
    	}	      
    }
    
    /**
     * Add A Action
     * @param a
     */
    public void addAAction(Action a){
    	aAction = a;
    }
    
    /**
     * Add A Action
     * @param b
     */
    public void addBAction(Action b){
    	bAction = b;
    }
    
    /**
     * Add Speed Modifier Control
     * @param smc
     */
    public void addSMControl(Action smc){
    	sMControl = smc;
    }
    
    /**
     * Set Timer
     * @param t
     */
    public void setTimer(float t){
    	timer = t;
    }
    
    /**
     * Get Timer
     * @return
     */
    public float getTimer(){
    	return timer;
    }
    
    /**
     * Update Timer
     * @param t
     * @return
     */
    public float updateTimer(float t){
    	timer = t;
    	return timer;
    }
    
    /**
     * Set Debug
     * Sets Debug Switch
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
    	if(!movementOverride){
	    	currentSpeed = body.getXVelocity();
			body.applyForce(-speed * speedMultiplier, 0);
    	}
    }
    
    /**
     * Move Right
     * Sets up a right movement.
     * @param grid
     */
    public void moveRight(){
    	if(!movementOverride){
    		currentSpeed = body.getXVelocity();
  		  	body.applyForce(speed * speedMultiplier, 0);
    	}
    }
    
    /**
     * Jump
     * Makes character jump
     */
    public void jump(){
    	if(!movementOverride){
			if(jumpNumber < maxJump){
				if(jumpNumber == 0){
					body.applyImpulse(0, -jumpPower);
				}
				if(jumpNumber <= maxJump){
					body.applyImpulse(0, -(jumpPower*0.5f));
				}
			}
			jumpNumber++;
    	}
    }
    
    /**
     * Update Sprite
     * Updates Character Sprite
     * @param left
     * @param right
     */
    public void updateSprite(Boolean left, Boolean right){
    	if(inAir){
    		if(left || current == idleLeft || current == jumpLeft){
    			current = jumpLeft;
    		}
    		
    		if(right || current == idleRight || current == jumpRight){
    			current = jumpRight;
    		}
    	}
    	else{
    		if(left){
    			current = movingLeft;
    		}
    		
    		if(right){
    			current = movingRight;
    		}
    		
    		if((!right || currentSpeed < -1f) && (!left || currentSpeed > 1f)){
    			if(current == movingLeft || current == jumpLeft){
    				current = idleLeft;
    			}
    			if(current == movingRight || current == jumpRight){
    				current = idleRight;
    			}
    		}
    	}
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
