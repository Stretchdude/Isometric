package de.immerfroehlich.model;

import java.rmi.server.UID;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class CollisionObject implements Entity {
	
	private Shape collisionShape;
	private int layer;
	private boolean moving;
	private EntityName name;
	
	/**
	 * Der Offset zum Mittelpunkt des Collision- Shapes
	 * auf dem isometrischen 2D- Bild (tile image).
	 * Das ist die einzige Stelle im Model, an der
	 * Bildschirmkoordinaten angegeben werden.
	 */
	private Vector2f isoImageOffset;

	public CollisionObject(Shape collisionShape, int layer, boolean moving, Vector2f isoImageOffset, EntityName name) {
		this.collisionShape = collisionShape;
		this.layer = layer;
		this.moving = moving;
		this.isoImageOffset = isoImageOffset;
		this.name = name;
	}

	@Override
	public Vector2f getPosition() {
		return new Vector2f(collisionShape.getCenterX(), collisionShape.getCenterY());
	}

	@Override
	public void setPosition(Vector2f position) {
		collisionShape.setCenterX(position.getX());
		collisionShape.setCenterY(position.getY());
	}

	@Override
	public int getLayer() {
		return layer;
	}
	
	public boolean doCollide(CollisionObject collObj) {
		if(this.collisionShape.intersects(collObj.collisionShape))
			return true;
		
		return false;
	}
	
	public boolean isMovingObject() {
		return this.moving;
	}

	public Vector2f getIsoImageOffset() {
		return isoImageOffset;
	}

	@Override
	public EntityName getName() {
		return name;
	}

	public static EntityName createUniqueName() {
		UID uid = new UID();
		return new EntityName("CollisionObject:" + uid);
	}

	@Override
	public String toString() {
		return "CollisionObject [name=" + name + "]";
	}
	
}
