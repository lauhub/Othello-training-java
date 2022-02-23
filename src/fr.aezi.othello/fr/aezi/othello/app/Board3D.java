package fr.aezi.othello.app;

import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;

public class Board3D extends Group{
	double boardWidth = 800;
	double boardHeight = 800;
	private static final double colorStrength = 0.2;
	private static final double LINE_THICKNESS = 4;

	public static final Color PLAYABLE_COLOR = new Color(colorStrength+0.2, colorStrength, colorStrength, 0.0001);
	public static final Color NON_PLAYABLE_COLOR = new Color(colorStrength, colorStrength, colorStrength, 0.00);

	public Board3D(double width, double height) {
		this.boardWidth = width;
		this.boardHeight = height;
		setDepthTest(DepthTest.ENABLE);
		init();
	}

	private void init() {
		Box mainBoard = new Box(boardWidth + LINE_THICKNESS, boardHeight + LINE_THICKNESS, 2);
		mainBoard.setTranslateX(-0.5);
		mainBoard.setTranslateY(-LINE_THICKNESS / 4);
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Color.GREEN);
		material.setSpecularColor(Color.DARKOLIVEGREEN);
		mainBoard.setMaterial(material);
		
		this.getChildren().add(mainBoard);
		
		int i = 0;
		double step = 800 / 8;
		while(i < (800 + LINE_THICKNESS)){
			createLine(i,0, false);
			createLine(0,i, true);
			i += step;
		}
	}
	
	
	private void createLine(double x, double y, boolean vertical) {
		Box lineBox = new Box(LINE_THICKNESS, vertical?boardHeight:boardWidth, 2);
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
	public Shape3D createSquare(double x, double y) {
		//Box invisibleHandler = new Box(boardWidth / 8 - 4, boardHeight / 8 -4 , 2);
		//invisibleHandler.setMaterial(new PhongMaterial(NON_PLAYABLE_COLOR));
		Cylinder box = new Cylinder(boardWidth / 16 - 4, 4);
		box.setRotationAxis(Rotate.X_AXIS);
		box.setRotate(90);
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(PLAYABLE_COLOR);
		box.setMaterial(material);
		box.setTranslateX(x - 400);
		box.setTranslateY(y - 400);
		this.getChildren().add(box);
		box.setTranslateZ(-0.03);
		box.setAccessibleHelp("playable");

		return box;
	}
}
