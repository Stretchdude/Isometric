package de.immerfroehlich.events;

public interface Observer {
	/**
	 * Teilt dem Observer mit, dass ein Event
	 * aufgetreten ist. Dieser kann durch die
	 * Implementierung dieser Methode entsprechend
	 * reagieren.
	 * @param event
	 * Eine konkrete Implementierung des
	 * Events (z.B. CollisionEvent, KeyPressedEvent, etc.).
	 */
	public void notify(Event event);
}
