package core;

import java.awt.Point;
import org.jbox2d.dynamics.FixtureDef;
import org.newdawn.fizzy.*;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.geom.Rectangle;
/**
 * Cell
 * @author adrian and Kieran Clare
 * This class represnts a single cell from a maze grid
 */
public class Cell {
	//Slick 2D Rectangle
	Rectangle rect;
	//JBox2D Rectangle
	org.newdawn.fizzy.Rectangle fizRect;
	//Physics Body
	Body<Object> objRect;
	//Position
	Point position;
	//Physics Body Aspects
	FixtureDef fd;

	//Offsets
	int offsetX = -100;
	int offsetY = 100; 
	int offsetFizzY = -100; 
	int offsetFizzX = -100;
	float fizX = 0;
	float fizY = 0;
	
	/**
	 * Cell
	 * Initiates Class
	 * @param x
	 * @param y
	 * @param x2
	 * @param y2
	 */
	Cell(int x, int y, int x2, int y2) {
		//For Positioning
		position = new Point(x, y);
		//Cell Type
		cellType = FREE;
		
		initFizRect(x2, y2);
	}
	
	public void initFizRect(int x, int y){
		//Physics Objects
		fizRect = new org.newdawn.fizzy.Rectangle(15, 15);
		objRect = new StaticBody<Object>(fizRect, x + offsetFizzX, y + offsetFizzY);
		fizX = x + offsetFizzX;
		fizY = y + offsetFizzY;
		objRect.setFriction(0.17f);
		objRect.setRestitution(0);
	}
	
	/**
	 * Get FizRect
	 * Returns Physics Object
	 * @return
	 */
	public org.newdawn.fizzy.Rectangle getFizRect(){
		return fizRect;
	}

	/**
	 * Set FizRect
	 * Sets the Physics Object
	 * @param r
	 */
	public void setFizRect(org.newdawn.fizzy.Rectangle r){
		fizRect = r;
	}
	
	/**
	 * Translate ObjRect
	 * Moves Object
	 * @param x
	 * @param y
	 */
	public void transObjRect(float x, float y){
		objRect.setPosition(x, y);
	}
	
	/**
	 * Get ObjRect
	 * Returns Physics Object
	 * @return
	 */
	public Body getObjRect(){
		return objRect;
	}

	/**
	 * Set Objrect
	 * Sets Physics Object
	 * @param r
	 */
	public void setObjRect(Body<Object> r){
		objRect = r;
	}
	
	/**
	 * @return the cellType
	 */
	public int getCellType() {
		return cellType;
	}
	
	/**
	 * Initialise Slick Rectangle
	 * Creates a new Slick Rectangle
	 */
	public void intSlickRect(){
		rect = new Rectangle(objRect.getX() - offsetX, objRect.getY() + offsetY, fizRect.getWidth(), fizRect.getHeight());
	}
	
	/**
	 * Get Slick Rectangle
	 * Returns Cell Rectangle
	 * @return
	 */
	public Rectangle getSlickRect(){
		return rect;
	}

	/**
	 * Set Cell Type
	 * @param cellType
	 * the cellType to set
	 */
	public void setCellType(int cellType) {
		this.cellType = cellType;
	}
	
	/**
	 * Set Offset
	 * Sets offsets for Cell
	 * @param offsetX
	 * @param offsetY
	 */
	public void setOffsets(int offsetX, int offsetY){
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	/**
	 * Is Wall?
	 * Returns whether the Cell is a wall
	 * @return
	 */
	public boolean isWall() {
		return cellType == WALL;
	}
	
	/**
	 * Get Centre X
	 * Returns Central X Co-Ord
	 * @return
	 */
	public float getCentreX(){
		return rect.getCenterX();
	}
	
	/**
	 * Get Centre Y
	 * Returns Central Y Co-Ord
	 * @return
	 */
	public float getCentreY(){
		return rect.getCenterY();
	}
	
	/**
	 * Get X
	 * Returns X Co-Ord
	 * @return
	 */
	public float getX(){
		return rect.getX();
	}
	
	/**
	 * Get Y
	 * Returns Y Co-Ord
	 * @return
	 */
	public float getY(){
		return rect.getY();
	}

	/**
	 * To String
	 * Outputs a String Representation of the Cell
	 */
	@Override
	public String toString() {
		return "Cell [position=" + position + "]";
	}	
	
	//Initial Cell Type
	int cellType = FREE;
	//Free
	static final int FREE = 0;
	//Wall
	static final int WALL = 1;
	
	/**
	 * Show Cell Type
	 * Draws the Cells Type
	 * @param g
	 */
	public void showCellType(Graphics g){
		g.drawString("" + cellType, rect.getX(), rect.getY());
	}
}
