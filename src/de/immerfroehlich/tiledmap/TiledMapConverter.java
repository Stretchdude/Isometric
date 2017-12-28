package de.immerfroehlich.tiledmap;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.newtiled.MapNotLoadedException;
import org.newdawn.slick.opengl.PNGImageData;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import de.immerfroehlich.collision.CollisionDetection;
import de.immerfroehlich.model.CollisionObject;
import de.immerfroehlich.model.EntityName;
import de.immerfroehlich.model.GameMap;
import de.immerfroehlich.model.Layer;
import de.immerfroehlich.model.LayerName;
import de.immerfroehlich.model.Tile;

public class TiledMapConverter {
	
	private int mapWidth;
	private int mapHeight;
	private int tileWidth;
	private int tileHeight;
	
	/**
	 * Gibt die Breite eines Quadrates im
	 * Grid an. Das Grid ist nicht isometrisch.
	 * Da es sich hier um ein Quadrat handelt
	 * ist es mit der Höhe identisch.
	 */
	private float gridElementWidth;
	
	private SpriteSheet spriteSheet;
	private Image spriteSheetImage;
	private GameMap gameMap;
	private CollisionDetection collDetection;
	
	public TiledMapConverter(GameMap gameMap, Image image, CollisionDetection collDetection){
		this.gameMap = gameMap;
		this.spriteSheetImage = image;
		//TODO andreas: Nur übergangsweise später sollen die
		//Kollisionsobjekte ähnlich wie beim Visibility-Check
		//herausgefunden werden, damit nicht immer sämtliche
		//Objekte geprüft werden müssen.
		this.collDetection = collDetection;
	}

	public void loadXml(String ref) throws MapNotLoadedException {
		ref = ref.replace('\\','/');
		load(ResourceLoader.getResourceAsStream(ref));
	}

