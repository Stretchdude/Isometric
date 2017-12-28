package de.immerfroehlich.rendering;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import de.immerfroehlich.model.Drawable;
import de.immerfroehlich.model.Layer;
import de.immerfroehlich.model.GameMap;

public class VisibilityChecker {
	
	GameMap gameMap;
	View view;
	GridConverter gridConverter;
	IsometricConverter isoConverter;
	
	final float start = -10f;
	final Vector2f viewSize;
	final Vector2f stepWidth;
	
	public VisibilityChecker(GameMap gameMap, View view, GridConverter gridConverter, IsometricConverter isoConverter) {
		this.gameMap = gameMap;
		this.view = view;
		this.gridConverter = gridConverter;
		this.isoConverter = isoConverter;
		
		viewSize = new Vector2f(view.getSize().x + 30f, view.getSize().y + 10f);
		stepWidth = new Vector2f(gameMap.getTileWidth() / 2f, gameMap.getTileHeight() / 2f);
	}
	
	/**
	 * Sehr naiver Algorithmus zum Ermitteln aller
	 * sichtbaren Tiles.
	 * Fügt die ermittelten Tiles der GameMap hinzu.
	 */
	public List<Drawable> findAllVisibleElements() {
		List<Drawable> visibleElements = new LinkedList<Drawable>();
		
		addAllVisibleTilesToVisibleElements(visibleElements);
		addAllObjectsToVisibleElements(visibleElements);
		
//		System.out.println("Number of cycles: " + cycles);
		return visibleElements;
	}

	private void addAllObjectsToVisibleElements(List<Drawable> visibleElements) {
		//TODO später sollen natürlich nicht alle Objekte immer
		//sichtbar sein. Das ist nur für die Demo.
		visibleElements.addAll(gameMap.getObjectLayer().values());
	}

	private void addAllVisibleTilesToVisibleElements(
		List<Drawable> visibleElements) {
		Vector2f viewPos = view.getPosition();
		
		int cycles = 0;
		
		for(float x = start; x < viewSize.x; x += stepWidth.x) {
			for(float y = start; y < viewSize.y; y += stepWidth.y) {
				cycles++;
				Vector2f screenPos = new Vector2f(x, y);
				screenPos.add(viewPos); //Absoluter Punkt im Koordinatensystem
				Vector2f gridPos = isoConverter.convertToGrid(screenPos);
				Vector2f tileRelPos = gridConverter.convertToArrayField(gridPos);
				
				
				//Stellen an denen kein Tile ist, nicht zeichnen
				{
				Layer layer = gameMap.getTileLayer().get(0);
				if(tileRelPos.x < 0f || tileRelPos.y < 0)
					continue;
				if(tileRelPos.x >= layer.drawables.length
						|| tileRelPos.y >= layer.drawables[0].length)
					continue;
				}
				
				for(Integer key : gameMap.getTileLayer().keySet()) {
					//TODO Layer die keine Zeichenobjekte haben
					//sollen nicht gezeichnet werden, aber vielleicht
					//wäre es besser die schon beim auslesen der
					//Karte zu entfernen.
					Layer layer = gameMap.getTileLayer().get(key);
					Drawable tile = layer.drawables[(int) tileRelPos.x][(int) tileRelPos.y];
					if(tile != null)
						visibleElements.add(tile);
				}
			}
		}
	}

}
