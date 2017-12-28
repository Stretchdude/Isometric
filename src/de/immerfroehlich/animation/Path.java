package de.immerfroehlich.animation;

import java.util.ArrayList;
import java.util.List;

public final class Path {
	
	private List<Curve> curves = new ArrayList<Curve>();
	
	private Curve currentCurve;
	
	private boolean loop;
	
	public void add(Curve curve) {
		curves.add(curve);
	}

	public List<Curve> getCurves() {
		return curves;
	}

	Curve getCurrentCurve() {
		return currentCurve;
	}

	/**
	 * Liefert die nächste Curve und den internen
	 * Zustand auf die nächste Curve.
	 * @return
	 * Die nächste Curve.
	 */
	Curve next() {
		int number = curves.indexOf(currentCurve);
		number++;
		
		if(number > (curves.size() -1)) {
			if(loop) {
				number = 0;
			}
			else {
				return null;
			}
		}
		
		
		currentCurve = curves.get(number);
		return currentCurve;
	}

	public boolean isLoop() {
		return loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	
	public void close() {
		this.currentCurve = curves.get(0);
	}
	
	
}
