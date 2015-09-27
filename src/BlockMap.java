
import java.util.ArrayList;

import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Rectangle;
import org.newdawn.fizzy.World;
import org.newdawn.fizzy.StaticBody;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
 
/**
 * Block Map Class
 * Creates a game environment from a tiled map.
 * @author Kieran
 *
 */

public class BlockMap {
	public static TiledMap tmap;
	public static int mapWidth;
	public static int mapHeight;
	Cell[][] grid; // the 2D array of cells
	private Cell temp;
	boolean doSleep = true;
	Body<Body> block;
	
 
	public BlockMap(TiledMap map) throws SlickException {
		tmap = map;
		mapWidth = tmap.getWidth() * tmap.getTileWidth();
		mapHeight = tmap.getHeight() * tmap.getTileHeight();
		grid = new Cell[tmap.getWidth()][tmap.getHeight()];
		
	}
	
	public void generateMap(int a, int b) {
		for (int x = 0; x < tmap.getWidth(); x++) {
			for (int y = 0; y < tmap.getHeight(); y++) {
				int tileID = tmap.getTileId(x, y, 0);
				temp = new Cell(x, y, a + (x * 32), b + (y * 32));
//				block = new StaticBody<Body>(new Rectangle(32, 32), a + (x * 32), b + (y * 32));
//				world.add(block);
//				block.translate(a + (x * 32), b + (y * 32));
				if (tileID == 1) {
					block.setActive(true);
					grid[x][y] = temp;
					grid[x][y].cellType = 1;
					
				}
				else{
//					world.add(block);
//					block.setActive(false);
					grid[x][y] = temp;
					grid[x][y].cellType = 0;
				}
			}
		}
	}
	
	public void updateMap(int a, int b) {
		grid = new Cell[tmap.getWidth()][tmap.getHeight()];
//		world = new World();
//		world.setBounds(800, 600);
//		world.setGravity(-2);
		for (int x = 0; x < tmap.getWidth(); x++) {
			for (int y = 0; y < tmap.getHeight(); y++) {
				int tileID = tmap.getTileId(x, y, 0);
				temp = new Cell(x, y, a + (x * 32), b + (y * 32));
//				block = new StaticBody<Body>(new Rectangle(32, 32), a + (x * 32), b + (y * 32));
//				world.add(block);
//				block.translate(a + (x * 32), b + (y * 32));
				if (tileID == 1) {
					block.setActive(true);
					grid[x][y] = temp;
					grid[x][y].cellType = 1;
					
				}
				else{
//					block.setActive(false);
					grid[x][y] = temp;
					grid[x][y].cellType = 0;
				}
			}
		}
	}
	
	public TiledMap getMap(){
		return tmap;
	}
	
	public int getWidth(){
		return mapWidth;
	}
	
	public int getHeight(){
		return mapHeight;
	}
	
	public Cell[][] getGrid(){
		return grid;
	}
	
//	public World getWorld(){
////		return world;
//	}
	
}