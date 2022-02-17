package fr.aezi.othello.modele;

public enum Couleur {
	NOIR, BLANC;
	
	public static Couleur getCouleur(String s) {
		if(s == null) {
			throw new IllegalArgumentException("ne peut être null");
		}
		if("b".equalsIgnoreCase(s) || "blanc".equalsIgnoreCase(s)) {
			return BLANC;
		}
		if("n".equalsIgnoreCase(s) || "noir".equalsIgnoreCase(s)) {
			return NOIR;
		}
		throw new IllegalArgumentException("couleur invalide");
	}
}
