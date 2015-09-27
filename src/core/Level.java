package core;

import java.util.ArrayList;

import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Level
 * Handles Level Generation, Physics Calculations and Scrolling
 * @author Kieran Clare
 *
 */
public class Level {

	//Level Data
	public TiledMap map;
	public BlockMap worldMap;
	
	//Camera
	Camera camera;
	
	//JBox 2D Renderer || For Debugging
	PhysicsRender physRend;
	   
	//Cell Grid || For Positioning
	Cell[][] grid;
	
	//Slope List
	ArrayList<Slope> slopeList;
	
	//Physics World
	World world;
	
	//Debug Switches
	Boolean debugPhysRend = false, debugJBox2D = false;
	
	//Offsets
	float cameraOffsetX = 0;
	float cameraOffsetY = 0;
	float cameraFOffsetX = 0;
	float cameraFOffsetY = 0;
	
	/**
	 * Level
	 * Initialises Assets
	 * @param gc
	 * @param res
	 * @throws SlickException
	 */
	public Level(GameContainer gc, String res) throws SlickException{
		//Camera
		camera = new Camera(gc, res);
		//Camera hands over Tiled Map
		map = camera.map;
		//Creates new Block Map Grid Data
		worldMap = new BlockMap(map);
		worldMap.generateMap((int)(camera.controlX + cameraFOffsetX), (int)(camera.controlY + cameraFOffsetY));
		//Creates new Cell Grid
		grid = new Cell[map.getHeight()][map.getWidth()];
		grid = worldMap.getGrid();
		
		//Creates and sets up new Physics world
		world = new World();
		world.setBounds(worldMap.getWidth(), worldMap.getHeight());
		world.setGravity(1.0f);
		
		//Adds each Grid data to the level's World
		for (int x = 0; x < camera.map.getWidth(); x++) {
			for (int y = 0; y < camera.map.getHeight(); y++) {
				if(grid[x][y].isWall()){
					world.add(grid[x][y].getObjRect());		
				}
			}
		}	
		
		//Slope List
		slopeList = new ArrayList<Slope>();
		
		//Physics Render
		physRend = new PhysicsRender(world);
	}
	
	/**
	 * Render
	 * Draws Assets
	 * @param g
	 * @param camX
	 * @param camY
	 */
	public void render(Graphics g, int camX, int camY){
		
		//Alters the Y axis
		camera.translateGraphics();
			//Draws Map
			camera.drawMap(camX, camY);
			//Updates Map and Grid
			worldMap.updateMap((int)(camera.getControlX() + cameraFOffsetX), (int)(camera.getControlY() + cameraFOffsetY));
			grid = worldMap.getGrid();
			map = camera.map;
			//Draws Debug
			if(debugJBox2D){
				for (int x = 0; x < map.getWidth(); x++) {
					for (int y = 0; y < map.getHeight(); y++) {
						if(grid[x][y].getCellType() == 1){
							g.draw(grid[x][y].rect);
							g.drawString(""+grid[x][y].cellType, grid[x][y].rect.getCenterX() - 4, grid[x][y].rect.getCenterY() - 9);
							
						}
					}
				}
			}
			//Renders Slope List
			if(!slopeList.isEmpty()){
				for(Slope i : slopeList){
					i.slickTranslate(camera.getControlX() + (cameraFOffsetX), camera.getControlY() + (cameraFOffsetY));
					i.render(g);
				}
			}
		//Resets Y Axis
		camera.untranslateGraphics();
		//Physics Render
		if(debugPhysRend){
			physRend.render(g, 0, 0);
		}
	}
	
	/**
	 * Update
	 * Updates Assets
	 * @param delta
	 */
	public void update(int delta){
		//Updates Map
		map = camera.map;
		//Updates Grid
		grid = worldMap.getGrid();
		//Updates Map
		worldMap.updateMap((int)(camera.getControlX() + cameraFOffsetX), (int)(camera.getControlY() + cameraFOffsetY)); 
		//Updates Physics Model
	    world.update(delta * 0.01f);
	}
	
	/**
	 * Add Slope
	 * Creates a new Slope
	 * @param x
	 * @param y
	 * @param length
	 * @param width
	 * @param rotation
	 */
	public void addSlope(float x, float y, float length, float width, float rotation){
		//Slope
		//x and y Co-Ord
		//Length
		//Width
		//Camera Offset X + Focus Offset X
		//Camera Offset Y + Focus Offset Y
		Slope slope = new Slope(x, y, length, width, camera.getControlX() + cameraFOffsetX, camera.getControlY() + cameraFOffsetY);
		
		//Add Slope to slope list
		slopeList.add(slope);
		//Add Slope to World
		world.add(slope.getObjRect());
		//Rotate Slope by Rotation
		slope.rotateObjRect(rotation);
	}
	
	/**
	 * Set Debug
	 * Debug Switch
	 * @param PhysRend
	 * @param JBox2D
	 */
	public void setDebug(Boolean PhysRend, Boolean JBox2D){
		debugPhysRend = PhysRend; 
		debugJBox2D = JBox2D;
	}
	
	/**
	 * Get Camera
	 * Returns Level Camera
	 * @return
	 */
	public Camera getCamera(){
		return camera;
	}
	
	/**
	 * Add Object
	 * Adds Physics Objects to the level world
	 * @param b
	 */
	public void addObject(Body b){
		world.add(b);
	}
	
	/**
	 * Get Slope List
	 * Returns Slope List
	 * @return
	 */
	public ArrayList<Slope> getSlopeList(){
		return slopeList;
	}
}
