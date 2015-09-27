package core;

import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Circle;
import org.newdawn.fizzy.DynamicBody;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * Projectile
 * A characters shot bullets or whatever
 * @author Kieran Clare
 *
 */

public class Projectile {

	//Physics Object
    Circle oBody;
    Body<Object> body;
    
    //Slick 2D Objects
    Rectangle hitbox;
    
    //Object Offsets
	float objectOffsetX = 100;
	float objectOffsetY = 110;
	
	/**
	 * Projectile
	 * @param x
	 * @param y
	 * @param radius
	 * @param density
	 * @param camX
	 * @param camY
	 */
	public Projectile(float x, float y, float radius, float density, float camX, float camY){
		//Physics Body
		oBody = new Circle(10);
		body = new DynamicBody<Object>(oBody, x, y);
		body.setFixedRotation(true);
		body.setFriction(0);
		body.setRestitution(0);
		body.setDensity(density);
		
		//Slick 2D Hitbox
		hitbox = new Rectangle((body.getX() -10) + (camX + objectOffsetX), (body.getY() -11) + (camY + objectOffsetY), 10, 10);
	}
	
	/**
	 * Render
	 * Draws Assets
	 * @param g
	 */
	public void render(Graphics g){
		g.fill(hitbox);
	}
	
	/**
	 * Update
	 * Updates Projectile Location
	 * @param camX
	 * @param camY
	 */
	public void update(float camX, float camY){
		hitbox.setLocation((((body.getX() -10) + camX) + objectOffsetX), (((body.getY() -11) + camY) + objectOffsetY));
	}
	
	/**
	 * Get Body
	 * Returns Physics Body
	 * @return
	 */
	public Body getBody(){
		return body;
	}
	
	/**
	 * Translate
	 * Moves Body
	 * @param x
	 * @param y
	 */
	public void translate(float x, float y){
		body.setPosition(x, y);
	}
	
	/**
	 * Fire
	 * Applies a force to the projectile
	 * @param xMagnatude
	 * @param yMagnatude
	 */
	public void fire(float xMagnatude, float yMagnatude){
		body.applyForce(xMagnatude, yMagnatude);
	}
	
}
