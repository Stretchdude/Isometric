package de.immerfroehlich.tiledmap;

import java.io.InputStream;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.newtiled.MapNotLoadedException;

import de.immerfroehlich.collision.CollisionDetection;
import de.immerfroehlich.model.GameMap;

public final class GameMapLoader {
	
	private ResourceManager resourceManager;
	
	//TODO: Wenn die Kollisionserkennung dynamisch gefüllt
	//wird, kann sie hier entfernt werden.
	private CollisionDetection collisionDetection;
	
	ZipExtractor zip;
	
	public GameMapLoader(ResourceManager resourceManager, CollisionDetection collisionDetection) {
		this.resourceManager = resourceManager;
		this.collisionDetection = collisionDetection;
		zip = new ZipExtractor();
		zip.openZipFile("res/game.zip");
	}

	public void loadGlobalResources() {
		//TODO Globale Resourcen laden
		
		InputStream scriptStream = zip.getZipEntry("scripts/collisionScript.js");
		String script = ScriptReader.readScript(scriptStream);
		resourceManager.addGlobalScript("collisionScript", script);
	}
	
	public GameMap load(String gameMapName) {
		//TODO Hier wird später alles dynamisch aus der settings.ini
		//oder sonstwo geladen. Also per Konfiguration.
		
		
		
		//Map Resourcen laden
		String gameFilePrefix = gameMapName + "/" + gameMapName;
		String pngFileName = gameFilePrefix + ".png";
		InputStream imageStream = zip.getZipEntry(pngFileName);
		Image image = null;
		try {
			image = new Image(imageStream, pngFileName, false);
		} catch (SlickException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String mapName = gameFilePrefix + ".tmx";
		InputStream mapStream = zip.getZipEntry(mapName);
		
		GameMap gameMap = new GameMap();
	    TiledMapConverter map = new TiledMapConverter(gameMap, image, collisionDetection);
	    try {
	       map.load(mapStream);
	    } catch (MapNotLoadedException e) {
	       e.printStackTrace();
	    } catch (Exception e) {
	       e.printStackTrace();
	    }
	    
	    //TODO: Map Name aus dem Dateinamen lesen
	    resourceManager.initMapResources(gameMapName);
	    
	    //TODO: Alle Scripte der Karte laden
	    
	    return gameMap;
	}

}
