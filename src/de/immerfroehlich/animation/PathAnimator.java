package de.immerfroehlich.animation;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.geom.Vector2f;

import de.immerfroehlich.model.Entity;

public final class PathAnimator {
	
	private Map<Entity, Path> paths = new HashMap<Entity, Path>();
	
	public void add(Path path, Entity entity) {
		paths.put(entity, path);		
	}
	
	public void remove (Path path, Entity entity) {
		//TODO implementieren.
	}
	
	public void updateContainedEntityPositionsOffContainedPaths(int delta) {
		
		for(Entity entity : paths.keySet()) {
			Path path = paths.get(entity);
			
			Curve curve = path.getCurrentCurve();
			
			//Vektor aus den Punkten bestimmen
			float lX = curve.getEndPoint().x - curve.getStartPoint().x;
			lX = Math.abs(lX);
			float lY = curve.getEndPoint().y - curve.getStartPoint().y;
			lY = Math.abs(lY);
			
			//Länge (Vektorbetrag) berechnen
			float l = (float) Math.sqrt(lX*lX + lY*lY);
			
			//Vektor für bisher zurückgelegte Strecke berechnen
			float lOldX = entity.getPosition().x - curve.getStartPoint().x;
			lOldX = Math.abs(lOldX);
			float lOldY = entity.getPosition().y - curve.getStartPoint().y;
			lOldY = Math.abs(lOldY);
			
			//Länge für bisher zurückgelegte Strecke
			float currentL = (float) Math.sqrt(lOldX*lOldX + lOldY*lOldY);
			
			//Jetzt noch die im letzten delta zurückgelegte
			//Strecke draufrechnen
			currentL = currentL + 0.2f * delta;
			
			float t = currentL / l;
			
			//TODO Geschwindigkeit muss im Entity oder im Path
			//oder irgendwo anders definierbar sein.
			
			
			
			//TODO Prüfen ob die Animation beendet wurde
			
			if(t > 1) {
				curve = path.next();
				entity.setPosition(curve.getStartPoint());
				return;
			}
			
			Vector2f point = curve.calculatePoint(t);
			
			entity.setPosition(point);
		}
	}

}
