package fr.aezi.othello.modele;

import static fr.aezi.othello.modele.Couleur.BLANC;
import static fr.aezi.othello.modele.Couleur.NOIR;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.Set;

public class Jeu {
	private Damier damier ;
	private Pion[] mesPions = new Pion[64];
	private Couleur prochainJoueur = Couleur.NOIR;
	
	private Collection<Case> casesOccupees = new TreeSet<>();
	
	private List<GameListener> listeners = new LinkedList<>();
	
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
	
	public void jouer(Case c, Couleur couleur) {
		// La case est-elle jouable ?
		// sinon => exception
		if(!isCaseJouable(c, couleur)) {
			throw new IllegalArgumentException("La case "+c.getEmplacement() 
				+ " n'est pas jouable pour la couleur " + couleur);
		}
		
		ajouterPion(c, couleur);
		
		// Oui
		// Trouver tous les pions présents autour de cette case
		// Récupérer la liste des pions présents autour de cette case
		// ET qui sont d'une couleur différente
		
		Set<Case> casesARetourner = new TreeSet<>();
		
		for(Direction d: c.getDirVoisins()) {
			casesARetourner.addAll(retournementsPossibles(c, d, couleur));
			
			/*
			Couleur coulVoisin = getPion(v) != null?getPion(v).couleur:null;
			if(coulVoisin != couleur) {
				// C'est potentiellement une case à retourner
				// Je dois donc aller "regarder" dans cette direction
				// Pour voir si on rencontre un pion
				// de la couleur jouée
				
				
			}
			*/
		}
		for (Case case1 : casesARetourner) {
			getPion(case1).setCouleur(couleur);
		}
		
		Map<String, Object> properties = new HashMap<>();
		properties.put(GameEvent.DISCS_TO_TURN, casesARetourner);
		properties.put(GameEvent.PLAYED_COLOR, couleur);
		GameEvent e = new GameEvent(c, properties);
		
		dispatchEvent(e);
	}

	private void ajouterPion(String coord, Couleur couleur) {
		ajouterPion( damier.getCoord(coord), couleur );
	}
	public void ajouterPion(String coord) {
		ajouterPion(coord, prochainJoueur);
	}
	public void changerProchainJoueur() {
		prochainJoueur = Couleur.getOpposant(prochainJoueur);
		System.out.println("prochainJoueur:"+ prochainJoueur);
	}
	
	public Couleur getProchainJoueur() { return prochainJoueur; }
	
	/**
	 * Crée un Set ou ajouter toutes les les cases à retourner dans la direction
	 * donnée
	 * 
	 * @param c
	 * @param d
	 * @param couleur la couleur vers laquelle retourner. Les cases à retourner sont donc de la couleur opposée
	 * @return
	 */
	private Set<Case> retournementsPossibles(Case c, Direction d, Couleur couleur){
		Set<Case> casesARetourner = new TreeSet<>();
		while(c.hasNext(d)) {
			c = c.getVoisin(d);
			Pion disc = getPion(c);
			if(disc != null) {
				if(disc.getCouleur() == couleur) {
					//On a trouvé un pion de la même couleur, on arrête et on sort
					break;
				}
				else {
					//On ajoute le pion, puisqu'il est de couleur opposée
					casesARetourner.add(c);
				}
			}
			else { break; }
		}
		return casesARetourner;
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
	
	protected boolean isCaseJouable(Case square, Couleur couleur) {
		for (Direction dirVoisin : Direction.values()) {
			if(isCaseJouable(square, couleur, dirVoisin)) {
				return true ; // On arrête immédiatement le test
			}
		}
		return false;
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
					if(caseVoisine != null) {
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
		if(suivante == null) {
			return false;  // s'il n'y a pas de case suivante: le pion ne peut être pris
		}
		Pion pionSuivant = getPion(suivante);
		
		if(pionSuivant == null) {
			return false;  // s'il n'y a pas de suivant: le pion ne peut être pris
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
	
	public void addGameListener(GameListener l) {
		listeners.add(l);
	}
	
	public void removeGameListener(GameListener l) {
		listeners.remove(l);
	}
	
	private void dispatchEvent(GameEvent e) {
		for (GameListener l : listeners) {
			l.handle(e);
		}
	}
	
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("  |");
		for(char col: "12345678".toCharArray()) {
			sb.append(col).append("|");
		};
		for(char line : "ABCDEFGH".toCharArray()) {
			sb.append("\n").append(line).append(" |");
			for(char col: "12345678".toCharArray()) {
				Pion p = getPion(damier.getCoord(""+line+col));
				if(p != null) {
					sb.append(p.getCouleur().nomCourt()).append("|");
					
				}
				else {
					sb.append(" |");
				}
			}
		}
		return sb.toString();
	}
}
