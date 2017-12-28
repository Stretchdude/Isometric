package de.immerfroehlich.rendering;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;

public class IsometricConverterTest {

	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test
	public void testConvertToIsometric(){
		IsometricConverter converter = new IsometricConverter((float) (0.5 * Math.PI), 1f);
		Vector2f convertedPoint = converter.convertToIsometric(new Vector2f(2f, 4f));
		
		assertEquals(-4, convertedPoint.x, 0.01f);
		assertEquals(2, convertedPoint.y, 0.01f);
	}
	
	@Test
	public void testConvertToGrid(){
		IsometricConverter converter = new IsometricConverter((float) (0.5 * Math.PI), 1f);
		Vector2f convertedPoint = converter.convertToGrid(new Vector2f(2f, 4f));
		
		assertEquals(4, convertedPoint.x, 0.01f);
		assertEquals(-2, convertedPoint.y, 0.01f);
	}
	
	@Test
	public void testConversionInBothDirections(){
		IsometricConverter converter = new IsometricConverter((float) (0.5 * Math.PI), 1f);
		Vector2f originalPos = new Vector2f(55.3f, 75.5f);
		Vector2f isoPos = converter.convertToIsometric(originalPos);
		Vector2f backConvertedPos = converter.convertToGrid(isoPos);
		
		assertEquals(originalPos.x, backConvertedPos.x, 0.01f);
		assertEquals(originalPos.y, backConvertedPos.y, 0.01f);
	}

	@Test
	public void testStrechFactorForIso(){
		IsometricConverter converter = new IsometricConverter((float) (0.5 * Math.PI), 1.5f);
		Vector2f convertedPoint = converter.convertToIsometric(new Vector2f(4f, 0f));
		
		assertEquals(0, convertedPoint.x, 0.01f);
		assertEquals(6, convertedPoint.y, 0.01f);
		
	}

	@Test
	public void testStretchFactorToGrid(){
		IsometricConverter converter = new IsometricConverter((float) (0.5 * Math.PI), 1.5f);
		Vector2f convertedPoint = converter.convertToGrid(new Vector2f(0f, 6f));
		
		assertEquals(4, convertedPoint.x, 0.01f);
		assertEquals(0, convertedPoint.y, 0.01f);
	}
	
	@Test
	public void testOffsetToIso(){
		IsometricConverter converter = new IsometricConverter((float) (0.5 * Math.PI), 1f);
		converter.setOffset(new Vector2f(1.5f,1.5f));
		Vector2f convertedPoint = converter.convertToIsometric(new Vector2f(0f, 0f));
		
		assertEquals(1.5, convertedPoint.x, 0.01f);
		assertEquals(1.5, convertedPoint.y, 0.01f);
	}
	
	public void testOffsetToGrid() throws Exception {
		IsometricConverter converter = new IsometricConverter((float) (0.5 * Math.PI), 1f);
		converter.setOffset(new Vector2f(1.5f, 1.5f));
		Vector2f convertedPoint = converter.convertToGrid(new Vector2f(1.5f, 1.5f));
		
		assertEquals(0, convertedPoint.x, 0.01f);
		assertEquals(0, convertedPoint.y, 0.01f);
	}
	
}
