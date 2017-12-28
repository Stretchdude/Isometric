package de.immerfroehlich.rendering;

import org.newdawn.slick.geom.Vector2f;

public class View {
	
	/**
	 * Die View Position ist in Bildschirmkoordinaten
	 * angegeben (nicht Grid).
	 */
	private Vector2f position;
	
	private Vector2f size;
	
	public View(Vector2f size) {
		position = new Vector2f(0, 0);
		this.size = size;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getPosition() {
		return position;
	}
	
	public Vector2f getSize() {
		return size;
	}
}
