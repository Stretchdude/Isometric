package de.immerfroehlich.events;

import de.immerfroehlich.model.Entity;

public class CollisionEvent implements Event {
	
	private Entity movingEntity;
	private Entity staticEntity;

	public CollisionEvent(Entity movingEntity, Entity staticEntity) {
		this.movingEntity = movingEntity;
		this.staticEntity = staticEntity;
	}
	
	public Entity getMovingEntity(){return movingEntity;}
	public Entity getStaticEntity(){return staticEntity;}

}
