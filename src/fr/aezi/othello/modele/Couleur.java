package fr.aezi.othello.modele;

public enum Couleur {
	NOIR("N"), BLANC("B");
	private String court;
	private Couleur(String nomCourt) {
		court = nomCourt;
	}
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
	
	public boolean isOppose(Couleur c) {
		return c != this;
	}
	
	public static Couleur getOpposant(Couleur c) {
		if(c == NOIR) {
			return BLANC;
		}
		else {
			return NOIR;
		}
	}
	public String nomCourt() {
		return court;
	}
}
