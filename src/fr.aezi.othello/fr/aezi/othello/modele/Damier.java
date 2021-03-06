package fr.aezi.othello.modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.geometry.Point2D;

public class Damier {
	private Map<String, Case> casesParCoord = new HashMap<>();
	private List<Case> listeDeCasesParIndex = new ArrayList<>();
	private String[] lettreLignes = new String[9];
	
	public Damier(){
		int idx = 0;
		int i = 0;
		int j = 0;
		for(char ligne: "12345678".toCharArray()) {
			i++;
			j = 0;
			for(char col: "ABCDEFGH".toCharArray()) {
				j++;
				String clef = "" + col + ligne;
				Case c = new Case(clef, idx, i, j);
				casesParCoord.put(clef, c);
				listeDeCasesParIndex.add(c);
				
				//Si seconde colonne:
				if (j > 1) {c.setVoisin(Direction.O, listeDeCasesParIndex.get(c.getIndex() - 1));}
				// Si seconde ligne ou +:
				if(i > 1) {
					//Nord
					c.setVoisin(Direction.N, listeDeCasesParIndex.get(idx - 8));
					if(j > 1) {
						//Nord-Ouest
						c.setVoisin(Direction.NO, listeDeCasesParIndex.get(idx - 9));
					}
					if(j < 8) {
						//Nord-Est
						c.setVoisin(Direction.NE, listeDeCasesParIndex.get(idx - 7));
					}
				}
				idx++;
			}
		}
	}
	
	/**
	 * Récupère la case à la coordonnée donnée sous forme de String (ex: "B5")
	 * @param coord une coordonnée 
	 * @return une case
	 */
	public Case getCoord(String coord) throws IllegalArgumentException {
		if(!casesParCoord.containsKey(coord)) {
			throw new IllegalArgumentException(coord + " n'est pas une coordonnée valide.");
		}
		return casesParCoord.get(coord);
	}
	
	public Point2D getCoord(Case c, int boardWidth, int boardHeight) {
		return new Point2D((  2* c.getX() - 1) * boardWidth / 16, ( 2 * c.getY() -1) * boardHeight / 16);
	}
	
	public Point2D getCoord(String coord, int boardWidth, int boardHeight) {
		Case c = getCoord(coord);
		return getCoord(c, boardWidth, boardHeight);
	}
	
	public Set<String> getCoordSet(){
		return casesParCoord.keySet();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		for (int i = 1; i < lettreLignes.length; i++) {
			for(int j = 1 ; j < 9 ; j++) {
				String coord = lettreLignes[i]+j;
				sb.append(getCoord(coord).toString());
				if(j < 8) { sb.append(" , "); }
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}

	public String reprCoords() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for(Case c : listeDeCasesParIndex) {
			if(i % 8 == 0) {
				sb.append("\n|");
			}
			sb.append(c.getEmplacement()).append("|");
			i++;
		}
		sb.append("\n");
		return sb.toString();
	}

	
	public String reprCasesEtVoisinages() {
		StringBuffer sb = new StringBuffer();
		for(Case c : listeDeCasesParIndex) {
			sb.append(c.reprCaseEtVoisinage()).append("\n");
		}
		return sb.toString();
	}
	
	public String reprCasesEtEnvironnement() {
		StringBuffer sb = new StringBuffer();
		for(Case c : listeDeCasesParIndex) {
			sb.append(c.reprCaseEtEnvironnement()).append("\n");
		}
		return sb.toString();
	}
	
}
