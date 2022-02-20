package fr.aezi.othello.modele;

import java.util.HashMap;
import java.util.Map;

public class Case implements Comparable<Case>{
	private String emplacement ;
	private int index;
	private int lig, col;
	
	Case(String emplacement, int index, int lig, int col) {
		this.emplacement = emplacement;
		this.index = index;
		this.lig = lig;
		this.col = col;
		
	}
	
	public int getIndex() {return index;}
	public int getX() {return lig;}
	public int getY() {return col;}
	public String toString() {
		return emplacement + "("+index+"/"+lig+","+col+")";
	}

	Map<Direction, Case> voisinsParDir = new HashMap<>();

	public void setVoisin(Direction d, Case v) {
		// avec une condition que ce voisin n'est pas déjà défini
		if(!voisinsParDir.containsKey(d)) {
			// Ajoute v comme voisin dans la direction "direction" de la courante
			// (case courante: this)
			voisinsParDir.put(d, v);
			// Calculer / trouver la direction opposée à "direction" : "oppose"
			Direction oppose = d.getOppose();
			// ajoute this comme voisin de v dans la direction "oppose"
			v.setVoisin(oppose, this);
		}
	}
	public Case getVoisin(Direction d) {
		return voisinsParDir.get(d);
	}
	
	public Iterable<Direction> getDirVoisins(){
		return voisinsParDir.keySet();
	}
	
	public boolean hasNext(Direction d) { return voisinsParDir.containsKey(d) ; }
	
	public String reprCaseEtVoisinage() {
		StringBuffer sb = new StringBuffer();
		sb.append(emplacement).append("[");
		for(Direction d : Direction.values()) {
			if(voisinsParDir.containsKey(d)) {
				sb.append(d).append(":").append(voisinsParDir.get(d));
			}
		}
		sb.append("]");
		return sb.toString();		
	}

	private void ajouteDirEtVoisin(StringBuffer sb, Direction d) {
		sb.append(d).append(":").append(voisinsParDir.get(d));
	}
	
	public String reprCaseEtEnvironnement() {
		StringBuffer sb = new StringBuffer();
		sb.append(emplacement).append("[").append("\n");
		for(Direction d : Direction.ordreLecture) {
			if(voisinsParDir.containsKey(d)) {
				ajouteDirEtVoisin(sb, d);
			}
			else {
				sb.append("             ");
			}
			switch(d) {
			case NE: case E: case SE:
				sb.append("\n");
				break;
			case O:
				sb.append(", ").append(emplacement).append("          ");
			default: sb.append(", ");
			}
			
		}
		sb.append("]");
		return sb.toString();		
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) { return true; }
		
		if(o instanceof Case) {
			return this.index == ((Case)o).index ;
		}
		else {
			return false;
		}
	}
	
	@Override
	public int compareTo(Case o) {
		return this.index - o.index ;
	}
	
	public String getEmplacement() {
		return emplacement;
	}
}
