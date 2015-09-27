package core;

import java.util.List;

import org.jbox2d.common.Vec2;
import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.StaticBody;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

/**
 * Slope
 * Creates inclined parts of the level
 * @author Kieran Clare
 *
 */
public class Slope {
	
	//Slick 2D Rectangle
	org.newdawn.fizzy.Rectangle fizRect;
	Shape rect;
	
	//Physics Object
	Body<Object> objRect;
	
	//Offsets
	int offsetFizzY = -60; 
	int offsetFizzX = 0;
	int offsetX = -100;
	int offsetY = 100; 
	
	//Slope Angle
	float angle = 0;
	
	/**
	 * Slope
	 * Initialises a slope
	 * @param x
	 * @param y
	 * @param length
	 * @param width
	 * @param oX
	 * @param oY
	 */
	public Slope(float x, float y, float length, float width, float oX, float oY){
		//Fizzy Physics Object
		fizRect = new org.newdawn.fizzy.Rectangle(length, width);
		objRect = new StaticBody<Object>(fizRect, x, y);
		objRect.setFriction(0.1f);
		objRect.setRestitution(0);
		//Slick 2D Rectangle
		rect = new Rectangle((objRect.getX()), (objRect.getY()), fizRect.getWidth(), fizRect.getHeight());
	}
	
	/**
	 * Slick Translate
	 * Translates JBox2D Co-Ords into a Slick 2D Shape
	 * @param x
	 * @param y
	 */
	public void slickTranslate(float x, float y){
		rect.setLocation((objRect.getX() + x) - 400, (objRect.getY() + y) + 48);
	}
	/**
	 * Get FizRect
	 * Returns physics object
	 * @return
	 */
	public org.newdawn.fizzy.Rectangle getFizRect(){
		return fizRect;
	}
	
	/**
	 * Set FizRect
	 * Sets the physics object
	 * @param r
	 */
	public void setFizRect(org.newdawn.fizzy.Rectangle r){
		fizRect = r;
	}
	
	/**
	 * Render
	 * Draws assets
	 * @param g
	 */
	public void render(Graphics g){
		g.fill(rect);
	}

	/**
	 * Translate Object
	 * Moves the Physics Object
	 * @param x
	 * @param y
	 */
	public void transObjRect(float x, float y){
		objRect.setPosition(x, y);
	}
	
	/**
	 * Rotate ObjRect
	 * Rotates objects
	 * @param rotation
	 */
	public void rotateObjRect(float rotation){
		objRect.setRotation((float)Math.toRadians(rotation));
		rect = rect.transform(Transform.createRotateTransform((float)Math.toRadians(rotation), objRect.getX(), objRect.getY()));
		angle = rotation;
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
	 * Set ObjRect
	 * Sets Physics Object
	 * @param r
	 */
	public void setObjRect(Body<Object> r){
		objRect = r;
	}
	
	/**
	 * Get Slick Rectangle
	 * Returns Slick Rectangle
	 * @return
	 */
	public Shape getSlickRect(){
		return rect;
	}
}
