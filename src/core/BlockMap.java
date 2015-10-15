package core;

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
	
	/**
	 * Block Map
	 * Initialises BlockMap 
	 * @param map
	 * @throws SlickException
	 */
	public BlockMap(TiledMap map) throws SlickException {
		//Tiled Map
		tmap = map;
		mapWidth = tmap.getWidth() * tmap.getTileWidth();
		mapHeight = tmap.getHeight() * tmap.getTileHeight();
		//Grid Data
		grid = new Cell[tmap.getWidth()][tmap.getHeight()];
		temp = new Cell(0, 0, 0, 0);
	}
	
	/**
	 * Generate Map
	 * Creates new Level Data
	 * @param a
	 * @param b
	 */
	public void generateMap(int a, int b) {
		for (int x = 0; x < tmap.getWidth(); x++) {
			for (int y = 0; y < tmap.getHeight(); y++) {
				temp = new Cell(x, y, a + (x * tmap.getTileWidth()), b + (y * tmap.getTileHeight()));
				grid[x][y] = temp;
				int tileID = tmap.getTileId(x, y, 0);
				if (tileID == 0) {
					grid[x][y].cellType = 0;	
					grid[x][y].intSlickRect();
				}
				if(tileID == 1){
					grid[x][y].cellType = 1;
					grid[x][y].intSlickRect();
				}
			}
		}
//		System.out.println("Object Count: "+tmap.getObjectCount(0));
//		System.out.println("Object Name: "+tmap.getObjectName(0, 0));
//		System.out.println("Object X:" + tmap.getObjectX(0, 0) + " Y:" + tmap.getObjectY(0, 0) + " Width:" + tmap.getObjectWidth(0, 0) + " Height:" + tmap.getObjectHeight(0, 0));
	}
	
	/**
	 * Update Map
	 * Creates a new set of Level Data 
	 * @param a
	 * @param b
	 */
	public void updateMap(int a, int b) {
		grid = new Cell[tmap.getWidth()][tmap.getHeight()];
		for (int x = 0; x < tmap.getWidth(); x++) {
			for (int y = 0; y < tmap.getHeight(); y++) {
				temp = new Cell(x, y, a + (x * tmap.getTileWidth()), b + (y * tmap.getTileWidth()));
				grid[x][y] = temp;
				int tileID = tmap.getTileId(x, y, 0);
					if (tileID == 0) {
						grid[x][y].cellType = 0;
						grid[x][y].intSlickRect();						
					}
					if(tileID == 1){
						grid[x][y].cellType = 1;
						grid[x][y].intSlickRect();
					}
					
			}
		}
	}
	
	/**
	 * Get Map
	 * Returns Map
	 * @return
	 */
	public TiledMap getMap(){
		return tmap;
	}
	
	/**
	 * Get Width
	 * Returns Map Width
	 * @return
	 */
	public int getWidth(){
		return mapWidth;
	}
	
	/**
	 * Get Height
	 * Returns Map Height
	 * @return
	 */
	public int getHeight(){
		return mapHeight;
	}
	
	/**
	 * Get Grid
	 * Returns Grid Data
	 * @return
	 */
	public Cell[][] getGrid(){
		return grid;
	}
	
	
}