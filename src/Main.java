

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
	
	   public static final String gamename = "Platforming Test";
	   public static final int play = 0;

	   
	   public Main(String gamename) throws SlickException{
		      super(gamename);
		      this.addState(new Play(play));
		   }
	
	   public void initStatesList(GameContainer gc) throws SlickException{
		      this.getState(play).init(gc, this);
		      
		      this.enterState(play);
		   }
	   
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
