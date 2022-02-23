package fr.aezi.othello.modele;

import java.util.Map;

public class GameEvent {
	public enum PropKeys {
		DISCS_TO_TURN, PLAYED_COLOR, NEXT_PLAYER, PLAYER_MUST_PASS;
		
	}
	
	private Object source;
	private Map<PropKeys, Object> properties;
	public GameEvent(Object source, Map<PropKeys, Object> properties) {
		this.properties = properties;
		this.source = source;
	}
	
	public Object getProperty(PropKeys name) {
		return properties.get(name);
	}
	public boolean hasProperty(PropKeys name) {
		return properties.containsKey(name);
	}
	public Object getSource() { return source; }

}
