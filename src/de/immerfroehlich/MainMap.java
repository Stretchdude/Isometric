package de.immerfroehlich;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import de.immerfroehlich.animation.Curve;
import de.immerfroehlich.animation.Path;
import de.immerfroehlich.animation.PathAnimator;
import de.immerfroehlich.collision.CollisionDetection;
import de.immerfroehlich.events.EventHandler;
import de.immerfroehlich.model.Drawable;
import de.immerfroehlich.model.EntityName;
import de.immerfroehlich.model.GameMap;
import de.immerfroehlich.model.Layer;
import de.immerfroehlich.model.VisibleCollisionObject;
import de.immerfroehlich.rendering.GridConverter;
import de.immerfroehlich.rendering.IsometricConverter;
import de.immerfroehlich.rendering.RenderPipeline;
import de.immerfroehlich.rendering.View;
import de.immerfroehlich.rendering.VisibilityChecker;
import de.immerfroehlich.scripting.GameMapFacade;
import de.immerfroehlich.tiledmap.GameMapLoader;
import de.immerfroehlich.tiledmap.ResourceManager;

public class MainMap extends BasicGame {

   private IsometricConverter isoConverter;

   private GridConverter gridConverter;

   private RenderPipeline renderPipeline;

   private GameMap gameMap;

   private View view;

   private VisibilityChecker visibilityChecker;

   private final float playerSpeed = 0.2f;

   private VisibleCollisionObject player;

   private CollisionDetection collDetection;

   private PathAnimator pathAnimator;

   private VisibleCollisionObject movingEntity;
   
   private ResourceManager resourceManager;
   
   private GameMapLoader gameMapLoader;

   public MainMap(String title) {
      super(title);

      isoConverter = new IsometricConverter((float) (0.25 * Math.PI), 0.5f);
      // Vector2f offset = new Vector2f(-32f, 0f);
      // isoConverter.setOffset(offset);
   }

   /**
    * @param args
    * @throws SlickException
    */
   public static void main(String[] args) throws SlickException {
      try {
         AppGameContainer app = new AppGameContainer(new MainMap("IsoTest"));
         app.start();
      } catch (SlickException e) {
         e.printStackTrace();
      }
   }

   @Override
   public void render(GameContainer arg0, Graphics arg1) throws SlickException {
      renderPipeline.render(this.visibleElements);
   }

   @Override
   public void init(GameContainer arg0) throws SlickException {
      // TODO Screensize aus slick holen
      Vector2f screenSize = new Vector2f(640, 480);
      view = new View(screenSize);
      view.setPosition(new Vector2f(0, 0));
      
      //TODO Temporär zum Testen einen Spieler-Sprite laden
      Image playerImage = new Image("res/luigi.png");
      Vector2f viewPos = view.getPosition();
      Shape playCollShape = new Circle(viewPos.x, viewPos.y, 5);
      EntityName playerName = new EntityName("player");
      player = new VisibleCollisionObject(playCollShape, 1, true, new Vector2f(65f, 90f), playerImage, playerName);
      
      //Pathanimator initializieren
      pathAnimator = new PathAnimator();
      //TODO Zweite Figur auch wieder entfernen und vereinheitlichen.
      Shape movEntCollShape = new Circle(viewPos.x, viewPos.y, 5);
      EntityName movEntName = new EntityName("movingEntity");
      movingEntity = new VisibleCollisionObject(movEntCollShape, 1, true, new Vector2f(65f, 90f), playerImage, movEntName);
      Path movPath = new Path();
      movPath.add(new Curve(new Vector2f(0, 0), new Vector2f(130, 130)));
      movPath.add(new Curve(new Vector2f(130, 130), new Vector2f(230, 130)));
      movPath.add(new Curve(new Vector2f(230, 130), new Vector2f(0, 0)));
      movPath.setLoop(true);
      movPath.close();
      pathAnimator.add(movPath, movingEntity);
      
      //Kollisionserkennung initialisieren und Listener registrieren
      collDetection = new CollisionDetection();
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine scriptEngine = manager.getEngineByName("JavaScript");
      resourceManager = new ResourceManager();
      EventHandler eventHandler = new EventHandler(resourceManager, scriptEngine);
      collDetection.register(eventHandler);
      
      //Karte laden
      gameMapLoader = new GameMapLoader(resourceManager, collDetection);
      gameMap = gameMapLoader.load("start_map");
      gameMapLoader.loadGlobalResources();
      
      
      //Scripting initialisieren
      scriptEngine.put("collisionDetection", collDetection);
      GameMapFacade gameMapFacade = new GameMapFacade(gameMap, collDetection, resourceManager, gameMapLoader);
      scriptEngine.put("map", gameMapFacade);
      
      //TODO Temporär den Spieler und die bewegende Figur zum Objektlayer hinzufügen.
      //Später soll das beim auslesen der Karte passieren.
      gameMap.addEntity(player);
      gameMap.addEntity(movingEntity);
      collDetection.add(player); //temporär
      collDetection.add(movingEntity);
      
            

      gridConverter = new GridConverter(view, gameMap.getGridElementWidth());

      visibilityChecker = new VisibilityChecker(gameMap, view, gridConverter, isoConverter);

      renderPipeline = new RenderPipeline(gameMap, view, gridConverter, isoConverter);
      
      this.visibleElements = visibilityChecker.findAllVisibleElements();
   }

