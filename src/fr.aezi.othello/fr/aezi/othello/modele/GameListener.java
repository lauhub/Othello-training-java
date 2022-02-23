package fr.aezi.othello.modele;

import java.util.EventListener;

public interface GameListener extends EventListener {
	void handle(GameEvent e);
}
