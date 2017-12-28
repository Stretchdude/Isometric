package de.immerfroehlich.model;

import org.newdawn.slick.geom.Vector2f;

public interface Entity {
	
	Vector2f getPosition();
	void setPosition(Vector2f position);
	int getLayer();
	EntityName getName();
}
