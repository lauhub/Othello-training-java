package fr.aezi.othello.modele;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	void testGetCasesRetourneesApresE5() {
		Set<Case> retournements = jeu.retournementsPossibles(damier.getCoord("E5"), Direction.O, Couleur.NOIR);
		assertEquals(true, retournements.contains(damier.getCoord("E4")));
	}
	
	
	@Test
	void testGetCasesJouablesApresE6Noir() {
		jeu.jouer(damier.getCoord("E6"), Couleur.NOIR);
		jeu.getPion(damier.getCoord("E5")).setCouleur(Couleur.NOIR);
		Set<Case> jouables = jeu.getCasesJouables(Couleur.BLANC);
		System.out.println(jeu);
		
		assertEquals(false, jouables.contains(damier.getCoord("D3")));
		assertEquals(false, jouables.contains(damier.getCoord("C4")));
		assertEquals(false, jouables.contains(damier.getCoord("F5")));
		assertEquals(false, jouables.contains(damier.getCoord("E6")));
		assertEquals(true, jouables.contains(damier.getCoord("D6")));
		assertEquals(false, jouables.contains(damier.getCoord("E3")));
		assertEquals(false, jouables.contains(damier.getCoord("F3")));
		assertEquals(true, jouables.contains(damier.getCoord("F4")));
		assertEquals(true, jouables.contains(damier.getCoord("F6")));
	}

}