	public void load(InputStream in) throws MapNotLoadedException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new MapNotLoadedException();
		}
		builder.setEntityResolver(new EntityResolver() {
			public InputSource resolveEntity(String publicId,
					String systemId) throws SAXException, IOException {					
				return new InputSource(new ByteArrayInputStream(new byte[0]));
			}
		});
		
		Document doc;
		try {
			doc = builder.parse(in);
		} catch (SAXException e) {
			throw new MapNotLoadedException();
		} catch (IOException e) {
			throw new MapNotLoadedException();
		}
		Element docElement = doc.getDocumentElement();
		
		mapWidth = Integer.parseInt(docElement.getAttribute("width"));
		mapHeight = Integer.parseInt(docElement.getAttribute("height"));
		tileWidth = Integer.parseInt(docElement.getAttribute("tilewidth"));
		tileHeight = Integer.parseInt(docElement.getAttribute("tileheight"));
		
		//Errechnet die Seitenlänge a des Grids,
		//aus der Tilebreite.
		//Ist nichts weiter als die nach a
		//aufgelöste Formel von Pythagoras
		//mit a = b, also a² + a² = c²
		float gridWidth = tileWidth * tileWidth;
		gridWidth = gridWidth / 2;
		this.gridElementWidth = (float) Math.sqrt(gridWidth);
		
		//TODO Spritesheet später evtl nicht mehr nötig.
		spriteSheet = new SpriteSheet(spriteSheetImage, tileWidth, tileHeight);
		
		//TODO Map Properties einlesen?
		
		//TODO Tileset einlesen
		//createTileSet(docElement);
		
		NodeList layerNodes = docElement.getElementsByTagName("layer");
		for (int i=0; i<layerNodes.getLength(); i++) {
			Element current = (Element) layerNodes.item(i);
			Layer layer = createLayer(i, current);
			
			gameMap.put(i, layer);
		}
		
		gameMap.setTileWidth(tileWidth);
		gameMap.setTileHeight(tileHeight);
		gameMap.setGridElementWidth(this.gridElementWidth);
	}

	private void createTileSet(Element docElement) {
		Element tileset = (Element) docElement.getElementsByTagName("tileset").item(0);
		Element tile = (Element) tileset.getElementsByTagName("tile").item(0);
		Element image = (Element) tile.getElementsByTagName("image").item(0);
		Element imData = (Element) image.getElementsByTagName("data").item(0);
		Node imDataNode = imData.getFirstChild();
		char[] imEnc = imDataNode.getNodeValue().trim().toCharArray();
		byte[] imDec = Base64Decoder.decode(imEnc);
		InputStream imStream = new ByteArrayInputStream(imDec);
		PNGImageData pngData = new PNGImageData();
		try {
			pngData.loadImage(imStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
		
		Image slickImage = new Image(pngData);
		System.out.println();
	}
	
	private Layer createLayer(int layerNumber, Element element) {
		//TODO Read Layer Properties?
		int height = this.mapHeight;
		int width = this.mapWidth;
		int spriteSheetWidth = spriteSheet.getWidth() / tileWidth;

		Element dataNode = (Element) element.getElementsByTagName("data").item(
				0);
		String encoding = dataNode.getAttribute("encoding");
		String compression = dataNode.getAttribute("compression");

		if (!encoding.equals("base64") || !compression.equals("gzip")) {
			//TODO Richtige Exception werfen.
			throw new RuntimeException();
		}
		
		Layer layer = new Layer(width, height);
		
		try {
			Node cdata = dataNode.getFirstChild();
			char[] enc = cdata.getNodeValue().trim().toCharArray();
			byte[] dec = Base64Decoder.decode(enc);
			GZIPInputStream gz = new GZIPInputStream(
					new ByteArrayInputStream(dec));

			byte[] buf = new byte[height * width * 4];
			int bytesRead;
			int offset = 0;
			int remaining = height * width * 4;

			do {
				bytesRead = gz.read(buf, offset, remaining);

				if (bytesRead == 0) {
					throw new RuntimeException("Read 0 bytes from tiled map data stream");
				}

				if (bytesRead != -1) {
					offset += bytesRead;
					remaining -= bytesRead;
				}
			} while (bytesRead!=-1 && remaining>0); 

			ByteArrayInputStream is = new ByteArrayInputStream(buf);
			
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int tileId = 0;
					tileId |= is.read();
					tileId |= is.read() << 8;
					tileId |= is.read() << 16;
					tileId |= is.read() << 24;

					int spriteX = tileId % spriteSheetWidth;
					int spriteY = tileId / spriteSheetWidth;
					if(spriteX == 0) {
						spriteX += spriteSheetWidth;
						spriteY--;
					}
					spriteX--;
//					spriteY--;
					
					if(tileId == 0) {
						layer.put(x, y, null);
						continue;
					}
					
					
					float xGridPos = x * gridElementWidth;
					float yGridPos = y * gridElementWidth;
					Vector2f position = new Vector2f(xGridPos, yGridPos);
					
					//TODO Die Kollisionslayer irgendwie bezeichenen
					//möglicherweise total überarbeiten.
					//Evtl. sollte noch ein CollsionObject Interface
					//eingeführt werden, dass entsprechende Entitäten
					//implementieren. So lässt sich dann aus der Kollisionserkennung
					//leichter eine Entity entfernen.
					if(layerNumber == LayerName.COLLISION.ordinal()
							&& (tileId == 12 || tileId == 13)) {
						Shape shape = new Rectangle(xGridPos, yGridPos, gridElementWidth, gridElementWidth);
						EntityName name = CollisionObject.createUniqueName();
						CollisionObject colObj = new CollisionObject(shape, layerNumber, false, new Vector2f(0f, 0f), name);
						collDetection.add(colObj);
					}
					else {
						Image image = spriteSheet.getSprite(spriteX, spriteY);
						Vector2f isoImageOffset = new Vector2f(tileWidth / 2f, 0f);
						EntityName name = Tile.createUniqueName();
						Tile tile = new Tile(position, 0, image, isoImageOffset, name);
						layer.put(x, y, tile);
					}
				}
			}
			
			System.out.println("fertig");
		} catch (IOException e) {
			//TODO Richtige Exception werfen.
			throw new RuntimeException();
		}

		return layer;
	}

}
