package de.immerfroehlich.events;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import de.immerfroehlich.tiledmap.ResourceManager;

public class EventHandler implements Observer {

   private ScriptEngine scriptEngine;
   
   private ResourceManager resourceManager;
   
   public EventHandler(ResourceManager resourceManager, ScriptEngine scriptEngine) {
	   this.resourceManager = resourceManager;
	   this.scriptEngine = scriptEngine;
   }

   @Override
   public void notify(Event event) {
      if (event.getClass() == CollisionEvent.class) {
         CollisionEvent colEvent = (CollisionEvent) event;
         System.out.println("Collision between " + colEvent.getMovingEntity() + " and " + colEvent.getStaticEntity());

         scriptEngine.put("entity1", colEvent.getMovingEntity());
         scriptEngine.put("entity2", colEvent.getStaticEntity());
         try {
        	 //TODO Die Events sollten evtl. nicht direkt von den
        	 //Skripts wissen. Ich stelle mir da vielmehr einen
        	 //ScriptHandler vor, der die Scripte mit den jeweiligen
        	 //Events verknüpft. Hier werden sie dann nur noch
        	 //ausgeführt. -> ?????? Wirklich nötig?
        	 //TODO Der Levelname muss irgendwoher bezogen werden. -> Über das event?
        	 //TODO Die Verknüpfung von Scripten mit Events muss in einer Konfigurationsdatei
        	 //passieren. Um sie zuordnen zu können braucht man die Parameter
        	 //Kartenname und beide Entities
        	 //Für globale Scripte natürlich nur die Entities.
             scriptEngine.eval(resourceManager.getGlobalScript("collisionScript"));
         } catch (ScriptException e) {
            e.printStackTrace();
         }
      }

   }

}
