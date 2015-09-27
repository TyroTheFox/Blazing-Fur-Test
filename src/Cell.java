import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.newdawn.fizzy.StaticBody;
import org.newdawn.fizzy.World;
import org.newdawn.fizzy.Body;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.geom.Rectangle;
/**
 * 
 * @author adrian and Kieran Clare
 * This class represnts a single cell from a maze grid
 * The cell can be free, a wall, a taget or a follower
 */
public class Cell {
	Rectangle rect;
	PolygonShape physpoly;
	Point position;
	FixtureDef fd;
	Cell parent = null; // a pointer to the parent cell - null if no parent
	
	int exitID = 0;

	int fScore = 0;
	int gScore = 0;
	int hScore = 0;
	
	int offsetY = 23; 
	int offsetX = -1;

	BodyDef bd;
	World world;
	Body body;
	
	Cell(int x, int y, int x2, int y2) {
		position = new Point(x, y);
		cellType = FREE;
		rect = new Rectangle(x2 - offsetX, y2 - offsetY, 32, 32);
	}

	/**
	 * @return the cellType
	 */
	public int getCellType() {
		return cellType;
	}
	
	public void setExitID(int n){
		exitID = n;
	}
	
	public int getExitID(){
		return exitID;
	}
	

	/**
	 * @param cellType
	 *            the cellType to set
	 */
	public void setCellType(int cellType) {
		this.cellType = cellType;
	}

	public boolean isFree() {
		return cellType == FREE;
	}

	public void setWall() {
		cellType = WALL;

	}
	
	public void setOffsets(int offsetX, int offsetY){
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	public void setFollower() {
		cellType = FOLLOWER;

	}
	
	public void setPlayer() {
		cellType = PLAYER;

	}

	public void setTarget() {
		cellType = TARGET;

	}

	public boolean isWall() {
		return cellType == WALL;
	}

	public boolean isFollower() {
		return cellType == FOLLOWER;
	}

	public boolean isPlayer() {
		return cellType == PLAYER;
	}
	
	public void setFree() {
		cellType = FREE;
	}
	
	public float getCentreX(){
		return rect.getCenterX();
	}
	
	public float getCentreY(){
		return rect.getCenterY();
	}
	
	public float getX(){
		return rect.getX();
	}
	
	public float getY(){
		return rect.getY();
	}

	public void setParent(Cell parent, Cell target) {
		this.parent = parent;
		if (position.x != parent.position.x && position.y != parent.position.y)
			gScore = parent.gScore + 14; // it is diagonal
		else
			gScore = parent.gScore + 14;
		hScore = (Math.abs(this.position.x - target.position.x) + Math.abs(this.position.y - target.position.y)) * 10;
		fScore = gScore + hScore;

	}

	public void setParentIfBetter(Cell parent, Cell target) {
		int tempfScore;
		int tempgScore;
		int temphScore;
		if (position.x != parent.position.x && position.y != parent.position.y)
			tempgScore = parent.gScore + 14; // it is diagonal
		else
			tempgScore = parent.gScore + 14;
		temphScore = (Math.abs(this.position.x - target.position.x) + Math.abs(this.position.y - target.position.y)) * 10;
		tempfScore = tempgScore + temphScore;
		if (tempfScore < fScore) {
			fScore = tempfScore;
			gScore = tempgScore;
			hScore = temphScore;
			this.parent = parent;
		}

	}

	public int getFScore() {
		return fScore;
	}
	
	public void renderExits(Graphics g){
		g.fill(rect);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cell [position=" + position + ", fScore=" + fScore + "]";
	}

	
	
	
	/* constants to indicate what type of cell this is
	 */
	
	
	public void setInspected() {
		inspected = true;

	}

	public boolean isTarget() {
		return cellType == TARGET;
	}
	
	int cellType = FREE;

	static final int FREE = 0;

	static final int WALL = 1;

	static final int TARGET = 2;

	static final int FOLLOWER = 4;
	
	static final int PLAYER = 5;
	
	static final int NEXT = 6;
	
	private boolean inspected = false;
	
	public void showCellType(Graphics g){
		g.drawString("" + cellType, rect.getX(), rect.getY());
	}

}
