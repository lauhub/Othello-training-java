package fr.aezi.othello.modele;

public enum Direction {
	NO("Nord-Ouest"), N("Nord"), NE("Nord-Est"),
	 E("Est"), SE("Sud-Est"), S("Sud"), SO("Sud-Ouest"),
	 O("Ouest");
	/*
	NO, N, NE,
	 O,    E,
	SO, S, SE;
	*/
	
	static {
		NO.setOppose(SE);
		SE.setOppose(NO);
		N.setOppose(S);
		S.setOppose(N);
		NE.setOppose(SO);
		SO.setOppose(NE);
		E.setOppose(O);
		O.setOppose(E);
	}
	
	private String representation ;
	private Direction oppose;
	//private int incrementX, incrementY;
	public static final Direction[] ordreLecture = new Direction[] {
			NO, N, NE, O, E, SO, S, SE
	};
	
	Direction(String representation){
		this.representation = representation;
	}
	
	private void setOppose(Direction d) {
		this.oppose = d;
	}
	
	public Direction getOppose() { return oppose ; }
	
	public String getRepresentation() {return representation;}
	
}
