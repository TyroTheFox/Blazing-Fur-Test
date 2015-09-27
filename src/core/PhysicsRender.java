package core;

import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Circle;
import org.newdawn.fizzy.CompoundShape;
import org.newdawn.fizzy.Polygon;
import org.newdawn.fizzy.Rectangle;
import org.newdawn.fizzy.Shape;
import org.newdawn.fizzy.World;
import org.newdawn.slick.Graphics;

	/**
	 * A canvas that uses Java2D to display the world for debug
	 * 
	 * @author kevin
	 */
	public class PhysicsRender {
		/** The world we're displaying */
		private World world;
		/** True if the simulation is running */
		private boolean running;
		
		float renderOffsetX = 0;
		float renderOffsetY = 0;
		
		static float SCALE = 1f;
		
		/**
		 * Create a new canvas
		 * 
		 * @param world The to be displayed
		 */
		public PhysicsRender(World world) {
			this.world = world;
		}
		
//		/** 
//		 * Start the render loop
//		 */
//		public void start() {
//			createBufferStrategy(2);
//			running = true;
//			runLoop();
//		}
		
		/**
		 * The logic and render loop
		 */
		public void render(Graphics g, int a, int b) {
//			world.update(0.003f);
			
//			g.clear();
//			g.translate(800/2,600/2);
//			g.scale(1,-1);
			for (int i=0;i<world.getBodyCount();i++) {
//				world.getBody(i).translate(a, b);
				drawBody(g, world.getBody(i));
			}
		}
		
		public void update(float t){
			world.update(t);
		}
		
		/**
		 * Get the world being rendered
		 * 
		 * @return The world being renderer
		 */
		public World getWorld() {
			return world;
		}
		
		/**
		 * Get the world being rendered
		 * 
		 * @return The world being renderer
		 */
		public void setWorld(World w) {
			world = w;
		}
		
		/**
		 * Draw a body to the canvas
		 * 
		 * @param g The graphics context on which to draw
		 * @param body The body to be rendered
		 */
		private void drawBody(Graphics g, Body body) {
			Shape shape = body.getShape();
			drawShape(g,body,shape);
		}

		/**
		 * Draw a shape 
		 * 
		 * @param g The graphics context to render to
		 * @param body The body to be rendered
		 * @param shape The shape representing the body
		 */
		private void drawShape(Graphics g, Body body, Shape shape) {
			if (shape instanceof Circle) {
				drawCircle(g, body, (Circle) shape);
			}
			if (shape instanceof Rectangle) {
				drawRectangle(g, body, (Rectangle) shape);
			}
			if (shape instanceof Polygon) {
				drawPolygon(g, body, (Polygon) shape);
			}
			if (shape instanceof CompoundShape) {
				drawCompound(g, body, (CompoundShape) shape);
			}
		}

		/**
		 * Draw a compound shape 
		 * 
		 * @param g The graphics context to render to
		 * @param body The body to be rendered
		 * @param shape The shape representing the body
		 */
		private void drawCompound(Graphics g, Body body, CompoundShape shape) {
			int count = shape.getShapeCount();
			for (int i=0;i<count;i++) {
				drawShape(g, body, shape.getShape(i));
			}
		}
		
		/**
		 * Draw a body represented by a circle
		 * 
		 * @param g The graphics context to render to
		 * @param body The body to be rendered
		 * @param shape The shape representing the body
		 */
		private void drawCircle(Graphics g, Body body, Circle shape) {
//			g.translate(body.getX(), body.getY());
//			g.rotate(body.getX(), body.getY(), body.getRotation());
			
			float radius = shape.getRadius();
			
			g.setColor(org.newdawn.slick.Color.red);
			g.fillOval((int) -radius + renderOffsetX,(int) -radius + renderOffsetY,(int) (radius*2),(int) (radius*2));
			g.drawLine(0,0,0,(int) -radius);
			g.setColor(org.newdawn.slick.Color.white);
		}
		
		/**
		 * Draw a body represented by a rectangle
		 * 
		 * @param g The graphics context on which to render
		 * @param body The body to be rendered
		 * @param shape The shape representing the body
		 */
		private void drawRectangle(Graphics g, Body body, Rectangle shape) {
//			g.translate(body.getX(), body.getY());
//			g.rotate(body.getX(), body.getY(), body.getRotation());
//			g.translate(shape.getXOffset(), shape.getYOffset());
//			g.rotate(shape.getXOffset(), shape.getYOffset(), shape.getAngleOffset());
			
			float width = shape.getWidth();
			float height = shape.getHeight();
			
			g.setColor(org.newdawn.slick.Color.red);
			g.fillRect((int) body.getX() + renderOffsetX,(int) body.getY() + renderOffsetY,(int) width,(int) height);
			g.setColor(org.newdawn.slick.Color.white);
		}
		
		/**
		 * Draw a body represented by a polygon
		 * 
		 * @param g The graphics context on which to render
		 * @param body The body to be rendered
		 * @param shape The shape representing the body
		 */
		private void drawPolygon(Graphics g, Body body, Polygon shape) {
//			g.translate(body.getX(), body.getY());
//			g.rotate(body.getX(), body.getY(), body.getRotation());
//			g.translate(shape.getXOffset(), shape.getYOffset());
//			g.rotate(shape.getXOffset(), shape.getYOffset(), shape.getAngleOffset());

			g.setColor(org.newdawn.slick.Color.red);
			for (int i=0;i<shape.getPointCount();i++) {
				int n = i+1;
				if (n >= shape.getPointCount()) {
					n = 0;
				}
				g.drawLine((int) shape.getPointX(i) + renderOffsetX, (int) shape.getPointY(i) + renderOffsetY,
						   (int) shape.getPointX(n) + renderOffsetX, (int) shape.getPointY(n) + renderOffsetY);
			}
			g.setColor(org.newdawn.slick.Color.white);
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

}
