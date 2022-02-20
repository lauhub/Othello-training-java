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
	
	protected Set<Case> getCasesJouables(Couleur couleur){
		/*
		 * Les cases jouables sont en fonction de la couleur
		 * du prochain joueur et des cases libres adjacentes à ses pions
		 * 
		 */
		Set<Case> jouables = new TreeSet<>();
		
		for(Case square: casesOccupees) {
			if(!getPion(square).getCouleur().equals(couleur)) {
				// On cherche les cases libres adjacentes pour lesquelles
				// on pourrait jouer
				for(Direction dirVoisin: Direction.values()) {
					Case caseVoisine = square.getVoisin(dirVoisin); 
					if(getPion(caseVoisine) == null) {
						//La case est libre
						//Verifions si elle est jouable pour le joueur courant
						if(isCaseJouable(caseVoisine, couleur, dirVoisin.getOppose())) {
							jouables.add(caseVoisine);
						}
					}
				}
			}
		}
		
		return jouables;
	}
	

	
	public Set<Case> getCasesJouables(){
		/*
		 * Les cases jouables sont en fonction de la couleur
		 * du prochain joueur et des cases libres adjacentes à ses pions
		 * 
		 */
		Set<Case> jouables = new TreeSet<>();
		
		for(Case square: casesOccupees) {
			if(!getPion(square).getCouleur().equals(prochainJoueur)) {
				// On cherche les cases libres adjacentes pour lesquelles
				// on pourrait jouer
				for(Direction dirVoisin: Direction.values()) {
					Case caseVoisine = square.getVoisin(dirVoisin); 
					if(getPion(caseVoisine) == null) {
						//La case est libre
						//Verifions si elle est jouable pour le joueur courant
						if(isCaseJouable(caseVoisine, prochainJoueur, dirVoisin.getOppose())) {
							jouables.add(caseVoisine);
						}
					}
				}
			}
		}
		
		return jouables;
	}
	
	protected boolean isCaseJouable(Case square, Couleur couleur, Direction direction) {
		//Cherche si la case passée en paramètre est jouable
		// dans la direction donnée pour la couleur donnée
		// Pour cela, il faut que la case suivante soit de la couleur
		// opposée et qu'un pion de la couleur donnée soit
		// sur la même direction
		Case suivante = square.getVoisin(direction);
		Pion pionSuivant = getPion(suivante);
		if(pionSuivant != null && pionSuivant.getCouleur().isOppose(couleur)) {
			return peutEtrePris(suivante, couleur, direction);
		}
		else {
			return false;
		}
	}
	
	protected boolean peutEtrePris(Case square, Couleur couleur, Direction direction) {
		Case suivante = square.getVoisin(direction);
		Pion pionSuivant = getPion(suivante);
		
		if(pionSuivant == null) {
			return false; // s'il n'y a pas de suivant: le pion ne peut être pris
		}
		
		if(pionSuivant.getCouleur().equals(couleur)) {
			return true; // Le pion suivant est de la couleur cherchée
			// donc dans cette direction
			// on peut prendre
		}
		else {
			return peutEtrePris(suivante, couleur, direction);
		}
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
