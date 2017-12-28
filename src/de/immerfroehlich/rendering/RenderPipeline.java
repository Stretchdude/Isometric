package de.immerfroehlich.rendering;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import de.immerfroehlich.model.Drawable;
import de.immerfroehlich.model.GameMap;

public class RenderPipeline {
	
	GameMap gameMap;
	View view;
	GridConverter gridConverter;
	IsometricConverter isoConverter;
	private DrawingSequenceSorter sorter;
	
	public RenderPipeline(GameMap gameMap, View view, GridConverter gridConverter, IsometricConverter isoConverter) {
		this.gameMap = gameMap;
		this.view = view;
		this.gridConverter = gridConverter;
		this.isoConverter = isoConverter; 
		this.sorter = new DrawingSequenceSorter(gridConverter);
	}

	public void render(List<Drawable> visibleElements) {
		sorter.sort(visibleElements);
		newRenderMethod(visibleElements);
	}
	
	public void newRenderMethod(List<Drawable> drawables) {
		Vector2f viewPos = view.getPosition();
		
		for(Drawable drawable : drawables) {
			Vector2f tilePos = drawable.getPosition();
			Vector2f screenDrawPos = isoConverter.convertToIsometric(tilePos);
			screenDrawPos.sub(viewPos); //Zurück zum Bildschirmpunkt
			
			//Und noch das Tile- Image Offset draufrechnen
			Vector2f offset = drawable.getIsoImageOffset();
			Vector2f drawPos = new Vector2f(screenDrawPos.x - offset.x, screenDrawPos.y - offset.y);
//			Vector2f drawPos = new Vector2f(screenDrawPos.x - (world.getTileWidth() / 2), screenDrawPos.y);
			drawable.draw(drawPos);
		}
	}
	
}
