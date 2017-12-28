package de.immerfroehlich.collision;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.list.CursorableLinkedList;

import de.immerfroehlich.events.CollisionEvent;
import de.immerfroehlich.events.Subject;
import de.immerfroehlich.model.CollisionObject;

public class CollisionDetection extends Subject{
	
	/**
	 * CursorableLinkedList erlaubt das entfernen von Objekten während einer Iteration.
	 */
	private CursorableLinkedList allObjects = new CursorableLinkedList(); //Contains CollisionObject
	private CursorableLinkedList movingObjects = new CursorableLinkedList(); //Contains CollisionObject
	private Map<CollisionObject, Set<CollisionObject>> alreadyVisited = new HashMap<CollisionObject, Set<CollisionObject>>();
	
	public void checkCollisions() {
		
		@SuppressWarnings("unchecked")
		Iterator<CollisionObject> movingObjectsIterator = movingObjects.listIterator();
		while(movingObjectsIterator.hasNext()) {
			CollisionObject colObj = movingObjectsIterator.next();
			
			@SuppressWarnings("unchecked")
			Iterator<CollisionObject> allObjectsIterator = allObjects.listIterator();
			CollisionObject colObj2;
			while(allObjectsIterator.hasNext()) {
				colObj2 = allObjectsIterator.next();
				
				boolean objectTrysDetectionOnItself = colObj == colObj2;
				if(objectTrysDetectionOnItself) {
					continue;
				}
				
				Set<CollisionObject> visitedObjectsFromColObj = alreadyVisited.get(colObj);
				boolean colObjHasVisitedSet = visitedObjectsFromColObj != null;
				boolean detectionOnBothObjectsAlreadyHappend = colObjHasVisitedSet &&
					visitedObjectsFromColObj.contains(colObj2);
				
				if(detectionOnBothObjectsAlreadyHappend) {
					continue;
				}
				
				addObjectsToAlreadyVisitedMap(colObj, colObj2);
				
				boolean collisionBetweenObjects = colObj.doCollide(colObj2);
				if(collisionBetweenObjects) {
					CollisionEvent event = new CollisionEvent(colObj, colObj2);
					getObserver().notify(event);
				}
			}
		}
		
		alreadyVisited.clear();
	}
	
	private void addObjectsToAlreadyVisitedMap(CollisionObject colObj, CollisionObject colObj2) {
		Set<CollisionObject> visitedObjectsFromColObj = alreadyVisited.get(colObj);
		boolean colObjHasVisitedSet = visitedObjectsFromColObj != null;
		if(colObjHasVisitedSet) {
			visitedObjectsFromColObj.add(colObj2);
		}
		else {
			Set<CollisionObject> visitedSet = new HashSet<CollisionObject>();
			visitedSet.add(colObj2);
			alreadyVisited.put(colObj, visitedSet);
		}
		
		Set<CollisionObject> visitedObjectsFromColObj2 = alreadyVisited.get(colObj2);
		boolean colObj2HasVisitedSet = visitedObjectsFromColObj2 != null;
		if(colObj2HasVisitedSet) {
			visitedObjectsFromColObj2.add(colObj);
		}
		else {
			Set<CollisionObject> visitedSet = new HashSet<CollisionObject>();
			visitedSet.add(colObj);
			alreadyVisited.put(colObj2, visitedSet);
		}
	}
	
	public void add(CollisionObject colObj) {
		//TODO andreas: Um zu prüfen ob das Objekt schon aufgenommen
		//wurde muss evtl. eine effizientere Methode gefunden werden.
		if(allObjects.contains(colObj))
			return;
		
		if(colObj.isMovingObject() == true)
			this.movingObjects.add(colObj);
		
		this.allObjects.add(colObj);
	}
	
	public void remove(CollisionObject colObj) {
		if(colObj.isMovingObject() == true)
			this.movingObjects.remove(colObj);
		
		this.allObjects.remove(colObj);
	}
}
