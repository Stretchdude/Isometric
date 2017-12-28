package de.immerfroehlich.animation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.newdawn.slick.geom.Vector2f;


public class CurveTest {
	
	@Test
	public void testCalculatePoint() {
		
		Curve curve = new Curve(new Vector2f(0, 0), new Vector2f(4, 4));
		
		Vector2f firstSeg = curve.calculatePoint(0.25f);
		Vector2f firstSegExpected = new Vector2f(1, 1);
		assertEquals(firstSegExpected, firstSeg);
		
		Vector2f secondSeg = curve.calculatePoint(0.75f);
		Vector2f secondSegExpected = new Vector2f(3, 3);
		assertEquals(secondSegExpected, secondSeg);
		
		Vector2f lastSeg = curve.calculatePoint(0.75f);
		Vector2f lastSegExpected = new Vector2f(3, 3);
		assertEquals(lastSegExpected, lastSeg);
		
	}

}
