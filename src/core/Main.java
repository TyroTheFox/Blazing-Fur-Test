package core;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * Main Class
 * 
 * Initialises Game and States
 * @author Kieran
 *
 */

public class Main extends StateBasedGame{
	
	   public static final String gamename = "Platforming Test - Blazing Fur - Version 0.0.6 Pre-Alpha";
	   public static final int play = 0;

	   /**
	    * Main
	    * Initialises Class
	    * @param gamename
	    * @throws SlickException
	    */
	   public Main(String gamename) throws SlickException{
		      super(gamename);
		      this.addState(new Play(play));
		   }
	
	   /**
	    * Initialise States List
	    * Initialises each State and then chooses a state to begin running
	    */
	   public void initStatesList(GameContainer gc) throws SlickException{
		      this.getState(play).init(gc, this);
		      this.enterState(play);
		   }
	   
	   /**
	    * Main
	    * Game Update Cycle
	    * @param args
	    */
	   public static void main(String[] args) {
		      AppGameContainer appgc;
		      try{
		         appgc = new AppGameContainer(new Main(gamename));
		         appgc.setDisplayMode(800, 600, false);
		         appgc.start();
		      }catch(SlickException e){
		         e.printStackTrace();
		      }
		   }
}
