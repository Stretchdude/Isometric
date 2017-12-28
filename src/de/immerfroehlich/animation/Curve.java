package de.immerfroehlich.animation;

import org.newdawn.slick.geom.Vector2f;

/**
 * Repräsentiert eine lineare Bezier Kurve (simple Interpolation).
 * Kann zu einem Pfad zusammengesetzt werden.
 * @author andreasf
 *
 */
public class Curve {
	
	private Vector2f startPoint;
	private Vector2f endPoint;
	public Curve(Vector2f startPoint, Vector2f endPoint) {
		super();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	public Vector2f getStartPoint() {
		return startPoint;
	}
	public Vector2f getEndPoint() {
		return endPoint;
	}

	/**
	 * Liefert einen Wert auf einer
	 * geraden zwischen startPoint und Endpoint
	 * in Abhängigkeit zu zum Parameter t.
	 * 
	 * @param t
	 * Ist ein Wert zwischen 0 und 1.
	 * Bei 0 wird der startPoint zurückgegeben.
	 * Bei 1 wird der endPoint zurückgegeben.
	 * Alles andere liefert einen Wert auf einer
	 * geraden zwischen startPoint und Endpoint.
	 * @return
	 * Liefert einen Wert auf einer
	 * geraden zwischen startPoint und Endpoint.
	 * @see http://en.wikipedia.org/wiki/Bezier_curves#Linear_B.C3.A9zier_curves
	 */
	public Vector2f calculatePoint(float t) {
		float x = (1-t) * startPoint.x  +  t * endPoint.x;
		float y = (1-t) * startPoint.y  +  t * endPoint.y;
		
		return new Vector2f(x,y);
	}
}
