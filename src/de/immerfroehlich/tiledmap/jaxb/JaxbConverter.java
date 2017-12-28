package de.immerfroehlich.tiledmap.jaxb;

import java.io.File;

import javax.xml.bind.JAXB;

import de.immerfroehlich.tiledmap.jaxb.model.Map;

public class JaxbConverter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map map = JAXB.unmarshal(new File("res/embedimage.tmx"), Map.class);
		System.out.println();
	}

}
