package fr.aezi.othello.tests;

import fr.aezi.othello.modele.Case;
import fr.aezi.othello.modele.Couleur;
import fr.aezi.othello.modele.Damier;
import fr.aezi.othello.modele.Direction;
import fr.aezi.othello.modele.Jeu;
import fr.aezi.othello.modele.Pion;

public class TesterDamier {
	
	public static void main(String[] args) {
		Damier damier = new Damier();
		
		
		Case a4 = damier.getCoord("A4");
		System.out.println(a4);
		
		damier.getCoord("H8");
		//System.out.println(damier);
		
		//System.out.println(Direction.SO + " "+ Direction.SO.getRepresentation());
		
		System.out.println("-------------------------");
		//System.out.println(damier.getCasesEtEnvironnement());
		
		
		
		// Récupérer la liste des voisins en partant de A1 dans la direction SE
		Case voisine = damier.getCoord("A1");
		while(voisine != null) {
			System.out.println(voisine);
			voisine = voisine.getVoisin(Direction.S);
		}
		
		
		Pion p = new Pion(Couleur.BLANC);
		System.out.println(p);
		Jeu jeu = new Jeu(damier);
		System.out.println(jeu);
		System.out.println("-------------------------");
		System.out.println(damier.reprCoords());
		System.out.println("Voisin sud de A4:"+a4.getVoisin(Direction.S)) ;
		System.out.println("Voisin nord de A4:"+a4.getVoisin(Direction.N)) ;
		System.out.println("Voisin sud de D1:"+damier.getCoord("D1").getVoisin(Direction.N)) ;
		System.out.println("Voisin ouest de D1:"+damier.getCoord("D1").getVoisin(Direction.O)) ;
	}

}
