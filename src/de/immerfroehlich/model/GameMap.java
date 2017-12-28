package de.immerfroehlich.model;

import java.util.HashMap;
import java.util.Map;

public class GameMap {
	
	/**
	 * Schlüssel enthält Layer- Nummer
	 */
	private Map<Integer, Layer> tileLayer;
	
	/**
	 * Schlüssel enthält Object Name für Scripting.
	 */
	private Map<EntityName, Drawable> objectLayer;
	
	private int tileWidth;
	
	private int tileHeight;
	
	private float gridElementWidth;
	
	public GameMap() {
		this.tileLayer = new HashMap<Integer, Layer>();
		this.objectLayer = new HashMap<EntityName, Drawable>();
	}

	public Map<Integer, Layer> getTileLayer() {
		return tileLayer;
	}

	public void setTileLayer(Map<Integer, Layer> tileLayer) {
		this.tileLayer = tileLayer;
	}

	public void put(int i, Layer layer) {
		this.tileLayer.put(i, layer);
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public float getGridElementWidth() {
		return gridElementWidth;
	}

	public void setGridElementWidth(float gridElementWidth) {
		this.gridElementWidth = gridElementWidth;
	}

	public float getTileHeight() {
		return tileHeight;
	}
	
	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}

	public Map<EntityName, Drawable> getObjectLayer() {
		return objectLayer;
	}

	public void removeEntity(Drawable drawable) {
		EntityName name = drawable.getName();
		objectLayer.remove(name);
	}

	public void addEntity(Drawable drawable) {
		objectLayer.put(drawable.getName(), drawable);
	}
	
}
