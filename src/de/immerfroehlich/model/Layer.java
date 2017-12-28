package de.immerfroehlich.model;


public class Layer {
	
	public Drawable[][] drawables;
	
	public Layer(int width, int height) {
		drawables = new Drawable[width][height];
	}

	public void put(int xPos, int yPos, Drawable drawable){
		drawables[xPos][yPos] = drawable;
	}

}
