package de.immerfroehlich.rendering;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.FastTrig;

public final class IsometricConverter {

	private float stretchFactor;
	private Vector2f offset = new Vector2f(0, 0);
	double cosAngle;
	double sinAngle;

	public IsometricConverter(float angle, float stretchFactor) {
		this.stretchFactor = stretchFactor;
		this.cosAngle = FastTrig.cos(angle);
		this.sinAngle = FastTrig.sin(angle);
	}

	/**
	 * Konvertiert einen Vektor aus dem zweidimensionalen Raum in einen Vektor
	 * einer isometrischen Abbildung in den zweidimensionalen Raum. Also von 2D
	 * zu ISO2D oder wenn dir das mit den langen Namen nicht so liegt: Vom Grid
	 * zu ISO.
	 * Dabei ist zu beachten, dass der Bildschirm gegenüber dem mathematischen
	 * Koordinatensystem gespiegelt ist.
	 * Im mathematischen Koordinatensystem wird gegen den Uhrzeigersinn
	 * gedreht.
	 * 
	 * @param point
	 * Vektor einer zweidimensionalen Ebene, welcher konvertiert
	 * werden soll.
	 * @return
	 * Nach Isometrie konvertierter Vektor.
	 */
	public Vector2f convertToIsometric(Vector2f point) {

		double x = (cosAngle * point.x) - (sinAngle * point.y);
		double y = (sinAngle * point.x) + (cosAngle * point.y);

		y = y * stretchFactor;

		float offsetX = (float) (x + offset.x);
		float offsetY = (float) (y + offset.y);

		return new Vector2f(offsetX, offsetY);

	}

	/**
	 * Dreht einen Vektor im mathematischen Koordinatensystem
	 * im Uhrzeigersinn.
	 * @see IsometricConverter#convertToIsometric(Vector2f)
	 * 
	 * @param point
	 * Vektor einer zweidimensionalen, isometrischen Abbildung.
	 * @return
	 * Nach 2D- Grid konvertierter Vektor.
	 */
	public Vector2f convertToGrid(Vector2f point) {

		float x = (float) (point.x - offset.x);
		float y = (float) (point.y - offset.y);

		if (stretchFactor != 0.0f) {
			y = y / stretchFactor;
		}
		
		double gridX = (cosAngle * x) + (sinAngle * y);
		double gridY = (cosAngle * y) - (sinAngle * x);
		
		return new Vector2f((float)gridX, (float)gridY);

	}

	public void setOffset(Vector2f offset) {
		this.offset = offset;
	}
}
