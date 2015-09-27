import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.newdawn.fizzy.*;
import org.newdawn.fizzy.render.WorldCanvas;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Play Class
 * 
 * The main screen where the world map is.
 * @author Kieran
 *
 */

public class Play extends BasicGameState{
	
	public TiledMap map;
	public BlockMap worldMap;
	private boolean moveComplete = false;
	boolean foundTarget = false;
   
//	   
//	   Point2D cameraControl;
//	   
   Actor testActor;
   
   Cell[][] grid;
   Cell[] temp = null;
   ArrayList<Cell[]> exitList;
   
   Boolean movingUp;
   Boolean movingDown;
   Boolean movingLeft;
   Boolean movingRight;
   
   Cell[] route;
   
   int i = 0;
   boolean startTimer = false;
   long timer = 0;
	private boolean convoStarted;
	
	float timeStep = 1.0f / 60.f;
	int velocityIterations = 6;
	int positionIterations = 2;
	
	World world;
	WorldCanvas canvas;
	Rectangle recGround, recBody;
	org.newdawn.fizzy.Rectangle fizGround, fizBody, fizWall, fizWall2, fizCeiling, fizPlat, fizPlat2, fizPlat3, fizPlat4;
	
	Body<Object> body, ground, wall, wall2, ceiling, plat, plat2, plat3, plat4;
	
	int jumpNumber = 0;
	int maxJump = 2;
	float topSpeed = 5;
	float currentSpeed;
	
	private static final float SCALE = 0.01f;
	
	   public Play(int state){
	   }
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

			map = new TiledMap("res/testLevel.tmx", "res");
			
			//body definition
			world = new World();
			world.setBounds(gc.getWidth(), gc.getHeight());
			world.setGravity(1.0f);
			
			fizGround = new org.newdawn.fizzy.Rectangle(800, 50);
			ground = new StaticBody<Object>(fizGround, 0, 590);
			ground.setFriction(0.9f);
			ground.setRestitution(0);
			world.add(ground);
			
			fizWall = new org.newdawn.fizzy.Rectangle(20, 600);
			wall = new StaticBody<Object>(fizWall, 0, 0);
			wall.setFriction(0.2f);
			wall.setRestitution(0);
			world.add(wall);
			
			fizWall2 = new org.newdawn.fizzy.Rectangle(20, 600);
			wall2 = new StaticBody<Object>(fizWall2, 780, 0);
			wall2.setFriction(0.2f);
			wall2.setRestitution(0);
			world.add(wall2);
			
			fizCeiling = new org.newdawn.fizzy.Rectangle(850, 20);
			ceiling = new StaticBody<Object>(fizCeiling, 0, 0);
			ceiling.setFriction(0.2f);
			ceiling.setRestitution(0);
			world.add(ceiling);
			
			fizPlat = new org.newdawn.fizzy.Rectangle(200, 20);
			plat = new StaticBody<Object>(fizPlat, 580, 430);
			plat.setFriction(0.2f);
			plat.setRestitution(0);
			world.add(plat);
			
			world.addBodyListener(plat, new WorldListener() {

				@Override
				public void collided(CollisionEvent event) {
					jumpNumber = 0;					
				}

				@Override
				public void separated(CollisionEvent event) {
									
				}
				
			});
			
			fizPlat2 = new org.newdawn.fizzy.Rectangle(200, 20);
			plat2 = new StaticBody<Object>(fizPlat2, 20, 430);
			plat2.setFriction(0.2f);
			plat2.setRestitution(0);
			world.add(plat2);
			
			world.addBodyListener(plat2, new WorldListener() {

				@Override
				public void collided(CollisionEvent event) {
					jumpNumber = 0;					
				}

				@Override
				public void separated(CollisionEvent event) {
									
				}
				
			});
			
			fizPlat3 = new org.newdawn.fizzy.Rectangle(100, 20);
			plat3 = new StaticBody<Object>(fizPlat3, 200, 300);
			plat3.setFriction(0.2f);
			plat3.setRestitution(0);
			world.add(plat3);
			
			world.addBodyListener(plat3, new WorldListener() {

				@Override
				public void collided(CollisionEvent event) {
					jumpNumber = 0;					
				}

				@Override
				public void separated(CollisionEvent event) {
									
				}
				
			});
			
			fizPlat4 = new org.newdawn.fizzy.Rectangle(200, 20);
			plat4 = new StaticBody<Object>(fizPlat4, 500, 200);
			plat4.setFriction(0.2f);
			plat4.setRestitution(0);
			world.add(plat4);
			
			world.addBodyListener(plat4, new WorldListener() {

				@Override
				public void collided(CollisionEvent event) {
					jumpNumber = 0;					
				}

				@Override
				public void separated(CollisionEvent event) {
									
				}
				
			});
			
			fizBody = new org.newdawn.fizzy.Rectangle(32, 32);
			body = new DynamicBody<Object>(fizBody, 300, 100);
			body.setFixedRotation(true);
			body.setFriction(0.4f);
			body.setRestitution(0);
//			body.setFixedRotation(true);
			body.setDensity(2f);
			world.add(body);
			      
