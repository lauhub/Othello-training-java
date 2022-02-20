package fr.aezi.othello.modele;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CaseVoisinesTest {
	protected Jeu jeu;
	protected Damier damier ;
	
	/**
	 * Méthode d'initialisation: jouée avant chaque méthode
	 */
	@BeforeEach
	public void initJeu() {
		damier = new Damier();
		jeu = new Jeu(damier);
	}
	
	@Test
	void testGetCasesVoisineSudD4() {
		Case c = damier.getCoord("D4");
		assertEquals(damier.getCoord("D5"), c.getVoisin(Direction.S));
	}

	@Test
	void testIsCasesVoisineEstD4() {
		Case c = damier.getCoord("D4");
		assertEquals(damier.getCoord("E4"), c.getVoisin(Direction.E));
	}
	@Test
	void testGetCasesVoisineSudE4() {
		Case c = damier.getCoord("E4");
		assertEquals(damier.getCoord("E5"), c.getVoisin(Direction.S));
	}


}
