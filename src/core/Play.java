package core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Play
 * Handles a standard Test Mode and Control
 * @author Kieran Clare
 *
 */
public class Play extends BasicGameState{
	//Logic || Handles player characters, levels and enemies
	Logic logic;
	
	//Cell || Used for Positioning
	Cell[][] grid;
	
	//Control Switches
    Boolean movingUp = false;
    Boolean movingDown = false;
    Boolean movingLeft = false;
    Boolean movingRight = false;
    
    //Camera || Positions all objects around the player
    Camera camera;
	
    /**
     * Play
     * @param state
     */
    public Play(int state){
    }
	
    /**
     * Init
     * Initialises assets
     */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		logic = new Logic(gc);
	}

	/**
	 * Render
	 * Draws assets
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		logic.render(g);
	}
	
	/**
	 * Update
	 * Updates assets and handles inputs
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		//Registers Inputs
		Input input = gc.getInput();
		
		//Updates Logic
		logic.update(delta, movingLeft, movingRight);
		
		//Left Key
		if(input.isKeyDown(Input.KEY_LEFT))
	      {
	         movingLeft = true;
	      }
	    else{
	    	  movingLeft = false;
	      }
		
		//Right
	    if(input.isKeyDown(Input.KEY_RIGHT))
	      {
	         movingRight = true;
	      }
	     else{
	    	  movingRight = false;
	      }

	    //Up Key
	    if(input.isKeyPressed(Input.KEY_UP))
	      {
	         movingUp = true;
	      }
	    else{
	    	  movingUp = false;
	      }
	      
	    //Z Key
	    if(input.isKeyPressed(Input.KEY_Z))
	      {
	    	  for(int i = 0; i < logic.enemyList.length; i++){
	    		  logic.currentCharacter.doAAction(logic.enemyList[i], delta);
	    	  }
	      }
	      
	    //X Key
	    if(input.isKeyPressed(Input.KEY_X))
	      {
	    	  for(int i = 0; i < logic.enemyList.length; i++){
	    		  logic.currentCharacter.doBAction(logic.enemyList[i], delta);
	    	  }
	      }
	      
	    //Q Key - For Debug
	    if(input.isKeyPressed(Input.KEY_Q))
	      {
	    	  logic.debugJBox2D = !logic.debugJBox2D;
	      }
	      
	    //W Key - For Debug
	    if(input.isKeyPressed(Input.KEY_W))
	      {
	    	  logic.debugPhysRend = !logic.debugPhysRend;
	      }
	      
	    //E Key - For Debug
	    if(input.isKeyPressed(Input.KEY_E))
	      {
	    	  logic.debugHitbox = !logic.debugHitbox;
	      }	      
	      
	    
	    //Movement Actions
	    if(movingLeft)
	      {
	    	  logic.getCurrentPlayer().moveLeft();
	      }

	    if(movingRight)
	      {
	    	  logic.getCurrentPlayer().moveRight();
	      }

	    if(movingUp)
	      {
	    	  logic.getCurrentPlayer().jump();
	      }
	      
	    //Enter Key - Reset
	    if(input.isKeyPressed(Input.KEY_ENTER))
	      {
	    	  logic.removeOldCharacter();
	    	  if(logic.characterListPosition < logic.characterList.length - 1){
	    		  logic.characterListPosition += 1;
	    	  }
	    	  else{
	    		  logic.characterListPosition = 0;
	    	  }
	    	  logic.newCharacterSetup();
	    	  logic.getCurrentPlayer().body.setPosition(50, -100);
	      }
	      
	}

	/**
	 * Get ID
	 * Required for Slick 2D State Switching
	 */
	@Override
	public int getID() {
		return 0;
	}

}
