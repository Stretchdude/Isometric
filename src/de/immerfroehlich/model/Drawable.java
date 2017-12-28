package de.immerfroehlich.model;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public interface Drawable extends Entity {
	
	void draw(Vector2f position);
	Image getImage();
	Vector2f getIsoImageOffset();

}