   Vector2f oldView = new Vector2f();

private List<Drawable> visibleElements;

   @Override
   public void update(GameContainer container, int delta) throws SlickException {
	  collDetection.checkCollisions();  
	   
      Input input = container.getInput();
      if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

         int mouseX = input.getMouseX();
         int mouseY = input.getMouseY();
         Vector2f viewPos = view.getPosition();
         Vector2f mousePos = new Vector2f(mouseX + viewPos.x, mouseY + viewPos.y);

         Vector2f gridPos = isoConverter.convertToGrid(mousePos);
         Vector2f arrayPos = gridConverter.convertToArrayField(gridPos);

         Layer layer = gameMap.getTileLayer().get(0);

         if ((int) arrayPos.x < layer.drawables.length && (int) arrayPos.x >= 0
               && (int) arrayPos.y < layer.drawables.length && (int) arrayPos.y >= 0) {

            Drawable drawable = layer.drawables[(int) arrayPos.x][(int) arrayPos.y];
            if (drawable.getImage().getAlpha() == 0.5f) {
               drawable.getImage().setAlpha(1);
            } else {
               drawable.getImage().setAlpha(0.5f);
            }

         }

      }

      // View bewegen
      // TODO: Das soll später in ein Skript
      // ausgelagert werden
      Vector2f playerPos = player.getPosition();
      float playerX = playerPos.x;
      float playerY = playerPos.y;
      if (input.isKeyDown(Input.KEY_LEFT)) {
         playerX += playerSpeed * -delta;
      }
      if (input.isKeyDown(Input.KEY_UP)) {
         playerY += playerSpeed * -delta;
      }
      if (input.isKeyDown(Input.KEY_RIGHT)) {
         playerX += playerSpeed * delta;
      }
      if (input.isKeyDown(Input.KEY_DOWN)) {
         playerY += playerSpeed * delta;
      }
      playerPos = new Vector2f(playerX, playerY);

      player.setPosition(playerPos);
      playerPos = isoConverter.convertToIsometric(player.getPosition());
      float viewPosX = playerPos.x - (view.getSize().x / 2);
      float viewPosY = playerPos.y - (view.getSize().y / 2);
      view.setPosition(new Vector2f(viewPosX, viewPosY));
      
      pathAnimator.updateContainedEntityPositionsOffContainedPaths(delta);
      
      this.visibleElements = visibilityChecker.findAllVisibleElements();
   }

}
