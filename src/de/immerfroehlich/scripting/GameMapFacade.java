package de.immerfroehlich.scripting;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import de.immerfroehlich.collision.CollisionDetection;
import de.immerfroehlich.model.CollisionObject;
import de.immerfroehlich.model.Drawable;
import de.immerfroehlich.model.EntityName;
import de.immerfroehlich.model.GameMap;
import de.immerfroehlich.model.VisibleCollisionObject;
import de.immerfroehlich.tiledmap.GameMapLoader;
import de.immerfroehlich.tiledmap.ResourceManager;

public class GameMapFacade {
	
	private GameMap gameMap;
	private CollisionDetection collision;
	private ResourceManager resourceManager;
	private GameMapLoader gameMapLoader;
	
	private String currentMapName;
	
	public GameMapFacade(GameMap gameMap, CollisionDetection collision, ResourceManager resourceManager, GameMapLoader gameMapLoader) {
		super();
		this.gameMap = gameMap;
		this.collision = collision;
		this.resourceManager = resourceManager;
		this.gameMapLoader = gameMapLoader;
	}
	
	public void loadMap(String gameMapName) {
		//TODO die Karten müssen an anderer Stelle
	      //dynamisch geladen werden.
		currentMapName = gameMapName;
	    gameMap = gameMapLoader.load(currentMapName);
	}
	
	public void remove(CollisionObject entity) {
		collision.remove(entity);
		
		if(Drawable.class.isAssignableFrom(entity.getClass())) {
			gameMap.removeEntity((Drawable) entity);
		}
	}
	
	public VisibleCollisionObject createMovingEntity(float xPos, float yPos, float radius,
			String entityName, String mapName, String imageName) {
		
		Shape shape = new Circle(xPos, yPos, radius);
		Image image = resourceManager.getImage(mapName, imageName);
		VisibleCollisionObject obj = new VisibleCollisionObject(shape, 1, true, new Vector2f(0,0), image, new EntityName(entityName));
		gameMap.addEntity(obj);
		return obj;
	}

}
