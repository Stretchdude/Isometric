package de.immerfroehlich.events;

public abstract class Subject {
	
	private Observer observer;
	
	/**
	 * Registriert einen Observer an diesem
	 * Subject.
	 * @param observer
	 * Der Observer der registriert werden soll.
	 */
	final public void register(Observer observer) {
		this.observer = observer;
	}
	
	/**
	 * Wird von der implementierenden Klasse aufgerufen,
	 * um Zugriff auf den Observer zu erhalten.
	 * @return
	 * Der Observer auf den zugegriffen werden soll.
	 */
	final protected Observer getObserver() {
		return observer;
	}
}
