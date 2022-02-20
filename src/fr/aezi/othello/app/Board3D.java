package fr.aezi.othello.app;

import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

public class Board3D extends Group{
	double boardWidth = 800;
	double boardHeight = 800;
	public Board3D(double width, double height) {
		this.boardWidth = width;
		this.boardHeight = height;
		setDepthTest(DepthTest.ENABLE);
		init();
	}

	private void init() {
		Box mainBoard = new Box(boardWidth, boardHeight, 2);
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Color.GREEN);
		material.setSpecularColor(Color.DARKOLIVEGREEN);
		mainBoard.setMaterial(material);
		
		this.getChildren().add(mainBoard);
		
		int i = 0;
		double step = 800 / 8;
		while(i < 800){
			createLine(i,0, false);
			createLine(0,i, true);
			i += step;
		}
	}
	
	
	private void createLine(double x, double y, boolean vertical) {
		Box lineBox = new Box(4, vertical?boardHeight:boardWidth, 2);
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Color.BLACK);
		lineBox.setMaterial(material);
		if(vertical) {
			lineBox.setRotationAxis(Rotate.Z_AXIS);
			lineBox.setRotate(90);
			lineBox.setTranslateY(y - 400);
		}
		else {
			lineBox.setTranslateX(x- 400);
		}
		lineBox.setTranslateZ(-0.5);
		this.getChildren().add(lineBox);
	}
	public Node createSquare(double x, double y) {
		Box box = new Box(boardWidth / 8 - 4, boardHeight / 8 -4 , 2);
		PhongMaterial material = new PhongMaterial();
		double colorStrength = 0.2;
		Color playableColor = new Color(colorStrength+0.2, colorStrength, colorStrength, 0.01);
		Color nonPlayableColor = new Color(colorStrength, colorStrength, colorStrength, 0.00);
		material.setDiffuseColor(playableColor);
		box.setMaterial(material);
		box.setTranslateX(x - 400);
		box.setTranslateY(y - 400);
		this.getChildren().add(box);
		box.setTranslateZ(-0.001);
		box.setAccessibleHelp("playable");
		javafx.event.EventHandler<MouseEvent> eh = (e)-> {
			if(box.getAccessibleHelp().equals("playable")) {
				box.setAccessibleHelp("non-playable");
				material.setDiffuseColor(nonPlayableColor);
			}
			else {
				box.setAccessibleHelp("playable");
				material.setDiffuseColor(playableColor);
				
			}
		};
		//box.addEventHandler(MouseEvent.MOUSE_CLICKED, eh);
		return box;
	}
	
}
