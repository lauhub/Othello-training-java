package fr.aezi.othello.app;

import java.util.Map;
import java.util.TreeMap;

import fr.aezi.othello.modele.Couleur;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;

public class StatusBar extends Group {
	private double width, height, thickness;
	
	
	private Box colorBox ;
	private Map<Couleur, Color> colors = new TreeMap<>();
	private PhongMaterial barMaterial = new PhongMaterial();
	
	private Text nextPlayerText ;
	private Text supplementaryInformationText ;
	
	StatusBar(double width, double height, double thickness){
		colors.put(Couleur.NOIR, Color.BLACK);
		colors.put(Couleur.BLANC, Color.WHITE);
		this.width = width;
		this.height = height;
		this.thickness = thickness;
		init();
	}
	

	private void init() {
		colorBox = new Box(width,height,thickness);
		colorBox.setMaterial(barMaterial);
		getChildren().add(colorBox);
		
		nextPlayerText = new Text("Noir doit jouer");
		getChildren().add(nextPlayerText);
		nextPlayerText.setTranslateZ(-thickness - 2.0);
		nextPlayerText.setTranslateX(-0.45 * width);
		
		supplementaryInformationText = new Text("Thank you for playing this demo game !");
		getChildren().add(supplementaryInformationText);
		supplementaryInformationText.setTranslateZ(-thickness - 2.0);
		supplementaryInformationText.setTranslateX(-0.45 * width);
		supplementaryInformationText.setTranslateY(25.0);

		setPlayer(Couleur.NOIR);
	}
	
	public void setPlayer(Couleur c) {
		barMaterial.setDiffuseColor(colors.get(c));
		if(c == Couleur.NOIR) {
			nextPlayerText.setStroke(Color.WHITE);
			supplementaryInformationText.setStroke(Color.WHITE);
			nextPlayerText.setText("Black's turn");
		}
		else {
			nextPlayerText.setStroke(Color.BLACK);
			supplementaryInformationText.setStroke(Color.BLACK);
			nextPlayerText.setText("White's turn");
		}
	}
	public void setMainText(String text) {
		nextPlayerText.setText(text);
	}
	public void setInformation(String text) {
		supplementaryInformationText.setText(text);
	}

}
