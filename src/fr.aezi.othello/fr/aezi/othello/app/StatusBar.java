package fr.aezi.othello.app;

import java.util.Map;
import java.util.TreeMap;

import fr.aezi.othello.modele.Couleur;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class StatusBar extends Group {
	
	private Box colorBox ;
	private Map<Couleur, Color> colors = new TreeMap<>();
	private PhongMaterial barMaterial = new PhongMaterial();
	StatusBar(){
		colors.put(Couleur.NOIR, Color.BLACK);
		colors.put(Couleur.BLANC, Color.WHITE);
		init();
	}
	

	private void init() {
		colorBox = new Box(400,100,50);
		colorBox.setTranslateX(400);
		colorBox.setTranslateY(900);
		colorBox.setMaterial(barMaterial);
		getChildren().add(colorBox);

	}
	
	public void setPlayer(Couleur c) {
		barMaterial.setDiffuseColor(colors.get(c));
	}
	

}
