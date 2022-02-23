package fr.aezi.othello.modele;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PionJouableTest {
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
	void testIsCaseJouableC3() {
		Case c = damier.getCoord("C3");
		assertEquals(false, jeu.isCaseJouable(c, Couleur.NOIR, Direction.S));
	}

	@Test
	void testIsCaseJouableD3() {
		Case c = damier.getCoord("D3");
		assertEquals(true, jeu.isCaseJouable(c, Couleur.NOIR, Direction.S));
	}

	@Test
	void testIsCaseJouableE3() {
		Case c = damier.getCoord("E3");
		assertEquals(false, jeu.isCaseJouable(c, Couleur.NOIR, Direction.S));
	}
	
	@Test
	void testIsCaseJouableF6() {
		Case c = damier.getCoord("F6");
		jeu.ajouterPion(damier.getCoord("C3"), Couleur.NOIR);
		assertEquals(true, jeu.isCaseJouable(c, Couleur.NOIR, Direction.NO));
	}
	
	
	@Test
	void testGetCasesJouablesNoir() {
		Set<Case> jouables = jeu.getCasesJouables(Couleur.NOIR); 
		
		assertEquals(true, jouables.contains(damier.getCoord("D3")));
		assertEquals(true, jouables.contains(damier.getCoord("C4")));
		assertEquals(true, jouables.contains(damier.getCoord("F5")));
		assertEquals(true, jouables.contains(damier.getCoord("E6")));
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
