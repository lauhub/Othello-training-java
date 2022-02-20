package fr.aezi.othello.modele;

import static fr.aezi.othello.modele.Couleur.BLANC;
import static fr.aezi.othello.modele.Couleur.NOIR;

import java.util.Collection;
import java.util.TreeSet;
import java.util.Set;

public class Jeu {
	private Damier damier ;
	private Pion[] mesPions = new Pion[64];
	private Couleur prochainJoueur = Couleur.NOIR;
	
	private Collection<Case> casesOccupees = new TreeSet<>();
	
	public Jeu(Damier damier) {
		this.damier = damier;
		ajouterPion("D4", BLANC);
		ajouterPion("E5", BLANC);
		ajouterPion("E4", NOIR);
		ajouterPion("D5", NOIR);
	}
	
	public void ajouterPion(Case c, Couleur couleur) {
		mesPions[c.getIndex()] = new Pion(couleur);
		casesOccupees.add(c);
	}
	private void ajouterPion(String coord, Couleur couleur) {
		ajouterPion( damier.getCoord(coord), couleur );
	}
	public void ajouterPion(String coord) {
		ajouterPion(coord, prochainJoueur);
	}
	public void changerProchainJoueur() {
		prochainJoueur = Couleur.getOpposant(prochainJoueur);
	}
	
	private Set<Case> retournementsPossibles(Case c, Direction d, Couleur coulCherchee){
		Set<Case> casesARetourner = new TreeSet<>();
		while(c.hasNext(d)) {
			
		}
		return null;
	}
	
	public Collection<Case> getCasesOccupees() {
		return casesOccupees;
	}
	
	public Pion getPion(Case c) {
		return mesPions[c.getIndex()];
	}
	
	public void jouer(Case c, Couleur couleur) {
		// La case est-elle jouable ?
		// sinon => exception
		
		// Oui
		// Trouver tous les pions présents autour de cette case
		// Récupérer la liste des pions présents autour de cette case
		// ET qui sont d'une couleur différente
		
		Set<Case> casesARetourner = new TreeSet<>();
		
		for(Direction d: c.getDirVoisins()) {
			Case v = c.getVoisin(d);
			Couleur coulVoisin = getPion(v) != null?getPion(v).couleur:null;
			if(coulVoisin != couleur) {
				// C'est potentiellement une case à retourner
				// Je dois donc aller "regarder" dans cette direction
				// Pour voir si on rencontre un pion
				// de la couleur jouée
				
			}
		}
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < mesPions.length; i++) {
			if(i > 0 && i % 8 == 0) {
				s += "|\n";
			}
			s = s + "|" + ( mesPions[i] == null?" ":mesPions[i].toString());
		}
		s += "|\n";
		return s;
	}
}
