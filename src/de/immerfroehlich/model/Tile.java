package de.immerfroehlich.model;

import java.rmi.server.UID;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class Tile implements Drawable {

	Vector2f position;
	
	/**
	 * Isometric Image
	 */
	Image image;
	
	EntityName name;
	
	/**
	 * Diese Zahl gibt an, welches Tile zuerst
	 * gerendert wird, wenn sich mehrere Tiles
	 * im selben Grid- Segment befinden und somit
	 * überlappen.
	 * Das Tile mit der kleineren Zahl soll
	 * zuerst gerendert werden.
	 * Also z.B.
	 * layer = 0; zuerst zeichnen
	 * layer = 4; als zweites zeichnen
	 * layer = 5; als drittes zeichenen
	 */
	private int layer = 0;
	
	/**
	 * Der Offset zum Mittelpunkt des Collision- Shapes
	 * auf dem isometrischen 2D- Bild (tile image).
	 * Das ist die einzige Stelle im Model, an der
	 * Bildschirmkoordinaten angegeben werden.
	 */
	private Vector2f isoImageOffset;
	
	public Tile(Vector2f position, int layer, Image image, Vector2f isoImageOffset, EntityName name) {
		super();
		this.position = position;
		this.layer = layer;
		this.image = image;
		this.isoImageOffset = isoImageOffset;
		this.name = name;
	}

	@Override
	public Vector2f getPosition() {
		return position;
	}

	@Override
	public void setPosition(Vector2f position) {
		this.position = position;
	}

	@Override
	public void draw(Vector2f position) {
		image.draw(position.x, position.y);
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public int getLayer() {
		return layer;
	}

	@Override
	public Vector2f getIsoImageOffset() {
		return isoImageOffset;
	}

	@Override
	public EntityName getName() {
		return name;
	}
	
	public static EntityName createUniqueName() {
		UID uid = new UID();
		String name = "Tile:" + uid;
		return new EntityName(name);
	}

}
