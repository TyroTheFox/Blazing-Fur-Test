package core;

import java.awt.geom.Point2D;
import java.io.InputStream;

import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.ResourceLoader;

/**
 * Camera Class
 * @author Kieran
 *
 */

public class Camera {

   /** the map used for our scene */
   protected TiledMap map;
   
   /** the number of tiles in x-direction (width) */
   protected int numTilesX;
   
   /** the number of tiles in y-direction (height) */
   protected int numTilesY;
   
   /** the height of the map in pixel */
   protected int mapHeight;
   
   /** the width of the map in pixel */
   protected int mapWidth;
   
   /** the width of one tile of the map in pixel */
   protected int tileWidth;
   
   /** the height of one tile of the map in pixel */
   protected int tileHeight;
   
   /** the GameContainer, used for getting the size of the GameCanvas */
   protected GameContainer gc;

   /** the x-position of our "camera" in pixel */
   protected float cameraX;
   
   /** the y-position of our "camera" in pixel */
   protected float cameraY;
   
   protected Point2D.Float currentCenterPoint = new Point2D.Float(0,0);
   
   protected int controlX = 0;
   protected int controlY = 0;
   int focusX = 0;
   int focusY = 0;
   
   private final Vec2 initPosition = new Vec2();
   private float initScale;

   private final IViewportTransform transform;
   
   private final Vec2 oldCenter = new Vec2();
   private final Vec2 newCenter = new Vec2();
   
   /**
    * Create a new camera
    * 
    * @param gc the GameContainer, used for getting the size of the GameCanvas
    * @param string the TiledMap used for the current scene
 * @throws SlickException 
    */
   public Camera(GameContainer gc, String ref) throws SlickException {
	   InputStream iS = ResourceLoader.getResourceAsStream(ref);
	   //A tiled map 
	   map = new TiledMap(iS, "res");
      
      this.numTilesX = map.getWidth();
      this.numTilesY = map.getHeight();
      
      this.tileWidth = map.getTileWidth();
      this.tileHeight = map.getTileHeight();
      
      this.mapWidth = this.numTilesX * this.tileWidth;
      this.mapHeight = this.numTilesY * this.tileHeight;
      
		transform = new OBBViewportTransform();
		initScale = 1f;
      
      this.gc = gc;
   }
   
   /**
    * "locks" the camera on the given coordinates. The camera tries to keep the location in it's center.
    * 
    * @param x the real x-coordinate (in pixel) which should be centered on the screen
    * @param y the real y-coordinate (in pixel) which should be centered on the screen
    */
   public void centerOn(float x, float y) {
	    transform.getScreenToWorld(initPosition, oldCenter);
	    transform.getScreenToWorld(initPosition, newCenter);
		initPosition.set(x, y);
		Vec2 transformedMove = oldCenter.subLocal(newCenter);
		transform.setCamera(initPosition.x, initPosition.y, initScale);
		transform.setCenter(transform.getCenter().addLocal(transformedMove));
   }
   
   /**
    * "locks" the camera on the center of the given Rectangle. The camera tries to keep the location in it's center.
    * 
    * @param x the x-coordinate (in pixel) of the top-left corner of the rectangle
    * @param y the y-coordinate (in pixel) of the top-left corner of the rectangle
    * @param height the height (in pixel) of the rectangle
    * @param width the width (in pixel) of the rectangle
    */
   public void centerOn(float x, float y, float height, float width) {
      this.centerOn(x + width / 2, y + height / 2);
   }

   /**
    * "locks the camera on the center of the given Shape. The camera tries to keep the location in it's center.
    * @param shape the Shape which should be centered on the screen
    */
   public void centerOn(Shape shape) {
      this.centerOn(shape.getCenterX(), shape.getCenterY());
   }
   
   /**
    * draws the part of the map which is currently focussed by the camera on the screen
    */
   public void drawMap() {
      this.drawMap(0, 0);
   }
   
   /**
    * draws the part of the map which is currently focussed by the camera on the screen.<br>
    * You need to draw something over the offset, to prevent the edge of the map to be displayed below it<br>
    * Has to be called before Camera.translateGraphics() !
    * @param offsetX the x-coordinate (in pixel) where the camera should start drawing the map at
    * @param offsetY the y-coordinate (in pixel) where the camera should start drawing the map at
    */
   
   public void drawMap(int offsetX, int offsetY) {
       //calculate the offset to the next tile (needed by TiledMap.render())
       int tileOffsetX = (int) - (cameraX % tileWidth);
       int tileOffsetY = (int) - (cameraY % tileHeight);
       
       //calculate the index of the leftmost tile that is being displayed
       int tileIndexX = (int) (cameraX / tileWidth);
       int tileIndexY = (int) (cameraY / tileHeight);
       
       int mapOffsetX = 205;
       int mapOffsetY = 375;
       
       controlX = (tileOffsetX - offsetX) + mapOffsetX;
       controlY = (tileOffsetY - offsetY) + mapOffsetY;
       
       //finally draw the section of the map on the screen
//       System.out.println((((tileOffsetX - offsetX) + mapOffsetX) + focusX) + " ," + ((tileOffsetY - offsetY) + mapOffsetY) + focusY);
//       map.render(   
//             ((tileOffsetX - offsetX) + mapOffsetX), 
//             ((tileOffsetY - offsetY) + mapOffsetY), 
//             tileIndexX,  
//             tileIndexY,
//             (gc.getWidth()  - tileOffsetX) / tileWidth  + 1,
//             (gc.getHeight() - tileOffsetY) / tileHeight + 1);
       map.render(((tileOffsetX - offsetX) + mapOffsetX) + focusX, 
             ((tileOffsetY - offsetY) + mapOffsetY) + focusY);
   }
   
   /**
    * Translates the Graphics-context to the coordinates of the map - now everything
    * can be drawn with it's NATURAL coordinates.
    */
   public void translateGraphics() {
      gc.getGraphics().translate(-cameraX, -cameraY);
   }
   /**
    * Reverses the Graphics-translation of Camera.translatesGraphics().
    * Call this before drawing HUD-elements or the like
    */
   public void untranslateGraphics() {
      gc.getGraphics().translate(cameraX, cameraY);
   }
   
   public int getControlX(){
	   return controlX;
   }
   
   public int getControlY(){
	   return controlY;
   }
   
}