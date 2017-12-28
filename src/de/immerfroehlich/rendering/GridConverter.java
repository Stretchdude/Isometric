package de.immerfroehlich.rendering;

import org.newdawn.slick.geom.Vector2f;

public class GridConverter {
	
	View view;
	/**
	 * Es wird davon ausgegangen, dass die Höhe gleich
	 * der Breite ist.
	 */
	float gridElementWidth;
	
	public GridConverter(View view, float gridElementWidth) {
		this.view = view;
		this.gridElementWidth = gridElementWidth;
	}
	
	public Vector2f convertToArrayField(Vector2f gridPoint) {
		int x = (int) (gridPoint.x / gridElementWidth);
		int y = (int) (gridPoint.y / gridElementWidth);
		
		return new Vector2f(x, y);
	}

}
