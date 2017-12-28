package de.immerfroehlich.tiledmap;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;

public class ResourceManager {
	
	private final String GLOBAL = "_GLOBAL_the_user_cannot_choose_this_name_for_a_map";
	
	private class GameMapResources {
		//String = ScriptName, String = Script
		Map<String, String> scripts = new HashMap<String, String>();
		
		Map<String, Image> images = new HashMap<String, Image>();
	}
	
	//String = mapName or GLOBAL
	private Map<String, GameMapResources> gameMapResources = new HashMap<String, ResourceManager.GameMapResources>();
	
	public ResourceManager() {
		initMapResources(GLOBAL);
	}
	
	public String getScript(String gameMapName, String scriptName) {
		GameMapResources resources = getGameMapResources(gameMapName);
		
		String script = resources.scripts.get(scriptName);
		if(script == null)
			throw new IllegalStateException("Script has not been loaded.");
		
		return script;
	}
	
	//TODO remove not used resources


	public void addScript(String gameMapName, String scriptName, String script) {
		GameMapResources resources = getGameMapResources(gameMapName);
		resources.scripts.put(scriptName, script);
	}

	private GameMapResources getGameMapResources(String gameMapName) {
		GameMapResources resources = gameMapResources.get(gameMapName);
		if(resources == null)
			throw new IllegalStateException("GameMapResources have not been loaded.");
		return resources;
	}
	
	public void addImage(String gameMapName, String imageName, Image image) {
		GameMapResources resources = getGameMapResources(gameMapName);
		resources.images.put(imageName, image);
	}
	
	public Image getImage(String gameMapName, String imageName) {
		GameMapResources resources = getGameMapResources(gameMapName);
		return resources.images.get(imageName);
	}
	
	public void initMapResources(String mapName) {
		gameMapResources.put(mapName, new GameMapResources());
	}
	
	public void addGlobalScript(String scriptName, String script) {
		addScript(GLOBAL, scriptName, script);
	}
	
	public String getGlobalScript(String scriptName) {
		return getScript(GLOBAL, scriptName);
	}

}
