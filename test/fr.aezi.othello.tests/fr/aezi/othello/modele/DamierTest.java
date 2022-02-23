package fr.aezi.othello.modele;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DamierTest {
	protected Damier damier ;
	
	/**
	 * Méthode d'initialisation: jouée avant chaque méthode
	 */
	@BeforeEach
	public void initJeu() {
		damier = new Damier();
	}
	
	@Test
	void caseA1index() {
		Case c = damier.getCoord("A1");
		assertEquals(0, c.getIndex());
	}
	@Test
	void caseH8index() {
		Case c = damier.getCoord("H8");
		assertEquals(63, c.getIndex());
	}
	@Test
	void caseH4index() {
		Case c = damier.getCoord("H4");
		assertEquals(31, c.getIndex());
	}
	@Test
	void caseH1index() {
		Case c = damier.getCoord("H1");
		assertEquals(7, c.getIndex());
	}
	@Test
	void caseH1column() {
		Case c = damier.getCoord("H1");
		assertEquals(8, c.getX());
	}
	@Test
	void caseH1line() {
		Case c = damier.getCoord("H1");
		assertEquals(1, c.getY());
	}

	@Test
	void caseA1column() {
		Case c = damier.getCoord("A1");
		assertEquals(1, c.getX());
	}
	@Test
	void caseA1line() {
		Case c = damier.getCoord("A1");
		assertEquals(1, c.getY());
	}


	@Test
	void caseH8column() {
		Case c = damier.getCoord("H8");
		assertEquals(8, c.getX());
	}
	@Test
	void caseH8line() {
		Case c = damier.getCoord("H8");
		assertEquals(8, c.getY());
	}

	@Test
	void caseH4column() {
		Case c = damier.getCoord("H4");
		assertEquals(8, c.getX());
	}
	@Test
	void caseH4line() {
		Case c = damier.getCoord("H4");
		assertEquals(4, c.getY());
	}
	@Test
	void caseD5column() {
		Case c = damier.getCoord("D5");
		assertEquals(4, c.getX());
	}
	@Test
	void caseD5line() {
		Case c = damier.getCoord("D5");
		assertEquals(5, c.getY());
	}
}
