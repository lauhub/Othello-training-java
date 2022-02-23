package fr.aezi.othello.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.aezi.othello.modele.Case;
import fr.aezi.othello.modele.Couleur;
import fr.aezi.othello.modele.Damier;
import fr.aezi.othello.modele.Direction;
import fr.aezi.othello.modele.Jeu;

public class RetournementPionsTest {
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
	void testGetCasesRetourneesApresE6() {
		Case square = damier.getCoord("E6");
		Set<Case> retournements = jeu.retournementsPossibles(square, Direction.N, Couleur.NOIR);
		assertEquals(true, retournements.contains(damier.getCoord("E5")));
		assertEquals(1, retournements.size());
	}
	
	@Test
	void testGetCasesRetourneesApresE5() {
		Case square = damier.getCoord("F5");
		Set<Case> retournements = jeu.retournementsPossibles(square, Direction.O, Couleur.NOIR);
		assertEquals(true, retournements.contains(damier.getCoord("E5")));
		assertEquals(1, retournements.size());
	}
	
	
	@Test
	void testGetCasesRetourneesApresE5N_E5B() {
		jeu.jouer(damier.getCoord("F5"), Couleur.NOIR);
		Case square = damier.getCoord("F4");
		Set<Case> retournements = jeu.retournementsPossibles(square, Direction.O, Couleur.BLANC);
		assertEquals(true, retournements.contains(damier.getCoord("E4")));
		assertEquals(1, retournements.size());

		retournements = jeu.retournementsPossibles(square, Direction.SO, Couleur.BLANC);
		assertEquals(0, retournements.size());
	}

}
