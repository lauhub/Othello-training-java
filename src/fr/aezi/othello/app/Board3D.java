package fr.aezi.othello.app;

import javafx.scene.DepthTest;
import javafx.scene.Group;
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
		Box lineBox = new Box(4,800,2);
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
	
}
