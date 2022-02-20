package fr.aezi.othello.modele;

import java.util.Map;

public class GameEvent {
	
	public static final String DISCS_TO_TURN = "disc.to.turn";
	public static final String PLAYED_COLOR = "played.color";
	
	private Object source;
	private Map<String, Object> properties;
	public GameEvent(Object source, Map<String, Object> properties) {
		this.properties = properties;
		this.source = source;
	}
	
	public Object getProperty(String name) {
		return properties.get(name);
	}
	public Object getSource() { return source; }

}
