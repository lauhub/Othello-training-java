package fr.aezi.othello.modele;

import static fr.aezi.othello.modele.Couleur.NOIR;

public class Pion {
	Couleur couleur;
	
	public Pion() {
		couleur = NOIR;
	}
	public Pion(Couleur c) {
		couleur = c;
	}
	public void setCouleur(Couleur c) {
		couleur = c;
	}
	public void setCouleur(String s) {
		couleur = Couleur.getCouleur(s);
	}
	public Couleur getCouleur() {
		return couleur;
	}
	
	public String toString() {
		if(couleur.equals(NOIR)) {
			return "N";
		} 
		else {
			return "B";
		}
	}
}