			world.addListener(new WorldListener() {
			    @Override
			    public void collided(CollisionEvent event) {
			        System.out.println("Collision");
			    }

			    @Override
			    public void separated(CollisionEvent event) {
			        System.out.println("Separate");
			    }
			});
			
			world.addBodyListener(ground, new WorldListener() {

				@Override
				public void collided(CollisionEvent event) {
					jumpNumber = 0;					
				}

				@Override
				public void separated(CollisionEvent event) {
									
				}
				
			});
			
			recBody = new Rectangle(body.getX(), body.getY(), 32, 32);
			recGround = new Rectangle(ground.getX(), ground.getY(), 600, 50);
//			canvas = new WorldCanvas(world);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("Body Speed: " + currentSpeed, 100, 25);
		g.drawString("Ground Square: " + recGround.getX() + ", " + recGround.getY(), 100, 45);
//		g.draw(recGround);
//		g.draw(recBody);
		g.drawRect(ground.getX(), ground.getY(), fizGround.getWidth(), fizGround.getHeight());
		g.drawRect(wall.getX(), wall.getY(), fizWall.getWidth(), fizWall.getHeight());
		g.drawRect(wall2.getX(), wall2.getY(), fizWall2.getWidth(), fizWall2.getHeight());
		g.drawRect(ceiling.getX(), ceiling.getY(), fizCeiling.getWidth(), fizCeiling.getHeight());
		g.drawRect(plat.getX(), plat.getY(), fizPlat.getWidth(), fizPlat.getHeight());
		g.drawRect(plat2.getX(), plat2.getY(), fizPlat2.getWidth(), fizPlat2.getHeight());
		g.drawRect(plat3.getX(), plat3.getY(), fizPlat3.getWidth(), fizPlat3.getHeight());
		g.drawRect(plat4.getX(), plat4.getY(), fizPlat4.getWidth(), fizPlat4.getHeight());
		g.drawRect(body.getX(), body.getY(), fizBody.getWidth(), fizBody.getHeight());
		
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		currentSpeed = body.getXVelocity();
		
	      if(input.isKeyDown(Input.KEY_LEFT))
	      {
	         movingLeft = true;
	      }
	      else{
	    	  movingLeft = false;
	      }

	      if(input.isKeyDown(Input.KEY_RIGHT))
	      {
	         movingRight = true;
	      }
	      else{
	    	  movingRight = false;
	      }

	      if(input.isKeyPressed(Input.KEY_UP))
	      {
	         movingUp = true;
	      }
	      else{
	    	  movingUp = false;
	      }

//	      if(input.isKeyDown(Input.KEY_DOWN))
//	      {
//	         movingDown = true;
//	      }
//	      else{
//	    	  movingDown = false;
//	      }
	      
	      
	      if(movingLeft)
	      {
//	    	  testActor.moveLeft(grid);
	    	  if(currentSpeed > -topSpeed){
	    		  body.applyForce(-20f, 0);
	    	  }
	      }

	      if(movingRight)
	      {
//	    	  testActor.moveRight(grid);
	    	  if(currentSpeed < topSpeed){
	    		  body.applyForce(20f, 0);
	    	  }
	      }

	      if(movingUp)
	      {
//	    	  testActor.moveUp(grid);
//	    	  body.applyForce(0, -300f);
	    	  if(jumpNumber < maxJump){
	    		  if(jumpNumber == 0){
		    		  body.applyImpulse(0, -90f);
	    		  }
	    		  if(jumpNumber != 0){
	    			  body.applyImpulse(0, -70f);
	    		  }
	    		  jumpNumber++;
	    	  }
	      }


//	      canvas.update();      
	      world.update(delta * 0.01f);
	}
	
    public float lerp(float a, float b, float t) {
        if (t < 0)
           return a;

        return a + t * (b - a);
     }
    
 // Convert a Slick 2D screen x coordinate to a JBox2D x coordinate    
    public static float toPosX(float posX)
    {
        float x = posX * SCALE;
        return x;
    }

    // Convert a Slick 2D screen y coordinate to a JBox2D y coordinate
    public static float toPosY(float posY)
    {
        // As the physics world uses a 2d cartesian coordinate system
        // and slick uses screen coordinates with top-left being (0,0)
        // flip y
        float y = -posY * SCALE;
        return y;
    }

    /**
     * Convert a JBox2D x coordinate to a Slick 2D screen x coordinate
     * 
     * @param posX The JBox2D x coordinate
     * @return The Slick 2D x coordinate
     */
    public static float toScreenX(float posX)
    {
        // convert back to screen coordinate
        float x = posX / SCALE;
        return x;
    }

    public static float toScreenY(float posY)
    {
        // convert back to screen coordinate
        float y = -posY / SCALE;
        return y;
    }

	@Override
	public int getID() {
		return 0;
	}

}
