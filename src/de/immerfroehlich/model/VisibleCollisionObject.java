package de.immerfroehlich.model;

import java.rmi.server.UID;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class VisibleCollisionObject extends CollisionObject implements Drawable {

	private Image image;

	public VisibleCollisionObject(Shape collisionShape, int layer, boolean moving,
			Vector2f isoImageOffset, Image image, EntityName name) {
		super(collisionShape, layer, moving, isoImageOffset, name);
		this.image = image;
	}

	@Override
	public void draw(Vector2f position) {
		image.draw(position.x, position.y);
	}

	@Override
	public Image getImage() {
		return image;
	}
	
	public static EntityName createUniqueName() {
		UID uid = new UID();
		String name = "VisibleCollsionObject:" + uid;
		return new EntityName(name);
	}

}
